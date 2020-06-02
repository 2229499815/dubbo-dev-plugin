package com.dubbo.dev.plugin.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.dubbo.dev.plugin.loadbalance.CurrRequest;
import com.dubbo.dev.plugin.loadbalance.LbConst;
import com.dubbo.dev.plugin.loadbalance.ReqMeta;

import java.util.Map;

@Activate(group = {Constants.PROVIDER})
public class ProviderDevFilter implements Filter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ReqMeta reqMeta = new ReqMeta();
        Map<String, String> attachments = invocation.getAttachments();
        String dubboLbHost = attachments.get(LbConst.DUBBO_LB_HOST);
        String dubboLbDefault = attachments.get(LbConst.DUBBO_LB_DEFAULT);
        if (!StringUtils.isBlank(dubboLbHost)) {
            reqMeta.setDubboLbHost(dubboLbHost);
            reqMeta.setDubboLbDefault(dubboLbDefault);
        }
        CurrRequest.setMeta(reqMeta);
        Result result = invoker.invoke(invocation);
        CurrRequest.remove();
        return result;
    }
}
