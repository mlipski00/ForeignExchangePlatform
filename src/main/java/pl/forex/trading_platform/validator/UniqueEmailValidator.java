package pl.forex.trading_platform.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.forex.trading_platform.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

   @Autowired
   private UserService userService;

   public UniqueEmailValidator(UserService userService) {
   }

   public void initialize(UniqueEmail constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
       return userService.validUserEmail(email);
   }

}