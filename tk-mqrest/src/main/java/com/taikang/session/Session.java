package com.taikang.session;

import java.util.HashMap;
import java.util.Map;

public class Session implements ISession {

	private Map<String, Object> values = new HashMap<>();
	
	public Session() {
	}

	@Override
	public void putValue(String key, Object obj) {
		values.put(key, obj);
	}

	@Override
	public Object getValue(String key) {
		return values.get(key);
	}

}
