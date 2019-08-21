package com.apising.rpc.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProxyFactory {

	private static Map<String, Object> proxys;

	private static Properties prop;

	static {
		proxys = new HashMap<>();
		prop = new Properties();
		InputStream in = ProxyFactory.class.getClassLoader().getResourceAsStream("config/env.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Map.Entry<Object, Object> item : prop.entrySet()){
			String apiInfo = (String) item.getKey();
			String[] apiInfoArr = apiInfo.split("\\.");
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i < apiInfoArr.length - 1; i ++){
				sb.append(apiInfoArr[i]);
				if(apiInfoArr.length - 2 != i){
					sb.append(".");
				}
			}
			String interfaceName = sb.toString();
			MyProxy proxy = new MyProxy();
			try {
				Class<?> interfaceClass = Class.forName(interfaceName);
				proxy.setInterfaceClass(interfaceClass);
				Object obj = proxy.createProxyObj();
				proxys.put(interfaceName, obj);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(String key){
		return (T) proxys.get(key);
	}
	
	public static String getUrl(String interfaceName){
		return prop.getProperty(interfaceName);
	}

}
