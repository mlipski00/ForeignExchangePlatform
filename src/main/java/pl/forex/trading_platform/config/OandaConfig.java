package pl.forex.trading_platform.config;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.datasources.OandaDataSource;

import java.util.Arrays;
@Component
@Configuration
//@PropertySource("classpath:oandaApi.properties")
public class OandaConfig {

    @Value("${oanda.accountIDValue}")
    private String accountIDValue;

    @Value("${oanda.token}")
    private String oandaToken;

    @Value("${oanda.instrumentsList}")
    private String[] instrumentsList;

    @Value("${oanda.apiURI}")
    private String apiURI;

    @Value("${oanda.getQoutesInterval}")
    private int qoutesInterval;

    public OandaConfig() {
    }

    @Bean
    public OandaDataSource oandaDataSource() {
        OandaDataSource oandaDataSource = new OandaDataSource();
        System.out.println(accountIDValue);
        oandaDataSource.setAccountID(new AccountID(accountIDValue));
        oandaDataSource.setInstrumentsList(instrumentsList);
        oandaDataSource.setApiURI(apiURI);
        oandaDataSource.setContext(new Context(apiURI, oandaToken, "", AcceptDatetimeFormat.RFC3339, HttpClients.createDefault()));
        oandaDataSource.setOandaToken(oandaToken);
        oandaDataSource.setQoutesInterval(qoutesInterval);
        return oandaDataSource;
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }

    @Override
    public String toString() {
        return "OandaConfig{" +
                "accountIDValue='" + accountIDValue + '\'' +
                ", oandaToken='" + oandaToken + '\'' +
                ", instrumentsList=" + Arrays.toString(instrumentsList) +
                ", apiURI='" + apiURI + '\'' +
                ", qoutesInterval=" + qoutesInterval +
                '}';
    }
}
