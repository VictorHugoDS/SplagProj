package br.com.SplagProj.service.unidade;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.repository.unidade.UnidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Service
@Slf4j
public class UnidadeServiceImpl implements UnidadeService {

    @Autowired
    UnidadeRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RetornoContext<Object> get(Integer id) {
        try {
            UnidadeEntity unidade = repository.findById(id).orElse(null);
            if (Objects.isNull(unidade)) {
                String mensagem = ENTIDADE_NAO_ENCONTRADA + " a unidade";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            return RetornoContext.builder().body(unidade).status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> getAllPaginado(int page, int size) {
        try {
            return RetornoContext.builder().body(repository.findAll(PageRequest.of(page, size))).status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public RetornoContext<Object> save(UnidadeEntity unidade) {
        try {
            var retorno = repository.save(unidade);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> update(Integer id, UnidadeEntity unidade) {
        unidade.setId(id);
        return save(unidade);
    }

    @Override
    public void delete(Integer id) {
        // Não implementado
    }

    @Override
    public UnidadeEntity verificaUnidadeExiste(UnidadeEntity unidade) {
        return repository.findById(unidade.getId()).orElse(null);
    }

    @Override
    public void salvaListaUnidades(Set<UnidadeEntity> unidades) {
        repository.saveAll(unidades);
    }
}
