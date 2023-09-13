package hr.algebra.pokedex.controller;

import hr.algebra.pokedex.domain.Pokemon;
import hr.algebra.pokedex.dto.PokemonDTO;
import hr.algebra.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("pokemon")
@AllArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping
    public List<PokemonDTO> getAll() {
        return pokemonService.findAll();
    }

    @GetMapping("{id}")
    public PokemonDTO getById(@PathVariable final Long id) {
        return pokemonService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PokemonDTO save(@Valid @RequestBody final PokemonDTO pokemon){
        return pokemonService.save(pokemon)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Pokemon with the same id already exists"));
    }

    @PutMapping()
    public PokemonDTO update(@Valid @RequestBody final PokemonDTO pokemon){
        return pokemonService.update(pokemon)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        pokemonService.deleteById(id);
    }

    @GetMapping("/serialize")
    public void serializeAllPokemon() {
        pokemonService.serializeAllPokemon();
    }

    @GetMapping("/deserialize")
    public void deserializeAllPokemon() {
        pokemonService.deserializeAllPokemon();
    }
}
