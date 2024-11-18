package com.beaconstrategists.oauth2client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2ClientConfiguration {

    @Value("${CLIENT_ID:client-id}")
    private String clientId;

    @Value("${CLIENT_SECRET:client-pwd}")
    private String clientSecret;

    @Value("${CLIENT_NAME:client-name}")
    private String clientName;

    @Value("${CLIENT_SCOPE:read.cases,write.cases}")
    private String clientScope;

    @Value("${AUTH_BASE_URI:http://localhost:9000}")
    private String baseAuthUri;

    @Value("${TOKEN_EP_URI:/oauth2/token}")
    private String tokenUri;

    @Value("${AUTH_EP_URI:/oauth2/authorize}")
    private String authUri;

    @Value("${USER_INFO_EP_URI:/oauth2/userinfo}")
    private String userInfoUri;

    @Value("${JWK_SET_EP_URI:/oauth2/jwks}")
    private String jwksUri;

    @Bean(name = "customClientRegistrationRepository")
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("my-client-registration")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientName(clientName)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(clientScope.split(","))
//                .scope("read", "write")
                .tokenUri(baseAuthUri+tokenUri)
                .authorizationUri(baseAuthUri+authUri)
                .userInfoUri(baseAuthUri+userInfoUri)
                .jwkSetUri(baseAuthUri+jwksUri)
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean(name = "customOAuth2AuthorizedClientService")
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService);
    }
}
