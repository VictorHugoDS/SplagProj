package br.com.SplagProj.controller.fotopessoa;

import br.com.SplagProj.entity.FotoPessoa.dto.FotoPessoaDto;
import br.com.SplagProj.entity.FotoPessoa.FotoPessoaEntity;

import br.com.SplagProj.service.FotoPessoa.FotoPessoaService;
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
    private FotoPessoaService service;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable String id){
        try{
            FotoPessoaEntity response = service.get(Integer.valueOf(id));
            if(Objects.isNull(response)){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            System.out.println("Deu merda! {}");
            log.error("e: ", e);
            return ResponseEntity.badRequest().body("Não foi possivel executar a request");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid FotoPessoaDto request){
        try{
            FotoPessoaEntity response = service.save(request.toEntity());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            log.error("e: ", e);
            return ResponseEntity.badRequest().body("Não foi possivel executar a request");
        }
    }

    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id ,@RequestBody @Valid FotoPessoaDto request){
        try{
            FotoPessoaEntity response = service.update(Integer.valueOf(id),request.toEntity());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            log.error("e: ", e);
            return ResponseEntity.badRequest().body("Não foi possivel executar a request");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        try{
            service.delete(Integer.valueOf(id));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            log.error("e: ", e);
            return ResponseEntity.badRequest().body("Não foi possivel executar a request");
        }
    }
}
