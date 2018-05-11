package br.com.avelar.provider.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailEndpoint {

    @Autowired
    private JwtTokenStore tokenStore;

    @RequestMapping("/oauth/user")
    public ResponseEntity<UserDetail> getUser(@RequestHeader("Authorization") String bearerToken) {
        if(!validateToken(bearerToken)) {
            return returnUnauthorizedResponse();
        }

        OAuth2AccessToken token = findToken(bearerToken);
        if(token == null) {
            return returnUnauthorizedResponse();
        }

        return returnSuccessResponse(token);
    }

    private boolean validateToken(String token) {
        return token != null && !token.isEmpty();
    }

    private ResponseEntity<UserDetail> returnSuccessResponse(OAuth2AccessToken token) {
        OAuth2Authentication auth = tokenStore.readAuthentication(token);
        return ResponseEntity.ok(new UserDetail(auth.getName()));
    }
    
    private ResponseEntity<UserDetail> returnUnauthorizedResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private OAuth2AccessToken findToken(String bearerToken) {
        return tokenStore.readAccessToken(bearerToken.replace("Bearer ", "").trim());
    }

}
