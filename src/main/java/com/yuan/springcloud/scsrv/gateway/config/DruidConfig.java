/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.yuan.springcloud.scsrv.gateway.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.http.codec.ServerCodecConfigurer;

import javax.sql.DataSource;

/**
 * The type Druid configuration.
 */

@Configuration
public class DruidConfig {

	/**   mybatis 配置路径     */
	private static String MYBATIS_CONFIG = "mybatis.xml";
	/**   mybatis mapper resource 路径     */
	private static String MAPPER_PATH = "/mapping/*-Mapper.xml";

	private String typeAliasPackage = "com.yuan.springcloud.scsrv";

	/**
	 * Druid data source druid data source.
	 *
	 * @return the druid data source
	 */
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean(name="dataSource",destroyMethod = "close", initMethod = "init")
	public DruidDataSource getDruidDataSource() {

		DruidDataSource druidDataSource = new DruidDataSource();
		return  druidDataSource;
	}


	@Bean("transactionManager")
	public DataSourceTransactionManager getTransactionManager(@Qualifier("dataSource")DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
}
