package com.example.ecommerce.serviceimplementation;

import com.example.ecommerce.model.UserPrincipal;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user= userRepository.findByName(username);
        if(user==null) throw new UsernameNotFoundException("User not found");
        return new UserPrincipal(user);
    }

    public List<Users> getUsersDetails() {
        return userRepository.findAll();
    }

    public Users saveUsers(Users user) {
        return userRepository.save(user);

    }
}
