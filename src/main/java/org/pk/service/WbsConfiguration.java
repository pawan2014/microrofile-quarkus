package org.pk.service;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "wbs")
public class WbsConfiguration {

	public String url;
	public String scheme;
	
}
