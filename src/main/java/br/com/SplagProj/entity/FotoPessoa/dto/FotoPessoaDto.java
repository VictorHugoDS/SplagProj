package br.com.SplagProj.entity.FotoPessoa.dto;

import br.com.SplagProj.entity.FotoPessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.Pessoa.PessoaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class FotoPessoaDto {
    private PessoaDto pessoa;
    private LocalDate data;
    private String bucket;
    private String hash;

    public FotoPessoaEntity toEntity(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FotoPessoaEntity.class);
    }
}
