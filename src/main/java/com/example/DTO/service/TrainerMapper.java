package com.example.DTO.service;

import com.example.DTO.dto.PokemonDto;
import com.example.DTO.dto.RoleDto;
import com.example.DTO.dto.TrainerDto;
import com.example.DTO.entity.Pokemon;
import com.example.DTO.entity.Role;
import com.example.DTO.entity.Trainer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerMapper {

    public TrainerDto TransformTrainerEntityInTrainerDto(Trainer trainer) {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName(trainer.getName());

        List<PokemonDto> pokemonDtos = new ArrayList<>();

        for (Pokemon pokemon : trainer.getPokemons()) {
            PokemonDto pokemonDto = new PokemonDto();
            pokemonDto.setName(pokemon.getName());
            // Assurez-vous de copier toutes les autres propriétés du Pokémon à partir de l'entité vers le DTO

            pokemonDtos.add(pokemonDto);
        }

        trainerDto.setPokemonDtos(pokemonDtos);

        return trainerDto;
    }
}
