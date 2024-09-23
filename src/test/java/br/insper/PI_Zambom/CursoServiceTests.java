package br.insper.PI_Zambom;

import br.insper.PI_Zambom.curso.Curso;
import br.insper.PI_Zambom.curso.CursoRepository;
import br.insper.PI_Zambom.curso.CursoService;
import br.insper.PI_Zambom.curso.exceptions.*;
import br.insper.PI_Zambom.usuario.RetornarUsuarioDTO;
import br.insper.PI_Zambom.usuario.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTests {

	@InjectMocks
	private CursoService cursoService;

	@Mock
	private CursoRepository cursoRepository;

	@Mock
	private UsuarioService usuarioService;


	@Test
	public void testSalvarWhenProfessorExists() {
		Curso curso = new Curso();
		curso.setCPFprofessor("12345678901");
		curso.setAlunosCpf(null);

		RetornarUsuarioDTO professorDTO = new RetornarUsuarioDTO();
		professorDTO.setCpf("12345678901");
		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(professorDTO, HttpStatus.OK);
		Mockito.when(usuarioService.getUsuario("12345678901")).thenReturn(responseEntity);

		Mockito.when(cursoRepository.save(Mockito.any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Curso result = cursoService.salvar(curso);

		Assertions.assertNotNull(result.getId());
		Assertions.assertNotNull(result.getAlunosCpf());
		Assertions.assertEquals(0, result.getAlunosCpf().size());
		Mockito.verify(cursoRepository).save(Mockito.any(Curso.class));
	}

	@Test
	public void testSalvarWhenProfessorDoesNotExist() {
		Curso curso = new Curso();
		curso.setCPFprofessor("12345678901");

		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Mockito.when(usuarioService.getUsuario("12345678901")).thenReturn(responseEntity);

		Assertions.assertThrows(ProfessorNaoEncontradoException.class, () -> {
			cursoService.salvar(curso);
		});
	}


	@Test
	public void testListarWhenNomeIsNull() {
		List<Curso> cursos = new ArrayList<>();
		cursos.add(new Curso());
		Mockito.when(cursoRepository.findAll()).thenReturn(cursos);

		List<Curso> result = cursoService.listar(null);

		Assertions.assertEquals(1, result.size());
		Mockito.verify(cursoRepository).findAll();
	}

	@Test
	public void testListarWhenNomeIsNotNull() {
		String nome = "Matemática";
		List<Curso> cursos = new ArrayList<>();
		cursos.add(new Curso());
		Mockito.when(cursoRepository.findByNomeContaining(nome)).thenReturn(cursos);

		List<Curso> result = cursoService.listar(nome);

		Assertions.assertEquals(1, result.size());
		Mockito.verify(cursoRepository).findByNomeContaining(nome);
	}


	@Test
	public void testAddAlunoWhenCursoNotFound() {
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";
		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.empty());

		Assertions.assertThrows(CursoNaoEncontradoException.class, () -> {
			cursoService.addAluno(idCurso, cpfAluno);
		});
	}

	@Test
	public void testAddAlunoWhenAlunoDoesNotExist() {
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";

		Curso curso = new Curso();
		curso.setAlunosCpf(new ArrayList<>());
		curso.setMaxAlunos(30);

		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Mockito.when(usuarioService.getUsuario(cpfAluno)).thenReturn(responseEntity);

		Assertions.assertThrows(AlunoNaoEncontradoException.class, () -> {
			cursoService.addAluno(idCurso, cpfAluno);
		});
	}

	@Test
	public void testAddAlunoWhenLimiteDeAlunosAtingido() {
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";

		Curso curso = new Curso();
		List<String> alunosCpf = new ArrayList<>();
		alunosCpf.add("11111111111");
		alunosCpf.add("22222222222");
		curso.setAlunosCpf(alunosCpf);
		curso.setMaxAlunos(2);

		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

		RetornarUsuarioDTO alunoDTO = new RetornarUsuarioDTO();
		alunoDTO.setCpf(cpfAluno);
		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(alunoDTO, HttpStatus.OK);
		Mockito.when(usuarioService.getUsuario(cpfAluno)).thenReturn(responseEntity);

		Assertions.assertThrows(LimiteDeAlunosAtingidoException.class, () -> {
			cursoService.addAluno(idCurso, cpfAluno);
		});
	}

	@Test
	public void testAddAlunoSuccess() {
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";

		Curso curso = new Curso();
		curso.setAlunosCpf(new ArrayList<>());
		curso.setMaxAlunos(30);

		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

		RetornarUsuarioDTO alunoDTO = new RetornarUsuarioDTO();
		alunoDTO.setCpf(cpfAluno);
		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(alunoDTO, HttpStatus.OK);
		Mockito.when(usuarioService.getUsuario(cpfAluno)).thenReturn(responseEntity);

		Mockito.when(cursoRepository.save(Mockito.any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Curso result = cursoService.addAluno(idCurso, cpfAluno);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getAlunosCpf().size());
		Assertions.assertTrue(result.getAlunosCpf().contains(cpfAluno));
		Mockito.verify(cursoRepository).save(Mockito.any(Curso.class));
	}

}