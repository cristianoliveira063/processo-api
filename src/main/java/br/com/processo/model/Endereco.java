package br.com.processo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @SequenceGenerator(name = "ender_seq", sequenceName = "ender_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ender_seq")
    @EqualsAndHashCode.Include
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "id_pessoa",nullable = false)
    private  Pessoa pessoa;

    @Column(length = 50)
    private String bairro;

    @Column(length = 20)
    private String cep;

    @Column(length = 50)
    private String cidade;

    @Column(length = 30)
    private String uf;

    @Column(length = 100)
    private String complemento;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 20)
    private String numero;

}