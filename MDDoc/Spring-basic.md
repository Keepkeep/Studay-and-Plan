# Spring 入门学习
## 目录

#### 1.\<context:annotation-config/\>
它的作用是隐式的向Spring容器注册
-AutowiredAnnotationBeanPostProcessor,
-CommonAnnotationBeanPostProcessor,
-PersistenceAnnotationBeanPostProcessor,
RequiredAnnotationBeanPostProcessor
这4个BeanPostProcessor.注册这4个bean处理器主要的作用是为了你的系统能够识别相应的注解.
出处 : [参考文章](http://www.cnblogs.com/lcngu/p/5080702.html "参考")

#### 2.\<context:component-scan\> 
如果配置了\<context:component-scan\>那么\<context:annotation-config/\>标签就可以不用再xml中配置了，因为前者包含了后者。另外<context:component-scan>还提供了两个子标签
```xml
<context:include-filter>
<context:exclude-filter>
```
>指定的include-filter没有起到作用，只要把use-default-filter设置成false就可以了。这样就可以避免在base-packeage配置多个包名这种不是很优雅的方法来解决这个问题了

#### 3.\<mvc:annotation-driven /\>
它会自动注DefaultAnnotationHandlerMappi和 AnnotationMethodHandlerAdapter
>结论：在spring-servlet.xml中只需要扫描所有带@Controller注解的类，在applicationContext中可以扫描所有其他带有注解的类（也可以过滤掉带@Controller注解的类）。

#### 4.Spring监听类
>org.srpingframework.web.context.ContextLoaderListener 
该监听器实现了ServletContextListener接口，可以在Web容器启动的时候初始化Spring容器。
* web.xml配置
- \<!-- 配置环境参数,指定Spring配置文件的位置 --\>
```xml
<context-param> 
<param-name>contextConfigLocation</param-name> 
<param-value>/WEB-INF/Spring-*.xml</param-value> 
</context-param>
<!-- 配置Spring的ContextLoaderListener监听器,初始化Spring容器 -->
<listener> 
<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class> 
</listener>
```
其中,contextConfigLocation参数用来指定Spring配置文件的路径.可以写文件全名,也可以使用"星号"通配符来加载多个spring的配置文件.
>注意：如果没有指定 contextConfigLocation 参数,ContextLoaderListener默认会查找/WEB-INF/applicationContext.xml.换句话说,如果我们将Spring 的配置文件命名为applicationContext.xml并放在WEB-INF目录下,即使不指定
contextConfigLocation参数,也能实现配置文件的加载.
contextConfigLocation是ContextLoaderListener类的属性.

#### 5.视图解析器配置前缀和后缀
!["xml"](../spring/spring_prefix.jpg)
>如果配置了前缀和后缀,我们的TestController这个Handler中的modelAndView.setViewName("/WEB-INF/jsp/test.jsp");改为modelAndView.setViewName("test")

