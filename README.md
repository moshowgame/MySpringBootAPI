# springboot-api-template

A production-ready Spring Boot 3 API template with Tomcat + HikariCP + PostgreSQL + MyBatis + Spring Security + JWT + FastJSON2.

SpringBoot3 API 模板，基于 Tomcat + HikariCP + PgSQL + MyBatis + SpringSecurity + JWT + FastJSON2 + Lombok，开箱即用。

---

## Tech Stack / 技术栈

| Component / 组件 | Version / 版本 | Description / 说明 |
|------|------|------|
| Java | 17 | MSJDK17 recommended / 推荐微软MSJDK17 |
| Spring Boot | 3.3.4 | |
| Tomcat | Default | Thread pool configured / 默认容器，已配置线程池 |
| MyBatis | 3.0.4 | Annotation mode, no XML / 注解模式，无XML |
| Spring Security | 6.x | JWT + BCrypt authentication / JWT+BCrypt认证 |
| JWT | jjwt 0.12.6 | Token generation & validation / Token生成与验证 |
| HikariCP | Boot default | High-performance connection pool / 高性能连接池 |
| PostgreSQL | Boot managed (42.7.x) | |
| FastJSON2 | 2.0.53 | Replaces Jackson / 替代Jackson |
| SpringDoc | 2.6.0 | OpenAPI / Swagger UI |
| P6Spy | 1.10.0 | SQL logging with parameters / SQL完整参数日志 |
| Apache Commons Lang3 | 3.17.0 | String/Object utilities / 字符串/对象工具 |
| Apache Commons Codec | 1.17.1 | Encoding/Decoding / 编码解码 |
| Apache Commons Collections4 | 4.4 | Collection utilities / 集合工具 |
| Jasypt | 3.0.5 | Config encryption / 配置文件加密 |
| Lombok | 1.18.36 | |
| JUnit | 5 (Jupiter) | spring-boot-starter-test |

## Project Structure / 项目结构

```
com.softdev.system
├── aspect/          # AOP (request logging / 请求日志)
├── config/          # Configuration / 配置类
│   ├── CorsConfig              # CORS / 跨域
│   ├── Fastjson2Config         # FastJSON2
│   ├── FilterConfig            # XSS / SQL injection / Rate limit
│   ├── MyBatisConfig           # MapperScan
│   ├── SecurityConfig          # Security filter chain / 安全过滤链
│   └── SpringDocConfig         # Swagger + JWT Bearer
├── controller/      # Controllers / 控制器
│   ├── AuthController          # Login / Register / Verify
│   ├── IndexController         # Health check / 健康检查
│   └── SysConfigController     # CRUD
├── dto/             # Request DTOs / 请求传输对象
│   ├── LoginRequest
│   ├── RegisterRequest
│   └── SysConfigDTO            # Validation Groups (id required on update)
├── entity/          # Data entities / 数据实体
│   ├── SysConfig
│   └── SysUser
├── exception/       # Exceptions / 异常
│   └── BusinessException
├── filter/          # Security filters / 安全过滤
│   ├── XssFilter + XssHttpServletRequestWrapper   # HTML entity escaping
│   ├── SqlInjectionFilter                          # UNION SELECT / tautology
│   └── RateLimitFilter                             # 60 req/min/IP
├── mapper/          # MyBatis Mappers (annotation / 注解模式)
│   ├── SysConfigMapper          # @Options auto-increment PK
│   └── SysUserMapper
├── security/        # Security components / 安全组件
│   ├── JwtTokenProvider              # DCL-cached SecretKey / 双检锁缓存
│   ├── JwtAuthenticationFilter
│   ├── JwtAuthenticationEntryPoint   # 401 handler
│   └── CustomUserDetailsService
├── service/         # Service layer / 服务层
│   ├── SysConfigService
│   ├── SysUserService
│   └── impl/                      # @Transactional
├── util/            # Utilities / 工具类
│   └── ReturnUtil<T>              # Generic response wrapper / 泛型响应体
└── vo/              # Response VOs / 响应视图对象
    ├── LoginVO                    # @NoArgsConstructor
    ├── SysConfigVO
    └── UserVerifyVO               # No password/token exposed
```

## Getting Started / 如何运行

1. Install JDK 17 (MSJDK17 recommended) / 安装JDK17，推荐微软MSJDK17
2. Run `mvn clean compile` / Maven安装类库
3. Create a PostgreSQL database named `test` with UTF8 encoding / 新建PostgreSQL TEST数据库，编码UTF8
4. Execute SQL scripts in order / 依次导入SQL脚本：
   - `src/main/resources/SQL/public.sql` — sys_config table (auto-increment id / 自增ID)
   - `src/main/resources/SQL/sys_user.sql` — sys_user table (default admin/admin123, token field / 含默认账号及token字段)
5. Set environment variable for Jasypt (dev default: `devsecret`) / 设置Jasypt环境变量：`JASYPT_PASSWORD=devsecret`
6. Run the application / 运行Application

## API Endpoints / API接口

### Public (No Auth Required) / 公开接口（无需认证）

| Method | Path | Description / 说明 |
|--------|------|------|
| GET | `/` | Health check / 健康检查 |
| POST | `/auth/login` | Login, returns JWT token + static token / 登录，返回JWT和静态Token |
| POST | `/auth/register` | Register / 注册 |
| GET | `/auth/verify?token=xxx` | Static token verification, returns UserVerifyVO / 静态Token验证 |

