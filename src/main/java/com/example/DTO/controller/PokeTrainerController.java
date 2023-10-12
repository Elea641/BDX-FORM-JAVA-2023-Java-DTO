package com.example.DTO.controller;

import com.example.DTO.entity.Pokemon;
import com.example.DTO.entity.Trainer;
import com.example.DTO.repository.PokemonRepository;
import com.example.DTO.repository.TrainerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PokeTrainerController {

    private final PokemonRepository pokemonRepository;
    private final TrainerRepository trainerRepository;

    public PokeTrainerController(PokemonRepository pokemonRepository, TrainerRepository trainerRepository) {
        this.pokemonRepository = pokemonRepository;
        this.trainerRepository = trainerRepository;
    }

    @GetMapping("/trainers")
    public List<Trainer> getTrainers(){
        return trainerRepository.findAll();
    }

    @GetMapping("/pokemons")
    public List<Pokemon> getPokemons(){
        return pokemonRepository.findAll();
    }

    @PostMapping("/add/pokemon")
    public Pokemon addPokemon(@RequestBody Pokemon pokemon) {
        // Récupérer l'ID du dresseur depuis la requête
        Long trainerId = trainerRepository.getById(pokemon);

        // Charger le dresseur correspondant depuis la base de données
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Dresseur non trouvé avec l'ID: " + trainerId));

        // Créer le nouveau Pokémon
        Pokemon pokemon = new Pokemon(request.getName(), request.getPower(), request.getAttribute());

        // Associer le dresseur au Pokémon
        pokemon.setTrainer(trainer);

        // Enregistrer le Pokémon dans la base de données
        return pokemonRepository.save(pokemon);
    }

    @PostMapping("/add/trainer")
    public Trainer addTrainer(@RequestBody Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
