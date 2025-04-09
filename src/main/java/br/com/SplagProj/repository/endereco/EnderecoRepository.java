package br.com.SplagProj.repository.endereco;

import br.com.SplagProj.entity.endereco.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity,Integer> {
}
