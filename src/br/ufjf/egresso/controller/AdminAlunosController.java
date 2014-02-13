package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.business.AtuacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public class AdminAlunosController {
	private AlunoBusiness alunoBusiness = new AlunoBusiness();
	private Aluno novoAluno = null;
	private Map<Integer, Aluno> editTemp = new HashMap<Integer, Aluno>();
	private List<Aluno> todosAlunos = alunoBusiness.getTodos();
	private List<Aluno> filterAlunos = todosAlunos;
	private List<Turma> turmas = new TurmaBusiness().getTodas();
	private String filterString = "";

	public List<Aluno> getFilterAlunos() {
		return filterAlunos;
	}

	public Aluno getNovoAluno() {
		return this.novoAluno;
	}

	public void setNovoAluno(Aluno novoAluno) {
		this.novoAluno = novoAluno;
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
	public void delete(@BindingParam("aluno") final Aluno aluno) {
		if (aluno.getFacebookId() != null)
			Messagebox
					.show("Você tem certeza que deseja deletar o(a) aluno(a): "
							+ aluno.getNome()
							+ ", já cadastrado(a) no sistema? (Note que isso acarretará na exclusão de todas as atividades dele(a) no sistema permanentemente.)",
							"Confirmação", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event e) {
									if (Messagebox.ON_OK.equals(e.getName())) {
										int index = filterAlunos.indexOf(aluno);
										aluno.setFacebookId(null);
										aluno.setUrlFoto(null);
										if (alunoBusiness.editar(aluno)) {
											if (new AtuacaoBusiness()
													.excluiPorAluno(aluno)) {
												filterAlunos.set(index, aluno);
												notifyAlunos();
												Messagebox
														.show("O aluno foi excluído com sucesso.",
																"Sucesso",
																Messagebox.OK,
																Messagebox.INFORMATION);
											}
										} else {
											String errorMessage = "O aluno não pôde ser excluído.\n";
											for (String error : alunoBusiness
													.getErrors())
												errorMessage += error;
											Messagebox.show(errorMessage,
													"Erro", Messagebox.OK,
													Messagebox.ERROR);
										}

									}
								}
							});
		else
			Messagebox.show(
					"Você tem certeza que deseja deletar o registro do aluno: "
							+ aluno.getNome() + "?", "Confirmação",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event e) {
							if (Messagebox.ON_OK.equals(e.getName())) {

								if (alunoBusiness.exclui(aluno)) {
									removeFromList(aluno);
									notifyAlunos();
									Messagebox
											.show("O aluno foi excluído com sucesso.",
													"Sucesso", Messagebox.OK,
													Messagebox.INFORMATION);
								} else {
									String errorMessage = "O aluno não pôde ser excluído.\n";
									for (String error : alunoBusiness
											.getErrors())
										errorMessage += error;
									Messagebox.show(errorMessage, "Erro",
											Messagebox.OK, Messagebox.ERROR);
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

	@Command
	public void addAluno(@BindingParam("window") Window window) {
		this.limpa();
		window.doModal();
	}

	@Command
	public void submitAluno(@BindingParam("window") final Window window) {
		if (alunoBusiness.validar(novoAluno, null)) {
			if (alunoBusiness.salvar(novoAluno)) {
				todosAlunos.add(novoAluno);
				filterAlunos = todosAlunos;
				notifyAlunos();
				Messagebox.show("Aluno adicionado com sucesso!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
				limpa();
			} else {
				Messagebox.show("Aluno não foi adicionado!", "Erro",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			String errorMessage = "";
			for (String error : alunoBusiness.getErrors())
				errorMessage += error;
			Messagebox.show(errorMessage, "Dados insuficientes / inválidos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void notifyAlunos() {
		BindUtils.postNotifyChange(null, null, this, "filterAlunos");
	}

	public void limpa() {
		novoAluno = new Aluno();
		BindUtils.postNotifyChange(null, null, this, "novoAluno");
	}

}
