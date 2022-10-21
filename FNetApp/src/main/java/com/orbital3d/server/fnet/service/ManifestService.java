package com.orbital3d.server.fnet.service;

import java.io.IOException;
import java.util.jar.Manifest;

/**
 * MANIFEST.MF related operations service. {@link Manifest} failed to provide
 * required functionality.
 * 
 * @author msiren
 *
 */
public interface ManifestService
{
	/**
	 * @return Value of "FNet-Version" attribute
	 * @throws IOException
	 */
	String getFNetVersion() throws IOException;
}
