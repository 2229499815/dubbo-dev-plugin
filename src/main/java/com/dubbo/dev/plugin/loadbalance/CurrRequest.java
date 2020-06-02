package com.dubbo.dev.plugin.loadbalance;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcContext;

/**
 * @author 86180
 * @Description: 当前请求
 * @date 2020/6/19:22
 */
public class CurrRequest {
    private static ThreadLocal<ReqMeta> metaThreadLocal = new ThreadLocal<ReqMeta>();

    public static ReqMeta getMeta() {
        return metaThreadLocal.get();
    }

    public static void setMeta(ReqMeta reqMeta) {
        metaThreadLocal.set(reqMeta);
    }

    public static void remove() {
        metaThreadLocal.remove();
    }

    public static ReqMeta setAttachment(ReqMeta meta){
        String dubboLbHost = null;
        String dubboLbDefault = null;
        if (meta != null && !StringUtils.isBlank(meta.getDubboLbHost())) {
            dubboLbHost = meta.getDubboLbHost();
            dubboLbDefault = meta.getDubboLbDefault();
            RpcContext.getContext().setAttachment(LbConst.DUBBO_LB_HOST, dubboLbHost);
            RpcContext.getContext().setAttachment(LbConst.DUBBO_LB_DEFAULT, dubboLbDefault);
        } else {
            dubboLbHost = LbConfig.getConf().getProperty(LbConst.DUBBO_LB_HOST, "127.0.0.1");
            dubboLbDefault = LbConfig.getConf().getProperty(LbConst.DUBBO_LB_DEFAULT, "127.0.0.1");
            RpcContext.getContext().setAttachment(LbConst.DUBBO_LB_HOST, dubboLbHost);
        }
        ReqMeta reqMeta=new ReqMeta();
        reqMeta.setDubboLbHost(dubboLbHost);
        reqMeta.setDubboLbDefault(dubboLbDefault);
        return reqMeta;
    }

}
