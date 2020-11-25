package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@Deleteplace")
	public void beforeScenario() throws IOException
	{
		//execute code only when place id is null
		//write a code that gives you place id
		StepDefination m=new StepDefination();
		if(StepDefination.place_id==null)
		{
		
		   m.add_Place_Payload_with("dhanuu", "sanskrit", "Beeravara");
		   m.user_calls_with_http_request("AddPlaceAPI", "POST");
		   m.verify_place_Id_created_maps_to_using("dhanu", "getPlaceAPI");
		}
	}
}
