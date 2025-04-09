package br.com.SplagProj.service.unidade;

import br.com.SplagProj.entity.unidade.UnidadeEntity;
import br.com.SplagProj.repository.unidade.UnidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UnidadeServiceImpl implements UnidadeService{

    @Autowired
    UnidadeRepository repository;

    @Override
    public UnidadeEntity verificaUnidadeExiste(UnidadeEntity unidade) {
        Integer id = unidade.getId();
        boolean exists = repository.existsById(id);
        if(!exists){
            // id precisa estar nulo para salvar caso a unidade nao exista no banco
            unidade.setId(null);
            return repository.save(unidade);
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public void salvaListaUnidades(Set<UnidadeEntity> unidades) {
        repository.saveAll(unidades);
    }
}
