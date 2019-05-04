package br.com.gmfonseca.bolsaapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Configuration
public class AppConfig {

    public static AnnotationConfigApplicationContext getContext() {
        return new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Bean
    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("bolsavalores").createEntityManager();
    }

    /* examples
    @Bean
    public Router router() {
        return new Router(controller());
    }

    @Bean
    Controller controller() {
        return new Controller(this.getEntityManager());
    }
    */
}
