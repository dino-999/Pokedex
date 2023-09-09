package hr.algebra.pokedex.repository;

import hr.algebra.pokedex.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PokemonRepository extends JpaRepository<Pokemon,Long> {
}
