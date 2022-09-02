package com.orbital3d.server.fnet.error;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.orbital3d.web.security.weblectricfence.exception.AuthenticationException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView authExcept(WebRequest req) {
		return new ModelAndView("error/503");
	}

	@ExceptionHandler(LoginException.class)
	protected ModelAndView loginFailed() {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("reason", "Invalid credentials");
		return modelAndView;
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	protected Map<String, String> npeHandler(HttpServletResponse r) throws IOException {
		Map<String, String> msg = new HashMap<>();
		msg.put("message", "Server Error");
		msg.put("description", "Might be fixed someday");
		return msg;
	}

	@ExceptionHandler(HttpSessionRequiredException.class)
	@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
	protected void sesssionTimeout() {
		// Do nothing
	}
}
