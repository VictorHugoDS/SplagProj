package br.com.SplagProj.service.lotacao;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.lotacao.LotacaoEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.repository.lotacao.LotacaoRepository;
import br.com.SplagProj.service.pessoa.PessoaService;
import br.com.SplagProj.service.unidade.UnidadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Service
@Slf4j
public class LotacaoServiceImpl implements LotacaoService{

    @Autowired
    PessoaService pessoaService;

    @Autowired
    UnidadeService unidadeService;

    @Autowired
    LotacaoRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RetornoContext<Object> get(Integer id) {
        try{
            LotacaoEntity lotacao = repository.findById(id).orElse(null);
            if(Objects.isNull(lotacao)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " a Lotação";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            return RetornoContext.builder().body(lotacao).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> save(LotacaoEntity entity) {
        try{
            PessoaEntity pessoa = pessoaService.verificaPessoaExiste(entity.getPessoa());
            if(Objects.isNull(pessoa)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            entity.setPessoa(pessoa);

            UnidadeEntity unidade = unidadeService.verificaUnidadeExiste(entity.getUnidade());
            if(Objects.isNull(unidade)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            entity.setUnidade(unidade);

            var retorno = repository.save(entity);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();

        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Override
    public RetornoContext<Object> update(Integer id, LotacaoEntity entity) {
        entity.setId(id);
        return save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }


}
