package com.gyt.managementservice.business.concretes;

import com.gyt.managementservice.business.abstracts.RoleService;
import com.gyt.managementservice.business.dtos.response.get.GetRoleResponse;
import com.gyt.managementservice.business.rules.RoleBusinessRules;
import com.gyt.managementservice.mapper.RoleMapper;
import com.gyt.managementservice.model.entities.Role;
import com.gyt.managementservice.model.enums.RoleType;
import com.gyt.managementservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class RoleManager implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleBusinessRules roleBusinessRules;

    @Override
    public GetRoleResponse getByRoleType(RoleType roleType) {
        roleBusinessRules.roleShouldBeExist(roleType);
        Optional<Role> role = roleRepository.findByName(roleType);
        return RoleMapper.INSTANCE.getRoleToResponse(role.get());
    }
}