### Protected (Bearer Token Required) / 认证接口（需Bearer Token）

| Method | Path | Description / 说明 |
|--------|------|------|
| GET | `/config/{id}` | Get config by ID / 根据ID查询配置 |
| GET | `/config/list` | List all configs / 查询所有配置 |
| GET | `/config/key/{paramKey}` | Get config by key / 根据key查询配置 |
| POST | `/config` | Create config / 新增配置 |
| PUT | `/config` | Update config (id required) / 更新配置（id必填） |
| DELETE | `/config/{id}` | Delete config / 删除配置 |

### Examples / 调用示例

**Login / 登录：**
```bash
curl -X POST http://localhost:12666/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Response / 返回：
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "staticToken": "a1b2c3d4e5f6..."
  }
}
```

**Verify static token / 静态Token验证：**
```bash
curl http://localhost:12666/api/auth/verify?token=a1b2c3d4e5f6...
```

**Access protected endpoint / 访问受保护接口：**
```bash
curl http://localhost:12666/api/config/list \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

## Security / 安全特性

- **JWT Authentication**: Stateless JWT via Spring Security, Bearer token, DCL-cached SecretKey / 无状态JWT认证，SecretKey双检锁缓存
- **Static Token Verification**: UUID token stored in user table on login, verified via `/auth/verify`, response uses UserVerifyVO (no sensitive fields) / 登录生成UUID静态Token，验证接口不暴露密码/token
- **BCrypt**: Password hashing / 密码哈希存储
- **Jasypt**: DB password, JWT secret encrypted with ENC() / 数据库密码、JWT Secret等敏感配置加密
- **CORS**: Configurable allowed origins / 可配置允许域名
- **XSS Filter**: HTML entity escaping (`<>&"'`), no false positives / HTML实体转义，不误杀正常文本
- **SQL Injection Filter**: Blocks UNION SELECT and tautology patterns, no false positives on semicolons / 拦截联合查询和恒真条件，不误杀分号
- **Rate Limiting**: 60 req/min/IP / 每IP每分钟60次
- **Validation**: Jakarta Validation + Validation Groups (create vs update) / 参数校验+Groups区分
- **Exception Handling**: BusinessException + ValidationException + fine-grained handlers / 细分异常处理

## Architecture / 架构规范

- **Constructor Injection**: `private final` fields, no `@Autowired` / 构造器注入，无字段注入
- **3-Layer Architecture**: Controller → Service → Mapper, interface + impl separation / 三层架构，接口与实现分离
- **DTO/VO Separation**: DTO for input validation, VO for output control, Entity never exposed / 请求DTO、响应VO、实体不直接暴露
- **Transaction Management**: `@Transactional` on all write operations, duplicate key pre-check / 写操作加事务，重复键提前校验
- **Config Separation**: application.yml for shared config, application-{profile}.yml for environment-specific only / 共享配置与环境配置分离

## Additional Config / 其他配置

- **Swagger UI**: `http://localhost:12666/api/swagger-ui.html` (Bearer Token supported)
- **API Docs**: `http://localhost:12666/api/api-doc`
- **Logging**: Console output in dev, file output in prod (`logs/application.log`, 30-day retention) / dev控制台，prod文件输出

## Tips

- FastJSON2 requires 3 artifacts: fastjson2, fastjson2-extension, fastjson2-extension-spring6 / 需要3个类库
- Spring Boot 3 uses `jakarta.servlet` instead of `javax.servlet` / 使用jakarta替代javax
- P6Spy prints full SQL with parameters (disabled in prod) / 打印完整SQL参数，生产环境关闭
- HikariCP is the default pool, no extra dependency needed / 默认连接池，无需额外依赖

## Author

Powered by `Moshow郑锴(大狼狗)` — [https://zhengkai.blog.csdn.net](https://zhengkai.blog.csdn.net)

## Changelog / 版本更新

| Date / 日期 | Changes / 内容 |
|------------|--------|
| 2026-07-12 | **P0 Security**: JWT Secret & Druid password Jasypt-encrypted, verifyToken returns VO (no sensitive fields), XSS filter uses HTML entity escaping, SQL injection filter removes false positives. **P1 Architecture**: Login dedup query, @Transactional on writes, sys_config auto-increment ID, DTO Validation Groups, JWT SecretKey DCL cache. **P2 Dependencies**: Remove redundant jakarta.servlet-api & spring-boot-starter-json, Lombok 1.18.36, PostgreSQL version managed by Boot, clean pom comments. **P3 Code Quality**: Constructor injection (no @Autowired), LoginVO @NoArgsConstructor, shared config extracted to application.yml. Druid→HikariCP, Hutool→Apache Commons. |
| 2026-07-12 | Major upgrade: Tomcat, MyBatis annotations (replaces MyBatis Plus), Spring Security + JWT, Jasypt, CORS, XSS/SQL injection filters, rate limiting, AOP logging, generic ReturnUtil, DTO/VO layers, Service interface + impl, exception system, JUnit 5 (26 tests), logback, multi-profile. User token field & verify endpoint. |
| 2024-11-11 | P6Spy SQL logging, SpringDoc Swagger UI. |
| 2024-09-29 | Druid config optimization. |
| 2024-09-28 | Init 3.3.4, Undertow, FastJSON2, jakarta fix. |
| 2024-06-27 | Init 2.7.8. |
