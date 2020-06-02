package com.dubbo.dev.plugin.loadbalance;

import com.alibaba.dubbo.common.utils.ConfigUtils;

import java.util.Properties;

/**
 * @author 86180
 * @Description:
 * @date 2020/6/116:13
 */
public class LbConfig {
    private final static Properties lbConfig = ConfigUtils.loadProperties("lb-dev.properties");

    public static Properties getConf() {
        return lbConfig;
    }
}
