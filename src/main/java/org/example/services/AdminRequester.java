package org.example.services;

import org.example.dto.AdminDTO;
import org.example.dto.StoreManagerDTO;
import org.example.dto.StoreOwnerDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminRequester {

    private final RabbitTemplate rabbitTemplate;

    public AdminRequester(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public AdminDTO getAdminByEmail(String email) {
        return (AdminDTO) rabbitTemplate.convertSendAndReceive(
                "admin.request.queue", email);
    }
    public StoreOwnerDTO getStoreOwnerByEmail(String email) {
        return (StoreOwnerDTO) rabbitTemplate.convertSendAndReceive(
                "store.request.queue", email
        );
    }
    public StoreManagerDTO  getStoreManagerByEmail(String email) {
        return (StoreManagerDTO) rabbitTemplate.convertSendAndReceive(
                "manager.request.queue", email
        );
    }
}
