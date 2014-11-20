package br.ufjf.egresso.business;

import java.util.List;

import br.ufjf.egresso.model.Curso;
import br.ufjf.egresso.persistent.CursoDAO;
/**
 * Classe para intermediar as informações da classe {@link Curso}
 * @author Eduardo Rocha Soares
 *
 */
public class CursoBusiness {
	private CursoDAO cursoDao;
	
	 
	public CursoBusiness() {
		cursoDao = new CursoDAO();
	}
	/**Retorna a lista dos códigos de todos os cursos 
	 * salvos no banco de dados
	 * 
	 * @return {@link List} de {@link Integer}
	 */
	public List<Integer> getTodosCod() {
		return cursoDao.getAllCodes();
	}
	/**
	 * Retorna todos os cursos salvos no banco de dados
	 * @return {@link List} de {@link Curso}
	 */
	public List<Curso> getTodos(){
		return cursoDao.getTodos();
	}
	/**
	 * Retorna um curso salvo no banco pelo seu código
	 * @param cod
	 * 	Código do {@link Curso} buscado
	 * @return  {@link Curso}
	 */
	public Curso getPorCod(int cod) {
		return cursoDao.getPorCod(cod);
	}
}
