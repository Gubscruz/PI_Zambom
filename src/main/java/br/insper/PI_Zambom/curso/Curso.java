package br.insper.PI_Zambom.curso;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Curso {

	@Id
	private String id;
	private String nome;
	private String descricao;
	private int MaxAlunos;
	private String CPFprofessor;
	private List<String> alunosCpf;


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
		return MaxAlunos;
	}

	public void setMaxAlunos(int maxAlunos) {
		MaxAlunos = maxAlunos;
	}

	public String getCPFprofessor() {
		return CPFprofessor;
	}

	public void setCPFprofessor(String CPFprofessor) {
		this.CPFprofessor = CPFprofessor;
	}

	public List<String> getAlunosCpf() {
		return alunosCpf;
	}

	public void setAlunosCpf(List<String> alunosCpf) {
		this.alunosCpf = alunosCpf;
	}
}
