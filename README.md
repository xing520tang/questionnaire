# Questionnaire
## Introduction

一个基于SpringBoot的简易问卷调查系统

### Features

* 采用Nginx实现前后端分离（并不规范）
* 用户管理
* 发布问卷
* 回答问卷

### ToDo

- 后台管理
- 验证码
- 带答案式问卷
- 统计分析
- 导出excel
- 权限验证

### 后端依赖

+ SpringBoot 2.1.10
+ Mybatis
+ hutool工具包
+ Lombok插件
+ PageHelper分页工具

### 前端依赖

| 框架                     | 介绍                            |
| ------------------------ | ------------------------------- |
| BootStrap                | 基础的布局框架                  |
| BootStrap-datetimepicker | 日期选择工具                    |
| BootStrap-table          | 表格显示工具                    |
| BootStrapValidator       | 表单验证工具                    |
| Jquery                   | 基础框架，是BootStrap框架的基础 |
| Jsrender                 | 模板引擎，动态生成页面          |
| toastr                   | 提示工具                        |

### 开发工具

+ IDEA 2018.1.5

### 参考项目/资料

* [ 基于Beego + BootStrap的简易问卷调查系统 ](https://github.com/qwqw3qee/beego_survey)

### 如何使用

1. 部署运行环境
   * 安装反向代理服务软件Nginx，windows或linux版本均可
   * 静态html，js，图片等在上面的resource文件夹内，将这些放入服务器中
   * Java版本1.8
   * mysql5.5
   
2. 配置文件

   * Nginx配置文件，打开nginx.conf文件，进行如下配置

   ```nginx
   server {
           listen       80;
        server_name  127.0.0.1;  ####需设置服务器ip####
   
        #charset koi8-r;
   
           #access_log  logs/host.access.log  main;
   
           location /nginx/ {
               root   html;
               index  index.html index.htm;
           }
   
           #动静分离
           location /views/ {
           ###下面的根目录需要设置为静态资源的根目录，若将代码克隆下来直接放到linux服务器根目录下，则不需要改###
                   root /resource/questionnaire_static_res;#html页面存储位置的根目录
           }
   #该配置是指nginx收到的url中包含/views/的请求，nginx就会去/resource/questionnaire_static_res/views中查找
           location /static/ {
                   root /resource/questionnaire_static_res; #同上
           }
   
          #反向代理，如果nginx匹配完上述两个规则后，未匹配到相应资源，则会将请求转发至如下服务器的对应端口
           location / {
           ###下面的地址需要设置为后端入口地址###
                   proxy_pass http://localhost:9999;
           }
   }
   ```
   
   * application.properties文件作相应修改
   
   ```properties
   #服务端设置
server.port=请求端口 （这个端口要与nginx配置文件中反向代理配置的端口保持一致）
   #图片文件夹路径
path.image.dir=图片保存路径 （该路径需与resource中的
   
   #数据源配置
   spring.datasource.username=数据库用户名
   spring.datasource.password=数据库密码
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/question?serverTimezone=GMT%2B8
   
   #上传文件大小限制
   spring.servlet.multipart.max-file-size=2MB
   
   #告诉springmvc封装日期类型的数据使用如下格式
   spring.mvc.date-format=yyyy-MM-dd HH:mm:ss
   
   #日志配置
   #com.tinyspot包下的日志级别为debug
   logging.level.com.tinyspot=debug  
   logging.file=日志存储文件位置，可以不设置
   #logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%-5level] %logger{50}==> %msg%n
   
   ```
   
   * 导入数据库
   
   ```properties
   将 data 下面的sql文件导入数据库中即可
   ```
   
   * 其他说明
   
   ```properties
   数据库的users表需要一个默认用户，id为0号的匿名用户
   ```
   
   
   
   * 未完待续。。。
