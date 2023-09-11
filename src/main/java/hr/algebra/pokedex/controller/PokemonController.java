package hr.algebra.pokedex.controller;

import hr.algebra.pokedex.domain.Pokemon;
import hr.algebra.pokedex.dto.PokemonDTO;
import hr.algebra.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("pokemon")
@AllArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    private JmsTemplate jmsTemplate;
    @GetMapping
    public List<PokemonDTO> getAll() {
        jmsTemplate.convertAndSend("Fetching all pokemon from the database...");
        return pokemonService.findAll();
    }

    @GetMapping("{id}")
    public PokemonDTO getById(@PathVariable final Long id) {
        jmsTemplate.convertAndSend("Fetching pokemon from the database with id:"+ id);
        return pokemonService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PokemonDTO save(@Valid @RequestBody final PokemonDTO pokemon){
        System.out.println(pokemon.toString());
        jmsTemplate.convertAndSend("Adding new pokemon to the database..");
        return pokemonService.save(pokemon)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Pokemon with the same id already exists"));
    }

    @PutMapping()
    public PokemonDTO update(@Valid @RequestBody final PokemonDTO pokemon){
        jmsTemplate.convertAndSend("Updateing pokemon from the database with id:"+pokemon.getId());
        return pokemonService.update(pokemon)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        jmsTemplate.convertAndSend("Deleting pokemon from the database with id:"+id);
        pokemonService.deleteById(id);
    }
}
