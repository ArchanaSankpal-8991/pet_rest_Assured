package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() 
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().cellPhone());
       // userPayload.setUserStatus(faker.hashCode());
	
        logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("***** Creating user *******");
		Response response=UserEndPoint.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	
	    logger.info("******** User is created ********");
	}

	@Test(priority=2)
	public void testGetUserByName()
	{
		
		logger.info("***** Reading user *******");
		Response response=UserEndPoint.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	    
		logger.info("***** Read user *******");
	}
	
	@Test(priority=3)
	public void testUpdateUser()
	
	{
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
		
        Response response=UserEndPoint.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	
}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		Response response=UserEndPoint.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
}
