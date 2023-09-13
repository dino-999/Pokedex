package hr.algebra.pokedex.controller;

import hr.algebra.pokedex.domain.RefreshToken;
import hr.algebra.pokedex.dto.AuthRequestDTO;
import hr.algebra.pokedex.dto.AuthResponseDTO;
import hr.algebra.pokedex.dto.RefreshTokenRequestDTO;
import hr.algebra.pokedex.service.security.JwtGeneratorService;
import hr.algebra.pokedex.service.security.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private JwtGeneratorService jwtGeneratorService;

    private RefreshTokenService refreshTokenService;


    @PostMapping("/login")
    public AuthResponseDTO authenticate(@RequestBody final AuthRequestDTO request) {
        if (!Objects.equals(request, new AuthRequestDTO("username", "password"))) {
            throw new UsernameNotFoundException("User not found...");
        }
        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.username());
        return AuthResponseDTO.builder()
                .accessToken(jwtGeneratorService.generateToken(request.username()))
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @PostMapping("/refreshToken")
    public AuthResponseDTO refreshToken(@RequestBody final RefreshTokenRequestDTO request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.token())
                .orElseThrow(() -> new RuntimeException("Refresh token doesn't exist."));

        refreshTokenService.verifyExpiration(refreshToken);

        String username = refreshToken.getUsername();
        String accessToken = jwtGeneratorService.generateToken(username);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(request.token())
                .build();
    }
}
