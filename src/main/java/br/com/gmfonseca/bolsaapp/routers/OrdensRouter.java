package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.OrdensController;
import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.util.OrdemType;
import org.springframework.web.bind.annotation.*;

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
    @CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public synchronized List<Ordem> getOrdens(){
        return ordensController.getOrdens();
    }

    @CrossOrigin
    @RequestMapping(value = "/compra", method = RequestMethod.GET)
    @ResponseBody
    public synchronized List<Ordem> getOrdensCompra() throws OrderNotFoundException {
        return ordensController.getOrdens(OrdemType.COMPRA);
    }

    @CrossOrigin
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public synchronized List<Ordem> getOrdensCompra(String dateTime) throws OrderNotFoundException {
        return ordensController.getOrdens(dateTime);
    }

    @CrossOrigin
    @RequestMapping(value = "/venda", method = RequestMethod.GET)
    @ResponseBody
    public synchronized List<Ordem> getOrdensVenda() throws OrderNotFoundException {
        return ordensController.getOrdens(OrdemType.VENDA);
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Ordem getOrdem(@PathVariable("id") int ordemId) throws OrderNotFoundException {
        return ordensController.getOrdem(ordemId);
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Ordem createOrdem(OrdemType operacao, int quantidade, double valor, String ativoId, String corretoraNome)
            throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, BrokerAlreadyExistsException, BrokerNotFoundException, AssetNotFoundException, OrderNotFoundException, WrongSellOrderTypeException, WrongBuyOrderTypeException {
        return ordensController.createOrdem(operacao, quantidade, valor, ativoId, corretoraNome);
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public synchronized Ordem deleteOrdem(@PathVariable("id") int orderId)
            throws OrderNotFoundException, TransactionNotFoundException {
        return ordensController.deleteOrdem(orderId);
    }


}
