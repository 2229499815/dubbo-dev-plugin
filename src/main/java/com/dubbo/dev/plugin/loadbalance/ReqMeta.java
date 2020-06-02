package com.dubbo.dev.plugin.loadbalance;

/**
 * @author 86180
 * @Description: 请求源数据
 * @date 2020/6/19:25
 */
public class ReqMeta {
    private String dubboLbHost = "127.0.0.1";

    private String dubboLbDefault = "127.0.0.1";

    public String getDubboLbHost() {
        return dubboLbHost;
    }

    public void setDubboLbHost(String dubboLbHost) {
        this.dubboLbHost = dubboLbHost;
    }

    public String getDubboLbDefault() {
        return dubboLbDefault;
    }

    public void setDubboLbDefault(String dubboLbDefault) {
        this.dubboLbDefault = dubboLbDefault;
    }

}
