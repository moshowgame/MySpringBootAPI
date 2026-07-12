# MySpringBootAPI 脚手架进化实施计划

## Context
当前项目是一个 Spring Boot 3.3.4 脚手架，使用 Undertow + MyBatis Plus，缺少认证、架构规范和质量保障。用户要求：(1) 切回 Tomcat (2) MyBatis Plus 换为 MyBatis 注解模式 (3) 基于 Spring Security 的安全加固 (4) 架构规范 (5) 质量保障。

---

## 实施步骤（按顺序）

### Step 1: Web 容器切回 Tomcat
- **pom.xml**: 删除 `spring-boot-starter-undertow` 依赖，删除 `spring-boot-starter-web` 中排除 Tomcat 的 `<exclusions>` 块
- **application-dev.yml**: 删除 `undertow:` 配置段，添加 Tomcat 线程配置，删除无用的 Freemarker 配置

### Step 2: MyBatis Plus → MyBatis 注解模式
- **pom.xml**: 删除 `mybatis-plus-spring-boot3-starter`，添加 `mybatis-spring-boot-starter:3.0.4`
- **删除** `MybatisPlusConfig.java`
- **新建** `MyBatisConfig.java` — 仅保留 `@MapperScan` + `@EnableTransactionManagement`
- **SysConfig.java**: 删除 `@TableId`、`IdType` 等 MyBatis Plus 注解
- **SysConfigMapper.java**: 去掉 `extends BaseMapper<SysConfig>`，用 `@Select/@Insert/@Update/@Delete` 注解重写所有方法
- **application-dev.yml**: 添加 `mybatis.configuration.map-underscore-to-camel-case: true`

### Step 3: 架构规范（在加安全之前先搭好结构）
- **ReturnUtil.java**: 改为泛型 `ReturnUtil<T>`，构造器私有化，工厂方法加 `<T>` 类型参数
- **新建 DTO**: `LoginRequest`、`RegisterRequest`、`SysConfigDTO`
- **新建 VO**: `LoginVO`、`SysConfigVO`
- **SysConfigService.java**: 重写为接口
- **新建 SysConfigServiceImpl.java**: 实现接口，Controller 不再直接注入 Mapper
- **IndexController.java**: 简化为仅返回健康信息
- **新建 SysConfigController.java**: 标准 CRUD，注入 Service，使用 DTO/VO
- **新建 logback-spring.xml**: dev 用 CONSOLE，prod 用滚动文件

### Step 4: Spring Security 安全加固
- **pom.xml**: 添加 `spring-boot-starter-security`、`jjwt-api/impl/jackson:0.12.6`、`jasypt-spring-boot-starter:3.0.5`、`spring-boot-starter-validation`
- **新建 SQL**: `sys_user.sql` — 用户表 + 默认 admin 账号
- **新建 Entity**: `SysUser.java`
- **新建 Mapper**: `SysUserMapper.java`（MyBatis 注解模式）
- **新建 Service**: `SysUserService` 接口 + `SysUserServiceImpl`
- **新建 Controller**: `AuthController` — `/auth/login`、`/auth/register`
- **新建 Security 层**:
  - `JwtTokenProvider` — 生成/验证 JWT
  - `JwtAuthenticationFilter` — 请求拦截提取 Token
  - `JwtAuthenticationEntryPoint` — 401 响应
  - `CustomUserDetailsService` — 加载用户
  - `SecurityConfig` — 过滤链配置（放行 `/auth/**`、`/api-doc/**`、`/swagger-ui/**`、`/system/druid/**`）
  - `CorsConfig` — CORS 配置 Bean
- **新建 Exception**: `BusinessException`（业务异常）、自定义验证异常
- **重写 GlobalDefaultExceptionHandler**: 细分异常类型（BusinessException、MethodArgumentNotValidException、BadCredentialsException、AccessDeniedException、通用 Exception），用 `log.error()` 替代 `e.printStackTrace()`，不再泄露内部错误信息
- **SpringDocConfig.java**: 添加 JWT Bearer `@SecurityScheme`
- **删除 UserLoginInterceptor.java**: 已被 Spring Security 替代
- **application-dev.yml**: 添加 `jwt.secret/expiration`、`cors.allowed-origins`，DB 密码用 Jasypt `ENC()` 加密

