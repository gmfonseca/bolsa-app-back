package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.TransacoesController;
import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.models.Transacao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Transacao> getTransacoes(){
        return transacoesController.getTransacoes();
    }

    /**
     *
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Transacao getTransacao(@PathVariable("id") int transacaoId) throws TransactionNotFoundException {
        return transacoesController.getTransacao(transacaoId);
    }

    /**
     *
     *
     */
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Transacao deleteTransacao(@PathVariable("id") int orderId)
            throws TransactionNotFoundException {
        return transacoesController.deleteTransacao(orderId);
    }

}
