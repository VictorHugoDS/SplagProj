package br.com.SplagProj.entity.lotacao;

import br.com.SplagProj.entity.base.BaseEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
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
@Table(name = "lotacao")
@AttributeOverride(name = "id", column = @Column(name = "lot_id",nullable = false, unique = true))
public class LotacaoEntity extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pes_id")
    private PessoaEntity pessoa;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "unid_id")
    private UnidadeEntity unidade;

    @Column(name = "lot_data_locacao")
    private LocalDate dataLocacao;

    @Column(name = "lot_portaria",length = 100)
    private LocalDate portaria;

}
