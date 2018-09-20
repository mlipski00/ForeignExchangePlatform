package pl.forex.trading_platform.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.user.CustomUserDetails;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User getLoggedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(customUserDetails.getId());
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            logger.error("getLoggedUser() method failed");
           return new User();
        }
    }

    @Override
    public boolean validUserEmail(String email) {
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty()) {
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
    @Override
    public List<User> userRankingList() {
        return userRepository.findAll().stream().sorted(Comparator.comparing(User::getBalance).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public boolean isLoggedUserAdmin() {
        Authentication authentication = authenticationFacade.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public void updateUserBalance(Long usersToUpdateId, double newBalanceAmount) {
        Optional<User> userOptional = userRepository.findById(usersToUpdateId);
        userOptional.ifPresent(user -> {
            user.setBalance(newBalanceAmount);
            userRepository.save(user);
        });
    }
}
