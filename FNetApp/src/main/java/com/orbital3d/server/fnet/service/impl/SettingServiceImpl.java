package com.orbital3d.server.fnet.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.service.SettingsService;

@Service
public class SettingServiceImpl implements SettingsService {

	private static final Logger LOG = LoggerFactory.getLogger(SettingServiceImpl.class);

	@Value("${latest.default-limit:5}")
	private Integer defaultLimitLatest;

	@Value("${administrator.groupname:administrator}")
	private String administratorGroupName;

	@Value("${storage.path}")
	private String storagePath;

	@PostConstruct
	protected void postConstruct() throws IOException {
		if (StringUtils.isEmpty(storagePath)) {
			storagePath = "c:/tmp/";
		}
		Path storage = Paths.get(storagePath);
		if (!Files.exists(storage)) {
			Files.createDirectory(storage);
		}
		if (!Files.isDirectory(storage)) {
			throw new IllegalStateException("Storage is not a directory");
		}
		LOG.info("Storage path {} ready", storagePath);
	}

	@Override
	public Integer latestDefaultLimit() {
		return defaultLimitLatest;
	}

	@Override
	public String administratorGroupName() {
		return administratorGroupName;
	}

	@Override
	public String storagePath() {
		return storagePath;
	}

}
