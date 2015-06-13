package com.xname.appname;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.randomchat.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@IntegrationTest("server.port=0") 
@WebAppConfiguration
@ActiveProfiles("integrationtest")
public class BaseIntegrationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseIntegrationTest.class);
	
	private static final String baseURI = "http://localhost";
	protected Printer printToConsole = new Printer();
	
    @Value("${local.server.port}")   // 6
    protected int port;
	
    @Autowired
    protected ObjectMapper objectMapper;
    
	@Before
	public void _setUp() {
		RestAssured.baseURI = baseURI;
		RestAssured.port = port;
	}
	
	protected String getAbsoluteUrl(String relativeUrl) {
		return String.format("%s:%d%s", baseURI, port, relativeUrl);
	}
	
	protected String api(String relativeUrl) {
		return String.format("%s:%d/v0/api%s", baseURI, port, relativeUrl);
	}
	
	protected RequestSpecification login(String username, String password) {
		Response response = 
			given()
			.body(asJson("username", username, "password", password))
			.post(api("/users/login"));
		
		String accessToken = response.body().jsonPath().get("accessToken");
		
		RequestSpecification spec = 
				given().header("Authorization", String.format("Bearer %s", accessToken));
		
		return spec;
	}

	private String asJson(String key, String firstValue, String ... keyValuePairs) {
		if (keyValuePairs != null && keyValuePairs.length % 2 != 0) {
			throw new IllegalArgumentException("Odd number of key value pairs");
		}
		Map<String, String> params = new HashMap<>();
		params.put(key, firstValue);
		for(int i=0; i<keyValuePairs.length; i+=2) {
			params.put(keyValuePairs[i], keyValuePairs[i+1]);
		}
		
		try {
			return objectMapper.writeValueAsString(params);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}


class Printer extends BaseMatcher<Object> {

	@Override
	public boolean matches(Object item) {
		System.out.println("PRINTER: " + item);
		return true;
	}

	@Override
	public void describeTo(Description description) {
		
	}
	
}