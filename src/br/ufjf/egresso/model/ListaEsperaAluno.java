package br.ufjf.egresso.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "listaesperaaluno")
public class ListaEsperaAluno {

	@Id
	@Column(name = "idlistaEsperaAluno", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int idlistaEsperaAluno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idaluno", nullable = false)
	private Aluno aluno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idlistaEspera", nullable = false)
	private ListaEspera listaespera;

	@Column(name = "aprovado", nullable = true)
	private Boolean aprovado;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public ListaEspera getListaespera() {
		return listaespera;
	}

	public void setListaespera(ListaEspera listaespera) {
		this.listaespera = listaespera;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

}