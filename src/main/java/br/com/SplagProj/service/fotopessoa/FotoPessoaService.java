package br.com.SplagProj.service.fotopessoa;

import br.com.SplagProj.common.RetornoContext;
import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.service.baseentity.BaseEntityService;
import org.springframework.web.multipart.MultipartFile;

public interface FotoPessoaService extends BaseEntityService<FotoPessoaEntity> {
    RetornoContext<Object> getFotoUrl(String fileName);
    RetornoContext<Object> uploadFoto(MultipartFile file);
}

