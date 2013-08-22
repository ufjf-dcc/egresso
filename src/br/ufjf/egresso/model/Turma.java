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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "turma")
public class Turma {
	@Id
	@Column(name = "idturma", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int idturma;

	@Column(name = "turma", unique = true, length = 6, nullable = false)
	String turma;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
	private List<Aluno> aluno = new ArrayList<Aluno>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
	private List<ListaEspera> listaEspera = new ArrayList<ListaEspera>();

	public int getIdTurma() {
		return idturma;
	}

	public void setIdTurma(int idTurma) {
		this.idturma = idTurma;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public List<Aluno> getaluno() {
		return aluno;
	}

	public void setaluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}

	public List<ListaEspera> getlista_de_espera() {
		return listaEspera;
	}

	public void setlista_de_espera(List<ListaEspera> listaEspera) {
		this.listaEspera = listaEspera;
	}

}
