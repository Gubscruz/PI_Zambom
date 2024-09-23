package br.insper.PI_Zambom;

import br.insper.PI_Zambom.curso.Curso;
import br.insper.PI_Zambom.curso.CursoRepository;
import br.insper.PI_Zambom.curso.CursoService;

import br.insper.PI_Zambom.curso.dto.RetornarCursoDTO;
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

		RetornarCursoDTO result = cursoService.salvar(curso);

		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result.getId());
		Assertions.assertEquals("12345678901", result.getCPFprofessor());
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
		Curso curso = new Curso();
		curso.setId("curso-uuid");
		curso.setNome("Matemática");
		cursos.add(curso);

		Mockito.when(cursoRepository.findAll()).thenReturn(cursos);

		List<RetornarCursoDTO> result = cursoService.listar(null);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("curso-uuid", result.get(0).getId());
		Assertions.assertEquals("Matemática", result.get(0).getNome());
		Mockito.verify(cursoRepository).findAll();
	}

	@Test
	public void testListarWhenNomeIsNotNull() {
		// Preparação
		String nome = "Matemática";
		List<Curso> cursos = new ArrayList<>();
		Curso curso = new Curso();
		curso.setId("curso-uuid");
		curso.setNome("Matemática Avançada");
		cursos.add(curso);

		Mockito.when(cursoRepository.findByNomeContaining(nome)).thenReturn(cursos);

		// Chamada do código testado
		List<RetornarCursoDTO> result = cursoService.listar(nome);

		// Verificação dos resultados
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("curso-uuid", result.get(0).getId());
		Assertions.assertEquals("Matemática Avançada", result.get(0).getNome());
		Mockito.verify(cursoRepository).findByNomeContaining(nome);
	}

	// ***** TEST addAluno(String idCurso, String cpfAluno) *****

	@Test
	public void testAddAlunoWhenCursoNotFound() {
		// Preparação
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";
		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.empty());

		// Chamada do código testado e verificação
		Assertions.assertThrows(CursoNaoEncontradoException.class, () -> {
			cursoService.addAluno(idCurso, cpfAluno);
		});
	}

	@Test
	public void testAddAlunoWhenAlunoDoesNotExist() {
		// Preparação
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";

		Curso curso = new Curso();
		curso.setAlunosCpf(new ArrayList<>());
		curso.setMaxAlunos(30);

		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

		// Mock para quando o aluno não existe
		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Mockito.when(usuarioService.getUsuario(cpfAluno)).thenReturn(responseEntity);

		// Chamada do código testado e verificação
		Assertions.assertThrows(AlunoNaoEncontradoException.class, () -> {
			cursoService.addAluno(idCurso, cpfAluno);
		});
	}

	@Test
	public void testAddAlunoWhenLimiteDeAlunosAtingido() {
		// Preparação
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";

		Curso curso = new Curso();
		List<String> alunosCpf = new ArrayList<>();
		alunosCpf.add("11111111111");
		alunosCpf.add("22222222222");
		curso.setAlunosCpf(alunosCpf);
		curso.setMaxAlunos(2);

		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

		// Mock para quando o aluno existe
		RetornarUsuarioDTO alunoDTO = new RetornarUsuarioDTO();
		alunoDTO.setCpf(cpfAluno);
		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(alunoDTO, HttpStatus.OK);
		Mockito.when(usuarioService.getUsuario(cpfAluno)).thenReturn(responseEntity);

		// Chamada do código testado e verificação
		Assertions.assertThrows(LimiteDeAlunosAtingidoException.class, () -> {
			cursoService.addAluno(idCurso, cpfAluno);
		});
	}

	@Test
	public void testAddAlunoSuccess() {
		// Preparação
		String idCurso = "curso-uuid";
		String cpfAluno = "98765432100";

		Curso curso = new Curso();
		curso.setId(idCurso);
		curso.setAlunosCpf(new ArrayList<>());
		curso.setMaxAlunos(30);
		curso.setNome("Matemática");

		Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

		// Mock para quando o aluno existe
		RetornarUsuarioDTO alunoDTO = new RetornarUsuarioDTO();
		alunoDTO.setCpf(cpfAluno);
		ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(alunoDTO, HttpStatus.OK);
		Mockito.when(usuarioService.getUsuario(cpfAluno)).thenReturn(responseEntity);

		// Mock do cursoRepository.save
		Mockito.when(cursoRepository.save(Mockito.any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Chamada do código testado
		RetornarCursoDTO result = cursoService.addAluno(idCurso, cpfAluno);

		// Verificação dos resultados
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getAlunosCpf().size());
		Assertions.assertTrue(result.getAlunosCpf().contains(cpfAluno));
		Mockito.verify(cursoRepository).save(Mockito.any(Curso.class));
	}

}