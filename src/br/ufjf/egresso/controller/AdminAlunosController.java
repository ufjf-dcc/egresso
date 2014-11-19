package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.CursoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Administrador;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public class AdminAlunosController {
	private AlunoBusiness alunoBusiness = new AlunoBusiness();
	private Map<Integer, Aluno> editTemp = new HashMap<Integer, Aluno>();
	private List<Aluno> todosAlunos;
	private List<Aluno> filterAlunos;
	private List<Turma> turmas = new TurmaBusiness().getTodas();
	private String filterString = "";
	private List<String> semestres = new ArrayList<String>();
	private Aluno novoAluno;
	private Administrador admin;
	private Set<Integer> anos;
	private int s = 3;

	@Init
	public void init() {

		admin = ((Administrador) Sessions.getCurrent().getAttribute("admin"));
		filterAlunos = todosAlunos = alunoBusiness.getTodosCurso(admin
				.getCurso());
		anos = new HashSet<Integer>();

		System.out.println(admin.getCurso().getCodCurso());
		for (Turma t : turmas) {

			semestres.add(t.getAno() + " " + t.getSemestre() + "º semestre");
			anos.add(t.getAno());
		}
		BindUtils.postNotifyChange(null, null, this, "todosAlunos");

		BindUtils.postNotifyChange(null, null, this, "anos");

	}

	public AlunoBusiness getAlunoBusiness() {
		return alunoBusiness;
	}

	public void setAlunoBusiness(AlunoBusiness alunoBusiness) {
		this.alunoBusiness = alunoBusiness;
	}

	public Map<Integer, Aluno> getEditTemp() {
		return editTemp;
	}

	public void setEditTemp(Map<Integer, Aluno> editTemp) {
		this.editTemp = editTemp;
	}

	public List<Aluno> getTodosAlunos() {
		return todosAlunos;
	}

	public void setTodosAlunos(List<Aluno> todosAlunos) {
		this.todosAlunos = todosAlunos;
	}

	public List<String> getSemestres() {
		return semestres;
	}

	public void setSemestres(List<String> semestres) {
		this.semestres = semestres;
	}

	public Aluno getNovoAluno() {
		return novoAluno;
	}

	public void setNovoAluno(Aluno novoAluno) {
		this.novoAluno = novoAluno;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public Set<Integer> getAnos() {
		return anos;
	}

	public void setAnos(Set<Integer> anos) {
		this.anos = anos;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public void setFilterAlunos(List<Aluno> filterAlunos) {
		this.filterAlunos = filterAlunos;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Aluno> getFilterAlunos() {
		return filterAlunos;
	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	@Command
	public void submitAluno(@BindingParam("window") Window window,
			@BindingParam("semestre") int sem, @BindingParam("ano") String ano) {
		novoAluno.setAtivo(Aluno.ATIVO);
		if (ano == null || ano.trim() == "")
			Messagebox
					.show("Por favor, informe o ano em que o aluno ingressou no curso.",
							"Erro", Messagebox.OK, Messagebox.ERROR);
		else {
			if (sem == -1)
				Messagebox
						.show("Por favor, informe o semestre em que você ingressou no curso.",
								"Erro", Messagebox.OK, Messagebox.ERROR);

			else {
				novoAluno.setTurma(new TurmaBusiness().getTurma(
						Integer.parseInt(ano), sem + 1));
			}

			novoAluno.setCurso(admin.getCurso());
			window.setVisible(false);
			if (alunoBusiness.validar(novoAluno, novoAluno.getMatricula())) {
				if (alunoBusiness.salvar(novoAluno)) {
					todosAlunos.add(novoAluno);
					filterAlunos = todosAlunos;
					BindUtils.postNotifyChange(null, null, this, "filterAlunos");

				} else {
					Messagebox.show("Erro ao salvar", "Erro", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				String errorMessage = "";
				for (String error : alunoBusiness.getErrors())
					errorMessage += error;
				Messagebox.show(errorMessage,
						"Dados insuficientes / inválidos", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	@Command
	public void addAluno(@BindingParam("window") Window window) {
		this.limpa();
		window.doModal();
		System.out.println("asa");

	}

	public void limpa() {
		novoAluno = new Aluno();
		BindUtils.postNotifyChange(null, null, this, "novoAluno");
	}

	@Command
	public void changeEditableStatus(@BindingParam("aluno") Aluno aluno) {
		if (!aluno.getEditingStatus()) {
			Aluno temp = new Aluno();
			temp.copiar(aluno);
			editTemp.put(aluno.getId(), temp);
			aluno.setEditingStatus(true);
		} else {
			aluno.copiar(editTemp.get(aluno.getId()));
			editTemp.remove(aluno.getId());
			aluno.setEditingStatus(false);
		}
		refreshRowTemplate(aluno);
	}

	@Command
	public void confirm(@BindingParam("aluno") Aluno aluno) {
		if (alunoBusiness.validar(aluno, editTemp.get(aluno.getId())
				.getMatricula())) {
			if (!alunoBusiness.editar(aluno))
				Messagebox.show("Não foi possível editar o aluno.", "Erro",
						Messagebox.OK, Messagebox.ERROR);
			editTemp.remove(aluno.getId());
			aluno.setEditingStatus(false);
			refreshRowTemplate(aluno);
		} else {
			String errorMessage = "";
			for (String error : alunoBusiness.getErrors())
				errorMessage += error;
			Messagebox.show(errorMessage, "Dados insuficientes / inválidos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void alterarEstado(@BindingParam("aluno") final Aluno aluno) {
		if (aluno.getAtivo() == Aluno.INATIVO_ALUNO) {
			Messagebox
					.show("Você não pode aivar o perfil deste aluno, pois ele aluno decidiu desativá-lo.",
							"Não é possível desativar", Messagebox.OK,
							Messagebox.EXCLAMATION);
		}

		Messagebox.show("Você tem certeza que deseja "
				+ (aluno.getAtivo() == Aluno.ATIVO ? "desativar" : "re-ativar")
				+ " o(a) aluno(a) " + aluno.getNome() + ", de matrícula "
				+ aluno.getMatricula() + "?", "Confirmação", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {
							aluno.setAtivo(aluno.getAtivo() == Aluno.ATIVO ? Aluno.INATIVO_ADMIN
									: Aluno.ATIVO);
							if (alunoBusiness.salvaOuEdita(aluno)) {
								notifyAlunos();
								Messagebox.show(
										(aluno.getAtivo() == Aluno.ATIVO ? "Re-Ativação"
												: "Desativação")
												+ " realizada com sucesso.",
										"Sucesso", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show(
										(aluno.getAtivo() == Aluno.ATIVO ? "Desativação"
												: "Re-ativação")
												+ " não pôde ser realizada. Por favor, tente mais tarde",
										"Erro", Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});

	}

	public void removeFromList(Aluno aluno) {
		filterAlunos.remove(aluno);
		todosAlunos.remove(aluno);
		notifyAlunos();
	}

	public void refreshRowTemplate(Aluno aluno) {
		BindUtils.postNotifyChange(null, null, aluno, "editingStatus");
	}

	@Command
	public void filtra() {
		filterAlunos = new ArrayList<Aluno>();
		String filter = filterString.toLowerCase().trim();
		for (Aluno c : todosAlunos) {
			if (c.getNome().toLowerCase().contains(filter)
					|| c.getMatricula().toLowerCase().contains(filter)) {
				filterAlunos.add(c);
			}
		}
		notifyAlunos();
	}

	public void notifyAlunos() {
		BindUtils.postNotifyChange(null, null, this, "filterAlunos");
	}

	public static void main(String[] args) {
		Messagebox.show("Erro ao adicionar!", "Erro", Messagebox.OK,
				Messagebox.ERROR);
	}

}
