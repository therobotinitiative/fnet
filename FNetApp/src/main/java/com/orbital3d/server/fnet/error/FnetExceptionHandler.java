package com.orbital3d.server.fnet.error;

import javax.security.auth.login.LoginException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.orbital3d.web.security.weblectricfence.exception.AuthenticationException;

@ControllerAdvice
public class FnetExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView authExcept(WebRequest req) {
		System.out.println("***********************************************************************************");
		return new ModelAndView("error/503");
	}

	@ExceptionHandler(LoginException.class)
	protected ModelAndView loginFailed() {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("reason", "Invalid credentials");
		return modelAndView;
	}
}
