package br.com.SplagProj.service.pessoa;

import br.com.SplagProj.entity.pessoa.PessoaEntity;

public interface PessoaService {
    PessoaEntity verificaPessoaExiste(PessoaEntity pessoa);
    PessoaEntity verificaPessoaExiste(Integer id);
    PessoaEntity encontrarPorId(Integer id);
}
