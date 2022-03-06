package com.lik;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CodeGenerator {

    @Test
    public void run() {
        String url = "jdbc:mysql://localhost:3306/lik_tiger?serverTimezone=GMT%2B8";
        String username = "root";
        String password = "123456";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("lik") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://Code//idea//lik-demo//lik-test//lik-test-mybatis-plus-generator//src//main//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.lik") // 设置父包名
//                            .moduleName("pojo") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "D://Code//idea//lik-demo//lik-test//lik-test-mybatis-plus-generator//src//main//java//com//lik//mapper//xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
//                            .addInclude("t_simple") // 设置需要生成的表名
                            .addTablePrefix("bs_"); // 设置过滤表前缀

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
