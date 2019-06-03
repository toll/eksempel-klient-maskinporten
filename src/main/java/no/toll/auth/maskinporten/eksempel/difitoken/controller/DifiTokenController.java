package no.toll.auth.maskinporten.eksempel.difitoken.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.toll.auth.maskinporten.eksempel.difitoken.client.DifiTokenClient;
import no.toll.auth.maskinporten.eksempel.difitoken.model.DifiResponse;

@RestController
public class DifiTokenController {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final DifiJwtCreator difiJWTCreator;
  private final DifiTokenClient difiTokenClient;

  public DifiTokenController(final DifiJwtCreator difiJWTCreator, final DifiTokenClient difiTokenClient) {
    this.difiJWTCreator = difiJWTCreator;
    this.difiTokenClient = difiTokenClient;
  }

  @GetMapping(value = "/request-jwt")
  public String requestJwt() {
    return difiJWTCreator.createRequestJwt();
  }

  @GetMapping(value = "/difi-access-token")
  public DifiResponse difiAccessToken() throws IOException {
    final String difiResponse = difiTokenClient.performTokenRequest(requestJwt());
    return OBJECT_MAPPER.readValue(difiResponse, DifiResponse.class);
  }
}
