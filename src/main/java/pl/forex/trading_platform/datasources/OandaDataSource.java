package pl.forex.trading_platform.datasources;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;

public class OandaDataSource {

    private String oandaToken;
    private String[] instrumentsList;
    private String apiURI;
    private int qoutesInterval;
    private AccountID accountID;
    private Context context;

    public String getOandaToken() {
        return oandaToken;
    }

    public void setOandaToken(String oandaToken) {
        this.oandaToken = oandaToken;
    }

    public String[] getInstrumentsList() {
        return instrumentsList;
    }

    public void setInstrumentsList(String[] instrumentsList) {
        this.instrumentsList = instrumentsList;
    }

    public String getApiURI() {
        return apiURI;
    }

    public void setApiURI(String apiURI) {
        this.apiURI = apiURI;
    }

    public int getQoutesInterval() {
        return qoutesInterval;
    }

    public void setQoutesInterval(int qoutesInterval) {
        this.qoutesInterval = qoutesInterval;
    }

    public AccountID getAccountID() {
        return accountID;
    }

    public void setAccountID(AccountID accountID) {
        this.accountID = accountID;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
