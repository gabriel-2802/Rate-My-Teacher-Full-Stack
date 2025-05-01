package app.demo.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String accessToken;
    private String tokenType;

    public AuthDTO(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer ";
    }
}
