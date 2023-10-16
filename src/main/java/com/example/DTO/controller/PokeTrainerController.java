package com.example.DTO.controller;

import com.example.DTO.dto.PokemonDto;
import com.example.DTO.dto.TrainerDto;
import com.example.DTO.entity.Pokemon;
import com.example.DTO.entity.Trainer;
import com.example.DTO.repository.PokemonRepository;
import com.example.DTO.repository.TrainerRepository;
import com.example.DTO.service.PokemonMapper;
import com.example.DTO.service.TrainerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PokeTrainerController {

    private final PokemonRepository pokemonRepository;
    private final TrainerRepository trainerRepository;

    private final PokemonMapper pokemonMapper;

    private final TrainerMapper trainerMapper;

    public PokeTrainerController(PokemonRepository pokemonRepository, TrainerRepository trainerRepository, PokemonMapper pokemonMapper, TrainerMapper trainerMapper) {
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

            // Récupérer la liste des Pokémon associés au dresseur
            List<Pokemon> trainerPokemons = trainer.getPokemons();

            List<PokemonDto> pokemonDtos = new ArrayList<>();

            for (Pokemon pokemon : trainerPokemons) {
                PokemonDto pokemonDto = pokemonMapper.TransformPokemonEntityInPokemonDto(pokemon);
                pokemonDtos.add(pokemonDto);
            }

            TrainerDto trainerDto = trainerMapper.TransformTrainerEntityInTrainerDto(trainer);
            trainerDto.setPokemonDtos(pokemonDtos);

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

    @PutMapping("/trainers/{trainerId}/addPokemon/{pokemonId}")
    public ResponseEntity<?> addPokemonToTrainer(@PathVariable Long trainerId, @PathVariable Long pokemonId) {
        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(pokemonId);

        if (pokemonOptional.isPresent()) {
            Pokemon pokemon = pokemonOptional.get();

            // Vérifier si le Pokémon a déjà un dresseur
            if (pokemon.getTrainer() == null) {
                // Mettre à jour le trainer_id
                Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
                if (trainer != null) {
                    pokemon.setTrainer(trainer);
                    pokemonRepository.save(pokemon);
                    return ResponseEntity.ok("Le Pokémon a été ajouté au dresseur.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dresseur non trouvé.");
                }
            } else {
                return ResponseEntity.badRequest().body("Le Pokémon a déjà un dresseur attribué.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon non trouvé.");
        }
    }
}
