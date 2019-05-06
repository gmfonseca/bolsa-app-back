package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.CorretorasController;
import br.com.gmfonseca.bolsaapp.models.Corretora;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/brokers")
public class CorretorasRouter {

    private CorretorasController corretorasController;

    public CorretorasRouter(CorretorasController corretorasController) {
        this.corretorasController = corretorasController;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Set<Corretora> getBrokers() {
        return new HashSet<>();
    }

}
