package com.social.network.services;

import com.social.network.models.LoginRequest;
import com.social.network.models.LoginResponse;
import com.social.network.models.User;
import com.social.network.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user in the system.
     *
     * @param user The user object to be created.
     * @return The created user object.
     * @throws IllegalArgumentException if there is an error during user creation.
     */
    public User createUser(User user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null || user.getNickname() == null || user.getName() == null) {
                throw new BadRequestException("All user details must be provided");
            }
            if (!userExists(user.getNickname(), user.getEmail())) {
                throw new BadRequestException("User nickname or email already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info("Encoded password: {}", user.getPassword());
            user.setCreated_at(LocalDate.now().toString());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating user: " + e.getMessage());
        }
    }

    /**
     * Logs in a user by verifying their credentials.
     *
     * @param loginRequest The login request containing email/nickname and password.
     * @throws BadRequestException if the credentials are invalid or no data is provided.
     */
    public LoginResponse loginUser(LoginRequest loginRequest) throws BadRequestException {
        User userLogged;
        if (loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty()) {
            userLogged = userRepository.findByEmail(loginRequest.getEmail());
            checkIfUserIsNull(userLogged);
            matchWithPassword(userLogged, loginRequest.getPassword());
            userLogged.setPassword(null);
            return LoginResponse.builder()
                    .status("OK")
                    .user(userLogged)
                    .build();
        } else if (loginRequest.getEmail() != null && !loginRequest.getNickname().isEmpty()) {
            userLogged = userRepository.findByNickname(loginRequest.getNickname());
            checkIfUserIsNull(userLogged);
            matchWithPassword(userLogged, loginRequest.getPassword());
            userLogged.setPassword(null);
            return LoginResponse.builder()
                    .user(userLogged)
                    .status("OK")
                    .build();
        } else {
            log.warn("Error a la hora de informar los datos");
            throw new BadRequestException("No data provided");
        }
    }

    /**
     * Checks if the provided user object is null.
     *
     * @param user The user object to be checked.
     * @throws BadRequestException if the user object is null.
     */
    private void checkIfUserIsNull(User user) throws BadRequestException {
        if (user == null) {
            throw new BadRequestException("No user credentials valid");
        }
    }

    /**
     * Matches the provided raw password with the stored encoded password of the user.
     *
     * @param user        The user whose password is to be matched.
     * @param rawPassword The raw password to be matched.
     * @throws BadRequestException if the passwords do not match.
     */
    private void matchWithPassword(User user, String rawPassword) throws BadRequestException {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadRequestException("'No user credentials valid");
        }
    }

    /**
     * Checks if a user with the given nickname or email already exists.
     *
     * @param nickname The nickname to check.
     * @param email    The email to check.
     * @return true if a user with the given nickname or email exists, false otherwise.
     */
    private boolean userExists(String nickname, String email) {
        var response = userRepository.searchReplicateUser(nickname, email);
        return response.isEmpty();
    }

}