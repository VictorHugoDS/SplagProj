package br.com.SplagProj.repository.fotopessoa;

import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoaEntity, Integer> {
    List<FotoPessoaEntity> findByPessoaOrderByDataDesc(PessoaEntity pessoa);
}

