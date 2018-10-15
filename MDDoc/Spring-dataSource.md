# Spring 配置数据源

## 目录

#### 写属性及配置文件
```xml
#mysql数据库
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test
jdbc.username=root
jdbc.password=root

#oracle数据库
jdbc.driver=oracle.jdbc.driver.OracleDriver
jdbc.url=jdbc:oracle:thin:@localhost:1521/orcl
#jdbc.url=jdbc:oracle:thin:@localhost:1521:orcl
jdbc.username=root
jdbc.password=oracle

#sqlserver数据库
jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc.url=jdbc:microsoft.sqlserver://127.0.0.1:1433;DatabaseName=zhidao
jdbc.username=root
jdbc.password=oracle
```
#### applicationContext.xml

```xml
<!--数据源配置,使用Tomcat jdbc pool-->    
<bean id="dataSource" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">    
    <!--Connection Info-->
    <property name="driverClassName" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```