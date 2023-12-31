package hr.algebra.pokedex.service;

import hr.algebra.pokedex.dto.PokemonDTO;

import java.util.List;
import java.util.Optional;

public interface PokemonService {

    Optional<PokemonDTO> findById(Long id);
    Optional<List<PokemonDTO>> findByIdVulnurable(String id);
    List<PokemonDTO> findAll();
    Optional<PokemonDTO> save(PokemonDTO pokemon);
    Optional<PokemonDTO> update(PokemonDTO pokemon);
    void deleteById(Long id);
}
