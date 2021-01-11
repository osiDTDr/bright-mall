package cn.iocoder.mall.systemservice.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置
 *
 * @author zhengyuan
 * @since 2020/01/09
 */
@Configuration
@MapperScan(basePackages = "cn.iocoder", sqlSessionFactoryRef = "sessionFactory")
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private Environment environment;

    /**
     * function datasource
     */
    @Value("${function.datasource.jdbc.driverClassName}")
    private String functionDbDriver;

    @Value("${function.datasource.jdbc.url}")
    private String functionDbUrl;

    @Value("${function.datasource.jdbc.username}")
    private String functionDbUsername;

    @Value("${function.datasource.jdbc.password}")
    private String functionDbPassword;

    /**
     * regression datasource
     */
    @Value("${regression.datasource.jdbc.driverClassName}")
    private String regressionDbDriver;

    @Value("${regression.datasource.jdbc.url}")
    private String regressionDbUrl;

    @Value("${regression.datasource.jdbc.username}")
    private String regressionDbUsername;

    @Value("${regression.datasource.jdbc.password}")
    private String regressionDbPassword;


    /**
     * 创建 Functional dataSource
     */
    @Bean
    @Primary
    public DataSource functionalDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(functionDbDriver);
        dataSource.setUrl(functionDbUrl);
        dataSource.setUsername(functionDbUsername);
        dataSource.setPassword(functionDbPassword);

        return dataSource;
    }

    /**
     * 创建 回归环境 dataSource
     */
    @Bean
    public DataSource regressionDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(regressionDbDriver);
        dataSource.setUrl(regressionDbUrl);
        dataSource.setUsername(regressionDbUsername);
        dataSource.setPassword(regressionDbPassword);

        return dataSource;
    }

    /**
     * 1、创建动态数据源
     */
    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("functionalDataSource") DataSource functionalDataSource,
                                               @Qualifier("regressionDataSource") DataSource regressionDataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DatabaseType.FUNCTION, functionalDataSource);
        targetDataSource.put(DatabaseType.REGRESSION, regressionDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(regressionDataSource);

        return dataSource;
    }

    /**
     * 2、根据数据源创建SqlSessionFactory
     *
     * @throws Exception exception
     */
    @Bean
    @Primary
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String mapperLocations = environment.getProperty("mybatis.mapperLocations");
        logger.warn("mapper location path is {}", mapperLocations);
        if (StringUtils.isNotEmpty(mapperLocations)) {
            sessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));    //*Mapper.xml位置
        }
        return sessionFactoryBean.getObject();
    }
}
