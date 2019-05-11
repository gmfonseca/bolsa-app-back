package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.CorretorasController;
import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Corretora;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RequestMapping("/corretoras")
public class CorretorasRouter {

    private CorretorasController corretorasController;

    public CorretorasRouter(CorretorasController corretorasController) {
        this.corretorasController = corretorasController;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public synchronized List<Corretora> getBrokers() {
        return corretorasController.getCorretoras();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Corretora getCorretora(@PathVariable("id") int corretoraId) throws BrokerNotFoundException {
        return corretorasController.getCorretora(corretoraId);
    }

    @RequestMapping(value = "/{nome}", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Corretora getCorretora(@PathVariable("nome") String nome) throws BrokerNotFoundException {
        return corretorasController.getCorretora(nome);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Corretora createCorretora(String nome) throws BrokerAlreadyExistsException, NotFilledRequiredFieldsException {
        return corretorasController.createCorretora(nome);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public synchronized Corretora renameCorretora(@PathVariable("id") int corretoraId, String nome) throws BrokerNotFoundException, NotFilledRequiredFieldsException, BrokerNotUpdatedException {
        return corretorasController.renameCorretora(corretoraId, nome);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public synchronized Corretora deleteCorretora(@PathVariable("id") int corretoraId) throws BrokerNotFoundException, OrderNotFoundException, TransactionNotFoundException {
        return corretorasController.deleteCorretora(corretoraId);
    }

}
