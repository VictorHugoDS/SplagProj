package br.com.SplagProj.service.unidade;

import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.service.baseentity.BaseEntityService;

import java.util.List;
import java.util.Set;

public interface UnidadeService extends BaseEntityService<UnidadeEntity> {
    UnidadeEntity verificaUnidadeExiste(UnidadeEntity unidade);
    void salvaListaUnidades(Set<UnidadeEntity> unidades);
}
