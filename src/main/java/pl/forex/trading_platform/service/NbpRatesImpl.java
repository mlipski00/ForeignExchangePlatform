package pl.forex.trading_platform.service;


import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Getter
@Setter
@Transactional
public class NbpRatesImpl implements NbpRates {

    @Override
    public String getTableAQuotes(String nbpUrl) {
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
}
