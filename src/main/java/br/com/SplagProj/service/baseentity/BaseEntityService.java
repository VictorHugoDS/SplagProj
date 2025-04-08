package br.com.SplagProj.service.baseentity;


import br.com.SplagProj.common.RetornoContext;

public interface BaseEntityService<T> {

    RetornoContext<Object> get(Integer id);
    RetornoContext<Object> save(T obj);
    RetornoContext<Object> update(Integer id,T obj);
    void delete(Integer id);

}
