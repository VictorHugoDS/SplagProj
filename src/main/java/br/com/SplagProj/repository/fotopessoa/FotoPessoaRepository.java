package br.com.SplagProj.repository.fotopessoa;

import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoaEntity,Integer> {
}

