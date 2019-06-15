spring.application.name=gateway

server.port=8111

#�Ƿ���ע�����������������Ϣ��֤���Լ����ţ�Ĭ��true
eureka.client.register-with-eureka=true
#��ע�����������������Ϣ���ڣ�Ĭ��30S
eureka.instance.lease-renewal-interval-in-seconds=5
#�Ƿ��ע���������ȡ��Ⱥ�����������ע����Ϣ��Ĭ��Ϊtrue
eureka.client.fetch-registry=true
#��ע���������ȡ��Ⱥ�����������ע����Ϣ���ڣ�Ĭ��30S
eureka.client.registry-fetch-interval-seconds=1

#eureka.client.serviceUrl.defaultZone=http://peer1111:1111/eureka/,http://peer1112:1112/eureka/,http://peer1113:1113/eureka/
#eureka.client.serviceUrl.defaultZone=http://peer1111:1111/eureka/
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848

#curl -X POST host:port/shutdown��ʽ�رշ���
#endpoints.shutdown.enabled=true
spring.boot.admin.url=http://localhost:8090

server.tomcat.uri-encoding=utf-8

#logging.config=classpath:logback-spring.xml
#logging.path=/Users/yuan/logs/gateway
#LOGGING.PATH=/Users/yuan/logs/gateway

LOG_HOME=/Users/yuan/logs/gateway


#-------------datasource--------------
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=CTT
spring.datasource.username=root
spring.datasource.password=111111
spring.datasource.poolPingConnectionsNotUsedFor=60000
spring.datasource.removeAbandoned=true
spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
spring.datasource.minIdle=1
spring.datasource.validationQuery=SELECT 1 FROM DUAL
spring.datasource.initialSize=5
spring.datasource.maxWait=60000
spring.datasource.poolPreparedStatements=false
spring.datasource.filters=stat,wall
spring.datasource.testOnBorrow=false
spring.datasource.testWhileIdle=true
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.timeBetweenEvictionRunsMillis=60000
spring.datasource.testOnReturn=false
spring.datasource.maxActive=50
#--------------end----------------------

#spring.security.user.name=admin
#spring.security.user.password=111111
#spring.security.basic.enabled=false

#spring.cloud.gateway.routes[0].id=SCSRV
#spring.cloud.gateway.routes[0].uri=lb://SCSRV
#spring.cloud.gateway.routes[0].predicates[0]=Path=/scsrv/**
#spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
##spring.cloud.gateway.routes[0].filters[1]=PreOauth2SSOGatewayFilter


#-------------redis cluster-------------
spring.redis.cache.serverPort=7000,7001,7002,7003,7004,7005
#����
spring.redis.cache.password=


#���������, Ĭ��20��
spring.redis.cache.maxTotal=20
##������������, Ĭ��20��
spring.redis.cache.maxIdle=20
#��С����������, Ĭ��0
spring.redis.cache.minIdle=8
#��ȡ����ʱ�����ȴ�������(�������Ϊ����ʱBlockWhenExhausted), �����ʱ�����쳣, С����:������ȷ����ʱ��, Ĭ�� - 1
spring.redis.cache.maxWait=1000
#���Ƿ��ڴӳ���ȡ������ǰ���м���,�������ʧ��,��ӳ���ȥ�����Ӳ�����ȡ����һ��
spring.redis.cache.testOnBorrow=true
#��return��poolʱ���Ƿ���ǰ����validate����
spring.redis.cache.testOnReturn=false
#�ڿ���ʱ�����Ч��, Ĭ��false
spring.redis.cache.testWhileIdle=true
#��ʾһ����������ͣ����idle״̬�����ʱ�䣬Ȼ����ܱ�idle object evitorɨ�貢������һ��ֻ����timeBetweenEvictionRunsMillis����0ʱ��������
spring.redis.cache.minEvictableIdleTimeMillis=1800000
#��ʾidle object evitorÿ��ɨ������Ķ�����
spring.redis.cache.numTestsPerEvictionRun=3
#��minEvictableIdleTimeMillis�����ϣ�����������minIdle�������Ѿ���pool�����ˡ�
#���Ϊ-1��evicted�������idle time�����κζ������minEvictableIdleTimeMillis>0��
#��������������壬��ֻ����timeBetweenEvictionRunsMillis����0ʱ��������
spring.redis.cache.softMinEvictableIdleTimeMillis=1800000
#��ʾidle object evitor����ɨ��֮��Ҫsleep�ĺ�����
spring.redis.cache.timeBetweenEvictionRunsMillis=60000

