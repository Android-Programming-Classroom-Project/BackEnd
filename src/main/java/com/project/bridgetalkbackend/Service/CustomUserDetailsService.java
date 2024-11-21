package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.dto.CustomUserDetails;
import com.project.bridgetalkbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //DB에서 조회
        User userData = userRepository.findByEmail(email);

        if (userData != null) {
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }
        throw new UsernameNotFoundException("아이디 또는 비밀번호가 다릅니다.");
    }

    public User findUser(UUID userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new UsernameNotFoundException("user정보 X"));
    }
}
