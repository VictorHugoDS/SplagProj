package br.com.SplagProj.controller.pessoa;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.service.pessoa.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    PessoaService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404", description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar Pessoa por id", description = "Busca Pessoa por id", tags = "Pessoa")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable String id) {
        RetornoContext<Object> response = service.get(Integer.valueOf(id));
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar lista de Pessoas", description = "Busca lista de Pessoas em páginas de padrão de 10 itens", tags = "Pessoa")
    @GetMapping
    public ResponseEntity<Object> getPessoas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var context = service.getAllPaginado(page, size);
        return context.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404", description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Salvar Pessoa", description = "Salva uma nova Pessoa", tags = "Pessoa")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid PessoaEntity request) {
        RetornoContext<Object> response = service.save(request);
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404", description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Atualiza Pessoa por id", description = "Atualiza uma Pessoa existente", tags = "Pessoa")
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid PessoaEntity request) {
        RetornoContext<Object> response = service.update(Integer.valueOf(id), request);
        return response.toResponseEntity();
    }
} 