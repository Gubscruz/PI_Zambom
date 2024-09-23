package br.insper.PI_Zambom.curso.dto;

import br.insper.PI_Zambom.curso.Curso;
import java.util.List;

public class RetornarCursoDTO {

	private String id;
	private String nome;
	private String descricao;
	private int maxAlunos;
	private String professorCpf;
	private List<String> alunosCpf;

	public RetornarCursoDTO fromCurso(Curso curso) {
		RetornarCursoDTO dto = new RetornarCursoDTO();
		dto.setId(curso.getId());
		dto.setNome(curso.getNome());
		dto.setDescricao(curso.getDescricao());
		dto.setMaxAlunos(curso.getMaxAlunos());
		dto.setProfessorCpf(curso.getCPFprofessor());
		return dto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getMaxAlunos() {
		return maxAlunos;
	}

	public void setMaxAlunos(int maxAlunos) {
		this.maxAlunos = maxAlunos;
	}

	public String getProfessorCpf() {
		return professorCpf;
	}

	public void setProfessorCpf(String professorCpf) {
		this.professorCpf = professorCpf;
	}

	public List<String> getAlunosCpf() {
		return alunosCpf;
	}

	public void setAlunosCpf(List<String> alunosCpf) {
		this.alunosCpf = alunosCpf;
	}
}



	// Método estático para converter de Curso para RetornarCursoDTO
