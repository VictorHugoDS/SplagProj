package br.com.SplagProj.entity.servidorefetivo;

import br.com.SplagProj.entity.base.BaseEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "servidor_efetivo")
@ToString
public class ServidorEfetivoEntity extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pes_id")
    private PessoaEntity pessoa;

    @Column(name = "se_matricula",length = 20)
    private String matricula;
}
