package br.com.SplagProj.repository.servidorefetivo;

import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.servidorefetivo.ServidorEfetivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivoEntity, Integer> {
    Optional<ServidorEfetivoEntity> findByPessoa(PessoaEntity pessoa);
    List<ServidorEfetivoEntity> findByPessoaNomeContainingIgnoreCase(String nomeParcial);
}
