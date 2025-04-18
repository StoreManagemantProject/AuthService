package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class AdminDTO implements Serializable {
    private UUID id;
    private String email;
    private String password;
    private Set<RoleDTO> roles;


}
