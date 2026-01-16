package day1;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Map_APIS {
// Below is the actual URL
	
	// https://rahulshettyacademy.com/maps/api/place/add/json?key=qaclick123
	 static String placeIdValue; 
	@Test(priority = 1)
	public void createPlaceId() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		Response response = given()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body("{\n" +
	                  "  \"location\": {\n" +
	                  "    \"lat\": -38.383494,\n" +
	                  "    \"lng\": 33.427362\n" +
	                  "  },\n" +
	                  "  \"accuracy\": 50,\n" +
	                  "  \"name\": \"Dolakpur house\",\n" +
	                  "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
	                  "  \"address\": \"899, side layout, cohen 09\",\n" +
	                  "  \"types\": [\"shoe \",\"shop\"],\n" +
	                  "  \"website\": \"http://asdf.com\",\n" +
	                  "  \"language\": \"English-IN\"\n" +
	                  "}")
			.when()
			.post("maps/api/place/add/json");
		
			String statusValue = response.jsonPath().get("status");
			 placeIdValue = response.jsonPath().get("place_id");
			String idValue = response.jsonPath().get("id");
			int statuscodeValue = response.getStatusCode();
			Assert.assertEquals(statuscodeValue, 200);
			Assert.assertEquals(statusValue, "OK");
			Assert.assertNotNull(placeIdValue, "place_id is missing in response");
			Assert.assertEquals(placeIdValue.length(), 32, "place_id length mismatch");
			Assert.assertNotNull(idValue,"id is missing on response");
			Assert.assertEquals(idValue.length(),32,"Id is missing in response");
		
			/*	.pathParam("pathValue1", "maps")
			.pathParam("pathValue2", "api")
			.pathParam("pathValue3", "place")
			.pathParam("pathValue4", "add")
			.pathParam("pathValue5", "json")
			.queryParam("key", "qaclick123")*/
		
	/*	.when()
		.post("https://rahulshettyacademy.com/{pathValue1}/{pathValue2}/{pathValue3}/{pathValue4}/{pathValue5}");*/
		
	}
	
	
	@Test(priority = 2)
	public void getPlaceDetails() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		Response response =	given().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.when().get("maps/api/place/get/json?place_id=" + placeIdValue);		
		String  latitudeValue = response.jsonPath().get("location.latitude");
		String websiteValue	= response.jsonPath().get("website");
		String nameValue = response.jsonPath().getString("name");
		int statuscodeValue = response.getStatusCode();
		Assert.assertEquals(statuscodeValue, 200);
		Assert.assertEquals(latitudeValue, "-38.383494");
		Assert.assertEquals(websiteValue, "http://asdf.com");
		Assert.assertEquals(nameValue, "Dolakpur house");
		
		
	}
	
	@Test(priority = 3)
	public void updatePlaceDetails() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		Response response =	given().queryParam("key", "qaclick123")
				.header("Content-Type","application/json").
				body("{\r\n"
						+ "\"place_id\":\""+placeIdValue +"\",\r\n"
						+ "\"address\":\"Chottapur,Nepalism\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n"
						+ "}")
				.when().put("maps/api/place/update/json");
		String msgValue = response.jsonPath().getString("msg");
		int statuscodeValue = response.getStatusCode();
		Assert.assertEquals(statuscodeValue, 200);
		Assert.assertEquals(msgValue, "Address successfully updated");
		
	}
	
	@Test (priority = 4)
	public void readUpdatedPlaceDetails() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		Response response =	given().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.when().get("maps/api/place/get/json?place_id=" + placeIdValue);		
		response.prettyPrint();
		int statuscodeValue = response.getStatusCode();
		Assert.assertEquals(statuscodeValue, 200);
		String  latitudeValue = response.jsonPath().get("location.latitude");
		Assert.assertEquals(latitudeValue, "-38.383494");
		String addressValue = response.jsonPath().getString("address");
		Assert.assertEquals(addressValue, "Chottapur,Nepalism");
		
		
	}

	@Test(priority = 5)
	public void deletePlaceDetails() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		Response response =	given().queryParam("key", "qaclick123")
				.header("Content-Type","application/json").
				body("{"
						+"\"place_id\":\""+ placeIdValue +"\""
						 +"}")
				.when().delete("/maps/api/place/delete/json");
		int statuscodeValue = response.getStatusCode();
		Assert.assertEquals(statuscodeValue, 200);
		String msgValue = response.jsonPath().getString("status");
		Assert.assertEquals(msgValue, "OK");

	}	
	
}
