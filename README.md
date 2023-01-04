## 后端工具

| 功能                 | 使用方法                                                     | 参考文件                                                     |
| -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 数据库相关生成       | 运行CodeGenerator后输入需要连接的数据库表名回车              | src/main/java/com/campus/backend/CodeGenerator.java          |
| 异常类               |                                                              | src/main/java/com/campus/backend/common/exception/GlobalExceptionHandler.java |
| 请求结果类           |                                                              | src/main/java/com/campus/backend/common/lang/Result.java     |
| 接口访问  次数限制   | 在对应接口上方加入注解：<br>@AccessLimit(seconds=30,days = 60 * 60 * 24, perCount = 1, dayCount = 500, needLogin = false)<br>参数意义：<br>seconds:短时间限制，样例为30s可访问次数<br>days:长时间限制，样例为一天可访问次数<br>perCount:短时间限制内可访问次数<br>dayCount:长时间限制内可访问次数<br>needLogin:是否需要登录后访问接口 | src/main/java/com/campus/backend/interceptor/AccessLimitInterceptor.java |
| redis调用接口        |                                                              | src/main/java/com/campus/backend/redis/RedisOperator.java    |
| shiro+jwt            |                                                              | src/main/java/com/campus/backend/shiro<br>src/main/java/com/campus/backend/config/ShiroConfig.java |
| 获取登录用户ip并解析 |                                                              | src/main/java/com/campus/backend/tool/GetIpAddressUtil.java  |
| 发送邮箱验证码       | 参考类文件里面的main函数                                     | src/main/java/com/campus/backend/tool/EmailUtil.java         |
| 调用openAI           | 参考类文件里面的main函数，当前使用的模型支持联系上下文，所以每次新的对话开始时，需要调用clear()清除一下之前的上下文内容。 | src/main/java/com/campus/backend/tool/openAIUtil.java        |

 ## 包管理

工具类：src/main/java/com/campus/backend/tool

配置类：src/main/java/com/campus/backend/config

接口类（注解）：src/main/java/com/campus/backend/interceptor

​	注意：添加注解需要到src/main/java/com/campus/backend/config/CorsConfig.java中进行注册

一组、二组后端单独建立包进行管理：

​	一组：src/main/java/com/campus/AIDraw

​	二组：src/main/java/com/campus/community

​	公共部分：src/main/java/com/campus/backend

AI Python部分在总项目backend、frontend同级目录下建立相关接口项目

## 数据库管理

## 注意：更改数据库之后详细记录更改部分并更新文档，更改类型或名称若存在相关调用及时说明

1.User

| 字段     | 名称   | 用途                           | 格式    | 特别注意 |
| -------- | ------ | ------------------------------ | ------- | -------- |
| id       |        |                                | int     | 主键     |
| username | 用户名 |                                | varchar |          |
| avatar   | 头像   |                                | varchar |          |
| password | 密码   |                                | varchar | Md5加密  |
| email    | 邮箱   |                                | varchar |          |
| status   | 状态   | 记录当前用户状态（离线、在线） | int     |          |

### 实体

1.AI

| 字段    | 名称 | 用途 | 格式     |
| ------- | ---- | ---- | -------- |
| id      |      |      | int      |
| request | 请求 |      | longtext |
| reply   | 回复 |      | longtext |

2.identifier

| 字段              | 名称     | 用途 | 格式    |
| ----------------- | -------- | ---- | ------- |
| id                |          |      | int     |
| emailAddress      | 邮箱地址 |      | varchar |
| phoneNumber手机号 |          |      | varchar |
| identifier        | 验证码   |      | int     |
