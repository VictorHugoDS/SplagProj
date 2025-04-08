package br.com.SplagProj.repository.FotoPessoa;

import br.com.SplagProj.entity.FotoPessoa.FotoPessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoaEntity,Integer> {
}

