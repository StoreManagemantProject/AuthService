package org.example.controllers;

import org.example.configuration.JwtTokenProvider;
import org.example.dto.AdminDTO;
import org.example.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.StoreManagerDTO;
import org.example.dto.StoreOwnerDTO;
import org.example.services.AdminRequester;
import org.example.util.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminRequester adminRequester;

    @Autowired
    private JwtTokenProvider provider;

    @Operation(
            summary = "Authenticate Admin Master",
            description = "Login with email and password, returns a JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"token\": \"jwt_token_here\" }"))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"Invalid credentials\" }"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"error\": \"An unexpected error occurred\" }"))
            )
    })
    @PostMapping("/admin-master/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        try {
            AdminDTO admin = adminRequester.getAdminByEmail(login.getEmail());
            if (!Authorization.isAuthorized(login.getPassword(), admin.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            String token = provider.generateAdminToken(admin);
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }
    }
    @PostMapping("/costumer/login")
    public ResponseEntity<?> costumerLogin(@RequestBody LoginDTO login) {
        try{
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }catch (Exception e){
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PostMapping("/store-owner/login")
    public ResponseEntity<?> storeOwnerLogin(@RequestBody LoginDTO login) {
        try {
            StoreOwnerDTO admin = adminRequester.getStoreOwnerByEmail(login.getEmail());
            if (!Authorization.isAuthorized(login.getPassword(), admin.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            String token = provider.generateStoreOwnerToken(admin);
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PostMapping("/store-manager/login")
    public ResponseEntity<?> storeManagerLogin(@RequestBody LoginDTO login) {
        try{
            StoreManagerDTO storeManager = adminRequester.getStoreManagerByEmail(login.getEmail());
            if(!Authorization.isAuthorized(login.getPassword(), storeManager.getPassword())){
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            String token = provider.generateStoreManagerToken(storeManager);
            return ResponseEntity.ok().body(Map.of("token", token));
        }catch (IllegalArgumentException e ){
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }
    }
}

