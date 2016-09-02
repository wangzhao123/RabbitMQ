package com.taikang.web.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Date;

import com.taikang.session.ISession;
import com.taikang.session.SessionManager;

public class CommonUtil {
	/**
	 * 推送 http请求
	 * 
	 * @param body   推送消息
	 * @param reqUrl 推送接口url
	 * @return
	 * @throws Exception
	 */
	public static String reqUrl(String body,String reqUrl) throws Exception{
		URL url = new URL(reqUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.connect();
		
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(body);
		out.flush();
		out.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
		String result="";
		while((line = in.readLine())!=null){
			result+=line;
		}
		in.close();
		conn.disconnect();
		return result;
	}
	
	
	public static boolean isTimeout(String token){
		SessionManager sessionManager = SessionManager.getInstance();
		ISession session = sessionManager.getSession(token);
		System.out.println(session.getValue("username")+"------->" + session.getValue("syscode"));
		long currentTime = new Date().getTime();//当前时间
		long createTime = (long) session.getValue(token); //token生成时间
		
		if(currentTime - createTime <= 30 * 60 * 1000){//每一次请求时判断 30分钟有效 
			if(sessionManager.hasSession(token)){
				session.putValue(token, currentTime);//没请求一次就会把session里面的初始化改变
				return true;//token有效
			}
			return false;//不正确的token
		}else{//超过30分钟 则删除此session
			sessionManager.removeSession(token);
			return false; //token失效
		}
	}
	
	
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
	 * @throws Exception 
     */
    public static String sendPost(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
           throw e;
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    public static void main(String[] args) throws Exception {
		CommonUtil.reqUrl("1111", "https://example.com");
	}
    
    public static String formatMsg (String result,String message){
    	return "{\"result\":\""+result+"\",\"message\":\""+message+"\"}";
    }
    
    public static Timestamp getSystemDate() {
		return new Timestamp(new Date().getTime());
	}
}
