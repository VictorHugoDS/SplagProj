package br.com.SplagProj.repository.lotacao;

import br.com.SplagProj.entity.lotacao.LotacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository extends JpaRepository<LotacaoEntity,Integer> {
}
