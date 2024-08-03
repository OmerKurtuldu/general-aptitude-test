package com.gyt.managementservice.mapper;

import com.gyt.managementservice.business.dtos.response.get.GetRoleResponse;
import com.gyt.managementservice.model.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    GetRoleResponse getRoleToResponse (Role role);
    Role getResponseToRole (GetRoleResponse response);

}
