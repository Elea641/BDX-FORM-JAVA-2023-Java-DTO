package com.example.DTO.dto;

import com.example.DTO.entity.Pokemon;

import java.util.List;

public class TrainerDto {
    private String name;
    private List<Pokemon> pokemons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
