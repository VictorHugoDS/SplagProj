package br.com.SplagProj.entity.cidade;

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
@Table(name = "cidade")
@AttributeOverride(name = "id", column = @Column(name = "cid_id",nullable = false, unique = true))
public class CidadeEntity extends BaseEntity {

    @Column(name = "cid_nome",length = 200)
    private String nome;

    @Column(name = "cid_uf",length = 2)
    private String uf;
}
