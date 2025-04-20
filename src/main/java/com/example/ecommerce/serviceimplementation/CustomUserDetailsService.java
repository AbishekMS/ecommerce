package com.example.ecommerce.serviceimplementation;

import com.example.ecommerce.model.UserPrincipal;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;
    //private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

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
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
    public String verify(Users user){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getName());
        }
        else return "Fail";

    }

}
