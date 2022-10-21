package com.orbital3d.server.fnet.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.service.ManifestService;

@Service
public class ManifestServiceImpl implements ManifestService
{

	@Override
	public String getFNetVersion() throws IOException
	{
		return parseManifest().get("FNet-Version");
	}

	private Map<String, String> parseManifest() throws IOException
	{
		InputStream manifestInputStream = ManifestServiceImpl.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
		// Manifest class doesn't see to work so DIY. Tried this for a long time. Either
		// this is the best solution right now or I'm just having very very bad luck.
		String manifestContent = new String(manifestInputStream.readAllBytes());
		Stream<String> streamLines = manifestContent.lines();
		Iterator<String> lineIterator = streamLines.iterator();
		Map<String, String> values = new HashMap<>();
		while (lineIterator.hasNext())
		{
			String line = lineIterator.next();
			if (!line.startsWith("#") && !line.isEmpty())
			{
				String[] splittedValues = line.split(":");
				values.put(splittedValues[0], splittedValues[1].strip());
			}
		}
		return values;
	}

}
