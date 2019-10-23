package br.com.processo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_pessoa")
public class Pessoa {

    @Id
    @SequenceGenerator(name = "pess_seq", sequenceName = "pess_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pess_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private  String nome;

    @Column(nullable = false,unique = true)
    private  String cpf;

    @Column(nullable = false,unique = true)
    private  String email;

    @OneToMany(orphanRemoval = true,mappedBy = "pessoa")
    private List<Endereco> enderecos = new ArrayList<>();


}
