package br.ufjf.egresso.model;

import java.sql.Date;
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
@Table(name = "atuacao")
public class Atuacao {

	@Id
	@Column(name = "idatuacao", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int idatuacao;

	@Column(name = "dataInicio")
	Date dataInicio;

	@Column(name = "dataTermino")
	Date dataTermino;

	@Column(name = "local", length = 45)
	String local;

	@Column(name = "especificacao", length = 45)
	String especificacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idaluno", nullable = false)
	private Aluno aluno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoAtucao", nullable = false)
	private TipoAtuacao tipoAtuacao;

	public int getIdatuacao() {
		return idatuacao;
	}

	public void setIdatuacao(int idatuacao) {
		this.idatuacao = idatuacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date data_termino) {
		this.dataTermino = data_termino;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public TipoAtuacao getTipo_atuacao() {
		return tipoAtuacao;
	}

	public void setTipo_atuacao(TipoAtuacao tipo_atuacao) {
		this.tipoAtuacao = tipo_atuacao;
	}
	
}