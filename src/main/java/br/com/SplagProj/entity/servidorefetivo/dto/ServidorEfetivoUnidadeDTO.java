package br.com.SplagProj.entity.servidorefetivo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoUnidadeDTO {
    private String nome;
    private Integer idade;
    private String unidadeLotacao;
    private String fotoUrl;
} 