package com.grupo04.Biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long cdBibliotecario;

    @Column(name = "nm_bibliotecario")
    private String nmBibliotecario;

    @Column(name = "cd_senha")
    @NotNull
    private String cdSenha;

    @Column(name = "dt_nascimento")
    private LocalDateTime    dtNascimento;

    @Column(name = "tp_sexo")
    private char tpSexo;

    @NotNull
    @Column(name = "ds_email", unique = true)
    private String dsEmail;
}
