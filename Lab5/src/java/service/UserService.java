package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saves a User object to the database.
     *
     * @param  user  the User object to be saved
     */
    @Transactional
    public void saveUser(User user) {
        userRepository.saveUser(user);
        Integer userId = userRepository.fetchUserID(user);
        user.setId(userId);

        System.out.println(user.getId());

    }

    /**
     * Handles the user login by checking if the user exists in the database.
     * If the user does not exist, it saves the user to the database.
     * If the user exists, it updates the user objects ID.
     *
     * @param  user  the user object containing the user data
     * @return       void
     */
    public void handleUserLogin(User user) {

        Integer userId = userRepository.checkIfUserExistInDB(user);

        if (userId == -1) {
            saveUser(user);
        } else {
            user.setId(userId);
        }
    }

}
