package com.beaconstrategists.oauth2client.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class OAuth2ClientService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final RestClient restClient;

    public OAuth2ClientService(OAuth2AuthorizedClientManager authorizedClientManager, RestClient restClient) {
        this.authorizedClientManager = authorizedClientManager;
        this.restClient = restClient;
    }

    public String getAccessToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("my-client-registration")
                .principal("client") // Principal name (not important for client_credentials)
                .build();

//        String clientId = authorizeRequest.getAuthorizedClient().getClientRegistration().getClientId();
//        String clientSecret = authorizeRequest.getAuthorizedClient().getClientRegistration().getClientSecret();
//        System.out.println("Sending clientId: " + clientId);
//        System.out.println("Sending clientSecret: " + clientSecret);

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
            return authorizedClient.getAccessToken().getTokenValue();
        } else {
            throw new IllegalStateException("Unable to obtain access token");
        }
    }

    public String getTacCases() {
        String accessToken = getAccessToken();
        try {
            return restClient.get()
                    .uri("/tacCases")
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Failed to retrieve TAC cases: " + e.getMessage(), e);
        }
    }

}

