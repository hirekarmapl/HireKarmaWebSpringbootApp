package com.hirekarma.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hirekarma.beans.Response;
@RestController
public class SecurityController {
	

	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private static String authorizationRequestBaseUri
      = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
      = new HashMap<String, String>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    

    @GetMapping("/loginSuccess")
    public String getLoginInfo(OAuth2AuthenticationToken authentication) {
    	if(authentication==null) {
    		System.out.println("authentication is null");
    	}else {
    		System.out.println("authentication.getAuthorizedClientRegistrationId():"+authentication.getAuthorizedClientRegistrationId() +" \nauthentication.getName() : "+authentication.getName());
            OAuth2AuthorizedClient client = authorizedClientService
              .loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), 
                  authentication.getName());
            String userInfoEndpointUri = client.getClientRegistration()
            		  .getProviderDetails().getUserInfoEndpoint().getUri();
    System.out.println(userInfoEndpointUri);
            		if(!userInfoEndpointUri.equals("")) {
            			  RestTemplate restTemplate = new RestTemplate();
            			  HttpHeaders headers = new HttpHeaders();
            			  headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
            		      .getTokenValue());
            			  
            			  HttpEntity<String> request = new HttpEntity<String>(headers);
            			  ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, request, Map.class);
            			  Map userAttributes = response.getBody();
            			  System.out.println(userAttributes.toString());
            			  System.out.println("name:"+userAttributes.get("name")+" accesstoken:"+client.getAccessToken()
            		      .getTokenValue());
            			  return userAttributes.get("name").toString();
            			   

            		}
    	}
    	return "failed";
       
    }
}
