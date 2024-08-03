package com.gyt.managementservice.business.dtos.response.get;

import com.gyt.managementservice.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllUserResponse {
    private int id;
    private String email;
    private List<RoleType> roleNames;
}
