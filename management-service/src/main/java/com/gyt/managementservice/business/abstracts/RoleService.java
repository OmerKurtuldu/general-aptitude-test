package com.gyt.managementservice.business.abstracts;

import com.gyt.managementservice.business.dtos.response.get.GetRoleResponse;
import com.gyt.managementservice.model.entities.Role;
import com.gyt.managementservice.model.enums.RoleType;

public interface RoleService {
    GetRoleResponse getByRoleType(RoleType roleType);
}
