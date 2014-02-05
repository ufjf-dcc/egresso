package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.persistent.impl.AtuacaoDAO;

public class AtuacaoBusiness {

	private List<String> errors;
	private AtuacaoDAO atuacaoDao;

	public AtuacaoBusiness() {
		this.errors = new ArrayList<String>();
		atuacaoDao = new AtuacaoDAO();
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean validar(Atuacao atuacao) {
		errors.clear();

		validaCargo(atuacao.getCargo());
		validaLocal(atuacao.getLocal());
		return errors.size() == 0;
	}

	private void validaCargo(String cargo) {
		if (cargo == null || cargo.trim().length() == 0)
			errors.add("É necessário informar o cargo;\n");
	}
	private void validaLocal(String local) {
		if (local == null || local.trim().length() == 0)
			errors.add("É necessário informar o local;\n");
	}
	

	public List<Atuacao> getPorAluno(Aluno aluno) {
		return atuacaoDao.getPorAluno(aluno);
	}

	public boolean editar(Atuacao atuacao) {
		return atuacaoDao.editar(atuacao);
	}

	public boolean exclui(Atuacao atuacao) {
		return atuacaoDao.exclui(atuacao);
	}

	public boolean salvar(Atuacao novaAtuacao) {
		return atuacaoDao.salvar(novaAtuacao);
	}

	public List<Atuacao> getTodas() {
		return new AtuacaoDAO().getTodas();
	}

}
