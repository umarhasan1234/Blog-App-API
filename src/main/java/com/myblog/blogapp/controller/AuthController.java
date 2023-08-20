package com.myblog.blogapp.controller;

import com.myblog.blogapp.entity.Role;
import com.myblog.blogapp.entity.User;
import com.myblog.blogapp.payload.LoginDto;
import com.myblog.blogapp.payload.SignUpDto;
import com.myblog.blogapp.reprository.RoleRepository;
import com.myblog.blogapp.reprository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticatedUser(@RequestBody LoginDto loginDto){
        Authentication authenticatiion = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        //if username and password is correct then bellow one line of code will run otherwise program strop run.
        SecurityContextHolder.getContext().setAuthentication(authenticatiion);

        return new ResponseEntity<>("user signed-in successfully", HttpStatus.OK);
    }


    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        //add check for  username exists in a db
        if(userRepository.exitbyUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("username is already taken",HttpStatus.BAD_REQUEST);

        }
        if (userRepository.exitByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("eamil is already taken",HttpStatus.BAD_REQUEST);
        }

        User user=new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles=roleRepository.findbyName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("user registered successfully", HttpStatus.OK);
    }
}
