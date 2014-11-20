package br.ufjf.egresso.business;

import java.util.List;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Interesse;
import br.ufjf.egresso.persistent.InteresseDAO;
/**
 * Classe para intermediar acesso à informação da classe {@link Interesse}
 * @author Eduardo Rocha Soares
 *
 */
public class InteresseBusiness {
	private InteresseDAO interesseDao;

	public InteresseBusiness() {
		
		interesseDao = new InteresseDAO();
	}
	/**
	 * Retorna todos os interesses de um determinado aluno
	 * @param aluno
	 * 		Aluno do qual queremos pesquisar os interesses
	 *  no banco de dados
	 * @return {@link List} de {@link Interesse}
	 */
	public List<Interesse> getInteresses(Aluno aluno){
		return interesseDao.getInteresses(aluno);
	}
	/**
	 *  Salva um novo interesse no banco
	 * @param novoInteresse
	 * 	Interesse a ser salvo no banco de dados
	 * @return {@link true} se for feito com sucesso;
	 *  {@link false} se falhar
	 */
	public  boolean salvar(Interesse novoInteresse) {
		return interesseDao.salvar(novoInteresse);
		
	}
	/**
	 * Exclui um determinado interesse do banco
	 * @param interesse
	 * 	Interesse a ser excluído do banco
	 * @return {@link true} se houver sucesso
	 * {@link false} se falhar
	*/
	public boolean exclui(Interesse interesse) {
		return interesseDao.exclui(interesse);
	}
	/**
	 *  Salva ou edita um {@link Interesse} no banco, depende se já 
	 * existe ou não um registro desse {@link Interesse} no banco.
	 * @param interesse
	 * 	Interesse a ser editado lou salvo
	 * @return {@link true} se houver sucesso
	 * {@link false} se falhar
	 */
	public boolean salvaOuEdita(Interesse interesse) {
		return interesseDao.salvaOuEdita(interesse);
	}

}
