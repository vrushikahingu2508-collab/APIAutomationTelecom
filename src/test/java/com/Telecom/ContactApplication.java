package com.Telecom;

//given
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import POJO.ContactDetails;
import POJO.User;
import io.restassured.response.Response;


public class ContactApplication {
	
	String token1 = "";
	String token2 = "";
	String contactId = "";
	
	//dynamic email
	String userEmail = "bina"+System.currentTimeMillis()+"@gmail.com";
	  
  @Test (priority = 1)
  public void testAddNewUser() 
  {
	  
	
	  
	  //payload
	  HashMap<String,Object> data = new HashMap<String,Object>();
	  
	  data.put("firstName", "Bina");
	  data.put("lastName", "Gohel");
	  data.put("email", userEmail);
	  data.put("password", "bina@123");
	 
	  Response res=given()
			  		.header("Content-Type","application/json")
			  		.body(data)
			  			.when().post("https://thinking-tester-contact-list.herokuapp.com/users");
	  
	  //response
	  System.out.println("----Add New User----");
	  res.then().log().body();
	 int statusCode = res.getStatusCode();
	 String statusMsg = res.getStatusLine();
	
	 Assert.assertEquals(statusCode , 201,"Test Fail: Status Code not matched!!!");
	 System.out.println("Test Pass: status code match");
	 
	 Assert.assertTrue(statusMsg.contains("Created"),"Test Fail: "+statusMsg);
	 System.out.println("Test Pass: Created");
	  
	  //get Token
	  token1 = res.jsonPath().getString("token");
	  
	  System.out.println("Token 1 : "+token1);
	  
  }
  
