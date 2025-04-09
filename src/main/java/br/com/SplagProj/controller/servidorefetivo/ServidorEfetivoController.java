package br.com.SplagProj.controller.servidorefetivo;


import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.servidorefetivo.ServidorEfetivoEntity;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.entity.servidortemporario.dto.AdmissaoDemissaoDto;
import br.com.SplagProj.service.servidorefetivo.ServidorEfetivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("servidor-efetivo")
public class ServidorEfetivoController {

    @Autowired
    ServidorEfetivoService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar Servidor Efetivo por id pessoa",description = "Busca Servidor Efetivo por id pessoa",tags = "Servidor_Efetivo")
    @GetMapping(value = "{pessoaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getByPessoaId(@PathVariable String pessoaId){
        var context = service.get(Integer.valueOf(pessoaId));
        return context.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar lista de Servidores Efetivo",description = "Busca lista de Servidores Efetivo em páginas de padrão de 10 itens",tags = "Servidor_Efetivo")
    @GetMapping("/servidores")
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
    @Operation(summary = "Criar Servidor Efetivo",description = "Salva novo Efetivo temporario",tags = "Servidor_Efetivo")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvarServidorEfetivo(@RequestBody @Valid ServidorEfetivoEntity entity){
        RetornoContext<Object> response = service.save(entity);
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "204",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Atualiza Servidor Efetivo",description = "Atualiza Efetivo temporario",tags = "Servidor_Efetivo")
    @PutMapping(value = "{pessoaId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateServidorEfetivo(
            @PathVariable String pessoaId,
            @RequestBody @Valid ServidorEfetivoEntity entity){
        RetornoContext<Object> response = service.update(Integer.valueOf(pessoaId),entity);
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Solicitação aceita"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Deletar Servidor Efetivo",description = "Apaga o Registro do Servidor Efetivo",tags = "Servidor_Efetivo")
    @DeleteMapping(value = "{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deletarServidorEfetivo(@PathVariable String idPessoa){
        RetornoContext<Object> response = service.delete(Integer.valueOf(idPessoa));
        return response.toResponseEntity();
    }

}
