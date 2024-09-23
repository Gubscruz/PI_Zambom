package br.insper.PI_Zambom.curso;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends MongoRepository<Curso, String> {

		List<Curso> findByNomeContaining(String nome);

}