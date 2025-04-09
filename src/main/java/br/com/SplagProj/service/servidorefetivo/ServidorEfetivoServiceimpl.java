package br.com.SplagProj.service.servidorefetivo;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.servidorefetivo.ServidorEfetivoEntity;
import br.com.SplagProj.entity.servidorefetivo.dto.ServidorEfetivoUnidadeDTO;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.repository.servidorefetivo.ServidorEfetivoRepository;
import br.com.SplagProj.repository.servidortemporario.ServidorTemporarioRepository;
import br.com.SplagProj.service.pessoa.PessoaService;
import br.com.SplagProj.repository.lotacao.LotacaoRepository;
import br.com.SplagProj.entity.lotacao.LotacaoEntity;
import br.com.SplagProj.repository.fotopessoa.FotoPessoaRepository;
import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.repository.unidade.UnidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.SplagProj.common.Mensagens.ENTIDADE_NAO_ENCONTRADA;
import static br.com.SplagProj.common.Mensagens.ERRO_GENERICO_REQUISICAO;

@Slf4j
@Service
public class ServidorEfetivoServiceimpl implements ServidorEfetivoService {

    @Autowired
    ServidorEfetivoRepository repository;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    LotacaoRepository lotacaoRepository;

    @Autowired
    FotoPessoaRepository fotoPessoaRepository;

    @Autowired
    UnidadeRepository unidadeRepository;

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
    public RetornoContext<Object> getAllPaginado(int page, int size) {
        try {
            return RetornoContext.builder().body(repository.findAll(PageRequest.of(page, size))).status(HttpStatus.OK).build();
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

    @Override
    public RetornoContext<Object> buscarEnderecoFuncionalPorNome(String nomeParcial) {
        try {
            List<ServidorEfetivoEntity> servidores = repository.findByPessoaNomeContainingIgnoreCase(nomeParcial);
            
            if (servidores.isEmpty()) {
                return RetornoContext.builder()
                    .mensagem(ENTIDADE_NAO_ENCONTRADA + " servidor efeitvo com o nome informado")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            }

            List<LotacaoEntity> lotacoes = lotacaoRepository.findByPessoaInOrderByDataLocacaoDesc(
                servidores.stream().map(ServidorEfetivoEntity::getPessoa).toList()
            );

            if (lotacoes.isEmpty()) {
                return RetornoContext.builder()
                    .mensagem(ENTIDADE_NAO_ENCONTRADA + " lotacao do servidor efeitvo com o nome informado")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            }

            return RetornoContext.builder()
                .body(lotacoes)
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
    public RetornoContext<Object> buscarServidoresPorUnidade(Integer unidadeId) {
        try {
            UnidadeEntity unidade = unidadeRepository.findById(unidadeId).orElse(null);
            if (unidade == null) {
                return RetornoContext.builder()
                    .mensagem(ENTIDADE_NAO_ENCONTRADA + " a unidade do servidor")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            }

            List<LotacaoEntity> lotacoes = lotacaoRepository.findByUnidadeOrderByDataLocacaoDesc(unidade);
            if (lotacoes.isEmpty()) {
                return RetornoContext.builder()
                    .mensagem(ENTIDADE_NAO_ENCONTRADA + " a lotacao da unidade do servidor")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            }

            List<ServidorEfetivoUnidadeDTO> servidores = lotacoes.stream()
                .map(lotacao -> {
                    ServidorEfetivoEntity servidor = repository.findByPessoa(lotacao.getPessoa()).orElse(null);
                    if (servidor == null) return null;

                    FotoPessoaEntity foto = fotoPessoaRepository.findByPessoaOrderByDataDesc(lotacao.getPessoa())
                        .stream()
                        .findFirst()
                        .orElse(null);

                    int idade = Period.between(lotacao.getPessoa().getData(), LocalDate.now()).getYears();

                    return ServidorEfetivoUnidadeDTO.builder()
                        .nome(lotacao.getPessoa().getNome())
                        .idade(idade)
                        .unidadeLotacao(unidade.getNome())
                        .fotoUrl(foto != null ? foto.getBucket() + "/" + foto.getHash() : null)
                        .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            return RetornoContext.builder()
                .body(servidores)
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
