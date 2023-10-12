package com.example.DTO.controller;

import com.example.DTO.dto.PokemonDto;
import com.example.DTO.dto.TrainerDto;
import com.example.DTO.entity.Pokemon;
import com.example.DTO.entity.Trainer;
import com.example.DTO.repository.PokemonRepository;
import com.example.DTO.repository.TrainerRepository;
import com.example.DTO.service.PokemonMapper;
import com.example.DTO.service.TrainerMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PokeTrainerController {

    private final PokemonRepository pokemonRepository;
    private final TrainerRepository trainerRepository;

    private final PokemonMapper pokemonMapper;

    private final TrainerMapper trainerMapper;

    public PokeTrainerController(PokemonRepository pokemonRepository, TrainerRepository trainerRepository, PokemonDto pokemonDto, PokemonMapper pokemonMapper, TrainerMapper trainerMapper) {
        this.pokemonRepository = pokemonRepository;
        this.trainerRepository = trainerRepository;
        this.pokemonMapper = pokemonMapper;
        this.trainerMapper = trainerMapper;
    }

    @GetMapping("/trainers")
    public List<Trainer> getTrainers(){
        return trainerRepository.findAll();
    }

    @GetMapping("/pokemons/{id}/details")
    public ResponseEntity<?> getPokemonDetails(@PathVariable("id") Long id) {
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(id);

        if (optionalPokemon.isPresent()) {
            Pokemon pokemon = optionalPokemon.get();
            PokemonDto pokemonDto = pokemonMapper.TransformPokemonEntityInPokemonDto(pokemon);
            return ResponseEntity.ok(pokemonDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/trainer/{id}/pokemons")
    public ResponseEntity<?> getTrainerWithPokemonList(@PathVariable("id") Long id) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(id);

        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            TrainerDto trainerDto = trainerMapper.TransformTrainerEntityInTrainerDto(trainer);
            return ResponseEntity.ok(trainerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pokemons")
    public List<Pokemon> getPokemons(){
        return pokemonRepository.findAll();
    }

    @PostMapping("/add/pokemon")
    public Pokemon addPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @PostMapping("/add/trainer")
    public Trainer addTrainer(@RequestBody Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Transactional
    @PutMapping("/trainers/{trainerId}/addPokemon/{pokemonId}")
    public ResponseEntity<?> addPokemonToTrainer(@PathVariable Long trainerId, @PathVariable Long pokemonId) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerId);
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);

        if (optionalTrainer.isPresent() && optionalPokemon.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            Pokemon pokemon = optionalPokemon.get();

            if (pokemon.getTrainer() == null) {
                List<Pokemon> pokemons = trainer.getPokemons();
                if (!pokemons.contains(pokemon)) {
                    pokemons.add(pokemon);
                    trainer.setPokemons(pokemons);
                    trainerRepository.save(trainer);
                    return ResponseEntity.ok("Le Pokémon a été ajouté à la liste de l'entraîneur.");
                } else {
                    return ResponseEntity.badRequest().body("Le Pokémon est déjà dans la liste de l'entraîneur.");
                }
            } else {
                return ResponseEntity.badRequest().body("Le Pokémon appartient déjà à un autre entraîneur.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
