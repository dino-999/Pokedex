package hr.algebra.pokedex.service.security;

import hr.algebra.pokedex.domain.RefreshToken;
import hr.algebra.pokedex.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(final String username) {
        final RefreshToken token = RefreshToken.builder()
                .username(username)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(token);
    }

    @Override
    public Optional<RefreshToken> findByToken(final String token) {
        return refreshTokenRepository.findRefreshTokenByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(final RefreshToken token){
        if (token.getExpiryDate().isBefore(Instant.now())) {
            try {
                refreshTokenRepository.delete(token);
            }catch (Exception e) {
                throw new RuntimeException(token.getToken() + " refresh token has expired.");
            }
        }
        return token;
    }
}
