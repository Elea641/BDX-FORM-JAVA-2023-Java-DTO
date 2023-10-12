package com.example.DTO.controller;

import com.example.DTO.dto.RoleDto;
import com.example.DTO.entity.Role;
import com.example.DTO.entity.User;
import com.example.DTO.repository.UserRepository;
import com.example.DTO.service.RoleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {

    private final UserRepository userRepository;

    private final RoleMapper roleMapper;

    public RoleController(UserRepository userRepository, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
    }

    @GetMapping("/rolesSuccessMapper")
    public ResponseEntity<?> getRolesMapper() {
        User user = userRepository.findById(1L).get();
        List<Role> roles = user.getRoles();
        List<RoleDto> rolesDto = new ArrayList<RoleDto>();
        int i = 0;
        for (Role role : roles) {
            RoleDto roleDto = roleMapper.TransformRoleEntityInRoleDto(role);
            rolesDto.add(i, roleDto);
            i++;
        }
        return new ResponseEntity<>(rolesDto, HttpStatus.OK);
    }
}
