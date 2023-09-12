package hr.algebra.pokedex.dto;

import lombok.Builder;

@Builder
public record AuthResponseDTO(String accessToken, String refreshToken) {
}
