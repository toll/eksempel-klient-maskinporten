package no.toll.auth.maskinporten.eksempel.config;

import java.time.Duration;

import no.toll.auth.maskinporten.eksempel.difitoken.controller.DifiJwtCreator;
import no.toll.auth.maskinporten.eksempel.difitoken.client.DifiTokenClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class BaseConfig {

  @Bean
  public DifiJwtCreator difiJWTCreator(
      @Value("${sign.certificate.b64}") final String certificate,
      @Value("${sign.private.key.pkcs8.b64}") final String privateKey,
      @Value("${difi.token.request.audience.url}") final String difiTokenAudienceUrl,
      @Value("${difi.token.request.expiration.seconds}") final long awtExpirationSeconds) {
    return new DifiJwtCreator(
        certificate, privateKey, difiTokenAudienceUrl, Duration.ofSeconds(awtExpirationSeconds));
  }

  @Bean
  public DifiTokenClient difiAccessPoint(
      @Value("${difi.token.request.api.gw.url}") final String difiTokenRequestUrl) {
    return new DifiTokenClient(difiTokenRequestUrl);
  }
}
