package no.toll.auth.maskinporten.eksempel.difitoken.controller;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Clock;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * @see <a
 *     href="https://difi.github.io/idporten-oidc-dokumentasjon/oidc_auth_server-to-server-oauth2.html">
 *     Dokumentasjon hos difi</a>
 */
public class DifiJwtCreator {

  /** Base64 encoded certificate */
  private final String certificate;

  /** Private key in pkcs8 format base64 encoded */
  private final String privateKey;

  private final String difiTokenAudienceUrl;
  private final Duration expiration;

  public DifiJwtCreator(
          final String certificate, final String privateKey, final String difiTokenAudienceUrl, final Duration expiration) {
    this.certificate = certificate;
    this.privateKey = privateKey;
    this.difiTokenAudienceUrl = difiTokenAudienceUrl;
    this.expiration = expiration;
  }

  public String createRequestJwt() {
    final long now = Clock.systemUTC().millis();
    return Jwts.builder()
        .setHeaderParam(JwsHeader.X509_CERT_CHAIN, Collections.singletonList(certificate))
        .setAudience(difiTokenAudienceUrl)
        .setIssuer("oidc_tolletaten")
        .claim("iss_onbehalfof", "toll-onbehalfof")
        .claim("scope", "toll:ekspressfortolling")
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expiration.toMillis()))
        .signWith(SignatureAlgorithm.RS256, buildPrivateKey())
        .compact();
  }

  PrivateKey buildPrivateKey() {
    final byte[] pkcs8 = Base64.getDecoder().decode(privateKey.getBytes());
    final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8);
    try {
      return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    } catch (final InvalidKeySpecException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
