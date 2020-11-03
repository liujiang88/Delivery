package com.example.demo;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class AutoCode {
    public static void main(String[] args) {
        AutoGenerator autoGenerator=new AutoGenerator();
        GlobalConfig globalConfig=new GlobalConfig();
        String property = System.getProperty("user.dir");
        globalConfig.setOutputDir(property+"/src/main/java");
        globalConfig.setAuthor("刘江");
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);
        globalConfig.setIdType(IdType.ID_WORKER);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setSwagger2(true);

        autoGenerator.setGlobalConfig(globalConfig);


        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/delivered?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dataSourceConfig.setDbType(DbType.MYSQL);

        autoGenerator.setDataSource(dataSourceConfig);


        PackageConfig packageConfig=new PackageConfig();
        packageConfig.setModuleName("produce");
        packageConfig.setParent("com.example.demo");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("serviceImp");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);



        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("user_order_info");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setLogicDeleteFieldName("deleted");
        TableFill creatTime=new TableFill("creat_time", FieldFill.INSERT);
        TableFill updateTime=new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> r = new ArrayList<>();
        r.add(creatTime);
        r.add(updateTime);
        strategy.setTableFillList(r);
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        autoGenerator.setStrategy(strategy);
        autoGenerator.execute();

    }
}

