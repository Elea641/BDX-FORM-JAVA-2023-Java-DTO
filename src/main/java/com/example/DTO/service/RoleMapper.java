package com.example.DTO.service;

import com.example.DTO.dto.RoleDto;
import com.example.DTO.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {
    public RoleDto TransformRoleEntityInRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        roleDto.setId(role.getId());
        roleDto.setUserName(role.getUsers().get(0).getName());
        // Récupere et ajoute au DTO toutes les propriétés que tu auras déclarées des 2 côtés
        return roleDto;
    }

    public Role TransformRoleDtoInRoleEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        role.setId(roleDto.getId()); // Tu pourrais ajouter ici un peu de logique supplémentaire (Ex: condition) pour ajuster l'entité à tes besoins
        return role;
    }
}
