package com.example.orderservice.iservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.Food;
import com.example.orderservice.entity.Orders;
import com.example.orderservice.entity.User;
import com.example.orderservice.models.CartResponce;
import com.example.orderservice.models.JWTData;
import com.example.orderservice.models.OrderResponce;
import com.example.orderservice.repository.CartRepository;
import com.example.orderservice.repository.FoodRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.UserRepository;
import com.example.orderservice.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public ResponseEntity<?> addToCart(JWTData data, String foodId) {
        Optional<Cart> cart= cartRepository.findById(data.getUserId());
        if(cart.isPresent()){
            Optional<Food> food= foodRepository.findById(foodId);
            if(food.isPresent()){
                HashSet<String> cartList= cart.get().getItems();
                cartList.add(foodId);
                Criteria c = new Criteria().andOperator(Criteria.where("_id").is(data.getUserId()));
                Query query = new Query(c);
                Update update = new Update();
                update.set("items",cartList);
                mongoTemplate.upsert(query, update, Cart.class);
                return new ResponseEntity<>(cart.get(), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("invalid foodId", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user=userRepository.findById(data.getUserId());
        if(user.isPresent()){
            Optional<Food> food= foodRepository.findById(foodId);
            if(food.isPresent()){
                Cart newCart= new Cart(data.getUserId());
                newCart.getItems().add(foodId);
                cartRepository.save(newCart);
                return new ResponseEntity<>(newCart, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("invalid foodId", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("invalid user id", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> removeFromCart(JWTData data, String foodId) {
        Optional<Cart> cart= cartRepository.findById(data.getUserId());
        if(cart.isPresent()){
            Optional<Food> food= foodRepository.findById(foodId);
            if(food.isPresent()){
                HashSet<String> cartList= cart.get().getItems();
                if(cartList.contains(foodId)){
                    cartList.remove(foodId);
                    Criteria c = new Criteria().andOperator(Criteria.where("_id").is(data.getUserId()));
                    Query query = new Query(c);
                    Update update = new Update();
                    update.set("items",cartList);
                    mongoTemplate.upsert(query, update, Cart.class);
                    return new ResponseEntity<>(food.get().getName()+" deleted succfully!!!", HttpStatus.ACCEPTED);
                }
                return new ResponseEntity<>(food.get().getName()+" is not in cart", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("invalid foodId", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user=userRepository.findById(data.getUserId());
        if(user.isPresent()){
            return new ResponseEntity<>("empty cart", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("invalid user id", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> getCart(JWTData data) {
        Optional<Cart> cart= cartRepository.findById(data.getUserId());
        if(cart.isPresent()){
            List<Food> foods=foodRepository.findAllById(cart.get().getItems());
            double amount=foods.stream().mapToDouble(food-> Double.parseDouble(food.getPrice())).sum();
            
            return new ResponseEntity<>(new CartResponce(foods,BigDecimal.valueOf(amount)) , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("invalid user id/cart in empty", HttpStatus.BAD_REQUEST);
        
    }

    @Override
    public ResponseEntity<?> placeOrder(JWTData data, BigDecimal amount) {
        Optional<Cart> cart= cartRepository.findById(data.getUserId());
        if(cart.isPresent()){
            List<Food> foods=foodRepository.findAllById(cart.get().getItems());
            BigDecimal originlAmount=BigDecimal.valueOf(foods.stream().mapToDouble(food-> Double.parseDouble(food.getPrice())).sum());
            if(originlAmount.equals(amount)){
                orderRepository.save(new Orders(UUID.randomUUID().toString(),data.getUserId(),cart.get().getItems(),originlAmount));
                Query query = new Query().addCriteria(Criteria.where("_id").is(data.getUserId()));
                mongoTemplate.remove(query,Cart.class);
                return new ResponseEntity<>("order placed successfully" , HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("invalid amount" , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("invalid user id/cart in empty", HttpStatus.BAD_REQUEST);
        
    }

    @Override
    public ResponseEntity<?> getOrders(JWTData data) {
        List<Orders> orders=orderRepository.findAllByUserId(data.getUserId());
        System.out.println(orders.size());
        if(!orders.isEmpty()){
            List<OrderResponce> orderResponce =new ArrayList<>();
            orders.stream().parallel().forEach(order-> {
                List<Food> foods=foodRepository.findAllById(order.getItems());
                orderResponce.add(new OrderResponce(order.getOrderId(), foods, order.getAmount()));
            });
            
            return new ResponseEntity<>(orderResponce , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("invalid user id/no orders", HttpStatus.BAD_REQUEST);
    } 
    
    
}
