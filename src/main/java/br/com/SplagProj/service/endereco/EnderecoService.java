package br.com.SplagProj.service.endereco;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.endereco.EnderecoEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.service.baseentity.BaseEntityService;
import jakarta.validation.Valid;

import java.util.List;


public interface EnderecoService extends BaseEntityService<EnderecoEntity> {
    RetornoContext<Object> associarPessoas(String id, @Valid List<PessoaEntity> pessoas);
    RetornoContext<Object> associarUnidades(String id, @Valid List<UnidadeEntity> pessoas);
}
