package br.com.SplagProj.service.pessoa;

import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.repository.pessoa.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PessoaServiceImpl implements PessoaService{

    @Autowired
    PessoaRepository repository;

    @Override
    public PessoaEntity verificaPessoaExiste(PessoaEntity pessoa){
        Integer id = pessoa.getId();
        boolean exists = repository.existsById(id);
        if(!exists){
            // id precisa estar nulo para salvar caso a pessoa nao exista no banco
            pessoa.setId(null);
            return repository.save(pessoa);
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public PessoaEntity verificaPessoaExiste(Integer id) {
        var pessoa = new PessoaEntity();
        pessoa.setId(id);
        return verificaPessoaExiste(pessoa);
    }

    @Override
    public PessoaEntity encontrarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void salvaListaPessoas(List<PessoaEntity> pessoaEntityList) {
        repository.saveAll(pessoaEntityList);
    }
}
