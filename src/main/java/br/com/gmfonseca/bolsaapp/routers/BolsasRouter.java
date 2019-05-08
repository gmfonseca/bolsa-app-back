package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.BolsasController;
import br.com.gmfonseca.bolsaapp.models.Bolsa;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/bolsas")
public class BolsasRouter {

    private BolsasController bolsasController;

    public BolsasRouter(BolsasController bolsasController) {
        this.bolsasController = bolsasController;
    }

    @CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Set<Bolsa> getBolsas() {
        return new HashSet<>();
    }

}


