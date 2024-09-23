package br.insper.PI_Zambom.curso.dto;

import br.insper.PI_Zambom.curso.Curso;
import java.util.List;

public class RetornarCursoDTO {

	private String id;
	private String nome;
	private String descricao;
	private int maxAlunos;
	private String CPFprofessor;
	private List<String> alunosCpf;

	public RetornarCursoDTO(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.descricao = curso.getDescricao();
		this.CPFprofessor = curso.getCPFprofessor();
		this.maxAlunos = curso.getMaxAlunos();
		this.alunosCpf = curso.getAlunosCpf();
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

	public String getCPFprofessor() {
		return CPFprofessor;
	}

	public void setCPFprofessor(String professorCpf) {
		this.CPFprofessor = professorCpf;
	}

	public List<String> getAlunosCpf() {
		return alunosCpf;
	}

	public void setAlunosCpf(List<String> alunosCpf) {
		this.alunosCpf = alunosCpf;
	}
}
