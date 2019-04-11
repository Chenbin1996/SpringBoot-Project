# 通用后台框架-混合式开发基础项目

## 项目简介

> 所有项目的种子项目，关于混合式开发的后台项目，都从本项目中扩展；用户快速构建中小型RESTful项目，摆脱重复劳动构建项目，项目架构统一。

**`template`是一个基于Spring Boot + Mybatis  Plus + Layui组合而成的RBAC权限管理系统，内置基础的模块，用户管理，角色管理，部门管理，菜单设置，角色菜单管理；实现通过角色分配菜单思维控制用户权限，登录鉴权等功能。**

**注：**开箱即用，代码功能以及页面都已实现，只需要根据规则，在基础上扩展模块；`template`只是一个基础的框架项目，除了以上功能，其余功能自扩展。

## 功能简介

- 通过Spring HandlerInterceptor实现Url鉴权，未登录的请求都将被拦截到登录页面

- 登录逻辑以及登录页面

- 统一异常处理

- 统一前端URL前缀处理

- 解决跨域问题

- 最佳实践的项目结构、配置文件、精简的POM

- 统一响应结果封装及生成工具

- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控

- 使用FastJsonHttpMessageConverter，提高JSON序列化速度

- 使用JWT验证token，达到请求鉴权，用户菜单权限分配

- 集成MyBatis Plus、Mybatis Plus多数据源，实现单表业务零SQL

- 统一使用封装的redis客户端实现缓存

- 统一使用FileManagerClient实现文件上传下载

- 使用Filter控制项目模式（开发/生产）

- 使用Freemarker + LayUI实现前端页面

- 通过菜单设置实现系统菜单路由

## 架构简介

### 1. application.properties文件

整个项目的配置文件，有数据库连接池配置，可更改数据库地址，其余数据库设置不建议修改；

有Mybatis Plus配置，只需要修改`mybatis-plus.type-aliases-package`这一行，改成目前项目实体类所在包路径；

有Redis配置，只需要改地址、端口、密码，其余不需要修改；

Freemarker配置，只需要修改自定义配置项，改成自己的IP；

开发环境配置，部署到线上时，改成`pro`

### 2. 项目包com.ruxuanwo.template说明

1. Application启动类

   位于包根目录下，启动类上必须加上`@ComponentScan`和`@MapperScan`两个注解，用于扫描mapper文件以及IOC中的Bean

2. base包

   放置了扩展Mybatis Plus的全局Service以及前端请求路由的前缀控制

3. config包

   放置了整个项目的配置，有Mybatis Plus的配置、全局WebMvc配置，数据连接池配置

4. constant包

   存放项目一些自定义常量，在依赖util中有一个通用常量类

5. controller包

   存放路由接口

6. domain包

   数据库表实体类

7. dto包

   数据库表传输DTO

8. exception包

   全局异常配置

9. filter包

   全局过滤器，控制项目是开发模式还是生产模式

10. interceptor包

    全局拦截器，对所有请求进行拦截效验是否有权限

11. mapper包

    mybatis数据库操作接口

12. redis包

    提供了基础接口类，所有缓存方法都写在这个接口方法上并且实现，然后调用

13. service包

    业务处理层接口，所有的controller只能提供路由，具体业务实现需要在service层

14. utils包

    存放工具类

    

### 3. resources目录说明

1. mapper包

   存放mybatis XML文件

2. static包

   前端的静态资源目录，如css、js等依赖

3. template包

   前端的页面所在位置，必须放在这个目录下，不然不可渲染跳转




