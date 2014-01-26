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
@Table(name = "tipo_atuacao")
public class TipoAtuacao {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int id;

	@Column(name = "nome", length = 45, nullable = false)
	String nome;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoAtuacao")
	private List<Atuacao> tipo = new ArrayList<Atuacao>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Atuacao> getTipo() {
		return tipo;
	}

	public void setTipo(List<Atuacao> tipo) {
		this.tipo = tipo;
	}

}