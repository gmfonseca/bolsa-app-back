package br.com.gmfonseca.bolsaapp;

import br.com.gmfonseca.bolsaapp.controllers.BolsasController;
import br.com.gmfonseca.bolsaapp.controllers.BrokersController;
import br.com.gmfonseca.bolsaapp.routers.BolsasRouter;
import br.com.gmfonseca.bolsaapp.routers.BrokersRouter;
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

    @Bean
    BrokersController brokersController(){
        return new BrokersController(this.getEntityManager());
    }

    @Bean
    public BrokersRouter brokersRouter(){
        return new BrokersRouter(brokersController());
    }

    @Bean
    BolsasController bolsasController(){
        return new BolsasController(this.getEntityManager());
    }

    @Bean
    public BolsasRouter bolsasRouter(){
        return new BolsasRouter(bolsasController());
    }
}
