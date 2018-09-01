package pl.forex.trading_platform.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.nbp.TableA;
import pl.forex.trading_platform.domain.settings.PlatformSettings;
import pl.forex.trading_platform.domain.transactions.BuySell;
import pl.forex.trading_platform.domain.transactions.ExecutionFailReason;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.Role;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.*;
import pl.forex.trading_platform.service.NbpRates;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Component
@PropertySource("classpath:oandaApi.properties")
@PropertySource("classpath:platformSettings.properties")
public class InitialDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private PlatformSettingsRepository platformSettingsRepository;

    @Autowired
    private NbpRatesRepository nbpRatesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${oanda.instrumentsList}")
    private String[] instrumentsList;

    @Value("${platformSettings.decisionTime}")
    private int decisionTime;

    @Value("${platformSettings.nbpTableA}")
    private String nbpTableAurl;

    @Value("${platformSettings.minimumTradeAmount}")
    private Long minimumAmount;

    @Value("${platformSettings.maximumTradeAmount}")
    private Long maximumAmount;

    @Value("${platformSettings.initialBalance}")
    private Long initialBalance;

    @Autowired
    NbpRates nbpRates;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        setInstruments();
        setAllSettings();
        setNbpRates();
        mockUsers();
    }
    private void setInstruments() {
        for (int i = 0; i < instrumentsList.length; i++) {
            instrumentRepository.save(new Instrument(instrumentsList[i]));
        }
    }

    private void setAllSettings() {
        platformSettingsRepository.save(
                PlatformSettings.builder()
                .setDecisionTime(decisionTime)
                .setMinimumTradeAumount(minimumAmount)
                .setMaximumTradeAumount(maximumAmount)
                .setInitialBalance(initialBalance)
                .build()
        );
    }

    private void setNbpRates() {
        TableA[] tableAarray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        nbpRatesRepository.save(tableAarray[0]);
    }

    private void mockUsers() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        User user = new User();
        user.setUsername("Michał");
        user.setEmail("michal.lipski2@gmail.com");
        user.setBalance(initialBalance);
        user.setBlockedAmount(0);
        user.setActive(true);
        user.setRoles(roles);
        user.setPassword(BCrypt.hashpw("123123", BCrypt.gensalt()));
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("Tomek");
        user2.setEmail("t@gmail.com");
        user2.setBalance(initialBalance);
        user2.setBlockedAmount(0);
        user2.setActive(true);
        user2.setRoles(roles);
        user2.setPassword(BCrypt.hashpw("123123", BCrypt.gensalt()));
        userRepository.save(user2);
        Set<Transaction> transactionSet = new HashSet<>();

        Transaction transaction = new Transaction();
        transaction.setUser(user2);
        transaction.setBuySell(BuySell.BUY);
        transaction.setInstrument("EUR_PLN");
        transaction.setPrice(4.20);
        transaction.setClosePrice(4.35);
        transaction.setAmount(15000);
        transaction.setAmountPLN(transaction.getAmount()*transaction.getPrice());
        transaction.setProfit(transaction.getAmount()*transaction.getClosePrice() - transaction.getAmount()*transaction.getPrice());
        transaction.setExecuted(true);
        transaction.setClosed(true);
        transaction.setCloseDateTime(new Date());
        transaction.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());

        Transaction transaction2 = new Transaction();
        transaction2.setUser(user2);
        transaction2.setBuySell(BuySell.BUY);
        transaction2.setInstrument("USD_PLN");
        transaction2.setPrice(3.50);
        transaction2.setClosePrice(3.95);
        transaction2.setAmount(35000);
        transaction2.setAmountPLN(transaction2.getAmount()*transaction2.getPrice());
        transaction2.setProfit(transaction2.getAmount()*transaction2.getClosePrice() - transaction2.getAmount()*transaction2.getPrice());
        transaction2.setExecuted(true);
        transaction2.setClosed(true);
        transaction2.setCloseDateTime(new Date());
        transaction2.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());

        transactionSet.add(transaction);
        transactionSet.add(transaction2);
        user2.setBalance(user2.getBalance()+transaction.getProfit()+transaction2.getProfit());
        user2.setTransactions(transactionSet);
        userRepository.save(user2);

        roles.add(Role.ADMIN);
        User user3 = new User();
        user3.setUsername("Admin");
        user3.setEmail("a@gmail.com");
        user3.setBalance(initialBalance);
        user3.setBlockedAmount(0);
        user3.setActive(true);
        user3.setRoles(roles);
        user3.setPassword(BCrypt.hashpw("123123", BCrypt.gensalt()));
        userRepository.save(user3);
        Set<Transaction> transactionSet2 = new HashSet<>();

        Transaction transaction3 = new Transaction();
        transaction3.setUser(user3);
        transaction3.setBuySell(BuySell.BUY);
        transaction3.setInstrument("EUR_PLN");
        transaction3.setPrice(4.30);
        transaction3.setClosePrice(4.15);
        transaction3.setAmount(15000);
        transaction3.setAmountPLN(transaction3.getAmount()*transaction3.getPrice());
        transaction3.setProfit(transaction3.getAmount()*transaction3.getClosePrice() - transaction3.getAmount()*transaction3.getPrice());
        transaction3.setExecuted(true);
        transaction3.setClosed(true);
        transaction3.setCloseDateTime(new Date());
        transaction3.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());

        Transaction transaction4 = new Transaction();
        transaction4.setUser(user3);
        transaction4.setBuySell(BuySell.BUY);
        transaction4.setInstrument("USD_PLN");
        transaction4.setPrice(3.90);
        transaction4.setClosePrice(3.65);
        transaction4.setAmount(30000);
        transaction4.setAmountPLN(transaction4.getAmount()*transaction4.getPrice());
        transaction4.setProfit(transaction4.getAmount()*transaction4.getClosePrice() - transaction4.getAmount()*transaction4.getPrice());
        transaction4.setExecuted(true);
        transaction4.setClosed(true);
        transaction4.setCloseDateTime(new Date());
        transaction4.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());

        transactionSet2.add(transaction3);
        transactionSet2.add(transaction4);
        user3.setBalance(user3.getBalance()+transaction3.getProfit()+transaction4.getProfit());
        user3.setTransactions(transactionSet2);
        userRepository.save(user3);

    }
}
