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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "atuacao")
public class Atuacao {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	int id;

	@Column(name = "data_inicio", nullable = true)
	Date dataInicio;

	@Column(name = "data_final", nullable = true)
	Date dataTermino;
	
	@Column(name = "atual", nullable = true)
	int atual;
	

	@Column(name = "local", length = 45, nullable = true)
	String local;

	@Column(name = "cargo", length = 45, nullable = true)
	String cargo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_atuacao_id", nullable = false)
	private TipoAtuacao tipoAtuacao;
	@Transient
	private boolean editingStatus;
	@Transient
	private	boolean boolAtual;
	
	
	public boolean getBoolAtual() {
		return boolAtual;
	}

	public void setBoolAtual(boolean boolAtual) {
		this.boolAtual = boolAtual;
	}

	public int getAtual() {
		return atual;
	}

	public void setAtual(int i) {
		this.atual = i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public TipoAtuacao getTipoAtuacao() {
		return tipoAtuacao;
	}

	public void setTipoAtuacao(TipoAtuacao tipoAtuacao) {
		this.tipoAtuacao = tipoAtuacao;
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
	

	public boolean getEditingStatus() {
		return editingStatus;
	}
	public void setEditingStatus(boolean editingStatus) {
		this.editingStatus = editingStatus;
	}
	public void copy(Atuacao outra) {
		this.id = outra.id;
		this.cargo = outra.cargo;
		this.tipoAtuacao = outra.tipoAtuacao;
		this.dataInicio = outra.dataInicio;
		this.dataTermino = outra.dataTermino;
		this.local = outra.local;
		this.aluno = outra.aluno;
	}
}

