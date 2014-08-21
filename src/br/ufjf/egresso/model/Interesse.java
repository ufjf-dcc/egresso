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
@Table(name = "interesses")
public class Interesse {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@Column(name = "interesse", nullable = true)
	private String interesse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInteresse() {
		return interesse;
	}

	public void setInteresse(String interesse) {
		this.interesse = interesse;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void copy(Interesse outro) {
		this.id = outro.id;
		this.interesse = outro.interesse;
		this.aluno = outro.aluno;
	}
}
