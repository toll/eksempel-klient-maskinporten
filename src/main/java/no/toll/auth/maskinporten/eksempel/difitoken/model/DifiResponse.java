package no.toll.auth.maskinporten.eksempel.difitoken.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DifiResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private long expiresIn;

  private String scope;

  public DifiResponse() {}

  public DifiResponse(final String accessToken, final long expiresIn, final String scope) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
    this.scope = scope;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public String getScope() {
    return scope;
  }
}
