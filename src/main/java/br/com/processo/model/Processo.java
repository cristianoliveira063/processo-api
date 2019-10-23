package br.com.processo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_processo")
public class Processo {

    @Id
    @SequenceGenerator(name = "process_seq", sequenceName = "process_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "numero_processo",unique = true,nullable = false,length = 10)
    private String numeroProcesso;

    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;

    @ManyToOne
    @JoinColumn(name = "tipo_processo")
    private TipoProcesso tipoProcesso;


}
