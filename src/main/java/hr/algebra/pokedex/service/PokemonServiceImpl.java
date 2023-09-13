package hr.algebra.pokedex.service;

import hr.algebra.pokedex.domain.Pokemon;
import hr.algebra.pokedex.dto.PokemonDTO;
import hr.algebra.pokedex.repository.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PokemonServiceImpl implements PokemonService{

    private final PokemonRepository pokemonRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<PokemonDTO> findById(Long id) {
        return pokemonRepository.findById(id).map(PokemonDTO::new);
    }

    @Override
    public Optional<List<PokemonDTO>> findByIdVulnurable(String id) {
        try {
            String sql = "SELECT * FROM pokemon WHERE id =" + id; // Vulnerable to SQL injection
            List<PokemonDTO> pokemonList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                PokemonDTO pokemonDTO = new PokemonDTO();
                pokemonDTO.setId(resultSet.getLong("id"));
                pokemonDTO.setName(resultSet.getString("name"));
                pokemonDTO.setHeight(resultSet.getInt("height"));
                pokemonDTO.setWeight(resultSet.getInt("weight"));
                pokemonDTO.setType(resultSet.getString("type"));
                return pokemonDTO;
            });
            return Optional.ofNullable(pokemonList);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return Optional.empty();
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
}
