package br.com.SplagProj.service.pessoa;

import br.com.SplagProj.entity.pessoa.PessoaEntity;

import java.util.List;

public interface PessoaService {
    PessoaEntity verificaPessoaExiste(PessoaEntity pessoa);
    PessoaEntity verificaPessoaExiste(Integer id);
    PessoaEntity encontrarPorId(Integer id);
    void salvaListaPessoas(List<PessoaEntity> pessoaEntityList);
}
