package com.jlautomate.jlautomate_backend.dto;

public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresIn = 3600;
    private UserDto user;

    public static class UserDto {
        private Long id;
        private String email;
        private String[] roles;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String[] getRoles() { return roles; }
        public void setRoles(String[] roles) { this.roles = roles; }
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
