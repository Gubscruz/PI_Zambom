package br.insper.PI_Zambom.curso.dto;

public class SalvarCursoDTO {
	private String nome;
	private String descricao;
	private int maxAlunos;
	private String professorCpf;


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
}
