package br.com.SplagProj.service.servidorefetivo;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.servidorefetivo.ServidorEfetivoEntity;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.repository.servidorefetivo.ServidorEfetivoRepository;
import br.com.SplagProj.repository.servidortemporario.ServidorTemporarioRepository;
import br.com.SplagProj.service.pessoa.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Slf4j
@Service
public class ServidorEfetivoServiceimpl implements ServidorEfetivoService {

    @Autowired
    ServidorEfetivoRepository repository;

    @Autowired
    PessoaService pessoaService;

    @Override
    public RetornoContext<Object> get(Integer idPessoa) {

        try{
            PessoaEntity pessoa = pessoaService.encontrarPorId(idPessoa);
            if(Objects.isNull(pessoa)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " a pessoa";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }

            ServidorEfetivoEntity servidor = repository.findByPessoa(pessoa).orElse(null);
            if(Objects.isNull(servidor)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA + " o servidor temporario";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            return RetornoContext.builder().body(servidor).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @Override
    public RetornoContext<Object> save(ServidorEfetivoEntity entity) {
        entity.setId(null);
        try{
            PessoaEntity pessoa = pessoaService.verificaPessoaExiste(entity.getPessoa());
            if(Objects.isNull(pessoa)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            var registro = repository.findByPessoa(pessoa).orElse(null);
            if(Objects.nonNull(registro)) return RetornoContext.builder().mensagem("Pessoa já possui Matrícula").status(HttpStatus.NOT_ACCEPTABLE).build();

            entity.setPessoa(pessoa);
            var retorno = repository.save(entity);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> update(Integer idPessoa,ServidorEfetivoEntity entity) {
        try{
            PessoaEntity pessoa = pessoaService.encontrarPorId(idPessoa);
            ServidorEfetivoEntity servidor = repository.findByPessoa(pessoa).orElse(null);
            if(Objects.isNull(servidor)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA + " o servidor temporario";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NO_CONTENT).build();
            }
            ServidorEfetivoEntity registro = repository.findByPessoa(pessoa).orElse(null);
            if(Objects.nonNull(registro)) return RetornoContext.builder().mensagem("Pessoa já possui Matrícula").status(HttpStatus.NOT_ACCEPTABLE).build();


            entity.setId(servidor.getId());
            repository.save(entity);
            return RetornoContext.builder().body(servidor).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> delete(Integer idPessoa) {
        try{
            PessoaEntity pessoa = pessoaService.encontrarPorId(idPessoa);
            if(Objects.isNull(pessoa)) return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();


            ServidorEfetivoEntity servidor = repository.findByPessoa(pessoa).orElse(null);
            if(Objects.isNull(servidor)) return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();
            repository.delete(servidor);

            return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
