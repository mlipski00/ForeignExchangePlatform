package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.transactions.ExecutionFailReason;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.CustomUserDetails;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getLoggedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(customUserDetails.getId());
        return userOptional.get();
    }

    @Override
    public Transaction processTtransaction(Transaction transaction, User loggedUser) {
        Set<Transaction> transactionList = loggedUser.getTransactions();
        if (loggedUser.getBalance() - loggedUser.getBlockedAmount() - transaction.getAmount() >= 0) {
            transaction.setExecuted(true);
            transaction.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
            loggedUser.setBlockedAmount(loggedUser.getBlockedAmount() + transaction.getAmount());
            transactionList.add(transaction);
            userRepository.save(loggedUser);
        } else {
            transaction.setExecuted(false);
            transaction.setClosed(true);
            transaction.setExecutionFailReason(ExecutionFailReason.NOT_ENOUGH_BALANCE.getReason());
            transaction.setProfit(0);
            transactionList.add(transaction);
            userRepository.save(loggedUser);
        }
        List<Transaction> transactionSet2List = new ArrayList<>(loggedUser.getTransactions());
        return transactionSet2List.get(transactionSet2List.size() - 1);
    }


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
