package br.com.gmfonseca.bolsaapp;

import br.com.gmfonseca.bolsaapp.controllers.AtivosController;
import br.com.gmfonseca.bolsaapp.controllers.BolsasController;
import br.com.gmfonseca.bolsaapp.controllers.CorretorasController;
import br.com.gmfonseca.bolsaapp.controllers.OrdensController;
import br.com.gmfonseca.bolsaapp.routers.AtivosRouter;
import br.com.gmfonseca.bolsaapp.routers.BolsasRouter;
import br.com.gmfonseca.bolsaapp.routers.CorretorasRouter;
import br.com.gmfonseca.bolsaapp.routers.OrdensRouter;
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
    CorretorasController brokersController(){
        return new CorretorasController(this.getEntityManager());
    }

    @Bean
    public CorretorasRouter brokersRouter(){
        return new CorretorasRouter(brokersController());
    }

    @Bean
    BolsasController bolsasController(){
        return new BolsasController(this.getEntityManager());
    }

    @Bean
    public BolsasRouter bolsasRouter(){
        return new BolsasRouter(bolsasController());
    }

    @Bean
    AtivosController ativosController(){
        return new AtivosController(this.getEntityManager());
    }

    @Bean
    public AtivosRouter ativosRouter(){
        return new AtivosRouter(ativosController());
    }

    @Bean
    OrdensController ordensController(){
        return new OrdensController(this.getEntityManager());
    }

    @Bean
    public OrdensRouter ordensRouter(){
        return new OrdensRouter(ordensController());
    }



}
