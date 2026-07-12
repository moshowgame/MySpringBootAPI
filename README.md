# springboot-api-template
----
SpringBoot3 API模板，基于SpringBoot3+Tomcat+HikariCP+PgSQL+MyBatis+SpringSecurity+JWT+FastJSON2+Lombok。

## 技术栈

| 组件 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 推荐 MSJDK17 |
| Spring Boot | 3.3.4 | |
| Web容器 | Tomcat | 默认容器，已配置线程池 |
| MyBatis | 3.0.4 | Java Annotation 模式，无XML |
| Spring Security | 6.x | JWT + BCrypt 认证 |
| JWT | jjwt 0.12.6 | Token生成与验证 |
| HikariCP | Boot默认 | 高性能连接池（替代Druid） |
| PostgreSQL | Boot默认(42.7.x) | |
| FastJSON2 | 2.0.53 | JSON序列化（替代Jackson） |
| SpringDoc | 2.6.0 | OpenAPI/Swagger UI |
| P6Spy | 1.10.0 | SQL日志输出 |
| Apache Commons Lang3 | 3.17.0 | 字符串/对象工具 |
| Apache Commons Codec | 1.17.1 | 编码/解码工具 |
| Apache Commons Collections4 | 4.4 | 集合工具 |
| Jasypt | 3.0.5 | 配置文件加密 |
| Lombok | 1.18.36 | |
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
│   └── SysConfigDTO        # 含Validation Groups（Update时id必填）
├── entity/          # 数据实体
│   ├── SysConfig
│   └── SysUser
├── exception/       # 异常定义
│   └── BusinessException
├── filter/          # 安全过滤
│   ├── XssFilter            # XSS过滤（HTML实体转义）
│   ├── XssHttpServletRequestWrapper
│   ├── SqlInjectionFilter   # SQL注入过滤（UNION SELECT/恒真条件）
│   └── RateLimitFilter      # API限流（60次/分钟/IP）
├── mapper/          # MyBatis Mapper（注解模式）
│   ├── SysConfigMapper      # @Options自增主键回填
│   └── SysUserMapper
├── security/        # 安全组件
│   ├── JwtTokenProvider          # JWT工具类（SecretKey双检锁缓存）
│   ├── JwtAuthenticationFilter   # JWT过滤器
│   ├── JwtAuthenticationEntryPoint # 401处理
│   └── CustomUserDetailsService  # 用户加载
├── service/         # 服务接口
│   ├── SysConfigService
│   ├── SysUserService
│   └── impl/                # 服务实现（@Transactional）
├── util/            # 工具类
│   └── ReturnUtil<T>        # 泛型统一响应体
└── vo/              # 响应VO
    ├── LoginVO              # 含@NoArgsConstructor
    ├── SysConfigVO
    └── UserVerifyVO         # Token验证响应（不暴露密码/token）
```

## Author
>powered by `Moshow郑锴(大狼狗)` , [https://zhengkai.blog.csdn.net](https://zhengkai.blog.csdn.net)

## 如何运行

1. 确保JDK17，推荐微软的MSJDK17
2. 使用Maven安装类库，`mvn clean compile`
3. 使用PostgreSQL（作者本地V16），新建TEST数据库，编码UTF8
4. 依次导入SQL脚本：
   - `src/main/resources/SQL/public.sql` — 系统配置表（id自增）
   - `src/main/resources/SQL/sys_user.sql` — 用户表（含默认admin账号，密码admin123，token字段）
5. 如使用Jasypt加密配置，设置环境变量 `JASYPT_PASSWORD=devsecret`（dev环境默认密码）
6. 运行Application

## API接口

### 公开接口（无需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/` | 健康检查 |
| POST | `/auth/login` | 用户登录，返回JWT Token和静态Token |
| POST | `/auth/register` | 用户注册 |
| GET | `/auth/verify?token=xxx` | 静态Token验证，匹配则返回用户信息(UserVerifyVO) |

