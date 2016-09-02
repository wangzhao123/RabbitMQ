package com.taikang.session;

public interface ISession {
	public void putValue(String key, Object obj);
	
	public Object getValue(String key);
}
