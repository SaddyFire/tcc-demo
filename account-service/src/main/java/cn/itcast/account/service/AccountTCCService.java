package cn.itcast.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface AccountTCCService {

    // @TwoPhaseBusinessAction 此注解标记是代表TCC中的Try,name属性要与当前方法名一致，用于指定Try逻辑对应的方法
    // @BusinessActionContextParameter	此注解是把形参数据保存到 BusinessActionContext 中
    @TwoPhaseBusinessAction(name = "deduct", commitMethod = "confirm", rollbackMethod = "cancel")
    void deduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                @BusinessActionContextParameter(paramName = "money")int money);

    /**
     * 二阶段confirm确认方法、可以另命名，但要保证与commitMethod一致
     * @param ctx 上下文,可以传递try方法的参数
     * @return boolean 执行是否成功
     * */
    boolean confirm(BusinessActionContext ctx);
    /**
     * 二阶段回滚方法，要保证与rollbackMethod一致
     */
    boolean cancel(BusinessActionContext ctx);
}