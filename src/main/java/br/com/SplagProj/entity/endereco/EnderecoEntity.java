package br.com.SplagProj.entity.endereco;

import br.com.SplagProj.entity.base.BaseEntity;
import br.com.SplagProj.entity.cidade.CidadeEntity;
import br.com.SplagProj.entity.pessoa.PessoaEntity;
import br.com.SplagProj.entity.unidade.UnidadeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "lotacao")
@AttributeOverride(name = "id", column = @Column(name = "end_id",nullable = false, unique = true))
public class EnderecoEntity extends BaseEntity {

    @Column(name = "end_tipo_logradouro",length = 50)
    private String tipoLogradouro;

    @Column(name = "end_logradouro",length = 200)
    private String logradouro;

    @Column(name = "end_numero")
    private Integer numero;

    @Column(name = "end_bairro",length = 100)
    private String bairro;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cid_id")
    private CidadeEntity cidade;

    @ManyToMany
    @JoinTable(
     name = "pessoa_endereco",
                 joinColumns = @JoinColumn(name = "pes_id"),
                 inverseJoinColumns = @JoinColumn(name = "end_id")
     )

    private Set<PessoaEntity> pessoas;

    @ManyToMany
    @JoinTable(
            name = "unidade_endereco",
            joinColumns = @JoinColumn(name = "unid_id"),
            inverseJoinColumns = @JoinColumn(name = "end_id")
    )

    private Set<UnidadeEntity> unidades;
}
