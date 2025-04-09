package br.com.SplagProj.repository.cidade;

import br.com.SplagProj.entity.cidade.CidadeEntity;
import br.com.SplagProj.entity.endereco.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository  extends JpaRepository<CidadeEntity,Integer> {
}
