package br.insper.PI_Zambom.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.insper.PI_Zambom.curso.dto.RetornarCursoDTO;
import java.util.List;

@RestController
public class CursoController {

	@Autowired
	private CursoService cursoService;

	@GetMapping("/cursos")
	public ResponseEntity<List<RetornarCursoDTO>> listarCursos(@RequestParam(required = false) String nome) {
		List<RetornarCursoDTO> cursos = cursoService.listar(nome);
		return ResponseEntity.ok(cursos);
	}

	@PostMapping("/cursos/{idCurso}/alunos")
	public ResponseEntity<RetornarCursoDTO> adicionarAluno(@PathVariable String idCurso, @RequestBody String cpfAluno) {
		RetornarCursoDTO cursoAtualizado = cursoService.addAluno(idCurso, cpfAluno);
		return ResponseEntity.ok(cursoAtualizado);
	}

	@PostMapping("/cursos")
	public ResponseEntity<RetornarCursoDTO> criarCurso(@RequestBody Curso curso) {
		RetornarCursoDTO novoCurso = cursoService.salvar(curso);
		return ResponseEntity.ok(novoCurso);
	}
}