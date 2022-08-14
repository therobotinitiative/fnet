package com.orbital3d.server.fnet.error;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.orbital3d.web.security.weblectricfence.exception.AuthenticationException;
import com.orbital3d.web.security.weblectricfence.exception.AuthorizationException;
import com.orbital3d.web.security.weblectricfence.filter.util.AbstractFilterExceptionHandler;

@Component
public class ExceptionFilterHandler extends AbstractFilterExceptionHandler {

	@Override
	protected boolean doHandleException(Exception excepton, HttpServletRequest request, HttpServletResponse response) {
		if (excepton instanceof AuthenticationException) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.sendRedirect("/error/403.html");
			} catch (IOException e) {
				// Think what to do
				throw new RuntimeException(e);
			}
		} else if (excepton instanceof AuthorizationException) {
			try {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{message: \'not enough permissions\' }");
			} catch (IOException e) {
				// Think what to do
				throw new RuntimeException(e);
			}
		} else {
			return false;
		}
		return true;
	}

}
