package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.BrokersController;
import br.com.gmfonseca.bolsaapp.models.Bolsa;
import br.com.gmfonseca.bolsaapp.models.Broker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/brokers")
public class BrokersRouter {

    private BrokersController brokersController;

    public BrokersRouter(BrokersController brokersController) {
        this.brokersController = brokersController;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Set<Broker> getBrokers() {
        return new HashSet<>();
    }

}
