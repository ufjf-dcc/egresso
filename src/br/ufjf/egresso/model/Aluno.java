package br.ufjf.egresso.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aluno")
public class Aluno {
	public static final int ATIVO = 1, INATIVO_ALUNO = 0, INATIVO_ADMIN = -1;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int id;

	@Column(name = "matricula", unique = true, length = 15, nullable = false)
	String matricula;

	@Column(name = "nome", length = 65, nullable = false)
	String nome;

	@Column(name = "facebook_id", unique = true, nullable = true, length = 20)
	String facebookId;

	@Column(name = "url_foto", nullable = true, length = 255)
	String urlFoto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "atuacao_id", nullable = true)
	Atuacao atuacao;

	



	public Atuacao getAtuacao() {
		return atuacao;
	}

	public void setAtuacao(Atuacao atuacao) {
		this.atuacao = atuacao;
	}

	@Column(name = "ativo", nullable = true)
	private int ativo;

	@Column(name = "linkedin_access_token", nullable = true, length = 255)
	String lkAccessToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "turma_id", nullable = false)
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "curso_id", nullable = false)
	private Curso curso;
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Transient
	private boolean editingStatus;	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}


	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public String getLkAccessToken() {
		return lkAccessToken;
	}

	public void setLkAccessToken(String lkAccessToken) {
		this.lkAccessToken = lkAccessToken;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String getLinkFacebook() {
		return ("http://facebook.com/" + facebookId);
	}

	public boolean getEditingStatus() {
		return editingStatus;
	}

	public void setEditingStatus(boolean editingStatus) {
		this.editingStatus = editingStatus;
	}

	public void copiar(Aluno outro) {
		this.id = outro.id;
		this.matricula = outro.matricula;
		this.nome = outro.nome;
		this.facebookId = outro.facebookId;
		this.turma = outro.turma;
		this.urlFoto = outro.urlFoto;
		this.atuacao = outro.atuacao;
	}

}