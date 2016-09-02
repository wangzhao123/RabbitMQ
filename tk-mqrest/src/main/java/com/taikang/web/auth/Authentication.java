package com.taikang.web.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import sun.misc.BASE64Encoder;

/**
 * @describe 用户认证
 * @author wangzhao
 * 
 */
@SuppressWarnings("restriction")
public class Authentication implements EnvironmentAware{
	
	private static Map<String , Object> params = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static boolean validateLogin(String username ,String password) throws Exception{
		
		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
			
			String host = (String) params.get("host");
			String port = (String) params.get("port");
			
			String auth = getBase64(username+":"+password);
			URL url = new URL("http://"+host+":"+port+"/api/whoami");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.addRequestProperty("Authorization", "Basic "+auth);
			conn.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result="";
			while((line = in.readLine())!=null){
				result+=line;
			}
			in.close();
			//返回信息 {"name":"admin","tags":"administrator"}
			Map<String,String> map = (Map<String, String>) JSON.parse(result);
			if(map!=null && map.size()>0){
				for(String key : map.keySet()){
					String value = map.get(key);
					if(username.equals(value)){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void setEnvironment(Environment environment) {
		getInitProperties(environment);
	}

	private void getInitProperties(Environment environment) {
		RelaxedPropertyResolver data = new RelaxedPropertyResolver(environment, "spring.rabbitmq.");
		params.put("host",data.getProperty("host"));
		params.put("port",data.getProperty("clientPort"));
	}
	
	// 加密  
	private static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
//
//    // 解密  
//    private static String getFromBase64(String s) {  
//        byte[] b = null;  
//        String result = null;  
//        if (s != null) {  
//            BASE64Decoder decoder = new BASE64Decoder();  
//            try {  
//                b = decoder.decodeBuffer(s);  
//                result = new String(b, "utf-8");  
//            } catch (Exception e) {  
//                e.printStackTrace();  
//            }  
//        }  
//        return result;  
//	}
}