### Step 5: 质量保障
- **pom.xml**: 删除 JUnit 4，添加 `spring-boot-starter-test`（含 JUnit 5），添加 `spring-boot-starter-aop`
- **新建 RequestLogAspect.java**: AOP 切面记录请求耗时
- **新建 XssFilter + XssHttpServletRequestWrapper**: 过滤 XSS 攻击
- **新建 SqlInjectionFilter**: 防御 SQL 注入（正则黑名单）
- **新建 RateLimitFilter**: 基于 IP 的内存限流（60次/分钟）
- **新建 FilterConfig**: 注册以上 Filter
- **新建测试**:
  - `SysConfigServiceImplTest.java` — Mockito 单元测试
  - `SysUserServiceImplTest.java` — Mockito 单元测试
  - `SysConfigControllerTest.java` — @WebMvcTest
  - `AuthControllerTest.java` — @WebMvcTest
  - `JwtTokenProviderTest.java` — 单元测试

---

## 文件清单

| 操作 | 文件 | 说明 |
|------|------|------|
| 修改 | `pom.xml` | 依赖变更 |
| 修改 | `application-dev.yml` | Tomcat/JWT/CORS/MyBatis/Jasypt |
| 修改 | `application-prod.yml` | 生产环境配置模板 |
| 修改 | `SysConfig.java` | 去掉 MyBatis Plus 注解 |
| 修改 | `SysConfigMapper.java` | 纯 MyBatis 注解重写 |
| 修改 | `SysConfigService.java` | 重写为接口 |
| 修改 | `ReturnUtil.java` | 泛型化 |
| 修改 | `GlobalDefaultExceptionHandler.java` | 异常体系重构 |
| 修改 | `SpringDocConfig.java` | JWT SecurityScheme |
| 重写 | `IndexController.java` | 简化健康端点 |
| 删除 | `MybatisPlusConfig.java` | 被 MyBatisConfig 替代 |
| 删除 | `UserLoginInterceptor.java` | 被 Spring Security 替代 |
| 新建 | `MyBatisConfig.java` | MapperScan 配置 |
| 新建 | `SecurityConfig.java` | Security 过滤链 |
| 新建 | `CorsConfig.java` | CORS 配置 |
| 新建 | `FilterConfig.java` | 注册 XSS/限流 Filter |
| 新建 | `JwtTokenProvider.java` | JWT 工具类 |
| 新建 | `JwtAuthenticationFilter.java` | JWT 过滤器 |
| 新建 | `JwtAuthenticationEntryPoint.java` | 401 处理 |
| 新建 | `CustomUserDetailsService.java` | 用户加载服务 |
| 新建 | `SysUser.java` | 用户实体 |
| 新建 | `SysUserMapper.java` | 用户 Mapper |
| 新建 | `SysUserService.java` | 用户服务接口 |
| 新建 | `SysUserServiceImpl.java` | 用户服务实现 |
| 新建 | `SysConfigServiceImpl.java` | 配置服务实现 |
| 新建 | `AuthController.java` | 认证控制器 |
| 新建 | `SysConfigController.java` | 配置 CRUD 控制器 |
| 新建 | `LoginRequest.java` | 登录 DTO |
| 新建 | `RegisterRequest.java` | 注册 DTO |
| 新建 | `SysConfigDTO.java` | 配置 DTO |
| 新建 | `LoginVO.java` | 登录响应 VO |
| 新建 | `SysConfigVO.java` | 配置响应 VO |
| 新建 | `BusinessException.java` | 业务异常 |
| 新建 | `RequestLogAspect.java` | AOP 请求日志 |
| 新建 | `XssFilter.java` | XSS 过滤 |
| 新建 | `XssHttpServletRequestWrapper.java` | XSS 请求包装 |
| 新建 | `SqlInjectionFilter.java` | SQL 注入过滤 |
| 新建 | `RateLimitFilter.java` | 限流过滤器 |
| 新建 | `logback-spring.xml` | 日志配置 |
| 新建 | `sys_user.sql` | 用户表 DDL |
| 新建 | 5 个测试文件 | JUnit 5 单元测试 |

## 验证方式
1. `mvn clean compile` 编译通过
2. `mvn test` 全部测试通过
3. 启动应用后访问 `http://localhost:12666/api/` 返回健康信息
4. POST `/api/auth/login` 可获取 JWT Token
5. 携带 Token 访问 `/api/config/list` 返回配置数据
6. 不携带 Token 访问受保护接口返回 401
7. Swagger UI 可正常访问并支持 Bearer Token 认证
