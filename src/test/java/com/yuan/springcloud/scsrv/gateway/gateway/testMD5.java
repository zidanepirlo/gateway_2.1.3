package com.yuan.springcloud.scsrv.gateway.gateway;

import com.yuan.springcloud.scsrv.gateway.utils.MD5Utils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * testMD5
 *
 * @author yuanqing
 * @create 2019-06-15 22:30
 **/
public class testMD5 {

    private static final Logger logger = LoggerFactory.getLogger(testMD5.class);

    @Test
    public void md5Verify() throws Exception {

        String md5Str = MD5Utils.md5("yuanqing","123456");
        boolean isVerify = MD5Utils.verify("yuanqing","123456",md5Str);
        logger.info("isVerify={}",isVerify);
    }
}
