package com.example.login_demo.service;

import com.example.login_demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service   // Spring에 등록 → Security가 로그인할 때 자동으로 이걸 사용
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 로그인 폼에서 아이디를 입력하면 Security가 이 메서드를 호출함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + username));

        // DB의 User → Security가 이해하는 UserDetails 로 변환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())   // 해시값. 비교는 Security가 PasswordEncoder로 알아서 함
                .roles(user.getRole())
                .build();
    }
}