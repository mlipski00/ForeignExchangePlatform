package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.user.CustomUserDetails;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public User getLoggedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(customUserDetails.getId());
        return userOptional.get();
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean validUserEmail(String email) {
        try {
            List<User> userList = userRepository.findAll();
            if (null == userList) {
                return true;
            }
            for (User user : userList) {
                if (user.getEmail().equals(email)) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("validUserEmailException: " + e.getMessage());

        }
        return true;
    }
}
