package br.insper.PI_Zambom.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CursoController {

	@Autowired
	private CursoService cursoService;

	@GetMapping("/cursos")
	public ResponseEntity<List<Curso>> listarCursos(@RequestParam(required = false) String nome) {
		List<Curso> cursos = cursoService.listar(nome);
		return ResponseEntity.ok(cursos);
	}

	@PostMapping("/cursos/{idCurso}/alunos")
	public ResponseEntity<Curso> adicionarAluno(@PathVariable String idCurso, @RequestBody String cpfAluno) {
		Curso curso = cursoService.addAluno(idCurso, cpfAluno);
		return ResponseEntity.ok(curso);
	}

	@PostMapping("/cursos")
	public ResponseEntity<Curso> criarCurso(@RequestBody Curso curso) {
		Curso novoCurso = cursoService.salvar(curso);
		return ResponseEntity.ok(novoCurso);
	}
}