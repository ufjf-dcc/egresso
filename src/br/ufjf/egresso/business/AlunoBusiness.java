package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Curso;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.AlunoDAO;

/**
 * Classe para intermediar o acesso às informações da classe {@link Aluno}.
 * 
 * @author Jorge Augusto da Silva Moreira, Thiago Goldoni, Thiago Rizuti
 * 
 */
public class AlunoBusiness {
	private AlunoDAO alunoDao;
	private List<String> errors;

	public AlunoBusiness() {
		this.errors = new ArrayList<String>();
		alunoDao = new AlunoDAO();
	}

	/**
	 * @return Lista de {@link String} descrevendo os erros resultados da
	 *         validação.
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * Executa a validação de um {@link Aluno}.
	 * 
	 * @param aluno
	 *            {@link Aluno} a ser validado.
	 * @param matriculaAntiga
	 *            Matricula original do {@link Aluno}.
	 * @return Retorna {@link true} caso erros não sejam encontrados; {@link
	 *         false} caso pelo menos 1 erro seja encontrado. Para obter a lista
	 *         de erros, ver {@link #getErrors()}.
	 */
	public boolean validar(Aluno aluno, String matriculaAntiga) {
		errors.clear();

		validaMatricula(aluno.getMatricula(), matriculaAntiga);
		validaNome(aluno.getNome());
		validaTurma(aluno.getTurma());

		return errors.size() == 0;
	}

	private void validaTurma(Turma turma) {
		if (turma == null)
			errors.add("É necessário informar a turma do aluno;\n");
	}

	private void validaNome(String nome) {
		if (nome == null || nome.trim().length() == 0)
			errors.add("É necessário informar o nome do aluno;\n");
	}

	private void validaMatricula(String matricula, String matriculaAntiga) {
		if (matricula == null || matricula.trim().length() == 0)
			errors.add("É necessário informar o código do aluno;\n");
		else
			jaExiste(matricula, matriculaAntiga);
	}
	private boolean jaExiste(String matricula, String matriculaAntiga) {
		errors.clear();
		if (alunoDao.jaExiste(matricula, matriculaAntiga)) {
			errors.add("Já existe um aluno com esta matrícula.\n");
			return true;
		}
		return false;
	}

	/**Verifica se um {@link Aluno} já está cadastrado.
	 * 
	 * @param facebookId O Facebook ID do {@link Aluno}.
	 * @return {@link true} se existe; {@link false} se não.
	 * @throws HibernateException
	 * @throws Exception
	 */
	public boolean alunoCadastrado(String facebookId)
			throws HibernateException, Exception {
		return alunoDao.getAluno(facebookId) != null;
	}

	/**Retorna um {@link Aluno}.
	 * 
	 * @param facebookId O Facebook ID do {@link Aluno}.
	 * @return {@link Aluno}.
	 */
	public Aluno getAluno(String facebookId) {
		return alunoDao.getAluno(facebookId);
	}

	/**Retorna um {@link Aluno}.
	 * 
	 * @param id O ID do {@link Aluno}.
	 * @return {@link Aluno}.
	 */
	public Aluno getAluno(int id) {
		return alunoDao.getAluno(id);
	}

	/**Retorna um {@link Aluno}.
	 * 
	 * @param matricula A matrícula do {@link Aluno}.
	 * @return {@link Aluno}.
	 */
	public Aluno buscaPorMatricula(String matricula) {
		return alunoDao.getAlunoPorMatricula(matricula);
	}

	/**Edita um {@link Aluno} e salva no banco.
	 * 
	 * @param aluno {@link Aluno} a ser editado.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean editar(Aluno aluno) {
		return alunoDao.editar(aluno);
	}

	/**Obtem todos os {@link Aluno}s do banco.
	 * 
	 * @return Uma {@link List} de {@link Aluno}.
	 */
	public List<Aluno> getTodos() {
		return alunoDao.getTodos();
	}
	public List<Aluno> getTodosCurso(Curso curso) {
		return alunoDao.getTodosCurso(curso);
	}

	/**Exclui um {@link Aluno} do banco.
	 * 
	 * @param aluno {@link Aluno} a ser excluído.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean exclui(Aluno aluno) {
		return alunoDao.exclui(aluno);
	}

	/**Salva um novo {@link Aluno} no banco.
	 * 
	 * @param aluno {@link Aluno} a ser salvo.
	 * @return {@link true} se houve sucesso; {@link false} se não.
	 */
	public boolean salvar(Aluno novoAluno) {
		return alunoDao.salvar(novoAluno);
	}

	/**Retorna uma {@link List} de {@link Aluno}s de uma turma.
	 * 
	 * @param turma Turma dos {@link Aluno}s a serem obtidos.
	 * @return Uma {@link List} de {@link Aluno}.
	 */
	public List<Aluno> getAlunos(Turma turma) {
		return alunoDao.getAlunos(turma);
	}
	public List<Aluno> getAlunos() {
		return alunoDao.getAlunos();
	}

	public boolean salvaOuEdita(Aluno aluno) {
		return alunoDao.salvaOuEdita(aluno);
	}

	public List<Aluno> getAlunosCurso(Turma turma, Curso curso) {
		return alunoDao.getAlunosCurso(turma, curso);
	}

}