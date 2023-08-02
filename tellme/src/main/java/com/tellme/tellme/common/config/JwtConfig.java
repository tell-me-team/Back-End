package com.tellme.tellme.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;


import org.springframework.context.annotation.Bean;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

// TODO. 차후에 JWT RSA256 암호화 알고리즘으로 변환을 위한 class 보존
//@Configuration
//@RequiredArgsConstructor
public class JwtConfig {


    // rsa keypair 생성
    // @Bean
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // keypair 크기 설정. 여기선 2048비트
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    // rsa 객체 생성
    // @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey
                .Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    // JWKSource 생성(JSON Web Key source)
    // @Bean
    public JWKSource<SecurityContext> jwtSource(RSAKey rsaKey) {
        System.out.println(rsaKey);
        var jwkSet = new JWKSet(rsaKey);


        return (jwkSelector, context) -> jwkSelector.select(jwkSet);


    }

    // jwt 디코더 정의
    // @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }



    // jwt 인코더 정의
    // @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
