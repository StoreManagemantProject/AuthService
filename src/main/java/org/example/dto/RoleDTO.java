    package org.example.dto;

    import java.io.Serializable;

import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    public class RoleDTO implements Serializable {
        private Long id;
        private String name;
        private String description;
    }
