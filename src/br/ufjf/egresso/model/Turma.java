package br.ufjf.egresso.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "turma")
public class Turma {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int id;

	@Column(name = "semestre", unique = true, length = 6, nullable = false)
	String semestre;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
	private List<Aluno> alunos = new ArrayList<Aluno>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
	private List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
	@Transient
	private boolean editingStatus;

	public String getSemestre() {
		return semestre;
	}

	public boolean getEditingStatus() {
		return editingStatus;
	}
	public void setEditingStatus(boolean editingStatus) {
		this.editingStatus = editingStatus;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
	public void copy(Turma outra) {
		this.id = outra.id;
		this.semestre = outra.semestre;
		this.alunos = outra.alunos;
		this.solicitacoes = outra.solicitacoes;
		
	}
}
