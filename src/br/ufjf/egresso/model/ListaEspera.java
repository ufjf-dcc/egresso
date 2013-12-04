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
	@Table(name = "listaespera")
	public class ListaEspera {

		@Id
		@Column(name = "idlistaEspera", unique = true, nullable = false)
		@GeneratedValue(generator = "increment")
		@GenericGenerator(name = "increment", strategy = "increment")
		int idlistaEspera;

		@Column(name = "matricula", nullable = false, unique = true, length = 15)
		String matricula;

		@Column(name = "nome", length = 65, unique = false, nullable = true)
		String nome;

		@Column(name = "tokenfacebook", nullable = true, unique = true, length = 255)
		String tokenfacebook;
		
		@Column(name = "idfacebook", nullable = false, unique = true, length = 20)
		String idfacebook;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idturma", nullable = false)
		private Turma turma;
		
		@OneToMany(fetch = FetchType.LAZY, mappedBy = "listaespera")
		private List<ListaEsperaAluno> ListaEsperaAluno = new ArrayList<ListaEsperaAluno>();
		
		@Transient
		private String linkFacebook;
		
		public int getIdlistaEspera() {
			return idlistaEspera;
		}

		public void setIdlista_de_espera(int idlistaEspera) {
			this.idlistaEspera = idlistaEspera;
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

		public String getIdfacebook() {
			return idfacebook;
		}

		public void setIdfacebook(String idfacebook) {
			this.idfacebook = idfacebook;
		}

		public void setIdlistaEspera(int idlistaEspera) {
			this.idlistaEspera = idlistaEspera;
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
		
	}