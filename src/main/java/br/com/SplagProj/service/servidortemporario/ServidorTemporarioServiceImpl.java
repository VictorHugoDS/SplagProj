package br.com.SplagProj.service.servidortemporario;


import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.entity.servidortemporario.dto.AdmissaoDemissaoDto;
import br.com.SplagProj.repository.servidortemporario.ServidorTemporarioRepository;
import br.com.SplagProj.service.pessoa.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

            ServidorTemporarioEntity servidor = repository.findFirstByPessoaOrderByDataAdmissaoDesc(pessoa).orElse(null);
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

            ServidorTemporarioEntity registro = repository.findFirstByPessoaOrderByDataAdmissaoDesc(pessoa).orElse(null);
            if(Objects.nonNull(registro) && Objects.nonNull(registro.getDataAdmissao())) return RetornoContext.builder().mensagem("Pessoa já está admitida").status(HttpStatus.NOT_ACCEPTABLE).build();

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

            ServidorTemporarioEntity servTemp = repository.findFirstByPessoaOrderByDataAdmissaoDesc(pessoa).orElse(null);
            if(Objects.nonNull(servTemp) && Objects.nonNull(servTemp.getDataDemissao())) return RetornoContext.builder().mensagem("Pessoa já está demitida").status(HttpStatus.NOT_ACCEPTABLE).build();

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
    public RetornoContext<Object> update(Integer idPessoa,ServidorTemporarioEntity entity) {
        try{

            PessoaEntity pessoa = pessoaService.encontrarPorId(idPessoa);
            if(Objects.isNull(pessoa)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " a pessoa";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            ServidorTemporarioEntity servidor = repository.findFirstByPessoaOrderByDataAdmissaoDesc(pessoa).orElse(null);
            if(Objects.isNull(servidor)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA + " o servidor temporario";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NO_CONTENT).build();
            }
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


            ServidorTemporarioEntity servidor = repository.findFirstByPessoaOrderByDataAdmissaoDesc(pessoa).orElse(null);
            if(Objects.isNull(servidor)) return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();
            repository.delete(servidor);

            return RetornoContext.builder().status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> getAllPaginado(int page, int size) {
        try {
            return RetornoContext.builder().body(repository.findAll(PageRequest.of(page, size))).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
