package hr.algebra.pokedex.service.security;
import hr.algebra.pokedex.security.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals("username")) {
            throw new UsernameNotFoundException("User not found...");
        }
        final PasswordEncoder encoder = new BCryptPasswordEncoder();
        return new UserDetailsImpl(username, encoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
