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
@Table(name = "tipoatuacao")
public class TipoAtuacao {
	@Id
	@Column(name = "idtipoAtuacao", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int idipoAtuacao;

	@Column(name = "tipo", length = 45, nullable = false)
	String tipo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoAtuacao")
	private List<Atuacao> atuacao = new ArrayList<Atuacao>();

	public int getIdipo_atuacao() {
		return idipoAtuacao;
	}

	public void setIdipo_atuacao(int idipo_atuacao) {
		this.idipoAtuacao = idipo_atuacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Atuacao> getAtuacao() {
		return atuacao;
	}

	public void setAtuacao(List<Atuacao> atuacao) {
		this.atuacao = atuacao;
	}
	
}