package com.example.DTO.service;

import com.example.DTO.dto.RoleDto;
import com.example.DTO.dto.TrainerDto;
import com.example.DTO.entity.Role;
import com.example.DTO.entity.Trainer;
import org.springframework.stereotype.Service;

@Service
public class TrainerMapper {

    public TrainerDto TransformTrainerEntityInTrainerDto(Trainer trainer) {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName(trainer.getName());
        trainerDto.setPokemons(trainer.getPokemons());
        // Récupere et ajoute au DTO toutes les propriétés que tu auras déclarées des 2 côtés
        return trainerDto;
    }
}
