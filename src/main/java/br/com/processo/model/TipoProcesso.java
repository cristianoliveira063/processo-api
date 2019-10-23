package br.com.processo.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_tipo_processo")
public class TipoProcesso {

    @Id
    @SequenceGenerator(name = "tipoproc_seq", sequenceName = "tipoproc_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipoproc_seq")
    @EqualsAndHashCode.Include
    private Long id;

    private  String descricao;
}
