package br.com.SplagProj.service.servidorefetivo;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.servidorefetivo.ServidorEfetivoEntity;
import org.springframework.data.domain.Page;

public interface ServidorEfetivoService {
    RetornoContext<Object> get(Integer idPessoa);
    RetornoContext<Object> getAllPaginado(int page, int size);
    RetornoContext<Object> save(ServidorEfetivoEntity entity);
    RetornoContext<Object> update(Integer idPessoa,ServidorEfetivoEntity entity);
    RetornoContext<Object> delete(Integer idPessoa);
}
