package com.spl.safetyNet.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class BuildInfoContributor implements InfoContributor{



	@Override
	public void contribute(Builder builder) {
		// TODO Auto-generated method stub
		Map<String,String> data= new HashMap<String,String>();
        data.put("build.version", "2.0.0.M7");
        builder.withDetail("buildInfo", data);
	}

}
