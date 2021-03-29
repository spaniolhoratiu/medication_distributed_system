package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    @Transactional
    public UserDTO findUserByEmail(String email) {
        Optional<User> prosumerOptional = userRepository.findByEmail(email);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with email {} was not found in db", email);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + email);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    @Transactional
    public UUID update(UserDTO userDTO)
    {
        Optional<User> optionalUserFromDb = userRepository.findById(userDTO.getId());
        User userFromDb = optionalUserFromDb.get();

        if(!userDTO.getEmail().isEmpty())
            userFromDb.setEmail(userDTO.getEmail());
        if(!userDTO.getRole().isEmpty())
            userFromDb.setRole(userDTO.getRole());

        userFromDb = userRepository.save(userFromDb);
        LOGGER.debug("User with id {} was updated in db", userFromDb.getId());
        return userFromDb.getId();
    }

    @Transactional
    public UUID delete(UUID userId)
    {
        Optional<User> optionalUserFromDb = userRepository.findById(userId);
        User userFromDb = optionalUserFromDb.get();

        userRepository.delete(userFromDb);
        LOGGER.debug("User with id {} was deleted from db", userFromDb.getId());
        return userFromDb.getId();
    }


    public boolean userExistsByEmail(String email)
    {
       return userRepository.existsByEmail(email);
    }

    public boolean userExistsById(UUID id)
    {
        return userRepository.existsById(id);
    }


}
