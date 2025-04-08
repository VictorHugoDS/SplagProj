package br.com.SplagProj.service.FotoPessoa;

import br.com.SplagProj.entity.FotoPessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.Pessoa.PessoaEntity;
import br.com.SplagProj.repository.FotoPessoa.FotoPessoaRepository;
import br.com.SplagProj.repository.Pessoa.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FotoPessoaServiceImpl implements FotoPessoaService{

    @Autowired
    FotoPessoaRepository repository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FotoPessoaEntity get(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public FotoPessoaEntity save(FotoPessoaEntity pessoaFoto) {
        log.info("\nSaving {}\n",pessoaFoto);
        pessoaFoto.setPessoa(verificaPessoaExiste(pessoaFoto.getPessoa()));
        return repository.save(pessoaFoto);
    }

    @Override
    public FotoPessoaEntity update(Integer id, FotoPessoaEntity pessoaFoto) {
        pessoaFoto.setId(id);
        return save(pessoaFoto);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private PessoaEntity verificaPessoaExiste(PessoaEntity pessoa){
        Integer id = pessoa.getId();
        boolean exists = pessoaRepository.existsById(id);
        if(!exists){
            // id precisa estar nulo para salvar caso a pessoa nao exista no banco
            pessoa.setId(null);
            return pessoaRepository.save(pessoa);
        }
        return pessoaRepository.findById(id).orElse(null);
    }
}
