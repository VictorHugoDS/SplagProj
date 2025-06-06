package br.com.SplagProj.service.fotopessoa;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.repository.fotopessoa.FotoPessoaRepository;
import br.com.SplagProj.service.MinioService;
import br.com.SplagProj.service.pessoa.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Service
@Slf4j
public class FotoPessoaServiceImpl implements FotoPessoaService{

    @Autowired
    FotoPessoaRepository repository;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    MinioService minioService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RetornoContext<Object> get(Integer id) {
        try{
            FotoPessoaEntity fotoPessoa = repository.findById(id).orElse(null);
            if(Objects.isNull(fotoPessoa)){
                String mensagem = ENTIDADE_NAO_ENCONTRADA +  " a foto pessoa";
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
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public RetornoContext<Object> save(FotoPessoaEntity pessoaFoto) {
        try{

            PessoaEntity pessoa = pessoaService.verificaPessoaExiste(pessoaFoto.getPessoa());
            if(Objects.isNull(pessoa)) return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.NOT_FOUND).build();

            pessoaFoto.setPessoa(pessoa);
            var retorno = repository.save(pessoaFoto);
            return RetornoContext.builder().body(retorno).status(HttpStatus.OK).build();

        } catch (Exception e){
            log.info(String.valueOf(e));
            return RetornoContext.builder().mensagem(ERRO_GENERICO_REQUISICAO).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Override
    public RetornoContext<Object> update(Integer id, FotoPessoaEntity pessoaFoto) {
        pessoaFoto.setId(id);
        return save(pessoaFoto);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public RetornoContext<Object> getFotoUrl(String fileName) {
        try {
            String url = minioService.getPresignedUrl(fileName);
            return RetornoContext.builder()
                    .body(url)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return RetornoContext.builder()
                    .mensagem(ERRO_GENERICO_REQUISICAO)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public RetornoContext<Object> uploadFoto(MultipartFile file) {
        try {
            String fileName = minioService.uploadFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            return RetornoContext.builder()
                    .body(response)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return RetornoContext.builder()
                    .mensagem(ERRO_GENERICO_REQUISICAO)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}
