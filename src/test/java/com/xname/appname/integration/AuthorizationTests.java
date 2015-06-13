package com.xname.appname.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.xname.appname.BaseIntegrationTest;

public class AuthorizationTests extends BaseIntegrationTest {

	@Test
	public void createAnonymousUser() {
		Response response = 
			given()
			.when().post(api("/users/createAnonymousUser"));
		
		String accessToken = response.body().jsonPath().get("accessToken");
		
		response = 
			withAccessToken(accessToken)
			.post(api("/transactions"));
		
		System.out.println(response.getStatusCode());
		System.out.println(response.headers());
		System.out.println(response.asString());
		String transactionId = response.jsonPath().get("id");
		assertNotNull(transactionId);
	}

	private RequestSpecification withAccessToken(String accessToken) {
		return given().header("Authorization", String.format("Bearer %s", accessToken));
	}

}
