package com.taikang.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class SendDataRestApi {
	
	public static void main(String[] args) throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"name\"").append(":").append("\"world\"");
		sb.append("}");
		
		String messageId = UUID.randomUUID().toString();
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		buf.append("\"token\"").append(":").append("\"f56804c05373f53acc5f9f21e3fc64b0d8a93186\"").append(",");
		buf.append("\"sysCode\"").append(":").append("\"app\"").append(",");
		buf.append("\"destination\"").append(":").append("\"ww\"").append(",");
		buf.append("\"messageId\"").append(":").append("\""+messageId+"\"").append(",");
		buf.append("\"body\"").append(":").append("\"hello world\"").append(",");
		buf.append("\"transitionId\"").append(":").append("\"83e76ba2-956f-44c4-a550-3713778be748\"");
		buf.append("}");
		

		
		sendPost("http://localhost:8080/mqrest/produce?time="+System.currentTimeMillis(), new String(("params="+buf.toString()).getBytes(),"utf-8"));
//		}
	}
	
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
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
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
}
