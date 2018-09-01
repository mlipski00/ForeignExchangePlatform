package pl.forex.trading_platform.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorPage");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        modelAndView.addObject("errorCode", statusCode);
//        modelAndView.addObject("errorMsg", HttpStatus.valueOf(statusCode));
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}