package pl.forex.trading_platform.service;


import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.nbp.TableA;

import java.io.IOException;

@Service
@Getter
@Setter
@Transactional
public class NbpRatesImpl implements NbpRates {

    @Override
    public String getTableAQuotesString(String nbpUrl) {
        String rawResponse = null;
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = null;
        try {
            request = requestFactory.buildGetRequest(new GenericUrl(nbpUrl));
            rawResponse = request.execute().parseAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
         return rawResponse;
    }

    @Override
    public TableA[] getTableAQuotesArray(String nbpUrl) {
        String rawResponse = null;
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = null;
        try {
            request = requestFactory.buildGetRequest(new GenericUrl(nbpUrl));
            rawResponse = request.execute().parseAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TableA[] tableA = new Gson().fromJson(rawResponse, TableA[].class);
        return tableA;
    }
}
