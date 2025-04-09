package br.com.SplagProj.service.endereco;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.endereco.EnderecoEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.service.baseentity.BaseEntityService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;


public interface EnderecoService extends BaseEntityService<EnderecoEntity> {
    RetornoContext<Object> associarPessoas(String id, @Valid Set<PessoaEntity> pessoas);
    RetornoContext<Object> associarUnidades(String id, @Valid Set<UnidadeEntity> pessoas);
}
