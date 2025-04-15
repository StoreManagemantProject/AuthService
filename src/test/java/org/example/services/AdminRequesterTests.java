package loja.org.example.services;

import org.example.dto.AdminDTO;
import org.example.dto.StoreManagerDTO;
import org.example.dto.StoreOwnerDTO;
import org.example.services.AdminRequester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AdminRequesterTests {

    private RabbitTemplate rabbitTemplate;
    private AdminRequester adminRequester;

    @BeforeEach
    void setUp() {
        rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        adminRequester = new AdminRequester(rabbitTemplate);
    }

    @Test
    void testGetAdminByEmail() {
        String email = "admin@example.com";
        AdminDTO expectedAdmin = new AdminDTO();
        expectedAdmin.setEmail(email);

        when(rabbitTemplate.convertSendAndReceive("admin.request.queue", email))
                .thenReturn(expectedAdmin);

        AdminDTO result = adminRequester.getAdminByEmail(email);

        assertEquals(expectedAdmin, result);
        verify(rabbitTemplate, times(1))
                .convertSendAndReceive(eq("admin.request.queue"), eq(email));
    }

    @Test
    void testGetStoreOwnerByEmail() {
        String email = "owner@example.com";
        StoreOwnerDTO expectedOwner = new StoreOwnerDTO();
        expectedOwner.setEmail(email);

        when(rabbitTemplate.convertSendAndReceive("store.request.queue", email))
                .thenReturn(expectedOwner);

        StoreOwnerDTO result = adminRequester.getStoreOwnerByEmail(email);

        assertEquals(expectedOwner, result);
        verify(rabbitTemplate, times(1))
                .convertSendAndReceive(eq("store.request.queue"), eq(email));
    }

    @Test
    void testGetStoreManagerByEmail() {
        String email = "manager@example.com";
        StoreManagerDTO expectedManager = new StoreManagerDTO();
        expectedManager.setEmail(email);

        when(rabbitTemplate.convertSendAndReceive("manager.request.queue", email))
                .thenReturn(expectedManager);

        StoreManagerDTO result = adminRequester.getStoreManagerByEmail(email);

        assertEquals(expectedManager, result);
        verify(rabbitTemplate, times(1))
                .convertSendAndReceive(eq("manager.request.queue"), eq(email));
    }
}
