package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.UserService;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody UserDTO userDTO) {
        UUID userID = userService.insert(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") UUID userID) {
        UserDTO dto = userService.findUserById(userID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/getByEmail/{email}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String email) {
        UserDTO dto = userService.findUserByEmail(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) {
        if (!userService.userExistsById(userDTO.getId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with given ID does not exist in the database!"));


        UUID userID = userService.update(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDTO userDTO)
    {
        UUID userId = userDTO.getId();
        if (!userService.userExistsById(userId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with given ID does not exist in the database!"));

        userService.delete(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

}
