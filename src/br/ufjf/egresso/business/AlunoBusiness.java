package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.persistent.impl.AlunoDAO;

public class AlunoBusiness extends GenericBusiness {
	private AlunoDAO alunoDao;
	private List<String> errors;

	public AlunoBusiness() {
		this.errors = new ArrayList<String>();
		alunoDao = new AlunoDAO();
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean validar(Aluno aluno, String matriculaAntiga) {
		errors.clear();

		validaMatricula(aluno.getMatricula(), matriculaAntiga);
		validaNome(aluno.getNome());

		return errors.size() == 0;
	}

	private void validaNome(String nome) {
		if (nome == null || nome.trim().length() == 0)
			errors.add("É necessário informar o nome do aluno;\n");
	}

	private void validaMatricula(String matricula, String matriculaAntiga) {
		if (matricula == null || matricula.trim().length() == 0)
			errors.add("É necessário informar o código do curso;\n");
		else
			jaExiste(matricula, matriculaAntiga);
	}

	private boolean jaExiste(String matricula, String matriculaAntiga) {
		errors.clear();
        if (alunoDao.jaExiste(matricula, matriculaAntiga)){
                errors.add("Já existe um departamento com este código.\n");
                return true;
        }
        return false;
	}

	public boolean alunoCadastrado(String facebookId)
			throws HibernateException, Exception {
		return alunoDao.getAluno(facebookId) != null;
	}

	public Aluno getAluno(String facebookId) {
		return alunoDao.getAluno(facebookId);
	}

	public boolean checaLogin(Aluno aluno) throws HibernateException, Exception {
		if (aluno != null) {
			aluno = alunoDao.getAluno(aluno.getFacebookId());
			if (aluno != null) {
				return true;
			}
		}
		return false;
	}

	public Aluno buscaPorMatricula(String matricula) {
		return alunoDao.buscaPorMatricula(matricula);
	}

	public boolean editar(Aluno aluno) {
		return alunoDao.editar(aluno);
	}

	public List<Aluno> getTodos() {
		return alunoDao.getTodos();
	}

	public boolean exclui(Aluno aluno) {
		return alunoDao.exclui(aluno);
	}

	public boolean salvar(Aluno novoAluno) {
		return alunoDao.salvar(novoAluno);
	}

}