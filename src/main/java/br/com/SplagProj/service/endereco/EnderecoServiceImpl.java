package br.com.SplagProj.service.endereco;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.cidade.CidadeEntity;
import br.com.SplagProj.entity.endereco.EnderecoEntity;
import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.repository.cidade.CidadeRepository;
import br.com.SplagProj.repository.endereco.EnderecoRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Service
@Slf4j
public class EnderecoServiceImpl implements EnderecoService{

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EnderecoRepository repository;

    @Override
    public RetornoContext<Object> get(Integer id) {
        try{
            EnderecoEntity fotoPessoa = repository.findById(id).orElse(null);
            if(Objects.isNull(fotoPessoa)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " o endereço";
                return RetornoContext.builder().mensagem(mensagem).status(HttpStatus.NOT_FOUND).build();
            }
            return RetornoContext.builder().body(fotoPessoa).status(HttpStatus.OK).build();
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

}
