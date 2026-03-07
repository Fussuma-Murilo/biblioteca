package com.grupo04.Biblioteca.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record BibliotecarioDTO(String id, String nome, LocalDateTime nascimento, char sexo) { }
