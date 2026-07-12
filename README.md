# MySpringBootAPI
----
SpringBoot3脚手架，基于SpringBoot3+Tomcat+Druid+PgSQL+MyBatis+SpringSecurity+JWT+FastJSON2+Lombok。

## 技术栈

| 组件 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 推荐 MSJDK17 |
| Spring Boot | 3.3.4 | |
| Web容器 | Tomcat | 默认容器，已配置线程池 |
| MyBatis | 3.0.4 | Java Annotation 模式，无XML |
| Spring Security | 6.x | JWT + BCrypt 认证 |
| JWT | jjwt 0.12.6 | Token生成与验证 |
| Druid | 1.2.23 | 连接池 + SQL监控 |
| PostgreSQL | 42.3.10 | |
| FastJSON2 | 2.0.53 | JSON序列化（替代Jackson） |
| SpringDoc | 2.6.0 | OpenAPI/Swagger UI |
| P6Spy | 1.10.0 | SQL日志输出 |
| Apache Commons Lang3 | 3.17.0 | 字符串/对象工具 |
| Apache Commons Codec | 1.17.1 | 编码/解码工具 |
| Apache Commons Collections4 | 4.4 | 集合工具 |
| Jasypt | 3.0.5 | 配置文件加密 |
| Lombok | 1.18.24 | |
| JUnit | 5 (Jupiter) | spring-boot-starter-test |

## 项目结构

```
com.softdev.system
├── aspect/          # AOP切面（请求日志）
├── config/          # 配置类
│   ├── CorsConfig          # 跨域配置
│   ├── Fastjson2Config     # FastJSON2配置
│   ├── FilterConfig        # Filter注册（XSS/SQL注入/限流）
│   ├── MyBatisConfig       # MyBatis MapperScan
│   ├── SecurityConfig      # Spring Security过滤链
│   └── SpringDocConfig     # Swagger UI + JWT Bearer
├── controller/      # 控制器
│   ├── AuthController      # 认证（登录/注册/Token验证）
│   ├── IndexController     # 健康检查
│   └── SysConfigController # 系统配置CRUD
├── dto/             # 请求DTO
│   ├── LoginRequest
│   ├── RegisterRequest
│   └── SysConfigDTO
├── entity/          # 数据实体
│   ├── SysConfig
│   └── SysUser
├── exception/       # 异常定义
│   └── BusinessException
├── filter/          # 安全过滤
│   ├── XssFilter            # XSS过滤
│   ├── XssHttpServletRequestWrapper
│   ├── SqlInjectionFilter   # SQL注入过滤
│   └── RateLimitFilter      # API限流（60次/分钟/IP）
├── mapper/          # MyBatis Mapper（注解模式）
│   ├── SysConfigMapper
│   └── SysUserMapper
├── security/        # 安全组件
│   ├── JwtTokenProvider          # JWT工具类
│   ├── JwtAuthenticationFilter   # JWT过滤器
│   ├── JwtAuthenticationEntryPoint # 401处理
│   └── CustomUserDetailsService  # 用户加载
├── service/         # 服务接口
│   ├── SysConfigService
│   ├── SysUserService
│   └── impl/                # 服务实现
├── util/            # 工具类
│   └── ReturnUtil<T>        # 泛型统一响应体
└── vo/              # 响应VO
    ├── LoginVO
    └── SysConfigVO
```

## Author
>powered by `Moshow郑锴(大狼狗)` , [https://zhengkai.blog.csdn.net](https://zhengkai.blog.csdn.net)

## 如何运行

1. 确保JDK17，推荐微软的MSJDK17
2. 使用Maven安装类库，`mvn clean compile`
3. 使用PostgreSQL（作者本地V16），新建TEST数据库，编码UTF8
4. 依次导入SQL脚本：
   - `src/main/resources/SQL/public.sql` — 系统配置表
   - `src/main/resources/SQL/sys_user.sql` — 用户表（含默认admin账号，密码admin123）
5. 如使用Jasypt加密配置，设置环境变量 `JASYPT_PASSWORD=devsecret`（dev环境默认密码）
6. 运行Application

## API接口

### 公开接口（无需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/` | 健康检查 |
| POST | `/auth/login` | 用户登录，返回JWT Token和静态Token |
| POST | `/auth/register` | 用户注册 |
| GET | `/auth/verify?token=xxx` | 静态Token验证，匹配则返回用户信息 |

### 认证接口（需Bearer Token）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/config/{id}` | 根据ID查询配置 |
| GET | `/config/list` | 查询所有配置 |
| GET | `/config/key/{paramKey}` | 根据key查询配置 |
| POST | `/config` | 新增配置 |
| PUT | `/config` | 更新配置 |
| DELETE | `/config/{id}` | 删除配置 |

### 接口调用示例

**登录：**
```bash
curl -X POST http://localhost:12666/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

返回：
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "staticToken": "a1b2c3d4e5f6..."
  }
}
```

**静态Token验证：**
```bash
curl http://localhost:12666/api/auth/verify?token=a1b2c3d4e5f6...
```

**访问受保护接口：**
```bash
curl http://localhost:12666/api/config/list \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

## 安全特性

- **JWT认证**：基于Spring Security的无状态JWT认证，Bearer Token模式
- **静态Token验证**：登录时自动生成UUID静态Token存入用户表，可通过 `/auth/verify?token=xxx` 公开接口验证
- **BCrypt加密**：用户密码使用BCrypt哈希存储
- **Jasypt配置加密**：数据库密码等敏感配置使用ENC()加密
- **CORS跨域**：可配置允许的域名
- **XSS过滤**：全局过滤HTML危险标签
- **SQL注入过滤**：拦截常见SQL注入模式
- **API限流**：认证接口60次/分钟/IP
- **参数校验**：Jakarta Validation注解校验
- **异常体系**：BusinessException + ValidationException + 细分异常处理

## 其他配置

- **Swagger UI**：`http://localhost:12666/api/swagger-ui.html`，支持Bearer Token认证
- **Druid监控**：`http://localhost:12666/api/system/druid/`，账号admin/123456
- **API文档**：`http://localhost:12666/api/api-doc`
- **日志**：dev环境输出到控制台，prod环境输出到文件（logs/application.log，保留30天）

## Useful Tips
- FastJson2需要安装fastjson2、fastjson2-extension、fastjson2-extension-spring6三个类库
- SpringBoot3使用jakarta.servlet替代javax.servlet
- SpringBoot3+Druid YAML配置
- MyBatis使用P6Spy打印SQL日志

# 版本更新

| 日期         | 内容                                                     |
|------------|--------------------------------------------------------|
| 2026-07-12 | 脚手架全面升级：Tomcat容器、MyBatis注解模式替代MyBatis Plus、SpringSecurity+JWT认证、Jasypt配置加密、CORS、XSS/SQL注入过滤、API限流、AOP日志切面、泛型ReturnUtil、DTO/VO分层、Service接口+实现、异常体系重构、JUnit5单元测试(25个)、logback配置、多环境Profile。新增用户Token字段及verify公开接口。 |
| 2024-11-11 | MybatisPlus使用P6SY打印SQL日志。新增SwaggerUI(SpringDoc)功能。     |
| 2024-09-29 | 优化Druid配置。                                             |
| 2024-09-28 | 初始化3.3.4版本，修改默认web容器为undertow，配置FastJson2，修复jakarta问题。 |
| 2024-06-27 | 初始化2.7.8版本。                                            |
