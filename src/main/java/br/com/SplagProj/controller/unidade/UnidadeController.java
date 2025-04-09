package br.com.SplagProj.controller.unidade;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.service.unidade.UnidadeService;
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
@RequestMapping("unidade")
public class UnidadeController {

    @Autowired
    UnidadeService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404", description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar Unidade por id", description = "Busca Unidade por id", tags = "Unidade")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable String id) {
        RetornoContext<Object> response = service.get(Integer.valueOf(id));
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar lista de Unidades", description = "Busca lista de Unidades em páginas de padrão de 10 itens", tags = "Unidade")
    @GetMapping
    public ResponseEntity<Object> getUnidades(
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
    @Operation(summary = "Salvar Unidade", description = "Salva uma nova Unidade", tags = "Unidade")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid UnidadeEntity request) {
        RetornoContext<Object> response = service.save(request);
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404", description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Atualiza Unidade por id", description = "Atualiza uma Unidade existente", tags = "Unidade")
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid UnidadeEntity request) {
        RetornoContext<Object> response = service.update(Integer.valueOf(id), request);
        return response.toResponseEntity();
    }
} 