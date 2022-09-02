package com.orbital3d.server.fnet.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.service.SettingsService;

@Service
public class SettingServiceImpl implements SettingsService {

	@Value("${latest.default-limit:5}")
	private Integer defaultLimitLatest;

	@Value("${administrator.groupname:administrator}")
	private String administratorGroupName;

	@Override
	public Integer latestDefaultLimit() {
		return defaultLimitLatest;
	}

	@Override
	public String administratorGroupName() {
		return administratorGroupName;
	}

}
