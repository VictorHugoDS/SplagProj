package br.com.SplagProj.controller.fotopessoa;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.fotopessoa.dto.FotoPessoaDto;
import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;

import br.com.SplagProj.service.fotopessoa.FotoPessoaService;
import br.com.SplagProj.service.lotacao.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("foto-pessoa")
public class FotoPessoaController {

    @Autowired
    FotoPessoaService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar Foto Pessoa por id",description = "Busca Foto Pessoa por id",tags = "Foto_Pessoa")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable String id){
            RetornoContext<Object> response = service.get(Integer.valueOf(id));
            return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar lista de Fotos",description = "Busca lista Fotos em páginas de padrão de 10 itens",tags = "Foto_Pessoa")
    @GetMapping("/fotos")
    public ResponseEntity<Object> getServidores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var context = service.getAllPaginado(page,size);
        return context.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Salvar Foto Pessoa",description = "Salva Lotação, se o id da pessoa não existir no banco, salva uma nova entidade pessoa",tags = "Foto_Pessoa")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid FotoPessoaDto request){
            RetornoContext<Object> response = service.save(request.toEntity());
            return response.toResponseEntity();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Atualiza Lotação por id",description = "Busca Foto da Pessoa por id, se o id da pessoa não existir no banco, salva uma nova entidade pessoa",tags = "Foto_Pessoa")
    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id ,@RequestBody @Valid FotoPessoaDto request){
            RetornoContext<Object> response = service.update(Integer.valueOf(id),request.toEntity());
            return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Solicitação executada com sucesso, sem retorno de body"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Deleta Lotação",tags = "Foto_Pessoa")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
            service.delete(Integer.valueOf(id));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
