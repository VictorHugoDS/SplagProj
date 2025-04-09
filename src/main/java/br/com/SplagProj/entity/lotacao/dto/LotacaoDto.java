package br.com.SplagProj.entity.lotacao.dto;

import br.com.SplagProj.entity.fotopessoa.FotoPessoaEntity;
import br.com.SplagProj.entity.lotacao.LotacaoEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;


@AllArgsConstructor
@Getter
@Setter
public class LotacaoDto {

    private PessoaEntity pessoa;
    private UnidadeEntity unidade;
    private LocalDate dataLocacao;
    private LocalDate portaria;

    public LotacaoEntity toEntity(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, LotacaoEntity.class);
    }
}
