package hr.algebra.pokedex.security;

import java.util.List;

public class DeserializationWhitelist {

    private DeserializationWhitelist() {}

    private static final List<String> ALLOWED_CLASSES = List.of("hr.algebra.pokedex.dto.PokemonDTO");
    public static boolean isClassAllowed(final String className) {
        return ALLOWED_CLASSES.contains(className);
    }
}