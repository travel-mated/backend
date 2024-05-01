package com.tripmate.tripmate.auth;

import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * http://localhost:8080/login 일 때 동작
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername 발동");
        System.out.println("username = " + username);
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("유저네임이없습니다"));
        return new PrincipalDetails(user);
    }
}
