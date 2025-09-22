package com.social.network.respositories;

import com.social.network.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Search for users by nickname or email.
     * @param nickname the nickname to search for
     * @param email the email to search for
     * @return a list of users matching the nickname or email
     */
    @Query("{ '$or' :[{'nickname': ?0},{'email': ?1}] }")
    List<User> searchReplicateUser(String nickname, String email);

    /**
     * Find a user by their nickname.
     * @param nickname the nickname of the user
     * @return the user with the given nickname
     */
    @Query("{nickname: ?0}")
    User findByNickname(String nickname);

    /**
     * Find a user by their email.
     * @param email the email of the user
     * @return the user with the given email
     */
    @Query("{'email': ?0}")
    User findByEmail(String email);
}
