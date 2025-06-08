package com.awbd.myreviewer.services.security;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.domain.Domain;
import com.awbd.myreviewer.domain.security.Authority;
import com.awbd.myreviewer.domain.security.User;
import com.awbd.myreviewer.dtos.AccountDTO;
import com.awbd.myreviewer.mappers.AccountMapper;
import com.awbd.myreviewer.repositories.AccountRepository;
import com.awbd.myreviewer.repositories.security.AuthorityRepository;
import com.awbd.myreviewer.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("mysql")
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        Optional<User> userOpt= userRepository.findByUsername(username);
        if (userOpt.isPresent())
            user = userOpt.get();
        else
            throw new UsernameNotFoundException("Username: " + username);

        log.info(user.toString());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.getEnabled(), user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),user.getAccountNonLocked(),
                getAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null){
            return new HashSet<>();
        } else if (authorities.size() == 0){
            return new HashSet<>();
        }
        else{
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }

    public User save(AccountDTO account, String password) {
        String username = account.getName();
        String role = account.getRole();
        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username or password already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // map user to role
        Optional<Authority> authority = authorityRepository.findByRole(role.toUpperCase());

        if(authority.isEmpty()) {
            throw new IllegalArgumentException("Role does not exist");
        }

        user.setAuthorities(Set.of(authority.get()));

        return userRepository.save(user);
    }
}
