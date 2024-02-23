package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.CustomUser;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;


@Service
public class JwtSecurityService {

    private final WebApplicationContext webApplicationContext;

    @Value("${picture.jwtSecret}")
    private String jwtSecret;
    @Value("${picture.jwtRefreshSecret}")
    private String jwtRefreshSecret;
    @Value("${picture.jwtSecretExpiration}")
    private long jwtSecretExpiration;


    @Autowired
    public JwtSecurityService(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    private JWEHeader getHeader() {
        return new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
    }

    private Payload getPayload(String subject, String otherUserInfo) {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject) // идентификатор пользователя subject:
                .claim("other_user_info", otherUserInfo)
                .issueTime(new Date()) // время выдачи токена
                .expirationTime(new Date(System.currentTimeMillis() + jwtSecretExpiration))
                .build();

        return new Payload(claims.toJSONObject());
    }

    private void encrypt(JWEObject jweObject) throws JOSEException {
        jweObject.encrypt((JWEEncrypter) webApplicationContext.getBean("JWEEncrypter"));
//        jweObject.encrypt((JWEEncrypter) webApplicationContext.getBean("JWEEncrypter"));
    }

    public String generateToken(CustomUser user) throws JOSEException {
        JWEObject jweObject = new JWEObject(
                getHeader(),
                getPayload(
                        user.getUsername(),
                        "additional user info"
                ));

        encrypt(jweObject);
        return jweObject.serialize();
    }

    @Bean("JWEEncrypter")
    public JWEEncrypter encrypter() throws KeyLengthException {
        return new DirectEncrypter(jwtSecret.getBytes());
    }

    @Bean("JWTProcessor")
    public ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor() {
        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<>(jwtSecret.getBytes());
        JWEKeySelector<SimpleSecurityContext> jweKeySelector =
                new JWEDecryptionKeySelector<>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
        jwtProcessor.setJWEKeySelector(jweKeySelector);
        return jwtProcessor;
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private JWTClaimsSet extractClaims(String token) throws BadJOSEException, ParseException, JOSEException {
        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor =
                (ConfigurableJWTProcessor<SimpleSecurityContext>) webApplicationContext.getBean("JWTProcessor");
        return jwtProcessor.process(token, null);
    }

    public String getSubject(String token) throws BadJOSEException, ParseException, JOSEException {
        JWTClaimsSet claims = extractClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws BadJOSEException, ParseException, JOSEException {
        String userName = getSubject(token);
        Date expiration = extractClaims(token).getExpirationTime();
        return userName.equals(userDetails.getUsername()) && expiration.after(new Date());
    }

    public String getStringClaimsByKey(String token, String claimsKey) throws BadJOSEException, ParseException, JOSEException {
        JWTClaimsSet claims = extractClaims(token);
        return claims.getStringClaim(claimsKey);
    }

}
