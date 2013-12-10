package br.ufjf.egresso.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aluno")
public class Aluno {

	@Id
	@Column(name = "idaluno", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int idaluno;

	@Column(name = "matricula", unique = true, length = 15, nullable = true)
	String matricula;

	@Column(name = "nome", length = 65, nullable = false)
	String nome;

	@Column(name = "tokenFacebook", nullable = true, length = 255)
	String tokenFacebook;

	@Column(name = "idfacebook", unique = true, nullable = true, length = 20)
	String idfacebook;

	@Column(name = "tipoPermissao", nullable = false)
	int tipoPermissao;
	
	@Column(name = "urlFoto", nullable = true, length = 255)
	String urlFoto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idturma")
	private Turma turma;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno")
	private List<Atuacao> atuacao = new ArrayList<Atuacao>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno")
	private List<ListaEsperaAluno> ListaEsperaAluno = new ArrayList<ListaEsperaAluno>();

	@Transient
	private String linkFacebook;
	
	public int getIdaluno() {
		return idaluno;
	}

	public void setIdaluno(int idaluno) {
		this.idaluno = idaluno;
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

	public String getTokenFacebook() {
		return tokenFacebook;
	}

	public void setTokenFacebook(String tokenFacebook) {
		this.tokenFacebook = tokenFacebook;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Atuacao> getAtuacao() {
		return atuacao;
	}

	public void setAtuacao(List<Atuacao> atuacao) {
		this.atuacao = atuacao;
	}

	public String getIdfacebook() {
		return idfacebook;
	}

	public void setIdfacebook(String idfacebook) {
		this.idfacebook = idfacebook;
	}

	public int getTipoPermissao() {
		return tipoPermissao;
	}

	public void setTipoPermissao(int tipoPermissao) {
		this.tipoPermissao = tipoPermissao;
	}

	public List<ListaEsperaAluno> getListaEsperaAluno() {
		return ListaEsperaAluno;
	}

	public void setListaEsperaAluno(List<ListaEsperaAluno> listaEsperaAluno) {
		ListaEsperaAluno = listaEsperaAluno;
	}

	public String getLinkFacebook() {
		
		return ("http://facebook.com/"+idfacebook);
	}

	public void setLinkFacebook(String linkFacebook) {
		this.linkFacebook = linkFacebook;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	

}