package com.proaimltd.web.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @project: springboot-demo
 * @packageName: com.proaimltd.web.config
 * @author: fengkun.zhao
 * @date: 2019/9/15 1:28
 * @description：TODO
 */
@Slf4j
@Configuration
@MapperScan(value = "com.proaimltd.web.**.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfig implements TransactionManagementConfigurer {
	@Autowired
	private AppConfigBean appConfigBean;

	@Primary
	@Bean(name = "realDataSource")
	DataSource realDataSource() throws SQLException {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(appConfigBean.getJdbcUrl());
		dataSource.setDriverClassName(appConfigBean.getDriverClassName());
		dataSource.setUsername(appConfigBean.getJdbcUsername());
		dataSource.setPassword(appConfigBean.getJdbcPassword());
		return dataSource;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("realDataSource") DataSource dataSource) throws SQLException {

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);

		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			factoryBean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
			factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
			return factoryBean.getObject();
		} catch (Exception e) {
			log.warn("getSqlSessionFactory failed, errorMessage:{}", e);
			throw new RuntimeException(e);
		}
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		try {
			return new DataSourceTransactionManager(realDataSource());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}