#�ͻ��˳�ʱʱ�䵥λ�Ǻ���t
spring.redis.cache.timeout=2000
#�����������
spring.redis.cache.maxRedirections=3

spring.redis.cache.clusterNodes=127.0.0.1:7000,127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003,127.0.0.1:7004,127.0.0.1:7005

management.health.redis.enabled=false
#cluster
#-------------------end-----------------

# accessToken config
AccessToken.StoreTime.Unit = ONE_HOUR
AccessToken.StoreTime.Value = 1

# refreshToken config
RefreshToken.StoreTime.Unit = ONE_HOUR
RefreshToken.StoreTime.Value = 1


#--------------Lettuce redis---------
lettuce.redis.autoReconnect=true
lettuce.redis.maxRedirects=3
lettuce.redis.clusterNodes=redis://127.0.0.1:7000,redis://127.0.0.1:7001,redis://127.0.0.1:7002,redis://127.0.0.1:7003,redis://127.0.0.1:7004,redis://127.0.0.1:7005

#--------------Lettuce redis end---------

spring.cloud.gateway.routes[0].id=TOKEN_SRV
spring.cloud.gateway.routes[0].uri=lb://SCSRV
spring.cloud.gateway.routes[0].predicates[0]=Path=/token_Fresh/**
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[0].filters[1]=RefreshAccessToken=user_id

#spring.cloud.gateway.routes[1].id=SCSRV_TEST
#spring.cloud.gateway.routes[1].uri=lb://SCSRV
#spring.cloud.gateway.routes[1].predicates[0]=Path=/scsrv_Test/**
#spring.cloud.gateway.routes[1].filters[0]=StripPrefix=1
#spring.cloud.gateway.routes[1].filters[1]=CheckToken=ACCESS_TOKEN


spring.cloud.gateway.routes[1].id=SCSRV_TEST
spring.cloud.gateway.routes[1].uri=lb://SCSRV
spring.cloud.gateway.routes[1].predicates[0]=Path=/scsrv_Test/**
spring.cloud.gateway.routes[1].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[1].filters[1]=Retry
#spring.cloud.gateway.routes[1].filters[1].args=retries=15
spring.cloud.gateway.routes[1].filters[1].args.



spring.cloud.gateway.routes[2].id=SCSRV_USER
spring.cloud.gateway.routes[2].uri=lb://SCSRV
spring.cloud.gateway.routes[2].predicates[0]=Path=/scsrv_User/**
spring.cloud.gateway.routes[2].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[2].filters[1]=CheckToken=ACCESS_TOKEN

spring.cloud.gateway.routes[3].id=SCSRV_GRADE
spring.cloud.gateway.routes[3].uri=lb://SCSRV
spring.cloud.gateway.routes[3].predicates[0]=Path=/scsrv_Grade/**
spring.cloud.gateway.routes[3].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[3].filters[1]=CheckToken=ACCESS_TOKEN

spring.cloud.gateway.routes[4].id=SCSRV_LOGIN
spring.cloud.gateway.routes[4].uri=lb://SCSRV
spring.cloud.gateway.routes[4].predicates[0]=Path=/scsrv_Login/**
spring.cloud.gateway.routes[4].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[4].filters[1]=BuildToken=user_id


spring.cloud.gateway.routes[5].id=SCSRV_EXIT
spring.cloud.gateway.routes[5].uri=lb://SCSRV
spring.cloud.gateway.routes[5].predicates[0]=Path=/scsrv_Exit/**
spring.cloud.gateway.routes[5].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[5].filters[1]=ClearToken=user_id
