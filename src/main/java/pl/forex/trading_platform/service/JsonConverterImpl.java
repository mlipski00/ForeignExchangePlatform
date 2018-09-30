package pl.forex.trading_platform.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.Quotation;

import java.util.List;

@Service
public class JsonConverterImpl implements JsonConverter{

    @Autowired
    ObjectMapper objectMapper;

    public String Quotation2JsonConverter(List<Quotation> quotationList)  {
        String jsonResponse = "default";
        try {
            jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(quotationList);
        } catch (Exception e) {
            e.getMessage();
        }
        return jsonResponse;
    }
}
