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
@Table(name = "professor")

public class ProfessorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_seq")
    @SequenceGenerator(
            name = "professor_seq",
            sequenceName = "professor_seq",
            allocationSize = 1
    )
    @Column(name = "cd_professor")
    private Long cdProfessor;

    @Column(name = "nm_professor")
    private String nmProfessor;

    @Column(name = "dt_cadastro", insertable = false, updatable = false)
    private LocalDateTime dtCadastro;

    @Column(name = "sn_ativo")
    private char snAtivo;

}
