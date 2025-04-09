package br.com.SplagProj.service.servidortemporario;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.servidortemporario.ServidorTemporarioEntity;
import br.com.SplagProj.entity.servidortemporario.dto.AdmissaoDemissaoDto;


public interface ServidorTemporarioService  {

    RetornoContext<Object> get(Integer idPessoa);
    RetornoContext<Object> admissao(AdmissaoDemissaoDto dto);
    RetornoContext<Object> demissao(AdmissaoDemissaoDto dto);
    RetornoContext<Object> update(Integer idPessoa, ServidorTemporarioEntity entity);

    RetornoContext<Object> delete(Integer idPessoa);
}
