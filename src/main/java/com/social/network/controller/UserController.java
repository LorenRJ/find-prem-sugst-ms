package com.social.network.controller;

import com.social.network.models.LoginRequest;
import com.social.network.models.LoginResponse;
import com.social.network.models.User;
import com.social.network.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    /**
     * Endpoint to create a new user.
     *
     * @param user The user object to be created.
     * @return The created user object wrapped in a ResponseEntity.
     */
    @PostMapping(value = "/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    /**
     * Endpoint to log in a user.
     *
     * @param loginRequest The login request containing email/nickname and password.
     * @return A ResponseEntity indicating the result of the login operation.
     * @throws BadRequestException if the credentials are invalid or no data is provided.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) throws BadRequestException {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
