package br.com.SplagProj.entity.endereco.dto;

import br.com.SplagProj.entity.cidade.CidadeEntity;
import br.com.SplagProj.entity.endereco.EnderecoEntity;
import br.com.SplagProj.entity.lotacao.LotacaoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Getter
@Setter
public class EnderecoDto {

    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private CidadeEntity cidade;

    public EnderecoEntity toEntity(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EnderecoEntity.class);
    }
}
