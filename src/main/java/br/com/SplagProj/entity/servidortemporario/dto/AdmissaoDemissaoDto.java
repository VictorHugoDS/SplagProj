package br.com.SplagProj.entity.servidortemporario.dto;

import br.com.SplagProj.entity.pessoa.PessoaEntity;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdmissaoDemissaoDto {
    private PessoaEntity pessoaEntity;
    @Nullable
    private LocalDate dataAdmissaoDemissao;
}
