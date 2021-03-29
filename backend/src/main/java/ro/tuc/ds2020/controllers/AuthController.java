package ro.tuc.ds2020.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.payload.request.LoginRequest;
import ro.tuc.ds2020.payload.request.RegisterRequest;
import ro.tuc.ds2020.payload.response.JwtResponse;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.security.jwt.JwtUtils;
import ro.tuc.ds2020.security.services.UserDetailsImpl;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().toString();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        UserDTO userDTO = new UserDTO(registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getRole());
        userService.insert(userDTO);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