### 认证接口（需Bearer Token）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/config/{id}` | 根据ID查询配置 |
| GET | `/config/list` | 查询所有配置 |
| GET | `/config/key/{paramKey}` | 根据key查询配置 |
| POST | `/config` | 新增配置 |
| PUT | `/config` | 更新配置（id必填） |
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

- **JWT认证**：基于Spring Security的无状态JWT认证，Bearer Token模式，SecretKey双检锁缓存
- **静态Token验证**：登录时自动生成UUID静态Token存入用户表，可通过 `/auth/verify?token=xxx` 公开接口验证，响应使用UserVerifyVO不暴露敏感字段
- **BCrypt加密**：用户密码使用BCrypt哈希存储
- **Jasypt配置加密**：数据库密码、JWT Secret、Druid密码等敏感配置均使用ENC()加密
- **CORS跨域**：可配置允许的域名
- **XSS过滤**：HTML实体转义（<>&"'），不误杀正常文本
- **SQL注入过滤**：拦截UNION SELECT和恒真条件，不误杀合法分号
- **API限流**：认证接口60次/分钟/IP
- **参数校验**：Jakarta Validation注解校验 + Validation Groups区分创建/更新
- **异常体系**：BusinessException + ValidationException + 细分异常处理

## 架构规范

- **构造器注入**：全项目使用 `private final` + 构造器注入，无 @Autowired 字段注入
- **三层架构**：Controller → Service → Mapper，Service 接口 + 实现分离
- **DTO/VO分层**：请求DTO（输入校验）、响应VO（输出控制）、Entity不直接暴露
- **事务管理**：写操作均加 @Transactional，查询重复键冲突提前校验
- **配置分层**：application.yml 放共享配置，application-dev/prod.yml 仅放环境差异

## 其他配置

- **Swagger UI**：`http://localhost:12666/api/swagger-ui.html`，支持Bearer Token认证
- **API文档**：`http://localhost:12666/api/api-doc`
- **日志**：dev环境输出到控制台，prod环境输出到文件（logs/application.log，保留30天）

## Useful Tips
- FastJson2需要安装fastjson2、fastjson2-extension、fastjson2-extension-spring6三个类库
- SpringBoot3使用jakarta.servlet替代javax.servlet
- MyBatis使用P6Spy打印SQL日志
- HikariCP为Spring Boot默认连接池，无需额外依赖

# 版本更新

| 日期         | 内容                                                     |
|------------|--------------------------------------------------------|
| 2026-07-12 | P0安全加固：JWT Secret/Druid密码Jasypt加密、verifyToken用VO替代实体、XSS过滤改HTML实体转义、SQL注入过滤器去除误杀。P1架构修复：登录去重查库、@Transactional、sys_config自增ID、DTO Validation Groups、JWT SecretKey缓存。P2依赖清理：删除jakarta.servlet-api多余声明、删除spring-boot-starter-json矛盾引入、Lombok升级1.18.36、PostgreSQL驱动用Boot管理版本、清理pom残留注释、删除未使用属性。P3代码质量：全项目@Autowired改构造器注入、LoginVO加@NoArgsConstructor、application.yml提取共享配置+dev.yml精简。Druid→HikariCP切换，移除Druid依赖及监控配置。Hutool→Apache Commons(Lang3/Codec/Collections4)。 |
| 2026-07-12 | 脚手架全面升级：Tomcat容器、MyBatis注解模式替代MyBatis Plus、SpringSecurity+JWT认证、Jasypt配置加密、CORS、XSS/SQL注入过滤、API限流、AOP日志切面、泛型ReturnUtil、DTO/VO分层、Service接口+实现、异常体系重构、JUnit5单元测试(26个)、logback配置、多环境Profile。新增用户Token字段及verify公开接口。 |
| 2024-11-11 | MybatisPlus使用P6SY打印SQL日志。新增SwaggerUI(SpringDoc)功能。     |
| 2024-09-29 | 优化Druid配置。                                             |
| 2024-09-28 | 初始化3.3.4版本，修改默认web容器为undertow，配置FastJson2，修复jakarta问题。 |
| 2024-06-27 | 初始化2.7.8版本。                                            |
