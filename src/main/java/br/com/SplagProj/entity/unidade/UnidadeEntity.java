package br.com.SplagProj.entity.unidade;

import br.com.SplagProj.entity.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "unidade")
@AttributeOverride(name = "id", column = @Column(name = "unid_id",nullable = false, unique = true))
public class UnidadeEntity extends BaseEntity {

    @Column(name = "unid_nome",length = 200)
    private String nome;

    @Column(name = "unid_sigla",length = 20)
    private String sigla;
}
