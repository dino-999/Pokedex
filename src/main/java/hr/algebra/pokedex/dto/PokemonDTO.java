package hr.algebra.pokedex.dto;

import hr.algebra.pokedex.domain.Pokemon;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDTO {
    private Long id;

    private String name;


    private Integer height;


    private Integer weight;

    private String type;
    public PokemonDTO(Pokemon pokemon) {
        this.id= pokemon.getId();
        this.name = pokemon.getName();
        this.height = pokemon.getHeight();
        this.weight = pokemon.getWeight();
        this.type = pokemon.getType();
    }
}
