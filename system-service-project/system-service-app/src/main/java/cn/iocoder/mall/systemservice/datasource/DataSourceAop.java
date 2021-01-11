package cn.iocoder.mall.systemservice.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 数据源 aop 注入
 *
 * @author zhengyuan
 * @since 2020/01/09
 */
@Aspect
@Component
public class DataSourceAop {
    @Before("execution(* cn.iocoder.mall.systemservice.manager..*(..))")
    public void setDataSource2test01() {
        DatabaseContextHolder.setDatabaseType(DatabaseType.FUNCTION);
    }

    @Before("execution(* cn.iocoder.mall.systemservice.rpc..*(..))")
    public void setDataSource2test02() {
        DatabaseContextHolder.setDatabaseType(DatabaseType.REGRESSION);
    }
}