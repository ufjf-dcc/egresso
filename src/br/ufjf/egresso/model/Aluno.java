package br.ufjf.egresso.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aluno")
public class Aluno {

	@Id
	@Column(name = "idaluno", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int idaluno;

	@Column(name = "matricula", unique = true, length = 15)
	String matricula;

	@Column(name = "nome", length = 65, nullable = false)
	String nome;
	
	@Column(name = "tokenfacebook", unique = true, length = 255)
	String tokenfacebook;
	
	@Column(name = "idfacebook", nullable = false, unique = true, length = 20)
	String idfacebook;
	
	@Column(name = "coordenador", nullable = false)
	Boolean coordenador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "turma")
	private Turma turma;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno")
	private List<Atuacao> atuacao = new ArrayList<Atuacao>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "aluno")
	private List<ListaEspera> listaEspera = new ArrayList<ListaEspera>();

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

	public String getTokenfacebook() {
		return tokenfacebook;
	}

	public void setTokenfacebook(String tokenfacebook) {
		this.tokenfacebook = tokenfacebook;
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

	public List<ListaEspera> getLista_de_espera() {
		return listaEspera;
	}

	public void setListaEspera(List<ListaEspera> listaEspera) {
		this.listaEspera = listaEspera;
	}
	
	public String getIdfacebook() {
		return idfacebook;
	}

	public void setIdfacebook(String idfacebook) {
		this.idfacebook = idfacebook;
	}

	public Boolean getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Boolean coordenador) {
		this.coordenador = coordenador;
	}

	public List<ListaEspera> getListaEspera() {
		return listaEspera;
	}

}