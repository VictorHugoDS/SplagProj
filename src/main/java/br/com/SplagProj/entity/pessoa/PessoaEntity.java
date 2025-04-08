package br.com.SplagProj.entity.pessoa;

import br.com.SplagProj.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "pessoa")
@AttributeOverride(name = "pes_id", column = @Column(name = "fp_id",nullable = false, unique = true))
public class PessoaEntity extends BaseEntity {

    @Column(name = "pes_nome")
    private String nome;

    @Column(name = "pes_data")
    private LocalDate data;

    @Column(name = "pes_sexo",length = 9)
    private String sexo;

    @Column(name = "pes_mae",length = 200)
    private String mae;

    @Column(name = "pes_pai",length = 200)
    private String pai;
}