  @Test (priority = 2)
  public void testGetUserProfile() 
  {
	  Response res = given()
	  	.header("Content-Type","application/json")
	  	.header("Authorization", "Bearer " + token1)
	  	.when().get("https://thinking-tester-contact-list.herokuapp.com/users/me");
	  
	  System.out.println("----User Profile----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
		 String statusMsg = res.getStatusLine();
		
		 Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
		 System.out.println("Test Pass: status code match");
		 
		 Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
		 System.out.println("Test Pass: Ok");
		  
	  
  }
  
  @Test(priority = 3)
  public void testUpdateUser()
  {
	  User user = new User();
	  user.setFirstName("Bina");
	  user.setLastName("Sharma");
	  user.setEmail(userEmail);
	  user.setPassword("BinaShar@123");
	  
	  Response res = given()
			  .header("Content-Type","application/json")
			  .header("Authorization","Bearer "+token1)
			  .body(user)
	  			.when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me");
	  
	  System.out.println("----Update User----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
	  String statusMsg = res.getStatusLine();
	  
	  
	  Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	  System.out.println("Test Pass: status code match");
		 
	  Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	  System.out.println("Test Pass: Ok");
	  	
  }
  
  @Test (priority = 4)
  public void testLoginUser()
  {
	  
	  //JSONObject is class
	  JSONObject obj=new JSONObject();
	  obj.put("email",userEmail);
	  obj.put("password","BinaShar@123");
	  
	  
	  
	  Response res = given()
	          .header("Content-Type","application/json")
	          .body(obj.toString())
	          	.when().post("https://thinking-tester-contact-list.herokuapp.com/users/login");
	  
	  //response
	  System.out.println("----Login User----");
	  res.then().log().body();
	 int statusCode = res.getStatusCode();
	 String statusMsg = res.getStatusLine();
	
	 Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	 System.out.println("Test Pass: status code match");
	 
	 Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	 System.out.println("Test Pass: Created");
	  
	  //get Token
	  token2 = res.jsonPath().getString("token");
	  
	  System.out.println("Token 2 : "+token2);         
	          
  }
  
  @Test (priority =  5)
  public void testAddContact() 
  {
	  
	  //payload 
	  ContactDetails contact = new ContactDetails();
	  contact.setFirstName("Yash");
	  contact.setLastName("Kalani");
	  contact.setEmail("yash@gmail.com");
	  contact.setBirthdate("2001-11-01");
	  contact.setPhone("9835267019");
	  contact.setStreet1("120 ring St");
	  contact.setStreet2("Apartment V");
	  contact.setCity("Rajkot");
	  contact.setStateProvince("GJ");
	  contact.setPostalCode("356779");
	  contact.setCountry("India");
	  
	  
	  	Response res = given()
	  				.header("Content-Type","application/json")
	  				.header("Authorization","Bearer "+token2)
	  				.body(contact)
	  					.when().post("https://thinking-tester-contact-list.herokuapp.com/contacts");
	  	
	  	 System.out.println("----Add Contact To Logged User----");
		  res.then().log().body();
		  
		  int statusCode = res.getStatusCode();
		  String statusMsg = res.getStatusLine();
		  
		  
		  Assert.assertEquals(statusCode , 201,"Test Fail: Status Code not matched!!!");
		  System.out.println("Test Pass: status code match");
			 
		  Assert.assertTrue(statusMsg.contains("Created"),"Test Fail: "+statusMsg);
		  System.out.println("Test Pass: Created");
		  
		  contactId = res.jsonPath().getString("_id");
		  System.out.println("ContactId: " +contactId);
			
	  				
  }
  
  @Test (priority = 6)
  public void testGetContactList()
  {
	  Response res = given()
			  			.header("Content-Type","application/json")
			  			.header("Authorization","Bearer "+token2)
			  			.when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");
	  
	  System.out.println("----Get Contact List----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
	  String statusMsg = res.getStatusLine();
	  
	  
	  Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	  System.out.println("Test Pass: status code match");
		 
	  Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	  System.out.println("Test Pass: Ok");
	  
  }
  
  @Test (priority = 7)
  public void testGetContactAsPerId()
  {
	  Response res = given()
			  			.header("Content-Type","application/json")
			  			.header("Authorization","Bearer "+token2)
			  			 .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts/"+contactId);
	  
	  System.out.println("----Get Contact As Per Id----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
	  String statusMsg = res.getStatusLine();
	  
	  
	  Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	  System.out.println("Test Pass: status code match");
		 
	  Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	  System.out.println("Test Pass: Ok");
	  
  }
  
  @Test (priority = 8)
  public void testUpadteContact()
  {
	  ContactDetails contact = new ContactDetails();
	  contact.setFirstName("Viraj");
	  contact.setLastName("Desai");
	  contact.setEmail("viraj@gmail.com");
	  contact.setBirthdate("2001-01-01");
	  contact.setPhone("9835267019");
	  contact.setStreet1("45 ring Station");
	  contact.setStreet2("Apartment Y");
	  contact.setCity("Thunder Bay");
	  contact.setStateProvince("QA");
	  contact.setPostalCode("359979");
	  contact.setCountry("Canada");
	  
	  
	  Response res = given()
			  			.header("Content-Type","application/json")
			  			.header("Authorization","Bearer "+token2)
			  			.body(contact)
			  				.when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+contactId);
	  System.out.println("------Upadte Conatct-----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
	  String statusMsg = res.getStatusLine();

	  Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	  System.out.println("Test Pass: status code match");
		 
	  Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	  System.out.println("Test Pass: Ok");
	  
	  String email = res.jsonPath().getString("email");
		 
	  Assert.assertTrue(email.contains("viraj@gmail"),"Test Fail: "+email);
	  System.out.println("Test Pass: valid email");
  
  }
  
  
  @Test (priority = 9)
  public void testPartialUpdateContact()
  {
	 // ContactDetails contact = new ContactDetails();
	 //contact.setFirstName("Rohit");
	  //payload
	  HashMap<String,Object> data = new HashMap<String,Object>();
	  
	  data.put("firstName", "Rohit");
	  
	  Response res = given()
			  			.header("Content-Type","application/json")
			  			.header("Authorization","Bearer "+token2)
			  			.body(data)
			  			 .when().patch("https://thinking-tester-contact-list.herokuapp.com/contacts/"+contactId);
	  
	  System.out.println("-----Partial Update Contact-----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
	  String statusMsg = res.getStatusLine();

	  Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	  System.out.println("Test Pass: status code match");
		 
	  Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	  System.out.println("Test Pass: Ok");
	  
	  String fname = res.jsonPath().getString("firstName");
		 
	  Assert.assertTrue(fname.contains("Rohit"),"Test Fail: "+fname);
	  System.out.println("Test Pass: valid firstname");
	  
  }
  
  @Test (priority = 10)
  public void testLogOutUser()
  {
	  Response res = given()
			  			.header("Content-Type","application/json")
			  			.header("Authorization","Bearer "+token2)
			  				.when().post("https://thinking-tester-contact-list.herokuapp.com/users/logout");
	  
	  System.out.println("-----Logout User-----");
	  res.then().log().body();
	  
	  int statusCode = res.getStatusCode();
	  String statusMsg = res.getStatusLine();

	  Assert.assertEquals(statusCode , 200,"Test Fail: Status Code not matched!!!");
	  System.out.println("Test Pass: status code match");
		 
	  Assert.assertTrue(statusMsg.contains("OK"),"Test Fail: "+statusMsg);
	  System.out.println("Test Pass: Ok");
  }
  
  
  
  
}
