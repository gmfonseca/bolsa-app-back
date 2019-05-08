package br.com.gmfonseca.bolsaapp;

import br.com.gmfonseca.bolsaapp.controllers.*;
import br.com.gmfonseca.bolsaapp.routers.*;
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

    @Bean
    TransacoesController transacoesController(){
        return new TransacoesController(this.getEntityManager());
    }

    @Bean
    public TransacoesRouter transacoesRouter(){
        return new TransacoesRouter(transacoesController());
    }



}
