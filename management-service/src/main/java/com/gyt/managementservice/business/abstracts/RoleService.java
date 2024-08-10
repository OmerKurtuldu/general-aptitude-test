package com.gyt.managementservice.business.abstracts;

import com.gyt.corepackage.models.enums.RoleType;
import com.gyt.managementservice.business.dtos.response.get.GetRoleResponse;


public interface RoleService {
    GetRoleResponse getByRoleType(RoleType roleType);
}
