package com.apising.rpc.proxy;

import com.alibaba.fastjson.JSONObject;
import com.baj.common.exception.XException;
import com.baj.product.client.domain.product.SpuDto;
import com.baj.product.client.service.product.SpuReadService;

public class RpcMain {
	public static void main(String[] args) throws XException, Exception {
		SpuReadService spuReadService = ProxyFactory.getProxy(SpuReadService.class.getName());
		SpuDto spuDto = spuReadService.queryById(33157L);
		System.out.println(JSONObject.toJSONString(spuDto));
	}
}
