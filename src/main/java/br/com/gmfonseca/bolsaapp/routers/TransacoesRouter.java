package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.TransacoesController;
import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Transacao;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transacoes")
public class TransacoesRouter {

    private TransacoesController transacoesController;

    public TransacoesRouter(TransacoesController transacoesController) {
        this.transacoesController = transacoesController;
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Transacao> getTransacoes(){
        return transacoesController.getTransacoes();
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Transacao getTransacao(@PathVariable("id") int transacaoId) throws TransactionNotFoundException {
        return transacoesController.getTransacao(transacaoId);
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Transacao createTransacao(String ativoId, int quantidade, double valor, int vendaId, int compraId)
            throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, OrderNotFoundException, WrongSellOrderTypeException, WrongBuyOrderTypeException {
        return transacoesController.createTransacao(ativoId, quantidade, valor, vendaId, compraId);
    }

    /**
     *
     *
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Transacao deleteTransacao(@PathVariable("id") int orderId)
            throws TransactionNotFoundException {
        return transacoesController.deleteTransacao(orderId);
    }

}
