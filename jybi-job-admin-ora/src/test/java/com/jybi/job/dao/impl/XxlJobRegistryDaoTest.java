package com.jybi.job.dao.impl;

import com.jybi.job.admin.core.model.XxlJobRegistry;
import com.jybi.job.admin.dao.XxlJobRegistryDao;
import com.jybi.job.core.enums.RegistryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationcontext-*.xml")
public class XxlJobRegistryDaoTest {

    @Resource
    private XxlJobRegistryDao xxlJobRegistryDao;

    @Test
    public void test(){
        int ret = xxlJobRegistryDao.registryUpdate("g1", "k1", "v1");
        if (ret < 1) {
            ret = xxlJobRegistryDao.registrySave("g1", "k1", "v1");
        }

        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND,-1);
        List<XxlJobRegistry> list = xxlJobRegistryDao.findAll(now.getTime());

        int ret2 = xxlJobRegistryDao.removeDead(now.getTime());
    }

}
