package com.example.orderservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.Food;
import com.example.orderservice.entity.Orders;
import com.example.orderservice.entity.User;
import com.example.orderservice.iservice.OrderServiceImpl;
import com.example.orderservice.models.CartResponce;
import com.example.orderservice.models.JWTData;
import com.example.orderservice.models.OrderResponce;
import com.example.orderservice.repository.CartRepository;
import com.example.orderservice.repository.FoodRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class OrderserviceApplicationTests {

	@Mock
	FoodRepository foodRepositoryMock;
	@Mock
	MongoTemplate mongoTemplateMock;
	@Mock
	OrderRepository orderRepositoryMock;
	@Mock
	CartRepository cartRepositoryMock;
	@Mock
	UserRepository userRepositoryMock;

	@InjectMocks
	OrderServiceImpl orderService;

	private Food food;
	private List<Food> emptyList;
	private List<Food> filledList;
	private JWTData JWTuser;
	private Optional<User> user;
	private Optional<Cart> filledCart;
	private String foodId,newFoodId;
	private List<Orders> orders;
	private List<String> orderIteams;
	private Food newFood;

	@BeforeEach
	void setup() {
		foodId = "1";
		newFoodId="2";
		food = new Food("1", "my food", "good food", "my resto", "7.0", "89", "my cusine", "my Home");
		newFood = new Food("2", "my new food", "good new food", "my new resto", "7.0", "89", "my new cusine",
				"my new Home");
		emptyList = new ArrayList<>();
		filledList = new ArrayList<Food>();
		orderIteams=List.of("1");
		filledList.add(food);
		JWTuser = new JWTData("myname", "passwd");
		user = Optional.of(new User("1", "myname", "passwd"));
		HashSet<String> hashSet = new HashSet<>();
		hashSet.add("1");
		orders=List.of(new Orders("1","1", hashSet,BigDecimal.valueOf(89)));
		filledCart = Optional.of(new Cart("1", hashSet));
	}

	@Test
	void addToCartNothingInCartAndInvalidUserId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		when(userRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		ResponseEntity<?> response=orderService.addToCart(JWTuser, foodId);
		if(!response.getBody().equals("invalid user id")){
			Assertions.fail();
		}

	}

	@Test

	void addToCartNothingInCartAndValidUserId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		when(userRepositoryMock.findById(JWTuser.getUserId())).thenReturn(user);
		when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.of(food));
		ResponseEntity<?> response=orderService.addToCart(JWTuser, foodId);
		if(!response.getBody().getClass().equals(Cart.class)){
			Assertions.fail();
		}

	}

	@Test
	void addToCartNothingInCartAndValidUserIdAndInvalidFoodId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		when(userRepositoryMock.findById(JWTuser.getUserId())).thenReturn(user);
		when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.empty());
		ResponseEntity<?> response=orderService.addToCart(JWTuser, foodId);
		if(!response.getBody().equals("invalid foodId")){
			Assertions.fail();
		}

	}

	@Test
	void addToCartSomethingInCartAndValidUserIdAndInvalidFoodId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.empty());
		ResponseEntity<?> response=orderService.addToCart(JWTuser, foodId);
		if(!response.getBody().equals("invalid foodId")){
			Assertions.fail();
		}

	}

	@Test
	void addToCartSomethingInCartAndValidUserId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.of(food));
		ResponseEntity<?> response=orderService.addToCart(JWTuser, foodId);
		if(!response.getBody().getClass().equals(Cart.class)){
			Assertions.fail();
		}
	}

	@Test
	void removeFromCartNothingInCartAndInvalidUserId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		when(userRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		ResponseEntity<?> response=orderService.removeFromCart(JWTuser, foodId);
		if(!response.getBody().equals("invalid user id")){
			Assertions.fail();
		}

	}

	@Test
	void removeFromCartNothingInCartAndValidUserId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		when(userRepositoryMock.findById(JWTuser.getUserId())).thenReturn(user);
		ResponseEntity<?> response=orderService.removeFromCart(JWTuser, foodId);
		if(!response.getBody().equals("empty cart")){
			Assertions.fail();
		}

	}

	@Test
	void removeFromCartSomethingInCartAndValidUserIdAndInvalidFoodId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.empty());
		ResponseEntity<?> response=orderService.removeFromCart(JWTuser, foodId);
		if(!response.getBody().equals("invalid foodId")){
			Assertions.fail();
		}

	}
	@Test
	void removeFromCartSomethingInCartAndValidUserIdAndValidFoodIdAndInCart(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.of(food));
		ResponseEntity<?> response=orderService.removeFromCart(JWTuser, foodId);
		if(!response.getBody().equals(food.getName()+" deleted succfully!!!")){
			Assertions.fail();
		}

	}
	@Test
	void removeFromCartSomethingInCartAndValidUserIdAndValidFoodIdAndNotInCart(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findById(newFoodId)).thenReturn(Optional.of(newFood));
		ResponseEntity<?> response=orderService.removeFromCart(JWTuser, newFoodId);
		if(!response.getBody().equals(newFood.getName()+" is not in cart")){
			Assertions.fail();
		}

	}
	@Test
	void getCartWithVaildId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findAllById(filledCart.get().getItems())).thenReturn(filledList);
		ResponseEntity<?> response=orderService.getCart(JWTuser);
		if(!response.getBody().getClass().equals(CartResponce.class)){
			Assertions.fail();
		}
	}
	@Test
	void getCartWithInvaildId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		ResponseEntity<?> response=orderService.getCart(JWTuser);
		if(!response.getBody().equals("invalid user id/cart in empty")){
			Assertions.fail();
		}
	}
	@Test
	void getOrdersWithInvaildId(){
		when(orderRepositoryMock.findAllByUserId(JWTuser.getUserId())).thenReturn(List.of());
		ResponseEntity<?> response=orderService.getOrders(JWTuser);
		if(!response.getBody().equals("invalid user id/no orders")){
			Assertions.fail();
		}
	}
	@Test
	void getOrdersWithVaildId(){
		when(orderRepositoryMock.findAllByUserId(JWTuser.getUserId())).thenReturn(orders);
		when(foodRepositoryMock.findAllById(filledCart.get().getItems())).thenReturn(filledList);
		ResponseEntity<?> response=orderService.getOrders(JWTuser);
		if(!response.getBody().getClass().equals(ArrayList.class)){
			Assertions.fail();
		}
	}
	@Test
	void placeOrderWithInvalidUserId(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(Optional.empty());
		ResponseEntity response= orderService.placeOrder(JWTuser, orders.get(0).getAmount());
		if(!response.getBody().equals("invalid user id/cart in empty")){
			Assertions.fail();
		}
	}
	@Test
	void placeOrderWithValidUserIdAndInvalidAmount(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findAllById(filledCart.get().getItems())).thenReturn(filledList);
		ResponseEntity response= orderService.placeOrder(JWTuser, BigDecimal.valueOf(21));
		if(!response.getBody().equals("invalid amount")){
			Assertions.fail();
		}
	}
	@Test
	void placeOrderWithValidUserIdAndValidAmount(){
		when(cartRepositoryMock.findById(JWTuser.getUserId())).thenReturn(filledCart);
		when(foodRepositoryMock.findAllById(filledCart.get().getItems())).thenReturn(filledList);
		ResponseEntity response= orderService.placeOrder(JWTuser, BigDecimal.valueOf(89.0));
		if(!response.getBody().equals("order placed successfully")){
			Assertions.fail();
		}
	}
}
