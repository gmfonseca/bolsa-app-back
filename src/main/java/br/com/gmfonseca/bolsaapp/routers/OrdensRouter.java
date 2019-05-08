package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.OrdensController;
import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.util.OrdemType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping("/ordens")
public class OrdensRouter {

    private OrdensController ordensController;

    public OrdensRouter(OrdensController ordensController) {
        this.ordensController = ordensController;
    }

    /**
     *
     *
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Ordem> getOrdens(){
        return ordensController.getOrdens();
    }

    /**
     *
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Ordem getOrdem(@PathVariable("id") int ordemId) throws OrderNotFoundException {
        return ordensController.getOrdem(ordemId);
    }

    /**
     *
     *
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Ordem createOrdem(OrdemType operacao, int quantidade, double valor, String ativoId, int corretoraId)
            throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, InvalidOrderTypeValueException, BrokerNotFoundException {
        return ordensController.createOrdem(operacao, quantidade, valor, ativoId, corretoraId);
    }

    /**
     *
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Ordem deleteOrdem(@PathVariable("id") int orderId)
            throws OrderNotFoundException, TransactionNotFoundException {
        return ordensController.deleteOrdem(orderId);
    }


}
