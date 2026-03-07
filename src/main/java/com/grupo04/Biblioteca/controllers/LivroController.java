package com.grupo04.Biblioteca.controllers;

import com.grupo04.Biblioteca.dto.LivroDTO;
import com.grupo04.Biblioteca.models.LivroModel;
import com.grupo04.Biblioteca.repository.LivroRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.LazyInitializationBeanFactoryPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/livro")
@Tag(name = "Livro")
public class LivroController {
    @Autowired
    public LivroRepository repository;

    @GetMapping
    public ResponseEntity<List<LivroDTO>> getAll(){
        List<LivroModel> list = repository.findAll();
        List<LivroDTO> dtoList = new ArrayList<>();

        list.forEach(livroModel -> {
            dtoList.add(new LivroDTO(
                    livroModel.getCd_livro(),
                    livroModel.getNm_livro(),
                    livroModel.getCategoria(),
                    livroModel.getCd_autor(),
                    livroModel.getCd_bibliotecario(),
                    livroModel.getCd_aluno(),
                    livroModel.getCd_professor(),
                    livroModel.getSn_locado()
            ));
        });

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            LivroModel livroModel = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Livro inexistente.");
            });
            return ResponseEntity.ok(new LivroDTO(
                    livroModel.getCd_livro(),
                    livroModel.getNm_livro(),
                    livroModel.getCategoria(),
                    livroModel.getCd_autor(),
                    livroModel.getCd_bibliotecario(),
                    livroModel.getCd_aluno(),
                    livroModel.getCd_professor(),
                    livroModel.getSn_locado()
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody LivroModel newBook){
        try{
            repository.save(newBook);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody LivroModel livroAtualizado){
        try{
            LivroModel livroAtual = repository.findById(livroAtualizado.getCd_livro()).orElseThrow(() -> {
                throw new InvalidParameterException("Livro não existente.");
            });

            livroAtual.setCd_livro(livroAtualizado.getCd_livro());
            livroAtual.setNm_livro(livroAtualizado.getNm_livro());
            livroAtual.setCd_bibliotecario(livroAtualizado.getCd_bibliotecario());
            livroAtual.setCategoria(livroAtualizado.getCategoria());
            livroAtual.setCd_autor(livroAtualizado.getCd_autor());
            livroAtual.setCd_aluno(livroAtualizado.getCd_aluno());
            livroAtual.setCd_professor(livroAtualizado.getCd_professor());
            livroAtual.setSn_locado(livroAtualizado.getSn_locado());

            repository.save(livroAtual);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/{atributo}/{novoValor}")
    public ResponseEntity patch(@PathVariable Long id, @PathVariable String atributo, @PathVariable String novoValor){
        LivroModel livro;
        try{
            livro = repository.findById(id).orElseThrow(() -> {
               throw new InvalidParameterException("Livro inexistente.");
            });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        try {
            switch (atributo) {
                case "nome":
                    livro.setNm_livro(novoValor);
                    break;
                case "autor":
                    livro.setCd_autor(Long.parseLong(novoValor));
                    break;
                case "categoria":
                    livro.setCategoria(Long.parseLong(novoValor));
                    break;
                default:
                    throw new InvalidParameterException("Atributo inexistente.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        repository.save(livro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        try{
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("locar/{id}/{idUsuario}/{tipoUsuario}/{bibliotecario}")
    public ResponseEntity<?> rentBook (
            @PathVariable Long id,
            @PathVariable Long idUsuario,
            @PathVariable String tipoUsuario,
            @PathVariable Long bibliotecario
    )   {
        LivroModel livro;
        try{
            livro = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Livro inexistente.");
            });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        if(livro.getSn_locado() == 's') return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Livro já está locado");

        try {
            switch (tipoUsuario) {
                case "aluno":
                    livro.setCd_aluno(idUsuario);
                    break;
                case "professor":
                    livro.setCd_professor(idUsuario);
                    break;
                default:
                    throw new InvalidParameterException("Atributo inexistente.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        livro.setCd_bibliotecario(bibliotecario);
        livro.setSn_locado('s');
        repository.save(livro);
        return ResponseEntity.ok().build();

    }

    @PutMapping("devolver/{id}")
    public ResponseEntity<?> remand (@PathVariable Long id){
        LivroModel livro;
        try{
            livro = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Livro inexistente.");
            });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        livro.setCd_autor(null);
        livro.setCd_professor(null);
        livro.setCd_bibliotecario(null);
        livro.setSn_locado('n');

        repository.save(livro);
        return ResponseEntity.ok().build();
    }
}
