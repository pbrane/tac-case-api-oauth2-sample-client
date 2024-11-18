package com.beaconstrategists.oauth2client;

import com.beaconstrategists.oauth2client.service.OAuth2ClientService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellCommands {

    public static final String AUTHORIZATION = "Authorization";

    private final OAuth2ClientService clientService;


    public ShellCommands(OAuth2ClientService clientService) {
        this.clientService = clientService;
    }

    @ShellMethod(key = "getOAuth2Token", value = "Get Access Token")
    public String getOAuth2Token() {
        System.out.println("Get Access Token");
        return clientService.getAccessToken();
    }

    @ShellMethod(key = "getTacCases", value = "Get Access Token")
    public String getTacCases() {
        return clientService.getTacCases();
    }

}