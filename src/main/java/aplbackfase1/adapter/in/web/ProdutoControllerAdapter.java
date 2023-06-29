package aplbackfase1.adapter.in.web;

import aplbackfase1.adapter.in.web.exceptions.StandardError;
import aplbackfase1.domain.model.Produto;
import aplbackfase1.domain.enums.TipoProduto;
import aplbackfase1.domain.ports.in.IProdutoUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@RestController
@RequestMapping("/tech-challenge")
@RequiredArgsConstructor
public class ProdutoControllerAdapter {

    private final IProdutoUseCasePort produtoUseCasePort;

    @GetMapping("/produtos/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") UUID idProduto) {
            Optional<Produto> produto = this.produtoUseCasePort.buscarProdutoPorID(idProduto);
            return produto.isPresent() ?
                    new ResponseEntity<>(produto, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/produtos")
    public ResponseEntity<?> buscarProduto(@RequestParam String tipoProduto) {
        if(Objects.nonNull(tipoProduto)) {
            Optional<ArrayList<Produto>> produtoArrayList = this.produtoUseCasePort
                    .listarProdutosPorTipoProduto(TipoProduto.fromCodigo(tipoProduto));
            return produtoArrayList.isPresent() ?
                    new ResponseEntity<>(produtoArrayList.get(), HttpStatus.OK) :
                    new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } else {
            return this.buildStandardError(
                    HttpStatus.BAD_REQUEST,
                    "tipo do produto não pode ser nulo ou vazio",
                    null);
        }
    }

    @PostMapping("/produtos")
    public ResponseEntity<?> criarProduto(Produto request) {
        if(Objects.nonNull(request)) {
            Produto produto = this.produtoUseCasePort.criarProduto(request);
            return new ResponseEntity<>(produto, HttpStatus.CREATED);
        } else {
            return this.buildStandardError(
                    HttpStatus.BAD_REQUEST,
                    "corpo da requisição não pode ser nulo ou vazio",
                    null);
        }
    }

    @DeleteMapping("/produtos")
    public ResponseEntity<?> deletarProduto(@RequestParam String id) {
        if(Objects.nonNull(id)) {
            this.produtoUseCasePort.deletarProduto(UUID.fromString(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return this.buildStandardError(
                    HttpStatus.BAD_REQUEST,
                    "ID do produto não pode ser nulo ou vazio",
                    null);
        }
    }

    private ResponseEntity<?> buildStandardError(HttpStatus httpStatus, String erro, String mensagem){
        StandardError err = new StandardError(System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                erro,
                mensagem,
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().toString());

        return  new ResponseEntity<>(err, httpStatus);
    }

}