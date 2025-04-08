package br.com.SplagProj.entity.servidortemporario;

import br.com.SplagProj.entity.base.BaseEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "servidor_temporario")
@ToString
public class ServidorTemporarioEntity extends BaseEntity  {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pes_id")
    private PessoaEntity pessoa;

    @Column(name = "st_data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate dataDemissao;

}
