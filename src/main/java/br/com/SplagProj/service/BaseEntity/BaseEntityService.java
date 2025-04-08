package br.com.SplagProj.service.BaseEntity;

import br.com.SplagProj.entity.BaseEntity.BaseEntity;


public interface BaseEntityService<T> {

    T get(Integer id);
    T save(T obj);
    T update(Integer id,T obj);
    void delete(Integer id);

}
