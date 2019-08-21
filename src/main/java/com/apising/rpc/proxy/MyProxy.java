package com.apising.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.alibaba.fastjson.JSONObject;
import com.baj.common.exception.XException;
import com.baj.common.util.HttpUtil;
import com.google.common.base.Objects;

public class MyProxy implements InvocationHandler{
	
	private Class<?> interfaceClass;
	
	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String url = ProxyFactory.getUrl(interfaceClass.getName() + "." + method.getName());
		JSONObject json = HttpUtil.getJsonDataBodyByGet(url + "?id=" + args[0]);
		Class<?> returnType = method.getReturnType();
		if(!Objects.equal(json.getInteger("code"), 0)){
			throw new XException(json.getString("msg")).setShowText(json.getString("msg"));
		}
		Object result = json.getJSONObject("result").getJSONObject("data").toJavaObject(returnType);
		return result;
	}
	
	public Object createProxyObj(){
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, this);
	}

}
