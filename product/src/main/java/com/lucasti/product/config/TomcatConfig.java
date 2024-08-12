package com.lucasti.product.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Configuration
public class TomcatConfig {

//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
//        return factory -> factory.addConnectorCustomizers(connector -> {
//            // Configurar o ExecutorService para usar Virtual Threads
//            ExecutorService virtualThreadExecutor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());
//
//            // Definir o executor para o conector
//            connector.getProtocolHandler().setExecutor(virtualThreadExecutor);
//        });
//    }

//    @Bean(description = "taskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(12);
//        executor.setMaxPoolSize(24);
//        executor.setQueueCapacity(200);
//        executor.setThreadNamePrefix("Async-");
//
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//
//        executor.initialize();
//        return executor;
//    }

//    @Bean(description = "taskExecutor")
//    public Executor taskExecutor() {
//        return Executors.newVirtualThreadPerTaskExecutor(); // Use o executor desejado
//    }
}
