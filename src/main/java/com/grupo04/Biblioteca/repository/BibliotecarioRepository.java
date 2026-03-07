package com.grupo04.Biblioteca.repository;

import com.grupo04.Biblioteca.models.BibliotecarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecarioRepository extends JpaRepository<BibliotecarioModel, String> { }
