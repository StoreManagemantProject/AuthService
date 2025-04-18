package org.example.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.example.dto.*;
import org.example.util.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    private final String issuer = "store_app";


    @Value("${PUBLIC_KEY}")
    private String publicKeyPath;

    @Value("${PRIVATE_KEY}")
    private String privateKeyPath;

    public String generateAdminToken(AdminDTO admin) throws Exception {
        logger.debug("Generating JWT for AdminMasterEntity with ID: {}", admin.getId());
        try {
            Algorithm algorithm = Algorithm.RSA256(
                    (RSAPublicKey) Authorization.getPublicKey(publicKeyPath),
                    (RSAPrivateKey) Authorization.getPrivateKey(privateKeyPath)
            );

            String token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("email", admin.getEmail())
                    .withArrayClaim("roles", admin.getRoles().stream()
                            .map(RoleDTO::getName).toArray(String[]::new))
                    .withClaim("type", "adminMaster-")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 8000 * 60 * 60))
                    .sign(algorithm);

            logger.debug("JWT generated successfully for AdminMasterEntity.");
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT for AdminMasterEntity", e);
            throw e;
        }
    }

    public String generateStoreOwnerToken(StoreOwnerDTO admin) throws Exception {
        logger.debug("Generating JWT for StoreAdminEntity with ID: {}", admin.getId());
        try {
            Algorithm algorithm = Algorithm.RSA256(
                    (RSAPublicKey) Authorization.getPublicKey(publicKeyPath),
                    (RSAPrivateKey) Authorization.getPrivateKey(privateKeyPath)
            );
            String token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("email", admin.getEmail())
                    .withArrayClaim("roles", admin.getRole().stream()
                            .map(RoleDTO::getName).toArray(String[]::new))
                    .withClaim("name", admin.getName())
                    .withClaim("id", admin.getId().toString())
                    .withClaim("type", "storeAdmin")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 8000 * 60 * 60))
                    .sign(algorithm);

            logger.debug("Jwt generated successfully for StoreAdminEntity.");

            return token;
        }catch (Exception e) {
            logger.error("Error generating JWT for StoreAdminEntity", e);
            throw e;
        }

    }

    public String generateStoreManagerToken(StoreManagerDTO manager) throws Exception {
        logger.debug("Generating JWT for StoreAdminEntity with ID: {}", manager.getId());
        try {
            Algorithm algorithm = Algorithm.RSA256(
                    (RSAPublicKey) Authorization.getPublicKey(publicKeyPath),
                    (RSAPrivateKey) Authorization.getPrivateKey(privateKeyPath)
            );
            String token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("email", manager.getEmail())
                    .withArrayClaim("roles", manager.getRole().stream()
                            .map(RoleDTO::getName).toArray(String[]::new))
                    .withClaim("name", manager.getName())
                    .withClaim("id", manager.getId().toString())
                    .withClaim("type", "manager")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 8000 * 60 * 60))
                    .sign(algorithm);

            logger.debug("Jwt generated successfully for StoreAdminEntity.");

            return token;
        }catch (Exception e) {
            logger.error("Error generating JWT for StoreAdminEntity", e);
            throw e;
        }
    }

}
