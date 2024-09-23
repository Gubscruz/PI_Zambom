package br.insper.PI_Zambom.curso;

import br.insper.PI_Zambom.curso.exceptions.CursoNaoEncontradoException;
import br.insper.PI_Zambom.curso.exceptions.ProfessorNaoEncontradoException;
import br.insper.PI_Zambom.curso.exceptions.LimiteDeAlunosAtingidoException;
import br.insper.PI_Zambom.curso.exceptions.AlunoNaoEncontradoException;
import br.insper.PI_Zambom.usuario.RetornarUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.insper.PI_Zambom.curso.dto.RetornarCursoDTO;

import br.insper.PI_Zambom.usuario.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public RetornarCursoDTO salvar(Curso curso) {
		curso.setId(UUID.randomUUID().toString());

		ResponseEntity<RetornarUsuarioDTO> professor = usuarioService.getUsuario(curso.getCPFprofessor());
		if (!professor.getStatusCode().is2xxSuccessful()) {
			throw new ProfessorNaoEncontradoException("Professor com CPF " + curso.getCPFprofessor() + " não encontrado");
		}

		if (curso.getAlunosCpf() == null) {
			curso.setAlunosCpf(new ArrayList<>());
		}

		Curso cursoSalvo = cursoRepository.save(curso);

		return new RetornarCursoDTO(cursoSalvo);
	}

	public List<RetornarCursoDTO> listar(String nome) {
		List<Curso> cursos;
		if (nome != null && !nome.isEmpty()) {
			cursos = cursoRepository.findByNomeContaining(nome);
		} else {
			cursos = cursoRepository.findAll();
		}

		List<RetornarCursoDTO> cursosDTO = new ArrayList<>();
		for (Curso curso : cursos) {
			cursosDTO.add(new RetornarCursoDTO(curso));
		}
		return cursosDTO;
	}

	public RetornarCursoDTO addAluno(String idCurso, String cpfAluno) {
		Optional<Curso> optionalCurso = cursoRepository.findById(idCurso);
		if (optionalCurso.isEmpty()) {
			throw new CursoNaoEncontradoException("Curso não encontrado");
		}
		Curso curso = optionalCurso.get();

		ResponseEntity<RetornarUsuarioDTO> aluno = usuarioService.getUsuario(cpfAluno);
		if (!aluno.getStatusCode().is2xxSuccessful()) {
			throw new AlunoNaoEncontradoException("Aluno com CPF " + cpfAluno + " não encontrado");
		}

		if (curso.getAlunosCpf().size() >= curso.getMaxAlunos()) {
			throw new LimiteDeAlunosAtingidoException("O curso já atingiu o número máximo de alunos.");
		}

		curso.getAlunosCpf().add(cpfAluno);
		Curso cursoAtualizado = cursoRepository.save(curso);

		return new RetornarCursoDTO(cursoAtualizado);
	}
}