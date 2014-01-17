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
@Table(name = "pedido")
public class Pedido {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int id;

	@Column(name = "matricula", nullable = false, unique = true, length = 15)
	String matricula;

	@Column(name = "nome", length = 65, unique = false, nullable = true)
	String nome;

	@Column(name = "id_facebook", nullable = false, unique = true, length = 20)
	String idFacebook;

	@Column(name = "url_foto", nullable = true, length = 255)
	String urlFoto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_turma", nullable = false)
	private Turma turma;

	@Transient
	private String linkFacebook;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
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
		return ("http://facebook.com/" + idFacebook);
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