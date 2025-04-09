package br.com.SplagProj.service.pessoa;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.repository.pessoa.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Service
@Slf4j
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    PessoaRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RetornoContext<Object> get(Integer id) {
        try {
            PessoaEntity pessoa = repository.findById(id).orElse(null);
            if (Objects.isNull(pessoa)) {
                String mensagem = ENTIDADE_NAO_ENCONTRADA + " a pessoa";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            return RetornoContext.builder().body(pessoa).status(HttpStatus.OK).build();
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
    public RetornoContext<Object> save(PessoaEntity pessoa) {
        try {
            var retorno = repository.save(pessoa);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> update(Integer id, PessoaEntity pessoa) {
        pessoa.setId(id);
        return save(pessoa);
    }

    @Override
    public void delete(Integer id) {
        // Não implementado
    }

    @Override
    public PessoaEntity verificaPessoaExiste(PessoaEntity pessoa) {
        return repository.findById(pessoa.getId()).orElse(null);
    }

    @Override
    public PessoaEntity verificaPessoaExiste(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PessoaEntity encontrarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void salvaListaPessoas(Set<PessoaEntity> pessoaEntityList) {
        repository.saveAll(pessoaEntityList);
    }
}
