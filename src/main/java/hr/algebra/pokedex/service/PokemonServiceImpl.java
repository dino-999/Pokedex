package hr.algebra.pokedex.service;

import hr.algebra.pokedex.domain.Pokemon;
import hr.algebra.pokedex.dto.PokemonDTO;
import hr.algebra.pokedex.repository.PokemonRepository;
import hr.algebra.pokedex.utils.SerializationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PokemonServiceImpl implements PokemonService{

    private final PokemonRepository pokemonRepository;


    @Override
    public Optional<PokemonDTO> findById(Long id) {
        return pokemonRepository.findById(id).map(PokemonDTO::new);
    }

    @Override
    public List<PokemonDTO> findAll() {
        return pokemonRepository.findAll().stream().map(PokemonDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<PokemonDTO> save(PokemonDTO pokemon) {
        return Optional.of(
                 new PokemonDTO(pokemonRepository.save(new Pokemon(pokemon)))
                );

    }

    @Override
    public Optional<PokemonDTO> update(PokemonDTO pokemon) {
        return Optional.of(
                new PokemonDTO(pokemonRepository.save(new Pokemon(pokemon)))
        );
    }


    @Override
    public void deleteById(Long id) {
        pokemonRepository.deleteById(id);
    }

    @Override
    public void serializeAllPokemon() {
        List<PokemonDTO> pokemon = findAll();

        try {
            SerializationUtils.serialize(pokemon,"pokemon.ser");
        }catch (Exception e){
            System.out.println("Cant serialize pokemon");
        }

    }

    @Override
    public void deserializeAllPokemon() {
        try {
            SerializationUtils.deserialize("pokemon.ser");
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("There was an error with the deserialization");
        }
    }
}
