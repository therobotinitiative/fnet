package com.orbital3d.server.fnet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orbital3d.web.security.weblectricfence.configuration.FenceConfig;

@Configuration
public class WebLectricConfiguration {

	@Bean
	protected FenceConfig fenceConfig() {
		return new FenceConfig() {
			@Override
			public String secureContextRoot() {
				return "/fnet/";
			}
		};
	}
}
