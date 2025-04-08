package br.com.SplagProj.entity.FotoPessoa.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class PessoaDto {

    @NotNull
    private Integer id;
    @Nullable
    private String nome;
    @Nullable
    private LocalDate data;
    @Nullable
    private String sexo;
    @Nullable
    private String mae;
    @Nullable
    private String pai;
}
