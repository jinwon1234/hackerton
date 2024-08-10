package com.ll.hackerton.user;


import com.ll.hackerton.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String password, String contactNumber, String email, String role) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setContactNumber(contactNumber);
        user.setEmail(email);
        user.setRole(role);
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public void modify(SiteUser siteUser, String username, String contactNumber, String email) {
        siteUser.setUsername(username);
        siteUser.setContactNumber(contactNumber);
        siteUser.setEmail(email);
        this.userRepository.save(siteUser);
    }

    public SiteUser findByUsername(String username) {
        return  userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("user not found"));
    }
}
