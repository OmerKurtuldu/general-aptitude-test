package com.gyt.managementservice.business.concretes;

import com.gyt.corepackage.business.abstracts.MessageService;
import com.gyt.corepackage.models.enums.RoleType;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.managementservice.business.abstracts.RoleService;
import com.gyt.managementservice.business.abstracts.UserService;
import com.gyt.managementservice.business.dtos.request.RegisterRequest;
import com.gyt.managementservice.business.dtos.request.update.UpdatedUserRequest;
import com.gyt.managementservice.business.dtos.response.get.GetAllUserResponse;
import com.gyt.managementservice.business.dtos.response.get.GetRoleResponse;
import com.gyt.managementservice.business.dtos.response.get.GetUserResponse;
import com.gyt.managementservice.business.dtos.response.update.UpdatedUserResponse;
import com.gyt.managementservice.business.messages.Messages;
import com.gyt.managementservice.business.rules.UserBusinessRules;
import com.gyt.managementservice.mapper.RoleMapper;
import com.gyt.managementservice.mapper.UserMapper;
import com.gyt.managementservice.model.entities.Role;
import com.gyt.managementservice.model.entities.User;

import com.gyt.managementservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBusinessRules userBusinessRules;
    private final RoleService roleService;
    private final MessageService messageService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user by username: {}", username);

        User userByEmail = userRepository.findByEmail(username)
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.UserErrors.UserShouldBeExists)));
        log.info("Successfully loaded user by username: {}", username);
        return userByEmail;
    }

    @Override
    public void addOrganization(RegisterRequest request) {
        log.info("Adding new organization with email: {}", request.getEmail());

        userBusinessRules.validateUniqueEmail(request.getEmail());

        User user = userMapper.registerRequestToUser(request, passwordEncoder);

        RoleType roleType = RoleType.ORGANIZATION;
        GetRoleResponse roleResponse = roleService.getByRoleType(roleType);
        Role role = roleMapper.getResponseToRole(roleResponse);

        user.setAuthorities(Set.of(role));
        userRepository.save(user);

        log.info("Successfully added organization with email: {}", request.getEmail());
    }

    @Override
    public GetUserResponse getByIdUser(Long id) {
        log.debug("Fetching user by ID: {}", id);

        userBusinessRules.userShouldBeExist(id);

        User user = userRepository.findById(id).get();

        Set<Role> roles = setUserAuthorities(user.getAuthorities());

        List<RoleType> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        GetUserResponse getUserResponse = userMapper.getUserToResponse(user);
        getUserResponse.setRoles(roleNames);
        log.info("Successfully fetched user by ID: {}", id);
        return getUserResponse;
    }

    @Override
    public Page<GetAllUserResponse> getAllUsers(int page, int size) {
        log.debug("Fetching all users with page: {} and size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(user -> {
            GetAllUserResponse response = userMapper.getAllResponseToEntity(user);
            List<RoleType> roleNames = user.getAuthorities().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            response.setRoleNames(roleNames);
            log.info("Successfully fetched all users with page: {} and size: {}", page, size);
            return response;
        }
        );
    }

    @Override
    public UpdatedUserResponse updatedUser(UpdatedUserRequest request) {
        log.info("Updating user with ID: {}", request.getId());
        userBusinessRules.userShouldBeExist(request.getId());
        User updateFoundUser = userRepository.findById(request.getId()).orElseThrow();
        GetUserResponse authenticatedUser = getAuthenticatedUser();
        Set<Role> authorities = updateFoundUser.getAuthorities();
        userBusinessRules.userUpdateAuthorizationCheck(authenticatedUser, request.getId());
        User user = userMapper.updatedRequestToUser(request, passwordEncoder);
        Set<Role> role = setUserAuthorities(authorities);
        user.setAuthorities(role);
        userRepository.save(user);
        log.info("Successfully updated user with ID: {}", request.getId());
        return userMapper.updatedUserToResponse(user);
    }


    public Set<Role> setUserAuthorities(Set<Role> authorities) {
        log.debug("Setting user authorities");
        for (Role role : authorities) {
            if (role.getName().equals(RoleType.ORGANIZATION)) {
                return Set.of(role);
            } else {
                return Set.of(role);
            }
        }
        return authorities;
    }


    public GetUserResponse getAuthenticatedUser() {
        log.debug("Fetching authenticated user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userBusinessRules.ifNotAuthenticated(authentication);

        User user = userRepository.findByEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.UserErrors.UserShouldBeExists)));

        Set<Role> roles = user.getAuthorities();
        List<RoleType> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        GetUserResponse getUserResponse = userMapper.getUserToResponse(user);
        getUserResponse.setRoles(roleNames);

        log.info("Successfully fetched authenticated user");
        return getUserResponse;
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Deleting user with ID: {}", id);
        userBusinessRules.userShouldBeExist(id);
        userRepository.deleteById(id);
        log.info("Successfully deleted user with ID: {}", id);
    }

    @Override
    public User getByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(messageService.getMessage(Messages.UserErrors.UserShouldBeExists)));
        log.info("Successfully fetched user by email: {}", email);
        return userByEmail;
    }

}
