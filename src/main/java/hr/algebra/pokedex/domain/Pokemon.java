package hr.algebra.pokedex.domain;

import hr.algebra.pokedex.dto.PokemonDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pokemon")
public class Pokemon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "type")
    private String type;

    public Pokemon(PokemonDTO pokemon) {
        this.id= pokemon.getId();
        this.name = pokemon.getName();
        this.height= pokemon.getHeight();
        this.weight= pokemon.getWeight();
        this.type= pokemon.getType();
    }
}
