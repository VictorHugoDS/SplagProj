package br.com.SplagProj.controller.lotacao;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.lotacao.dto.LotacaoDto;
import br.com.SplagProj.service.lotacao.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lotacao")
public class LotacaoController {

    @Autowired
    private LotacaoService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar Lotação  por id",description = "Busca Lotação por id",tags = "Lotação")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable String id){
        RetornoContext<Object> response = service.get(Integer.valueOf(id));
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Salvar Lotação",description = "Salva Lotação, se o id da pessoa ou unidade não existir no banco, salva uma nova entidade",tags = "Lotação")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid LotacaoDto request){
        RetornoContext<Object> response = service.save(request.toEntity());
        return response.toResponseEntity();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Atualiza Lotação por id",description = "Busca Lotação por id, se o id da pessoa não existir no banco, salva uma nova entidade pessoa",tags = "Lotação")
    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id ,@RequestBody @Valid LotacaoDto request){
        RetornoContext<Object> response = service.update(Integer.valueOf(id),request.toEntity());
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Solicitação executada com sucesso, sem retorno de body"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Deleta Lotação",tags = "Lotação")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        service.delete(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
