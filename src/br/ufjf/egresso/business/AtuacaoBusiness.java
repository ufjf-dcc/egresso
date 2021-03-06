package br.ufjf.egresso.business;


import java.util.ArrayList;
import java.util.List;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;

import br.ufjf.egresso.persistent.AtuacaoDAO;

/**
 * Classe para intermediar o acesso às informações da classe {@link Atuacao}.
 * 
 * @author Eduardo Rocha Soares, Jorge Augusto da Silva Moreira
 * 
 */
public class AtuacaoBusiness {

	private List<String> errors;
	private AtuacaoDAO atuacaoDao;

	public AtuacaoBusiness() {
		this.errors = new ArrayList<String>();
		atuacaoDao = new AtuacaoDAO();
	}

	/**
	 * @return lista de {@link String} descrevendo os erros resultados da
	 *         validação.
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * Executa a validação de uma {@link Atuacao}.
	 * 
	 * @param atuacao
	 *            {@link Atuacao} a ser validada.
	 * @return Retorna {@link true} caso erros não sejam encontrados; {@link
	 *         false} caso pelo menos 1 erro seja encontrado. Para obter a lista
	 *         de erros, ver {@link #getErrors()}.
	 */
	public boolean validar(Atuacao atuacao) {
		errors.clear();

		validaCargo(atuacao.getCargo());
		validaLocal(atuacao.getLocal());
		//validaDataInicio(atuacao.getDataTermino());
		//validaDataTermino(atuacao.getDataInicio());
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
		return atuacaoDao.getAtuacoes(aluno);
	}

	public boolean excluiPorAluno(Aluno aluno) {
		return atuacaoDao.excluir(aluno);
	}

	/**
	 * Edita uma {@link Atuacao} e salva no banco.
	 * 
	 * @param atuacao
	 *            {@link Atuacao} a ser editada.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean editar(Atuacao atuacao) {
		return atuacaoDao.editar(atuacao);
	}

	/**
	 * Exclui uma {@link Atuacao} do banco.
	 * 
	 * @param atuacao
	 *            {@link Atuacao} a ser excluída.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean exclui(Atuacao atuacao) {
		return atuacaoDao.exclui(atuacao);
	}

	/**
	 * Salva uma nova {@link Atuacao} no banco.
	 * 
	 * @param atuacao
	 *            {@link Atuacao} a ser salva.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean salvar(Atuacao atuacao) {
		return atuacaoDao.salvar(atuacao);
	}

	public boolean salvaOuEdita(Atuacao novaAtuacao) {
		return atuacaoDao.salvaOuEdita(novaAtuacao);
	}

	/**
	 * Obtem todas as {@link Atuacao} do banco.
	 * 
	 * @return Uma {@link List} de {@link Atuacao}.
	 */
	public List<Atuacao> getTodas() {
		return new AtuacaoDAO().getTodas();
	}

	/**
	 * Retorna uma {@link Atuacao}.
	 * 
	 * @param id
	 *            O ID da {@link Atuacao}.
	 * @return {@link Atuacao}.
	 */
	public Atuacao getAtuacao(int id) {
		return (Atuacao) new AtuacaoDAO().procuraId(id, Atuacao.class);
	}

	
	

}
