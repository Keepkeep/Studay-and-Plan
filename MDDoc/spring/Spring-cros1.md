# Spring-Cros 学习笔记

## 目录
####

>在项目中难免遇到跨域问题，其实就是浏览器的同源策略导致。


#### 一、跨域问题
- 跨域问题其实是浏览器的同源策略 即：
	1.协议相同
	2.域名相同
	3.端口相同

- 三者同时成立才能叫同源。但凡有一项不满足浏览器就会把 No 'Access-Control-Allow-Origin' header is present on the requested resource 抛出。

#### 二、绕过同源策略的几种方案

- 2.1 JSONP 实现跨域问题  其实是通过<script>  标签的src属性没有跨域限制，将数据放在一个指定名字的回调函数里传回来。
- 2.1 服务器端做手脚，在响应头header中添加"Access-Control-Allow-Origin"，指定允许访问的源。（CROS就是这样的）。
- 2.3 Httpclient 通过后端调用接口返回数据给前端
- 2.4 spring4.2及以上版本提供了@CrossOrigin注解来方便实现跨域。
- 2.5 通过代理实现跨域处理。
- 2.6 通多Node.js 做中间层

#### 三、项目中采用JSONP
3.1 Jsonp原理：
	SONP是利用浏览器对script的资源引用没有同源限制，通过动态插入一个script标签，当资源加载到页面后会立即执行的原理实现跨域的。JSONP是一种非正式传输协议，该协议的一个要点就是允许用户传递一个callback或者开始就定义一个回调方法，参数给服务端，然后服务端返回数据时会将这个callback参数作为函数名来包裹住JSON数据，这样客户端就可以随意定制自己的函数来自动处理返回数据了。
项目中使用:
	项目中有一部分接口采用了JSONP,前端和后端配合实现jsonp，前端请求url路径中带callback键值对，后端通过filter拦截器进行拦截，获取url的产数判断是否是JSONP 请求，若是这对该请求的响应进行拦截和处理。对响应的json文本进行包装。
```java
	public class JsonpCallbackFilter implements Filter {
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Map<String, String[]> parms = httpRequest.getParameterMap();
		if (parms.containsKey("callback")) {
			OutputStream out = httpResponse.getOutputStream();
			GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);
			chain.doFilter(request, wrapper);
			//handles the content-size truncation
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write(new String(parms.get("callback")[0] + "(").getBytes());
			outputStream.write(wrapper.getData());
			outputStream.write(new String(");").getBytes());
			byte jsonpResponse[] = outputStream.toByteArray();
			wrapper.setContentType("text/javascript;charset=UTF-8");
			wrapper.setContentLength(jsonpResponse.length);
			out.write(jsonpResponse);
			out.close();
		} else {
			chain.doFilter(request, response);
		}
	}
	@Override
	public void destroy() {
	}
}
```
- 上面代码其实就是拦截请求，响应处理获取内容重新拼接输出。
- PS:由于response本身不具有缓存数据和对回应的数据进行操作，所以这里需要使用到HttpServletResponseWrapper 这个类和流的操作！包装的响应数据要和前端回调函数的名称相同。

- web.xml 中配置，对所有请求拦截
```xml
<filter>
		<filter-name>JSONPFilter</filter-name>
		<filter-class>com.dist.bdf.base.servlet.JsonpCallbackFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>JSONPFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
- 3.2 JSONP引发的血案：
	- 先说说JSONP 的弊端：
		- 1.弊端也比较明显：需要前端和后端定制进行开发，服务端返回的数据不能是标准的Json数据，而是callback包裹的数据。
		- 2.数据安全的问题，如果未设置响应内容，第三方的脚本随意地执行，那么它就可以篡改页面内容，截获敏感数据。针对这种解决方案（查看百度解决）：
		- 3.对输出的内容进行必要的安全转义
		- 4限定jsonp的回调方法名的安全字符范围为(a-zA-Z0-9$ )
		- 5设置响应类型是非json或javascript类型，比如text/html。
		
  	- 引发的血案 ：
  		由于项目集成DASC(dsc)，出于安全的考虑，对于浏览器的一些响应头做了一下处理。如添加下：主要是X-contenx-Type-Option:nosniff，Content—Type
 ![](../IMG/spring/http.jpg)
 JSONP 就请求不行了
 ![](../IMG/spring/cros.jpg)
 本来JSONP 用来处理跨域问题 ，这下可好，跨域倒是解决了，报的错也是个什么东西,定位MIME这个是个什么东西 和ContextType 有关系。
 
MIME: 
IE4引入了一个新的feature：MIME sniffing。这个feature的本意是为了兼容那些后端程序员没有正确设置头部的网站。比如说有一个返回text/html类型Body的Response被错误设置成了text/plain。这时候IE浏览器就通过对Response Body的前256个字节进行判断（嗅探），发现他实际是text/html。这个时候IE就用处理html的方式对其进行处理。因此我们上面提到的这个案例中的html文件就得到了正确的处理
浏览器通常使用MIME类型（而不是文件扩展名）来确定如何处理文档；因此服务器设置正确以将正确的MIME类型附加到响应对象的头部是非常重要的。
在缺失 MIME 类型或客户端认为文件设置了错误的 MIME 类型时，浏览器可能会通过查看资源来进行MIME嗅探。每一个浏览器在不同的情况下会执行不同的操作。因为这个操作会有一些安全问题，有的 MIME 类型表示可执行内容而有些是不可执行内容。浏览器可以通过请求头 Content-Type 来设置X-Content-Type-Options  以阻止MIME嗅探。
X-Content-Type-Options：nosniff 是用来终结MIME 的嗅探，但是也带来不便，如果Content-Type 内容设置不正确 就会出来，上面的问题。

项目采用可以不要在响应头中加这个响应头，或者修改Content-Tepe类型。

JSONP 将成为过去式！采用第三方的CORS 实现跨域处理

#### 四、项目中采用CORS
理解：新增一系列 HTTP 头，让服务器能声明哪些来源可以通过浏览器访问该服务器上的资源。
其核心是服务端返回响应中的 Access-Control-Allow-Origin 首部字段
- 4.1项目中配置信息
```xml
<!--前后端分开部署情况下存在跨域访问的问题，如果前后端部署在一个域下则注释掉下面的配置 -->
	<filter>
		<filter-name>CORS</filter-name>
		<filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
		<init-param>
			<param-name>cors.allowOrigin</param-name>
			<param-value>*</param-value>
		</init-param>
		<init-param>
			<param-name>cors.supportsCredentials</param-name>
			<param-value>true</param-value>
		</init-param>
		<init-param>
			<param-name>cors.exposedHeaders</param-name>
			<param-value>Set-Cookie</param-value>
		</init-param>
		<init-param>
			<param-name>cors.supportedHeaders</param-name>
			<param-value>Accept, Origin, X-Requested-With, Content-Type, Last-Modified, authorization</param-value>
		</init-param>
		<init-param>
			<param-name>cors.supportedMethods</param-name>
			<param-value>GET, POST, HEAD, OPTIONS,DELETE,PUT</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CORS</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
