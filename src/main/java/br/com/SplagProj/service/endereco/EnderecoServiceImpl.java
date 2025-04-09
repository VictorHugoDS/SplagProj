package br.com.SplagProj.service.endereco;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.cidade.CidadeEntity;
import br.com.SplagProj.entity.endereco.EnderecoEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.repository.cidade.CidadeRepository;
import br.com.SplagProj.repository.endereco.EnderecoRepository;
import br.com.SplagProj.service.pessoa.PessoaService;
import br.com.SplagProj.service.unidade.UnidadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Service
@Slf4j
public class EnderecoServiceImpl implements EnderecoService{

    @Autowired
    CidadeRepository cidadeRepository;
    
    @Autowired
    PessoaService pessoaService;
    
    @Autowired
    EnderecoRepository repository;

    @Autowired
    UnidadeService unidadeService;

    @Override
    public RetornoContext<Object> get(Integer id) {
        try{
            EnderecoEntity endereco = repository.findById(id).orElse(null);
            if(Objects.isNull(endereco)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " o endereço";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            return RetornoContext.builder().body(endereco).status(HttpStatus.OK).build();
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

    @Override
    public RetornoContext<Object> save(EnderecoEntity entity) {
        try{
            CidadeEntity cidade = verificaEnderecoExiste(entity.getCidade());
            if(Objects.isNull(cidade)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            entity.setCidade(cidade);
            var retorno = repository.save(entity);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();

        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public RetornoContext<Object> update(Integer id, EnderecoEntity endereco) {
        endereco.setId(id);
        return save(endereco);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private CidadeEntity verificaEnderecoExiste(CidadeEntity pessoa){
        Integer id = pessoa.getId();
        boolean exists = cidadeRepository.existsById(id);
        if(!exists){
            // id precisa estar nulo para salvar caso o endereco nao exista no banco
            pessoa.setId(null);
            return cidadeRepository.save(pessoa);
        }
        return cidadeRepository.findById(id).orElse(null);
    }


    @Transactional
    @Override
    public RetornoContext<Object> associarPessoas(String id, Set<PessoaEntity> pessoas) {
        try{
            pessoaService.salvaListaPessoas(pessoas);
            EnderecoEntity endereco = repository.findById(Integer.valueOf(id)).orElse(null);
            if(Objects.isNull(endereco)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " o endereço";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            endereco.setPessoas(pessoas);
            var retorno = repository.save(endereco);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @Override
    public RetornoContext<Object> associarUnidades(String id, Set<UnidadeEntity> unidades) {
        try{
            unidadeService.salvaListaUnidades(unidades);
            EnderecoEntity endereco = repository.findById(Integer.valueOf(id)).orElse(null);
            if(Objects.isNull(endereco)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " o endereço";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            endereco.setUnidades(unidades);
            var retorno = repository.save(endereco);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();
        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
