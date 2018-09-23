package pl.forex.trading_platform.validator;

import pl.forex.trading_platform.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

   private UserService userService;

   public UniqueEmailValidator(UserService userService) {
      this.userService = userService;
   }

   public void initialize(UniqueEmail constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
       return this.userService.validUserEmail(email);
   }

}