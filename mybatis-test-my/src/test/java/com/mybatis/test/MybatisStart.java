package com.mybatis.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.ibatis.DruidDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.myatis.example.mapper.BlogMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

public class MybatisStart {

    /**
     * 1、导入JDBC驱动包
     * 2、创建DriverManager 注册驱动
     * 3、创建连接
     * 4、创建Statement
     * 5、CRUD
     * 6、操作结果集
     * 7、关闭连接
     */
    @Test
    public void start(){
        String resource = "org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    private static DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://8.131.59.205:3306/my_sql_sty?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT&createDatabaseIfNotExist=true&nullCatalogMeansCurrent=true");
        druidDataSource.setUsername("huang");
        druidDataSource.setPassword("123456");
        return druidDataSource;
    }

    @Test
    public void start2(){
        DataSource dataSource = getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        System.out.println(mapper.findOne(10001));

        /**
         * 这里报出异常：
         * org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): org.myatis.example.mapper.BlogMapper.findOne
         *
         * 查看target目录可以看到 BlogMapper.xml没有被扫描到，需要将xml文件放入resource文件夹下才能被扫描得到，
         * 这里可以通过复制粘贴将xml放到和BlogMapper同级目录下，执行可以查询到代码
         * 结果：Blog(id=10001, title=文章10001, author=宣鹏, createTime=Wed Jun 23 19:11:55 CST 2021)
         */
    }
}
