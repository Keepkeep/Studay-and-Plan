# Spring-resource 学习笔记

## 目录
####

>编码的时候，除了代码本身，我们还需要对外部的资源进行处理。例如：URL资源、URI资源、File资源、ClassPath相关资源、服务器相关资源（VFS等）等等。
而这些资源的处理是类似而繁琐的，如：打开资源、读取资源、关闭资源。
所以Spring提供了一个专门的接口Resource 用于统一这些底层资源的访问。

- Spring的Resource接口代表底层外部资源，提供了对底层外部资源的一致性访问接口。

####  源码
```java
public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
}
public interface Resource extends InputStreamSource {
    boolean exists();
    boolean isReadable();
    boolean isOpen();
    URL getURL() throws IOException;
    URI getURI() throws IOException;
    File getFile() throws IOException;
    long contentLength() throws IOException;
    long lastModified() throws IOException;
    Resource createRelative(String relativePath) throws IOException;
    String getFilename();
    String getDescription();
}
```

#### 接口提供了抽象

resource提供了ByteArrayResource、InputStreamResource 、FileSystemResource 、UrlResource 、ClassPathResource、ServletContextResource、VfsResource等。

1.ByteArrayResource代表byte[]数组资源，对于 getInputStream() 操作将返回一个ByteArrayInputStream。ByteArrayResource可多次读取数组资源，即 isOpen() 永远返回false。

2.InputStreamResource代表java.io.InputStream字节流，对于getInputStream() 操作将直接返回该字节流，因此只能读取一次该字节流，即 isOpen() 永远返回true。

3.FileSystemResource代表java.io.File资源，对于 getInputStream() 操作将返回底层文件的字节流，isOpen() 将永远返回false，从而表示可多次读取底层文件的字节流。

4.ClassPathResource代表classpath路径的资源，将使用ClassLoader进行加载资源。classpath 资源存在于类路径中的文件系统中或jar包里，且 isOpen() 永远返回false，表示可多次读取资源。


5.UrlResource代表URL资源，用于简化URL资源访问。“isOpen”永远返回false，表示可多次读取资源。
- UrlResource一般支持如下资源访问：
	- http：通过标准的http协议访问web资源，如new UrlResource(“http://地址”)；
  	- tp：通过ftp协议访问资源，如new UrlResource(“ftp://地址”)；
      - file：通过file协议访问本地文件系统资源，如new UrlResource(“file:d:/test.txt”)；

6.ServletContextResource代表web应用资源，用于简化servlet容器的ServletContext接口的getResource操作和getResourceAsStream操作；

7.VfsResource代表Jboss 虚拟文件系统资源。



#### resource
ResourceLoader接口用于返回Resource对象；其实现可以看作是一个生产Resource的工厂类。

```java
public interface ResourceLoader {
    Resource getResource(String location);
    ClassLoader getClassLoader();
}
```
1.getResource接口用于根据提供的location参数返回相应的Resource对象;
2.getClassLoader则返回加载这些Resource的ClassLoader;

>Spring提供了一个适用于所有环境的DefaultResourceLoader实现，可以返回ClassPathResource、UrlResource；
还提供一个用于web环境的ServletContextResourceLoader，它继承了DefaultResourceLoader的所有功能，又额外提供了获取ServletContextResource的支持。

>ResourceLoader在进行加载资源时需要使用前缀来指定需要加载：“classpath:path”表示返回ClasspathResource，“http://path”和“file:path”表示返回UrlResource资源；如果不加前缀则需要根据当前上下文来决定；另外，DefaultResourceLoader默认实现可以加载classpath资源。

* 目前所有的ApplicationContext都实现了ResourceLoader，因此可以使用其来加载资源。
	* ClassPathXmlApplicationContext：不指定前缀将返回默认的ClassPathResource资源，否则将根据前缀来加载资源；
	* FileSystemXmlApplicationContext：不指定前缀将返回FileSystemResource，否则将根据前缀来加载资源；
	* WebApplicationContext：不指定前缀将返回ServletContextResource，否则将根据前缀来加载资源；
      * 其他：不指定前缀根据当前上下文返回Resource实现，否则将根据前缀来加载资源。
  
ResourceLoader
ResourceLoaderAware是一个标记接口，用于通过ApplicationContext注入ResourceLoader。   


注入Resource
通过注入来获取ResourceLoader，再来访问资源，很麻烦！！！
Spring提供了ResourceEditor（这是一个PropertyEditor），用于在注入的字符串和Resource之间进行转换。
因此可以使用注入方式注入Resource。 

```java
package cn.javass.spring.chapter4.bean;
import org.springframework.core.io.Resource;
public class ResourceBean3 {
    private Resource resource;
    public Resource getResource() {
        return resource;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
```

```xml
<bean id="resourceBean1" class="cn.javass.spring.chapter4.bean.ResourceBean3">
   <property name="resource" value="cn/javass/spring/chapter4/test1.properties"/>
</bean>
<bean id="resourceBean2" class="cn.javass.spring.chapter4.bean.ResourceBean3">
　　<property name="resource" value="classpath:cn/javass/spring/chapter4/test1.properties"/>
</bean>
```
>注意此处“resourceBean1”注入的路径没有前缀表示根据使用的ApplicationContext实现进行选择Resource实现。

#### Resource通配符路径

- Ant路径通配符支持“？”、“*”、“**”，注意通配符匹配不包括目录分隔符“/”：
	- “?”：匹配一个字符，如“config?.xml”将匹配“config1.xml”；
	- “*”：匹配零个或多个字符串，如“cn/*/config.xml”将匹配“cn/javass/config.xml”，但不匹配匹配“cn/config.xml”；而“cn/config-*.xml”将匹配“cn/config-dao.xml”；
	- “**”：匹配路径中的零个或多个目录，如“cn/**/config.xml”将匹配“cn /config.xml”，也匹配“cn/javass/spring/config.xml”；而“cn/javass/config-**.xml”将匹配“cn/javass/config-dao.xml”，即把“**”当做两个“*”处理。
	
	
	
#### 小结

① Spring通过Resource接口统一访问外部资源，并提供了一堆实现类，可以分别访问File、classpath、URL、URI等等资源。

② Spring通过ResourceLoader接口获取Resource。

③ ApplicationContext都实现了ResourceLoader接口，所以可以直接加载资源！

④ 实现ResourceLoaderAware接口，可以注入ResourceLoader。


      








 




