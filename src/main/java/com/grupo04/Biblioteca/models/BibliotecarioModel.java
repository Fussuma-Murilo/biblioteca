package com.grupo04.Biblioteca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bibliotecario")
public class BibliotecarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bibliotecario_seq")
    @SequenceGenerator(
            name = "bibliotecario_seq",
            sequenceName = "bibliotecario_seq",
            allocationSize = 1
    )
    @Column(name = "cd_bibliotecario")
    private String cdBibliotecario;

    @Column(name = "nm_bibliotecario")
    private String nmBibliotecario;

    @Column(name = "cd_senha")
    private String cdSenha;

    @Column(name = "dt_nascimento")
    private LocalDateTime dtNascimento;

    @Column(name = "tp_sexo")
    private char tpSexo;

    @Column(name = "ds_email")
    private String dsEmail;
}
