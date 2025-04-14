package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO {
    private UUID id;
    private String email;
    private String password;
    private Set<RoleDTO> roles;


}
