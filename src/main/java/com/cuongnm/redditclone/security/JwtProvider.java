package com.cuongnm.redditclone.security;

import com.cuongnm.redditclone.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static java.util.Date.from;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception occurred while loading keystore", e);
        }

    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(1000*60*60*10)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
        }
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = getUsernameFromJwt(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));

    }

    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPrivateKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }


    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPrivateKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }

}
