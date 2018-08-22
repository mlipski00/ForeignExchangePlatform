package pl.forex.trading_platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.forex.trading_platform.service.UserService;
import pl.forex.trading_platform.validator.UniqueEmailValidator;

@Configuration
public class ValidationCofiguration {

    @Autowired
    private UserService userService;

    @Bean
    public UniqueEmailValidator uniqueEmailValidator() {
        return  new UniqueEmailValidator(userService);
    }
}
