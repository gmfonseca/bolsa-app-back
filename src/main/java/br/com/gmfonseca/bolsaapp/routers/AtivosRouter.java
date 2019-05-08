package br.com.gmfonseca.bolsaapp.routers;

import br.com.gmfonseca.bolsaapp.controllers.AtivosController;
import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RequestMapping("/ativos")
public class AtivosRouter {

    private AtivosController ativosController;

    public AtivosRouter(AtivosController ativosController) {
        this.ativosController = ativosController;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Ativo> getAtivos() {
        return ativosController.getAtivos();
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    @ResponseBody
    public Ativo getAtivo(@PathVariable("codigo") String codigo) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException {
        return ativosController.getAtivo(codigo);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Ativo createAtivo(String nome, String codigo, String descricao) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetAlreadyExistsException {
        return ativosController.createAtivo(nome, codigo, descricao);
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
    @ResponseBody
    public Ativo updateAtivo(@PathVariable("codigo") String codigo, String nome, String descricao) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, AssetNotUpdatedException {
        return ativosController.updateAtivo(nome, codigo, descricao);
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
    @ResponseBody
    public Ativo deleteAtivo(@PathVariable("codigo") String codigo) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, OrderNotFoundException, TransactionNotFoundException {
        return ativosController.deleteAtivo(codigo);
    }



}
