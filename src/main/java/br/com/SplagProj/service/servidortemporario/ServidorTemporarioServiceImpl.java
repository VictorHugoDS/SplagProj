package br.com.SplagProj.service.servidortemporario;


import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.entity.servidortemporario.dto.AdmissaoDemissaoDto;
import br.com.SplagProj.repository.Pessoa.PessoaRepository;
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
public class ServidorTemporarioServiceImpl implements ServidorTemporarioService {

    @Autowired
    ServidorTemporarioRepository repository;

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

            ServidorTemporarioEntity servidor = repository.findByPessoa(pessoa).orElse(null);
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
    public RetornoContext<Object> admissao(AdmissaoDemissaoDto dto) {
        try{
            PessoaEntity pessoa = pessoaService.verificaPessoaExiste(dto.getPessoaEntity());
            if(Objects.isNull(pessoa)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            if(Objects.isNull(dto.getDataAdmissaoDemissao())){
                dto.setDataAdmissaoDemissao(LocalDate.now());
            }

            ServidorTemporarioEntity servTemp = ServidorTemporarioEntity.builder().pessoa(pessoa).dataAdmissao(dto.getDataAdmissaoDemissao()).build();
            var retorno = repository.save(servTemp);

            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> demissao(AdmissaoDemissaoDto dto) {
        try{
            PessoaEntity pessoa = pessoaService.verificaPessoaExiste(dto.getPessoaEntity());
            if(Objects.isNull(pessoa)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            ServidorTemporarioEntity servTemp =  repository.findByPessoa(pessoa).orElse(null);
            if(Objects.isNull(servTemp)) return RetornoContext.builder().mensagem("Funcionario não Admitido, não é possivel demitir").status(HttpStatus.NOT_ACCEPTABLE).build();

            if(Objects.isNull(dto.getDataAdmissaoDemissao())){
                dto.setDataAdmissaoDemissao(LocalDate.now());
            }
            if(servTemp.getDataAdmissao().isAfter(dto.getDataAdmissaoDemissao())) return RetornoContext.builder().mensagem("Data de demissão anterior a de admissão").status(HttpStatus.NOT_ACCEPTABLE).build();


            servTemp.setDataDemissao(dto.getDataAdmissaoDemissao());

            var retorno = repository.save(servTemp);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> update(ServidorTemporarioEntity entity) {
        try{
            ServidorTemporarioEntity servidor = repository.findByPessoa(entity.getPessoa()).orElse(null);
            if(Objects.isNull(servidor)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA + " o servidor temporario";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NO_CONTENT).build();
            }
            repository.delete(servidor);
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


            ServidorTemporarioEntity servidor = repository.findByPessoa(pessoa).orElse(null);
            if(Objects.isNull(servidor)) return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();
            repository.delete(servidor);

            return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
