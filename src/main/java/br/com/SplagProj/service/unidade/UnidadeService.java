package br.com.SplagProj.service.unidade;

import br.com.SplagProj.entity.unidade.UnidadeEntity;

import java.util.List;
import java.util.Set;

public interface UnidadeService {
    UnidadeEntity verificaUnidadeExiste(UnidadeEntity unidade);
    void salvaListaUnidades(Set<UnidadeEntity> unidades);
}