这个通过过滤去对配置允许的请求方法和响应头
cors.supportedMethods：取值范围{method-list}，默认“GET, POST, HEAD, OPTIONS”。列举所支持的HTTP方法。该信息将通“Access-Control-Allow-Methods”头信息返回给调用者，并且需要在service中实现CORS。非列表内的方法类型的请求将被CORS filters以HTTP 405 “Method not allowed”响应拒绝。
cors.supportedHeaders：取值范围{*|header-list}，默认*。定义所支持的自定义请求头，其信息将通过“Access-Control-Allow-Headers”头信息返回给请求者。如果配置为*，则包含任何自定义请求头信息的请求都将被接受。CORS Filter对此的实现是简单打包请求全部信息返回给浏览器。自定义请求头是指由浏览器JavaScript应用通过XMLHttpRequest.setRequestHeader()方法。例如通知浏览器允许“Content-Type, X-Requested-With”请求头。
cors.exposedHeaders：取值{header-list}，默认空表。列出浏览器通过XMLHttpRequest.getResponseHeader()方法可以暴露哪些header详细信息（而非简要信息）给跨域请求。CORS Filter通过“Access-Control-Expose-Headers”头提供这类信息详情，通知浏览器例如“X-Custom-1, X-Custom-2”自定义头信息可以安全的保留给初始化跨域请求的脚本。
cors.supportsCredentials：取值{true|false}，默认true。提示所支持的用户凭据类型，如cookies、HTTP授权或客户端证书。CORS Filter利用该值构造“Access-Control-Allow-Credentials”头信息。
cors.maxAge：取值{int}，默认-1（未定义）。定义web浏览器可以缓存预检请求结果的时间长度，单位为秒。如果值为-1，表示未定义。该信息通过“Access-Control-Max-Age”头信息传递给浏览器。建议浏览器保存预检请求缓存1小时，即该属性值为3600.
cors.tagRequests：取值{true|false}，默认false（不标记，或没有标签）。允许HTTP servlet请求标记提供给下游处理程序的CORS信息。允许标记只需将该属性值配置为true。

#### 五、Httpclient 实现后端调用
采用此方式调用使用的后端调用接口，完后基本的post，get 之类的请求。
```java
CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象 后面封装下  给路径
        //HttpGet get =new HttpGet("http://172.30.240.59:8680/DapService/ws/rs/gis/getProjectBase");
		HttpGet get =new HttpGet(businessUrl.getProjectURl());
		XmsDTO parseObject = null;
		CloseableHttpResponse response=null;
        try {
            response =httpClient.execute(get);
            String string = EntityUtils.toString(response.getEntity(),"utf-8");
            parseObject = JSON.parseObject(string,XmsDTO.class);
            response.close();
            httpClient.close(); 
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if(response!=null) 
				response.close();
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
```

#### 六.spring 4.0实现跨域
 Spring 从4.2 版本开始支持跨域，可采用注解的方式
   这个而感觉是提供给别的系统访问允许跨域，而不是解决自己系统的跨域问题
   网上说明：https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
 ：https://www.jianshu.com/p/9203e9b14465
 spring 5.0 好像提供处理，有待研究。
 
#### 7. 通过代理来解决跨域问题
后续研究学习下。
 
 总结：跨域问题就是浏览器同源策略


 
 


      








 




