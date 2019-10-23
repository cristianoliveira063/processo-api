package br.com.processo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_processo_parte")
public class ProcessoParte {

    @Id
    @SequenceGenerator(name = "procparte_seq", sequenceName = "procparte_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procparte_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_processo")
    private  Processo processo;

}
