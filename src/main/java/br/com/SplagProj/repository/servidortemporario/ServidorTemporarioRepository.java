package br.com.SplagProj.repository.servidortemporario;

import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporarioEntity,Integer> {
    Optional<ServidorTemporarioEntity> findFirstByPessoaOrderByDataAdmissaoDesc(PessoaEntity pessoa);
}
