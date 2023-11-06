package org.apache.alan.wrokflow;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @Description:
 * @Author: Alan
 * @date: 2023-11-03 15:46
 */
@EnableCaching
@ServletComponentScan(basePackages = "org.apache.alan.wrokflow.*")
@Configuration
@SpringBootApplication(scanBasePackages = {"org.apache.alan.wrokflow"})
@EnableScheduling
@EnableAsync
@MapperScan({"org.apache.alan.wrokflow.dao.**"})
@EnableTransactionManagement
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
