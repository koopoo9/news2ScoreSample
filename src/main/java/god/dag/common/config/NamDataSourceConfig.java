package god.dag.common.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@MapperScan(value = {"kcb.ids.idssampler.dao.nam"}, sqlSessionFactoryRef = "namSqlSessionFactory")
@Slf4j
public class NamDataSourceConfig {

    @Value("20")
    private int sqlTimeout;

    @Primary
    @Bean(name = "namJndiDataSource")
    public DataSource dataSource() throws IllegalArgumentException, NamingException {
        val bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/kcbNAM");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(true);
        bean.afterPropertiesSet();

        val proxyDataSource = new Log4jdbcProxyDataSource((DataSource) bean.getObject());
        val format = new Log4JdbcCustomFormatter();
        format.setLoggingType(LoggingType.SINGLE_LINE);
        proxyDataSource.setLogFormatter(format);
        return proxyDataSource;
    }

    @Primary
    @Bean(name = "namTxManager")
    public PlatformTransactionManager txManager(@Qualifier("namJndiDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "namSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("namJndiDataSource") DataSource dataSource, ApplicationContext context) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:mapper/**/*.xml"));
        val config = Objects.requireNonNull(sqlSessionFactoryBean.getObject()).getConfiguration();
        config.setJdbcTypeForNull(JdbcType.NULL);
        config.setCallSettersOnNulls(true);
        config.setDefaultStatementTimeout(sqlTimeout);
        config.setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "namSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("namSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}