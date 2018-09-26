package com.tct.user.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 20, 2018 12:09
 */
@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
public class HibernateConfiguration {

    private static Logger log = LoggerFactory.getLogger(HibernateConfiguration.class);

    @Value("${mysql.datasource.readSize}")
    private String readDataSourceSize;

    @Autowired
    @Qualifier("writeDataSource")
    private DataSource writeDataSource;
    @Autowired
    @Qualifier("readDataSource01")
    private DataSource readDataSource01;
    /*@Autowired
    @Qualifier("readDataSource02")
    private DataSource readDataSource02;*/

    /*@Bean(name="sessionfactory")
//    @ConfigurationProperties(prefix = "mysql.sessionfactory")
    public EntityManagerFactory getSessionFactorys() throws Exception {
        log.info("--------------------  sqlSessionFactory init ---------------------");
        try {
            LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
            sessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
            sessionFactoryBean.setAnnotatedPackages("com.tct.*.entity");
            *//*sessionFactoryBean.setPhysicalNamingStrategy(new PhysicalNamingStrategyStandardImpl());
            sessionFactoryBean.setImplicitNamingStrategy(new ImplicitNamingStrategyLegacyHbmImpl());

            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));*//*

            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error("hibernate SessionFactoryBean create error",e);
            return null;
        }
    }*/

    /**
     * 把所有数据库都放在路由中
     * @return
     */
    @Primary
    @Bean(name="dataSource")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一至，
        //否则切换数据源时找不到正确的数据源
        targetDataSources.put(DataSourceType.write.getType(), writeDataSource);
        targetDataSources.put(DataSourceType.read.getType()+"1", readDataSource01);
//        targetDataSources.put(DataSourceType.read.getType()+"2", readDataSource02);

        final int readSize = Integer.parseInt(readDataSourceSize);
        //     MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(readSize);

        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource(){
            private AtomicInteger count = new AtomicInteger(0);
            /**
             * 这是AbstractRoutingDataSource类中的一个抽象方法，
             * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
             * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
             */
            @Override
            protected Object determineCurrentLookupKey() {
                String typeKey = DataSourceContextHolder.getReadOrWrite();

                if(typeKey == null){
                    //	log.info("使用数据库write.............");
                    //    return DataSourceType.write.getType();
                    throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为空...");
                }

                if (typeKey.equals(DataSourceType.write.getType())){
                    log.info("使用数据库write.............");
                    return DataSourceType.write.getType();
                }

                //读库， 简单负载均衡
                int number = count.getAndAdd(1);
                int lookupKey = number % readSize;
                log.info("使用数据库read-"+(lookupKey+1));
                return DataSourceType.read.getType()+(lookupKey+1);
            }
        };

        proxy.setDefaultTargetDataSource(writeDataSource);//默认库
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

}
