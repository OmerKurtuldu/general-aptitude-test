package com.gyt.managementservice.business.rules;

import com.gyt.corepackage.business.abstracts.MessageService;
import com.gyt.corepackage.models.enums.RoleType;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.managementservice.business.messages.Messages;
import com.gyt.managementservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@RequiredArgsConstructor
@Service
public class RoleBusinessRules {
    private final RoleRepository roleRepository;
    private final MessageService messageService;

    public void roleShouldBeExist(RoleType roleType) {
        boolean exists = roleRepository.existsByName(roleType);

        if (!exists){
            log.error("Role not found with RoleType: {}", roleType);
            throw new BusinessException(messageService.getMessage(Messages.RoleErrors.RoleShouldBeExists));
        }
        log.info("Role found with RoleType: {}", roleType);

    }
}
