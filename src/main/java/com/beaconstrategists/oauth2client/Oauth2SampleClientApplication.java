package com.beaconstrategists.oauth2client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;

//@SpringBootApplication(exclude = OAuth2ClientAutoConfiguration.class)
//@SpringBootApplication(exclude = { OAuth2ClientAutoConfiguration.class })
@SpringBootApplication
public class Oauth2SampleClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2SampleClientApplication.class, args);
	}

}
