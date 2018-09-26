package com.tct.user.aop;

import com.tct.user.annotation.ReadDataSource;
import com.tct.user.annotation.WriteDataSource;
import com.tct.user.config.DataSourceContextHolder;
import com.tct.user.config.DataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 20, 2018 17:59
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
@Component
public class DataSourceAdvice implements PriorityOrdered {

    private static Logger log = LoggerFactory.getLogger(DataSourceAdvice.class);



    /*@Before("execution(* com.tct.user.service..*.*(..)) "
            + " && @annotation(com.tct.user.annotation.ReadDataSource) ")
    public void setReadDataSourceType() {
        //如果已经开启写事务了，那之后的所有读都从写库读
        if(!DataSourceType.write.getType().equals(DataSourceContextHolder.getReadOrWrite())){
            DataSourceContextHolder.setRead();
        }
    }

    @Before("execution(* com.tct.user.service..*.*(..)) "
            + " && @annotation(com.tct.user.annotation.WriteDataSource) ")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.setWrite();
    }*/

    @Before(value = "execution(* com.tct.user.service..*.*(..)) "
            /* + "&& (@annotation(com.tct.user.annotation.WriteDataSource)" +
            "|| @annotation(com.tct.user.annotation.ReadDataSource)" +
            "|| @annotation(org.springframework.transaction.annotation.Transactional))"*/)
    public void setDataSourceType(JoinPoint point) {
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        if(!DataSourceType.write.getType().equals(DataSourceContextHolder.getReadOrWrite())){
            Transactional transactional = AnnotationUtils.findAnnotation(method, Transactional.class);
            if(transactional != null && transactional.readOnly()){
                DataSourceContextHolder.setRead();
                return;
            }

            WriteDataSource writer = AnnotationUtils.findAnnotation(method, WriteDataSource.class);
            if(writer != null){
                DataSourceContextHolder.setWrite();
                return;
            }

            ReadDataSource reader = AnnotationUtils.findAnnotation(method, ReadDataSource.class);
            if(reader != null){
                DataSourceContextHolder.setRead();
                return;
            }
        }
        DataSourceContextHolder.setWrite();
    }

    @AfterThrowing(value = "execution(* com.tct.user.service..*.*(..)) ")
    public void setWriteDataSourceType() {
        log.error("数据库异常，切换到读库");
        DataSourceContextHolder.setWrite();
    }

    @Override
    public int getOrder() {
        /**
         * 值越小，越优先执行
         * 要优于事务的执行
         * 在启动类中加上了@EnableTransactionManagement(order = 10)
         */
        return 1;
    }

}
