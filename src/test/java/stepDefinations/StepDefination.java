package stepDefinations;
import static io.restassured.RestAssured.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StepDefination extends Utils{
	
	RequestSpecification res;
	Response response;
	ResponseSpecification resp;
	ResponseSpecification resspec; 
	static String place_id ;
	TestDataBuild data=new TestDataBuild();

	/*
	 * @Given("Add Place Payload") public void add_Place_Payload() throws
	 * IOException { // Write code here that turns the phrase above into concrete
	 * actions RestAssured.useRelaxedHTTPSValidation();
	 * 
	 * res=given().spec(requestSpecification()).body(data.addPlacePayLoad()); }
	 */
	@Given("Add Place Payload with  {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
        RestAssured.useRelaxedHTTPSValidation();
		
		res=given().spec(requestSpecification()).body(data.addPlacePayLoad(name,language,address));
	}

	/*
	 * @When("user calls {string} with Post http request") public void
	 * user_calls_with_Post_http_request(String resourse) { // Write code here that
	 * turns the phrase above into concrete actions
	 * RestAssured.useRelaxedHTTPSValidation(); APIResources resourceAPI =
	 * APIResources.valueOf(resourse);
	 * System.out.println(resourceAPI.getResource()); resp = new
	 * ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.
	 * JSON).build();
	 * response=res.when().post(resourceAPI.getResource()).then().spec(resp).extract
	 * ().response();
	 * o
	 * }
	 */
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
		//constructor will be called with value of resource which you pass
				APIResources resourceAPI=APIResources.valueOf(resource);
				System.out.println(resourceAPI.getResource());
				
				
				 resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
				if(method.equalsIgnoreCase("POST"))
				
					response =res.when().post(resourceAPI.getResource());
				
				else if(method.equalsIgnoreCase("GET"))
					response =res.when().get(resourceAPI.getResource());
	}

	@Then("the API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.useRelaxedHTTPSValidation();
		assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.useRelaxedHTTPSValidation();
		
		assertEquals(getJsonPath(response,keyValue	),Expectedvalue);
	}
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    
		place_id = getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
		
		
	}

@Given("DeletePlace Payload")
public void deleteplace_Payload() throws IOException {
    // Write code here that turns the phrase above into concrete actions
	 RestAssured.useRelaxedHTTPSValidation();
        res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id ));

}

}
