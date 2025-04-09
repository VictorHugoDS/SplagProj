package br.com.SplagProj.repository.lotacao;

import br.com.SplagProj.entity.lotacao.LotacaoEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotacaoRepository extends JpaRepository<LotacaoEntity, Integer> {
    List<LotacaoEntity> findByPessoaInOrderByDataLocacaoDesc(List<PessoaEntity> pessoas);
    List<LotacaoEntity> findByUnidadeOrderByDataLocacaoDesc(UnidadeEntity unidade);
}
