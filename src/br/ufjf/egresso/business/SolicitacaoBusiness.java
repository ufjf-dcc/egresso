package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Solicitacao;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.SolicitacaoDAO;

/**Classe para intermediar o acesso às informações da classe {@link Solicitacao}.
 * 
 * @author Jorge Augusto da Silva Moreira
 *
 */
public class SolicitacaoBusiness {
	private SolicitacaoDAO solicitacaoDAO;
	private List<String> errors;

	public SolicitacaoBusiness() {
		this.errors = new ArrayList<String>();
		solicitacaoDAO = new SolicitacaoDAO();
	}
	
	/** 
	 * @return lista de {@link String} descrevendo os erros resultados da validação.
	 */
	public List<String> getErrors() {
		return errors;
	}
	
	/**
	 * Executa a validação de uma {@link Solicitacao}.
	 * 
	 * @param solicitacao
	 *            {@link Solicitacao} a ser validada.
	 * @return Retorna {@link true} caso erros não sejam encontrados; {@link
	 *         false} caso pelo menos 1 erro seja encontrado. Para obter a lista
	 *         de erros, ver {@link #getErrors()}.
	 */
	public boolean validar(Solicitacao solicitacao) {
		errors.clear();

		validaMatricula(solicitacao.getMatricula());
		validaTurma(solicitacao.getTurma());

		return errors.size() == 0;
	}

	private void validaTurma(Turma turma) {
		if (turma == null)
			errors.add("É necessário informar a turma da solicitação;\n");
	}

	private void validaMatricula(String matricula) {
		if (matricula == null || matricula.trim().length() == 0)
			errors.add("É necessário informar o código do solicitação;\n");
	}

	public boolean naLista(String idFacebook) throws HibernateException,
			Exception {
		Solicitacao listaEspera = solicitacaoDAO.getSolicitacao(idFacebook);

		if (listaEspera != null) {
			Session session = Sessions.getCurrent();
			session.setAttribute("listaEspera", listaEspera);
			return true;
		}
		return false;
	}

	/**Salva um novo {@link Solicitacao} no banco.
	 * 
	 * @param solicitacao {@link Solicitacao} a ser salvo.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean salvar(Solicitacao solicitacao) {
		return solicitacaoDAO.salvar(solicitacao);
	}

	/**Retorna um {@link Solicitacao}.
	 * 
	 * @param facebookId O Facebook ID da {@link Solicitacao}.
	 * @return {@link atuacao}.
	 */
	public Solicitacao getSolicitacao(String facebookId) {
		return solicitacaoDAO.getSolicitacao(facebookId);
	}
	
	/**Obtem todos os {@link Solicitacao}s do banco.
	 * 
	 * @return Uma {@link List} de {@link Solicitacao}s.
	 */
	public List<Solicitacao> getTodos(){
		return solicitacaoDAO.getTodos();
	}

	/**Exclui um {@link Solicitacao} do banco.
	 * 
	 * @param solicitacao {@link Solicitacao} a ser excluído.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean exclui(Solicitacao solicitacao) {
		return solicitacaoDAO.exclui(solicitacao);
	}
}
