spring.application.name=gateway

server.port=8111

#是否向注册服务器发送心跳信息，证明自己活着，默认true
eureka.client.register-with-eureka=true
#向注册服务器发送心跳信息周期，默认30S
eureka.instance.lease-renewal-interval-in-seconds=5
#是否从注册服务器获取集群中其他服务的注册信息，默认为true
eureka.client.fetch-registry=true
#从注册服务器获取集群中其他服务的注册信息周期，默认30S
eureka.client.registry-fetch-interval-seconds=5

#eureka.client.serviceUrl.defaultZone=http://peer1111:1111/eureka/,http://peer1112:1112/eureka/,http://peer1113:1113/eureka/
#eureka.client.serviceUrl.defaultZone=http://peer1111:1111/eureka/
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848

#curl -X POST host:port/shutdown方式关闭服务
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
#密码
spring.redis.cache.password=


#最大连接数, 默认20个
spring.redis.cache.maxTotal=20
##最大空闲连接数, 默认20个
spring.redis.cache.maxIdle=20
#最小空闲连接数, 默认0
spring.redis.cache.minIdle=8
#获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted), 如果超时就抛异常, 小于零:阻塞不确定的时间, 默认 - 1
spring.redis.cache.maxWait=1000
#明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
spring.redis.cache.testOnBorrow=true
#在return给pool时，是否提前进行validate操作
spring.redis.cache.testOnReturn=false
#在空闲时检查有效性, 默认false
spring.redis.cache.testWhileIdle=true
#表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
spring.redis.cache.minEvictableIdleTimeMillis=1800000
#表示idle object evitor每次扫描的最多的对象数
spring.redis.cache.numTestsPerEvictionRun=3
#在minEvictableIdleTimeMillis基础上，加入了至少minIdle个对象已经在pool里面了。
#如果为-1，evicted不会根据idle time驱逐任何对象。如果minEvictableIdleTimeMillis>0，
#则此项设置无意义，且只有在timeBetweenEvictionRunsMillis大于0时才有意义
spring.redis.cache.softMinEvictableIdleTimeMillis=1800000
#表示idle object evitor两次扫描之间要sleep的毫秒数
spring.redis.cache.timeBetweenEvictionRunsMillis=60000

#客户端超时时间单位是毫秒t
spring.redis.cache.timeout=2000
#最大重连次数
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

spring.cloud.gateway.routes[1].id=SCSRV_TEST
spring.cloud.gateway.routes[1].uri=lb://SCSRV
spring.cloud.gateway.routes[1].predicates[0]=Path=/scsrv_Test/**
spring.cloud.gateway.routes[1].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[1].filters[1]=CheckToken=ACCESS_TOKEN

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
