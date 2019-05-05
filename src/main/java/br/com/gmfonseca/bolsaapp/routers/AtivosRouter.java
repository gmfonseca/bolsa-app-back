package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.AtivosController;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/ativos")
public class AtivosRouter {

    private AtivosController ativosController;

    public AtivosRouter(AtivosController ativosController) {
        this.ativosController = ativosController;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Set<Ativo> getAtivos() {
        return new HashSet<>();
    }
}
