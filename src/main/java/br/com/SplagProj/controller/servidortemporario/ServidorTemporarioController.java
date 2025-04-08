package br.com.SplagProj.controller.servidortemporario;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.entity.servidortemporario.dto.AdmissaoDemissaoDto;
import br.com.SplagProj.service.servidortemporario.ServidorTemporarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("servidor-temporario")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Buscar Servidor temporario por id pessoa",description = "Busca Servidor temporario por id pessoa",tags = "Servidor_temp")
    @GetMapping(value = "{pessoaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getByPessoaId(@PathVariable String pessoaId){
            var context = service.get(Integer.valueOf(pessoaId));
            return context.toResponseEntity();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Admitir Servidor temporario",description = "Admite Servidor temporario",tags = "Servidor_temp")
    @PostMapping(value = "/admitir",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> admitirServidorTemporario(@RequestBody @Valid AdmissaoDemissaoDto dto){
        RetornoContext<Object> response = service.admissao(dto);
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar alguma entidade"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Demitir Servidor temporario",description = "Demite Servidor temporario",tags = "Servidor_temp")
    @PutMapping(value = "/demitir",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> demitirServidorTemporario(@RequestBody @Valid AdmissaoDemissaoDto dto){
        RetornoContext<Object> response = service.demissao(dto);
        return response.toResponseEntity();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Solicitação executada com sucesso"),
            @ApiResponse(responseCode = "204",description = "Nenhuma alteração foi feita"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Atualizar Servidor Temporário",description = "Atualiza o Registro do Servidor Temporario",tags = "Servidor_temp")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> atualizarServidorTemporario(@RequestBody @Valid ServidorTemporarioEntity entity){
        RetornoContext<Object> response = service.update(entity);
        return response.toResponseEntity();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Solicitação aceita"),
            @ApiResponse(responseCode = "400",description = "Solicitação mal formatada"),
            @ApiResponse(responseCode = "500",description = "Erro ao realizar a solicitação")
    })
    @Operation(summary = "Deletar Servidor Temporário",description = "Apaga o Registro do Servidor Temporario",tags = "Servidor_temp")
    @DeleteMapping(value = "{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deletarServidorTemporario(@PathVariable String idPessoa){
        RetornoContext<Object> response = service.delete(Integer.valueOf(idPessoa));
        return response.toResponseEntity();
    }
}
