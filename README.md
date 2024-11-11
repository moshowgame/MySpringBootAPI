# MySpringBootAPI
----
SpringBoot脚手架，基于SpringBoot3+Druid+PgSQL+MyBatisPlus13+FastJSON2+Lombok，启动web容器为Undertow(非默认tomcat)，其他的请自行添加和配置。

```xml
<java.version>17</java.version>
<springboot.version>3.3.4</springboot.version>
<lombok.version>1.18.24</lombok.version>
<druid.version>1.2.23</druid.version>
<postgresql.version>42.3.10</postgresql.version>
<hutool.version>5.8.28</hutool.version>
<commonslang3.version>3.17.0</commonslang3.version>
<junit.version>4.13.1</junit.version>
<mybatisplus.version>3.5.8</mybatisplus.version>
<jakarta.version>6.1.0</jakarta.version>
<fastjson.version>2.0.53</fastjson.version>
<p6sy.version>1.10.0</p6sy.version>
<springdoc.version>2.6.0</springdoc.version>
```

# Author
>powered by `Moshow郑锴(大狼狗)` , [https://zhengkai.blog.csdn.net](https://zhengkai.blog.csdn.net)

# 如何运行
1. 首先确保你是JDK17(SpringBoot2.7需要JDK11,SpringBoot3需要JDK17)，推荐微软的MSJDK17
2. 使用Maven安装类库，国内推荐使用阿里云Maven镜像，`mvn clean compile`
3. 使用比较新版本的PostgreSQL，作者本地为V16
4. 项目已配置新版的SpringBoot3.3，需要2.7X版本请从SpringBoot2分支中提取
5. 导入`D:\Workspace\Project\MySpringBootAPI\src\main\resources\SQL\public.sql`到你的数据库去，新建一个叫TEST的空白数据库，编码UTF8
6. 找到Application，运行项目

# Useful Tips
- 如果你最近也在升级FastJson到FastJson2版本，而跟我一样也遇到了FastJsonHttpMessageConverter找不到类问题以及FastJsonConfig找不到问题，那么恭喜你，看完本文，安装完fastjson2、fastjson2-extension、fastjson2-extension-spring6这三个类库，你就可以成功使用新版FastJson2了。
 [FastJson2中FastJsonHttpMessageConverter找不到类问题](https://blog.csdn.net/moshowgame/article/details/138013669)

- 当项目从2.7.x的springboot升级到3.0.x的时候，遇到一个问题“java: 程序包javax.servlet.http不存在” 问题：
 [java: 程序包javax.servlet.http不存在](https://zhengkai.blog.csdn.net/article/details/131362304)

- 如果你还没配置好Druid，没有充分利用好他的慢监控/WebFilter，那么赶快开始你的配置吧 [SpringBoot3+Druid YAML配置](https://blog.csdn.net/moshowgame/article/details/142641883)

- 如何配置SQL日志输出？请看[MybatisPlus使用P6SY打印SQL日志](https://blog.csdn.net/moshowgame/article/details/143697627)


# 运行效果

<img src="./screencap2.png">
<img src="./screencap1.png">

```shell
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.3.4)

2024-09-29T15:15:11.690+08:00  INFO 31892 --- [MySpringBootAPI] [           main] com.softdev.system.Application           : Starting Application using Java 17.0.11 with PID 31892 (D:\Workspace\Project\MySpringBootAPI\target\classes started by Administrator in D:\Workspace\Project\MySpringBootAPI)
2024-09-29T15:15:11.692+08:00  INFO 31892 --- [MySpringBootAPI] [           main] com.softdev.system.Application           : The following 1 profile is active: "dev"
2024-09-29T15:15:12.339+08:00  WARN 31892 --- [MySpringBootAPI] [           main] io.undertow.websockets.jsr               : UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
2024-09-29T15:15:12.352+08:00  INFO 31892 --- [MySpringBootAPI] [           main] io.undertow.servlet                      : Initializing Spring embedded WebApplicationContext
2024-09-29T15:15:12.352+08:00  INFO 31892 --- [MySpringBootAPI] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 633 ms
2024-09-29T15:15:12.411+08:00  INFO 31892 --- [MySpringBootAPI] [           main] c.a.d.s.b.a.DruidDataSourceAutoConfigure : Init DruidDataSource
2024-09-29T15:15:12.478+08:00  INFO 31892 --- [MySpringBootAPI] [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
 _ _   |_  _ _|_. ___ _ |    _ 
| | |\/|_)(_| | |_\  |_)||_|_\ 
     /               |         
                        3.5.8 
2024-09-29T15:15:12.795+08:00  INFO 31892 --- [MySpringBootAPI] [           main] c.softdev.system.config.Fastjson2Config  : Fastjson2 Initial Done
2024-09-29T15:15:12.924+08:00  INFO 31892 --- [MySpringBootAPI] [           main] io.undertow                              : starting server: Undertow - 2.3.17.Final
2024-09-29T15:15:12.929+08:00  INFO 31892 --- [MySpringBootAPI] [           main] org.xnio                                 : XNIO version 3.8.16.Final
2024-09-29T15:15:12.934+08:00  INFO 31892 --- [MySpringBootAPI] [           main] org.xnio.nio                             : XNIO NIO Implementation Version 3.8.16.Final
2024-09-29T15:15:12.973+08:00  INFO 31892 --- [MySpringBootAPI] [           main] org.jboss.threads                        : JBoss Threads version 3.5.0.Final
2024-09-29T15:15:13.006+08:00  INFO 31892 --- [MySpringBootAPI] [           main] o.s.b.w.e.undertow.UndertowWebServer     : Undertow started on port 12666 (http) with context path '/api'
2024-09-29T15:15:13.013+08:00  INFO 31892 --- [MySpringBootAPI] [           main] com.softdev.system.Application           : Started Application in 1.615 seconds (process running for 1.982)
2024-09-29T15:15:13.015+08:00  INFO 31892 --- [MySpringBootAPI] [           main] com.softdev.system.Application           : Powered by https://zhengkai.blog.csdn.net/ 

```

访问`http://localhost:12666/api/author`

返回报文
```json
{
"code": 200,
"msg": "操作成功",
"data": "zhengkai.blog.csdn.net",
"count": 0
}
```

# 版本更新

| 日期         | 内容                                                     |
|------------|--------------------------------------------------------|
| 2024-11-11 | MybatisPlus使用P6SY打印SQL日志。新增SwaggerUI(SpringDoc)功能。     |
| 2024-09-29 | 优化Druid配置。                                             |
| 2024-09-28 | 初始化3.3.4版本，修改默认web容器为undertow，配置FastJson2，修复jakarta问题。 |
| 2024-06-27 | 初始化2.7.8版本。                                            |