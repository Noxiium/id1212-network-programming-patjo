package service;

import javax.websocket.Session;
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
    
    @Transactional
    public void saveUser(User user) {
        userRepository.saveUser(user);
        userRepository.fetchUserID(user);
    }

}
