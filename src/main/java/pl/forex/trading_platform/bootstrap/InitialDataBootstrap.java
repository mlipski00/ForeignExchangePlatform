package pl.forex.trading_platform.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.nbp.TableA;
import pl.forex.trading_platform.domain.settings.PlatformSettings;
import pl.forex.trading_platform.repository.InstrumentRepository;
import pl.forex.trading_platform.repository.NbpRatesRepository;
import pl.forex.trading_platform.repository.PlatformSettingsRepository;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.repository.UserRepository;
import pl.forex.trading_platform.service.NbpRates;

@Component
@PropertySource("classpath:oandaApi.properties")
@PropertySource("classpath:platformSettings.properties")
public class InitialDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private InstrumentRepository instrumentRepository;

    private PlatformSettingsRepository platformSettingsRepository;

    private NbpRatesRepository nbpRatesRepository;

    private UserRepository userRepository;

    private TransactionRepository transactionRepository;

    private NbpRates nbpRates;

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


    public InitialDataBootstrap(InstrumentRepository instrumentRepository, PlatformSettingsRepository platformSettingsRepository, NbpRatesRepository nbpRatesRepository, UserRepository userRepository, TransactionRepository transactionRepository, NbpRates nbpRates) {
        this.instrumentRepository = instrumentRepository;
        this.platformSettingsRepository = platformSettingsRepository;
        this.nbpRatesRepository = nbpRatesRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.nbpRates = nbpRates;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        setInstruments();
        setAllSettings();
        setNbpRates();
    }
    private void setInstruments() {
        for (String s : instrumentsList) {
            instrumentRepository.save(new Instrument(s));
        }
    }

    private void setAllSettings() {
        platformSettingsRepository.save(
                PlatformSettings.builder()
                .setDecisionTime(decisionTime)
                .setMinimumTradeAmount(minimumAmount)
                .setMaximumTradeAmount(maximumAmount)
                .setInitialBalance(initialBalance)
                .build()
        );
    }

    private void setNbpRates() {
        TableA[] tableArray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        nbpRatesRepository.save(tableArray[0]);
    }

    private void mockUsers() {
// todo: move to .sql

//        Transaction transaction = new Transaction();
//        transaction.setUser(user2);
//        transaction.setBuySell(BuySell.BUY);
//        transaction.setInstrument("EUR_PLN");
//        transaction.setPrice(4.20);
//        transaction.setClosePrice(4.35);
//        transaction.setAmount(15000);
//        transaction.setAmountPLN(transaction.getAmount()*transaction.getPrice());
//        transaction.setProfit(transaction.getAmount()*transaction.getClosePrice() - transaction.getAmount()*transaction.getPrice());
//        transaction.setExecuted(true);
//        transaction.setClosed(true);
//        transaction.setCloseDateTime(new Date());
//        transaction.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
//
//        Transaction transaction2 = new Transaction();
//        transaction2.setUser(user2);
//        transaction2.setBuySell(BuySell.BUY);
//        transaction2.setInstrument("USD_PLN");
//        transaction2.setPrice(3.50);
//        transaction2.setClosePrice(3.95);
//        transaction2.setAmount(35000);
//        transaction2.setAmountPLN(transaction2.getAmount()*transaction2.getPrice());
//        transaction2.setProfit(transaction2.getAmount()*transaction2.getClosePrice() - transaction2.getAmount()*transaction2.getPrice());
//        transaction2.setExecuted(true);
//        transaction2.setClosed(true);
//        transaction2.setCloseDateTime(new Date());
//        transaction2.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
//
//        transactionSet.add(transaction);
//        transactionSet.add(transaction2);
//        user2.setBalance(user2.getBalance()+transaction.getProfit()+transaction2.getProfit());
//        user2.setTransactions(transactionSet);
//        userRepository.save(user2);
//
//        Transaction transaction3 = new Transaction();
//        transaction3.setUser(user3);
//        transaction3.setBuySell(BuySell.BUY);
//        transaction3.setInstrument("EUR_PLN");
//        transaction3.setPrice(4.30);
//        transaction3.setClosePrice(4.15);
//        transaction3.setAmount(15000);
//        transaction3.setAmountPLN(transaction3.getAmount()*transaction3.getPrice());
//        transaction3.setProfit(transaction3.getAmount()*transaction3.getClosePrice() - transaction3.getAmount()*transaction3.getPrice());
//        transaction3.setExecuted(true);
//        transaction3.setClosed(true);
//        transaction3.setCloseDateTime(new Date());
//        transaction3.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
//
//        Transaction transaction4 = new Transaction();
//        transaction4.setUser(user3);
//        transaction4.setBuySell(BuySell.BUY);
//        transaction4.setInstrument("USD_PLN");
//        transaction4.setPrice(3.90);
//        transaction4.setClosePrice(3.65);
//        transaction4.setAmount(30000);
//        transaction4.setAmountPLN(transaction4.getAmount()*transaction4.getPrice());
//        transaction4.setProfit(transaction4.getAmount()*transaction4.getClosePrice() - transaction4.getAmount()*transaction4.getPrice());
//        transaction4.setExecuted(true);
//        transaction4.setClosed(true);
//        transaction4.setCloseDateTime(new Date());
//        transaction4.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
//
//        transactionSet2.add(transaction3);
//        transactionSet2.add(transaction4);
//        user3.setBalance(user3.getBalance()+transaction3.getProfit()+transaction4.getProfit());
//        user3.setTransactions(transactionSet2);
//        userRepository.save(user3);
    }
}
