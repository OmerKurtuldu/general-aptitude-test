package com.gyt.managementservice.business.dtos.response.get;


import com.gyt.corepackage.models.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetUserResponse {
    private Long id;
    private String email;
    private List<RoleType> roles;
}
