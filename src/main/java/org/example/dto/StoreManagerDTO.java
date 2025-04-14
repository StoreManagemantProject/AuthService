package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StoreManagerDTO {
    private UUID id;
    private String name;
    private Set<RoleDTO> role;
    private StoreDTO managedStore;
    private String email;
    private String password;

}
