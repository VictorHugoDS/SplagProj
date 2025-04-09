package br.com.SplagProj.service.unidade;

import br.com.SplagProj.entity.unidade.UnidadeEntity;

import java.util.List;

public interface UnidadeService {
    UnidadeEntity verificaUnidadeExiste(UnidadeEntity unidade);
    void salvaListaUnidades(List<UnidadeEntity> unidades);
}
