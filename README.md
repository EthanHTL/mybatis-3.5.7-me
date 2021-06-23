# Mybatis 源码
相关连接

[官方](https://mybatis.org/mybatis-3/zh/getting-started.html)
## 环境搭建
**相关连接**

[博客1](https://www.cnblogs.com/h--d/p/14728623.html)

### 编译源码

1、编译mybatis-parent项目
切换mybatis-parent项目：

命令：mvn clean install

2、编译mybatis项目
切换mybatis项目（可以修改一下版本号，修改成自己特有的版本，方便区分）：

修改mybatis版本（3.5.1-MY）。避免与官网依赖相同版本

命令：mvn install -Dmaven.test.skip=true

**注意：** 可能pdf报错
 
**解决：** 将maven-pdf-plugin插件注释，再次进行编译安装