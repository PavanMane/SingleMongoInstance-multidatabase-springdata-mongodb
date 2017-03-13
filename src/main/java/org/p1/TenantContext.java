package org.p1;

import org.springframework.util.StringUtils;

public class TenantContext {
	
	private static final String DEFAULT_TENANT = "default";
	
	private static final ThreadLocal<String> tenant = new ThreadLocal<String>();
	
	public static final String getTenant() {
		String tenantCode = tenant.get();
		if(StringUtils.isEmpty(tenantCode)) {
			tenant.set(DEFAULT_TENANT);
			tenantCode = tenant.get();
		}
		return tenantCode;
	}
	
	public static final void setTenant(String tenantCode) {
		tenant.set(tenantCode);
	}

}
