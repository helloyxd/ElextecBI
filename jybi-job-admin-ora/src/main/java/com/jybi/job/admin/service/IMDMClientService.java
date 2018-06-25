package com.jybi.job.admin.service;

import com.jybi.job.admin.core.model.MdmUser;

public interface IMDMClientService {
	public MdmUser getMdmUser(String userId,String userName);
}
