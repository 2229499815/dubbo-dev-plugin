package com.dubbo.dev.plugin.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.dubbo.dev.plugin.loadbalance.CurrRequest;
import com.dubbo.dev.plugin.loadbalance.LbConfig;
import com.dubbo.dev.plugin.loadbalance.LbConst;
import com.dubbo.dev.plugin.loadbalance.ReqMeta;

/**
 * @author 86180
 * @Description:
 * @date 2020/6/210:20
 */
@Activate(group = {Constants.CONSUMER})
public class ConsumerDevFilter implements Filter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ReqMeta meta = CurrRequest.getMeta();
        boolean b = invocation.getAttachments().containsKey(LbConst.DUBBO_LB_HOST);
        if(!b){
            CurrRequest.setAttachment(meta);
        }
        //此filter解决问题：调用链中，间隔性存在负载时，需要优先调用至本机
        Result result = invoker.invoke(invocation);
        return result;
    }
}
