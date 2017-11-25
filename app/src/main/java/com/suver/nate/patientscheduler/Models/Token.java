package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class Token {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String error_description;
    private Integer retries;
    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    public String getErrorDescription() {
        if (error_description==null) return "";
        return error_description;
    }

    public void setErrorDescription(String error_description) {
        this.error_description = error_description;
    }

    public Integer getRetries() {
        return retries;
    }

    public void incrementRetries() {
        this.retries++;
    }
}
