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

import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Turma;

public class AdminTurmasController {
	private TurmaBusiness turmaBusiness = new TurmaBusiness();
	private Turma novaTurma = new Turma();
	private Map<Integer, Turma> editTemp = new HashMap<Integer, Turma>();
	private List<Turma> todasTurmas = turmaBusiness.getTodas();
	private List<Turma> filterTurmas = todasTurmas;
	private String filterString = "";

	public List<Turma> getFilterTurmas() {
		return filterTurmas;
	}

	public Turma getNovaTurma() {
		return this.novaTurma;
	}

	public void setNovaTurma(Turma novaTurma) {
		this.novaTurma = novaTurma;
	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	@Command
	public void changeEditableStatus(@BindingParam("turma") Turma turma) {
		if (!turma.getEditingStatus()) {
			Turma temp = new Turma();
			temp.copy(turma);
			editTemp.put(turma.getId(), temp);
			turma.setEditingStatus(true);
		} else {
			turma.copy(editTemp.get(turma.getId()));
			editTemp.remove(turma.getId());
			turma.setEditingStatus(false);
		}
		refreshRowTemplate(turma);
	}



	@Command
	public void confirm(@BindingParam("turma") Turma turma) {
		if (turmaBusiness.validar(turma)) {
			if (!turmaBusiness.editar(turma))
				Messagebox.show("Não foi possível editar o departamento.",
						"Erro", Messagebox.OK, Messagebox.ERROR);
			editTemp.remove(turma.getId());
			turma.setEditingStatus(false);
			refreshRowTemplate(turma);
		} else {
			String errorMessage = "";
			for (String error : turmaBusiness.getErrors())
				errorMessage += error;
			Messagebox.show(errorMessage, "Dados insuficientes / inválidos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void delete(@BindingParam("turma") final Turma turma) {
		Messagebox.show(
				"Você tem certeza que deseja excluir a turma do semestre "
						+ turma.getSemestre() + "?", "Confirmação", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							if (turmaBusiness.exclui(turma)) {
								removeFromList(turma);
								Messagebox
										.show("A turma foi excluida com sucesso.",
												"Sucesso", Messagebox.OK,
												Messagebox.INFORMATION);
							} else {
								String errorMessage = "A turma não pode ser excluída.\n";
								for (String error : turmaBusiness.getErrors())
									errorMessage += error;
								Messagebox.show(errorMessage, "Erro",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});
	}

	public void removeFromList(Turma turma) {
		filterTurmas.remove(turma);
		todasTurmas.remove(turma);
		BindUtils.postNotifyChange(null, null, this, "filterTurmas");
	}

	public void refreshRowTemplate(Turma turma) {
		BindUtils.postNotifyChange(null, null, turma, "editingStatus");
	}

	@Command
	public void filtra() {
		filterTurmas = new ArrayList<Turma>();
		String filter = filterString.toLowerCase().trim();
		for (Turma c : todasTurmas) {
			if (c.getSemestre().toLowerCase().contains(filter)) {
				filterTurmas.add(c);
			}
		}
		BindUtils.postNotifyChange(null, null, this, "filterTurmas");
	}

	@Command
	public void addTurma(@BindingParam("window") Window window) {
		this.limpa();
		window.doModal();
	}

	@Command
	public void submitTurma(@BindingParam("window") final Window window) {
		if (turmaBusiness.validar(novaTurma)) {
			if (turmaBusiness.salvar(novaTurma)) {
				todasTurmas.add(novaTurma);
				filterTurmas = todasTurmas;
				notifyTurmas();
				Clients.clearBusy(window);
				Messagebox.show("Turma Adicionada!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
				limpa();
			} else {
				Clients.clearBusy(window);
				Messagebox.show("A turma não foi adicionada!", "Erro",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			String errorMessage = "";
			for (String error : turmaBusiness.getErrors())
				errorMessage += error;
			Messagebox.show(errorMessage, "Dados insuficientes / inválidos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void notifyTurmas() {
		BindUtils.postNotifyChange(null, null, this, "filterTurmas");
	}

	public void limpa() {
		novaTurma = new Turma();
		BindUtils.postNotifyChange(null, null, this, "novaTurma");
	}

}
