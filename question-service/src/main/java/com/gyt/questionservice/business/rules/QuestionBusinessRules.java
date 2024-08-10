package com.gyt.questionservice.business.rules;

import com.gyt.corepackage.business.abstracts.MessageService;
import com.gyt.corepackage.models.enums.RoleType;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.questionservice.api.clients.ManagementServiceClient;
import com.gyt.questionservice.business.dtos.response.GetUserResponse;
import com.gyt.questionservice.business.messages.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionBusinessRules {
    private final MessageService messageService;
    private final ManagementServiceClient managementServiceClient;

    public void userAuthorizationCheck(Long creatorId) {
        GetUserResponse authenticatedUser = managementServiceClient.getAuthenticatedUser();
        boolean hasOrganizationRole = false;

        for (String role : authenticatedUser.getRoles()) {
            if (role.equals(RoleType.ORGANIZATION)) {
                hasOrganizationRole = true;
            }
        }
        if (hasOrganizationRole && !authenticatedUser.getId().equals(creatorId)) {
            throw new BusinessException(messageService.getMessage(Messages.QuestionErrors.UserAuthorityError));
        }
    }

    public void textAndImageValidationRule(String text, String image) {
        if ((text.isEmpty() || text.isBlank()) && (image.isEmpty() || image.isBlank())) {
            throw new BusinessException(messageService.getMessage(Messages.QuestionErrors.TextOrImageUrlError));
        }
    }
}
