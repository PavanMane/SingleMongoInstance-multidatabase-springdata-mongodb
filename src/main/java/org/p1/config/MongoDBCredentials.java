package org.p1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="mongodb")
public class MongoDBCredentials {

	private String uri;
	
	private String defaultDatabaseName;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDefaultDatabaseName() {
		return defaultDatabaseName;
	}

	public void setDefaultDatabaseName(String defaultDatabaseName) {
		this.defaultDatabaseName = defaultDatabaseName;
	}

	
}
