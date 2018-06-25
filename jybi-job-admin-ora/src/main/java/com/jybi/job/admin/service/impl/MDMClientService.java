package com.jybi.job.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.jybi.job.admin.core.model.MdmUser;
import com.jybi.job.admin.service.IMDMClientService;

@Service
public class MDMClientService implements IMDMClientService {
	@Autowired  
    RestTemplate restTemplate; 
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	public MdmUser getMdmUser(String userId,String userName) {
		String url = "http://localhost:8080/mdm/mdm/user/getUser?id="+userId+"&userName="+userName;
        
		 List messageConverters=new ArrayList();
		 messageConverters.add(new SourceHttpMessageConverter());
		 messageConverters.add(new FormHttpMessageConverter());
		 messageConverters.add(new MappingJacksonHttpMessageConverter());
		 restTemplate.setMessageConverters(messageConverters);
       

        Map m = restTemplate.getForObject (url, Map.class);
        if(m==null) {
        	return null;
        }else {
        	MdmUser u = new MdmUser();
        	u.setUserName((String) m.get("userName"));

            return u;
        }
        
        
	}
}
