package loja.org.example.controller;

import org.example.configuration.JwtTokenProvider;
import org.example.controllers.AuthController;
import org.example.dto.AdminDTO;
import org.example.dto.LoginDTO;
import org.example.dto.StoreManagerDTO;
import org.example.dto.StoreOwnerDTO;
import org.example.services.AdminRequester;
import org.example.util.Authorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AdminRequester adminRequester;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Spy
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginDTO = new LoginDTO();
        loginDTO.setEmail("admin@example.com");
        loginDTO.setPassword("password");
    }

    @Test
    void loginAdmin_ShouldReturnToken_WhenCredentialsValid() throws Exception {
        AdminDTO admin = new AdminDTO();
        admin.setEmail("admin@example.com");
        admin.setPassword("password");
        try (MockedStatic<Authorization> mockedAuth = Mockito.mockStatic(Authorization.class)) {
            mockedAuth.when(() -> Authorization.isAuthorized(loginDTO.getPassword(), admin.getPassword()))
                    .thenReturn(true);
            when(adminRequester.getAdminByEmail(loginDTO.getEmail())).thenReturn(admin);
            when(jwtTokenProvider.generateAdminToken(admin)).thenReturn("mocked_token");

            ResponseEntity<?> response = authController.login(loginDTO);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("mocked_token", ((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("token"));
        }
    }

    @Test
    void loginAdmin_ShouldReturnUnauthorized_WhenPasswordInvalid() {
        AdminDTO admin = new AdminDTO();
        admin.setEmail("admin@example.com");
        admin.setPassword("differentPassword");

        when(adminRequester.getAdminByEmail(loginDTO.getEmail())).thenReturn(admin);

        ResponseEntity<?> response = authController.login(loginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", ((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("error"));
    }

    @Test
    void storeOwnerLogin_ShouldReturnToken_WhenCredentialsValid() throws Exception {
        StoreOwnerDTO owner = new StoreOwnerDTO();
        owner.setEmail("owner@example.com");
        owner.setPassword("password");
        try (MockedStatic<Authorization> mockedAuth = Mockito.mockStatic(Authorization.class)) {
                mockedAuth.when(() -> Authorization.isAuthorized(loginDTO.getPassword(), owner.getPassword()))
                        .thenReturn(true);
            when(adminRequester.getStoreOwnerByEmail(loginDTO.getEmail())).thenReturn(owner);

            when(jwtTokenProvider.generateStoreOwnerToken(owner)).thenReturn("owner_token");

            ResponseEntity<?> response = authController.storeOwnerLogin(loginDTO);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("owner_token", ((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("token"));
        }
    }

    @Test
    void storeOwnerLogin_ShouldReturnUnauthorized_WhenPasswordInvalid() {
        StoreOwnerDTO owner = new StoreOwnerDTO();
        owner.setEmail("owner@example.com");
        owner.setPassword("wrong");

        when(adminRequester.getStoreOwnerByEmail(loginDTO.getEmail())).thenReturn(owner);

        ResponseEntity<?> response = authController.storeOwnerLogin(loginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", ((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("error"));
    }

    @Test
    void storeManagerLogin_ShouldReturnToken_WhenCredentialsValid() throws Exception {
        StoreManagerDTO manager = new StoreManagerDTO();
        manager.setEmail("manager@example.com");
        manager.setPassword("password");
        try (MockedStatic<Authorization> mockedAuth = Mockito.mockStatic(Authorization.class)) {
            mockedAuth.when(() -> Authorization.isAuthorized(loginDTO.getPassword(), manager.getPassword()))
                    .thenReturn(true);
            when(adminRequester.getStoreManagerByEmail(loginDTO.getEmail())).thenReturn(manager);
            when(jwtTokenProvider.generateStoreManagerToken(manager)).thenReturn("manager_token");

            ResponseEntity<?> response = authController.storeManagerLogin(loginDTO);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("manager_token", ((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("token"));
        }
    }

    @Test
    void storeManagerLogin_ShouldReturnUnauthorized_WhenPasswordInvalid() {
        StoreManagerDTO manager = new StoreManagerDTO();
        manager.setEmail("manager@example.com");
        manager.setPassword("wrong");

        when(adminRequester.getStoreManagerByEmail(loginDTO.getEmail())).thenReturn(manager);

        ResponseEntity<?> response = authController.storeManagerLogin(loginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", ((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("error"));
    }

    @Test
    void costumerLogin_ShouldReturnNoContent() {
        ResponseEntity<?> response = authController.costumerLogin(loginDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
