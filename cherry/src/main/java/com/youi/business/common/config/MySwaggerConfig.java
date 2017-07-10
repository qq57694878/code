package com.youi.business.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/*
@Configuration
@EnableWebMvc//如果没加这个会报错
@EnableSwagger//上面三个注释都是必要的，无添加便会出现莫名奇妙的错误
@ComponentScan(basePackages="com.youi")//添加这个注释，会自动扫描该类中的每一个方法自动生成api文档*/
public class MySwaggerConfig {  
  /*
      private SpringSwaggerConfig springSwaggerConfig;
  
        *//**
         * Required to autowire SpringSwaggerConfig 
         *//*
        @Autowired
        public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)  
        {  
            this.springSwaggerConfig = springSwaggerConfig;  
        }  
  
        *//**
         * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc 
         * framework - allowing for multiple swagger groups i.e. same code base 
         * multiple swagger resource listings. 
         *//*
        @Bean
        public SwaggerSpringMvcPlugin customImplementation()
        {  
            return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(  
                    ".*?");  
        }  
  
        private ApiInfo apiInfo()
        {  
            ApiInfo apiInfo = new ApiInfo(  
                    "微动生活管控平台", //应用名称
                    "内部人员开发文档",//文档名称  
                    "本API为开发人员前后端交互文档", //概述
                    "57694878@qq.com", //联系作者
                    "无许可证",//许可证明  
                    "localhost:8888/api");//url地址
            return apiInfo;  
        }  */
}  