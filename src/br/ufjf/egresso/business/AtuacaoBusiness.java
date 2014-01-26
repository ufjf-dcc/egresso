package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;
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
	public List<Atuacao> getTodas(){
		return new AtuacaoDAO().getTodas();
	}
	public boolean validar(Atuacao atuacao) {
		errors.clear();

		validaCargo(atuacao.getCargo());
		return errors.size() == 0;
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
	
	private void validaCargo(String cargo) {
		if (cargo == null || cargo.trim().length() == 0)
			errors.add("É necessário informar o cargo;\n");
	}
	

}
