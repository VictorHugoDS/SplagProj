package br.com.SplagProj.repository.Pessoa;

import br.com.SplagProj.entity.Pessoa.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity,Integer> {

}
