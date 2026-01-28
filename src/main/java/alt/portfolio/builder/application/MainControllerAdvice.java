package alt.portfolio.builder.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import alt.portfolio.builder.exeptions.EntityNotFoundException;

@ControllerAdvice

public class MainControllerAdvice {

	@ExceptionHandler(exception = EntityNotFoundException.class)
	public ModelAndView entityNotFound() {
		return new ModelAndView("error/404", HttpStatus.NOT_FOUND);
	}

}
