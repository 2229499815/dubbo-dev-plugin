package com.dubbo.dev.plugin.loadbalance;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import com.alibaba.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;

import java.util.List;

/**
 * @author 86180
 * @Description: 扩展负载
 * @date 2020/6/19:20
 */
public class DevLoadBalance extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> list, URL url, Invocation invocation) {
        ReqMeta meta = CurrRequest.getMeta();
        ReqMeta reqMeta = CurrRequest.setAttachment(meta);
        String dubboLbHost = reqMeta.getDubboLbHost();
        String dubboLbDefault = reqMeta.getDubboLbDefault();
        //获取当前配置负载
        if (dubboLbHost != null) {
            for (Invoker<T> invoker : list) {
                if (invoker.getUrl().getHost().matches(dubboLbHost)) {
                    return invoker;
                }
            }
        }
        //获取默认的配置
        String[] split = dubboLbDefault.split(",");
        for (String pattern : split) {
            for (Invoker<T> invoker : list) {
                if (invoker.getUrl().getHost().matches(pattern)) {
                    return invoker;
                }
            }
        }

        return new RandomLoadBalance().select(list, url, invocation);
    }
}
