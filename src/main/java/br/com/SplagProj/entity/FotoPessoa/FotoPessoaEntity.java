package br.com.SplagProj.entity.FotoPessoa;

import br.com.SplagProj.entity.BaseEntity.BaseEntity;
import br.com.SplagProj.entity.Pessoa.PessoaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "foto_pessoa")
@ToString
@AttributeOverride(name = "id", column = @Column(name = "fp_id",nullable = false, unique = true))
public class FotoPessoaEntity  extends BaseEntity{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pes_id")
    private PessoaEntity pessoa;

    @Column(name = "fp_data")
    private LocalDate data;

    @Column(name = "fp_bucket",length = 50)
    private String bucket;

    @Column(name = "fp_hash",length = 50)
    private String hash;
}
