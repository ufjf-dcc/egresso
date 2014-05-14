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

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public class AdminAlunosController {
	private AlunoBusiness alunoBusiness = new AlunoBusiness();
	private Map<Integer, Aluno> editTemp = new HashMap<Integer, Aluno>();
	private List<Aluno> todosAlunos = alunoBusiness.getTodos();
	private List<Aluno> filterAlunos = todosAlunos;
	private List<Turma> turmas = new TurmaBusiness().getTodas();
	private String filterString = "";

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

		Messagebox.show("Você tem certeza que deseja "
				+ (aluno.isAtivo() ? "desativar" : "re-ativar")
				+ " o(a) aluno(a) " + aluno.getNome() + ", de matrícula "
				+ aluno.getMatricula() + "?", "Confirmação", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {
							aluno.setAtivo(!aluno.isAtivo());
							if (alunoBusiness.salvaOuEdita(aluno)) {
								notifyAlunos();
								Messagebox.show(
										(!aluno.isAtivo() ? "Desativação"
												: "Re-ativação")
												+ " realizada com sucesso.",
										"Sucesso", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show(
										(!aluno.isAtivo() ? "Desativação"
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

}
