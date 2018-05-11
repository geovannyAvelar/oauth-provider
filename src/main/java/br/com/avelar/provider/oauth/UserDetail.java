package br.com.avelar.provider.oauth;

import org.springframework.stereotype.Component;

@Component
public class UserDetail {

    private String username;

    public UserDetail() {

    }

    public UserDetail(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String toString() {
        return username != null ? username : "";
    }

}
