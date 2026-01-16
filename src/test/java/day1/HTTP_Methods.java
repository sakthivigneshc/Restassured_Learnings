package day1;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class HTTP_Methods {

	// @Test
	public void getMethod() {

		given().when().get("https://restful-booker.herokuapp.com/booking").then().statusCode(200).log().body();

	}

	@Test
	public void postMethod() {
		BookingDates dates = new BookingDates();
		dates.setCheckin("2026-01-15");
		dates.setCheckout("2026-01-20");

		// Main Booking POJO
		Booking booking = new Booking();
		booking.setFirstname("Sakthi");
		booking.setLastname("Vignesh");
		booking.setTotalprice(500);
		booking.setDepositpaid(true);
		booking.setBookingdates(dates);
		booking.setAdditionalneeds("Breakfast");
		
		Response response = given().baseUri("https://restful-booker.herokuapp.com").basePath("/booking")
		.contentType(ContentType.JSON)
		.body(booking).when().post();
		
		System.out.println(response.prettyPrint());	
		
		String firstName =	response.jsonPath().get("booking.firstname").toString();
		Assert.assertEquals(firstName, "Sakthi");
		
		String lastName =	response.jsonPath().get("booking.lastname").toString();
		Assert.assertEquals(lastName, "Vignesh");
		
		String totalPrice = response.jsonPath().getString("booking.totalprice").toString();
		Assert.assertEquals(totalPrice, "500");
		
		String checkIn_Value = response.jsonPath().get("booking.bookingdates.checkin").toString();
		Assert.assertEquals(checkIn_Value, "2026-01-15");

	}
	


		
			

}
