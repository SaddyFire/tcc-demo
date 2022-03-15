## 新增事务表:

xid:全局事务id

user_id:用户id

freeze_money:冻结金额

state:事务状态 0:try, 1:confirm, 2:cancel



## 业务:

Try业务:

1. 记录冻结金额和事务状态
2. 扣减account表可用金额

Confirm业务:

1. 根据xid删除冻结记录

Cancel业务:

1. 修改事务表, 冻结金额为0, state为2
2. 修改account表, 恢复可用金额

如何判断是否空回滚:

cancel中, 根据xid查询事务表, 如果为null说明try还没做, 需要回滚

如何避免业务悬挂:

try业务中, 根据xid查询account_freeze, 如果已经存在则证明Cancel已经执行, 拒绝执行try业务



## 代码

1. 依赖, 配置
2. com.baomidou.mybatisplus.annotation.IdType    冻结实体类
3. cn.itcast.account.service.AccountTCCService    TCC接口
4. cn.itcast.account.mapper.AccountMapper    账户mapper
5. cn.itcast.account.service.impl.AccountTCCServiceImpl    TCC实现类
6. 空回滚的判断  public boolean cancel(BusinessActionContext ctx);
7. 业务悬挂的判断  public void deduct(String userId, int money);