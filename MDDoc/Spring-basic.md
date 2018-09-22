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