package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
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
	public void changeEditableStatus(@BindingParam("aluno") Aluno departamento) {
		if (!departamento.getEditingStatus()) {
			Aluno temp = new Aluno();
			temp.copiar(departamento);
			editTemp.put(departamento.getId(), temp);
			departamento.setEditingStatus(true);
		} else {
			departamento.copiar(editTemp.get(departamento.getId()));
			editTemp.remove(departamento.getId());
			departamento.setEditingStatus(false);
		}
		refreshRowTemplate(departamento);
	}

	@Command
	public void confirm(@BindingParam("aluno") Aluno aluno) {
		if (alunoBusiness.validar(aluno, editTemp.get(aluno.getId())
				.getMatricula())) {
			if (!alunoBusiness.editar(aluno))
				Messagebox.show("Não foi possível editar o departamento.",
						"Erro", Messagebox.OK, Messagebox.ERROR);
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
		Messagebox.show(
				"Você tem certeza que deseja deletar o aluno: "
						+ aluno.getNome() + "?", "Confirmação", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							if (alunoBusiness.exclui(aluno)) {
								removeFromList(aluno);
								Messagebox
										.show("O departamento foi excluído com sucesso.",
												"Sucesso", Messagebox.OK,
												Messagebox.INFORMATION);
							} else {
								String errorMessage = "O departamento não pôde ser excluído.\n";
								for (String error : alunoBusiness.getErrors())
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
		BindUtils.postNotifyChange(null, null, this, "filterAlunos");
	}

	public void refreshRowTemplate(Aluno aluno) {
		BindUtils.postNotifyChange(null, null, aluno, "editingStatus");
	}

	@Command
	public void filtra() {
		filterAlunos = new ArrayList<Aluno>();
		String filter = filterString.toLowerCase().trim();
		for (Aluno c : todosAlunos) {
			if (c.getNome().toLowerCase().contains(filter) || c.getMatricula().toLowerCase().contains(filter)) {
				filterAlunos.add(c);
			}
		}
		BindUtils.postNotifyChange(null, null, this, "filterAlunos");
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
				Clients.clearBusy(window);
				Messagebox.show("Aluno adicionado com sucesso!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
				limpa();
			} else {
				Clients.clearBusy(window);
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
