package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AtuacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.TipoAtuacao;
import br.ufjf.egresso.persistent.impl.TipoAtuacaoDAO;




public class FbPerfilController {

	private Aluno aluno;
	private AtuacaoBusiness atuacaoBusiness = new AtuacaoBusiness();
	private List<Atuacao> todasAtuacoes =  atuacaoBusiness.getTodas();
	private Atuacao novaAtuacao = new Atuacao();
	private Map<Integer, Atuacao> editTemp = new HashMap<Integer, Atuacao>();
	private List<Atuacao> filterAtuacoes = todasAtuacoes;
	private String filterString = "";
	private List<TipoAtuacao> tipoAtuacao = new TipoAtuacaoDAO().getTodas();
	@Init
	public void init() {
		Session session = Sessions.getCurrent();
		aluno = (Aluno) session.getAttribute("aluno");
		if(todasAtuacoes == null)
			todasAtuacoes = new ArrayList<Atuacao>();
	}
	public List<Atuacao> getFilterAtuacoes() {
		return filterAtuacoes;
	}
	public List<TipoAtuacao> getTipoAtuacao() {
		return tipoAtuacao;
	}
	
	public String getFilterString() {
		return filterString;
	}
	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public Atuacao getNovaAtuacao() {
		return novaAtuacao;
	}

	public void setNovaAtuacao(Atuacao novaAtuacao) {
		this.novaAtuacao = novaAtuacao;
	}
	public void refreshRowTemplate(Atuacao atuacao) {
		BindUtils.postNotifyChange(null, null, atuacao, "editingStatus");
	}
	@Command
	public void filtra() {
		filterAtuacoes = new ArrayList<Atuacao>();
		String filter = filterString.toLowerCase().trim();
		for (Atuacao c : todasAtuacoes) {
			if (c.getCargo().toLowerCase().contains(filter)) {
				filterAtuacoes.add(c);
			}
		}
		BindUtils.postNotifyChange(null, null, this, "filterTurmas");
	}
	@Command
	public void changeEditableStatus(@BindingParam("atuacao") Atuacao atuacao) {
		if (!atuacao.getEditingStatus()) {
			Atuacao temp = new Atuacao();
			temp.copy(atuacao);
			editTemp.put(atuacao.getId(), temp);
			atuacao.setEditingStatus(true);
		} else {
			atuacao.copy(editTemp.get(atuacao.getId()));
			editTemp.remove(atuacao.getId());
			atuacao.setEditingStatus(false);
		}
		refreshRowTemplate(atuacao);
	}
	
	@Command
	public void addAtuacao(@BindingParam("window") Window window) {
		this.limpa();
		window.doModal();
	}

	@Command
	public void submitAtuacao(@BindingParam("window") final Window window) {
		novaAtuacao.setAluno(aluno);
	
		if (atuacaoBusiness.validar(novaAtuacao)) {
			if (atuacaoBusiness.salvar(novaAtuacao)) {
				todasAtuacoes.add(novaAtuacao);
				filterAtuacoes = todasAtuacoes;
				notifyAtuacoes();
				Clients.clearBusy(window);
				Messagebox.show("Atuacão Adicionada!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
				limpa();
			} else {
				Clients.clearBusy(window);
				Messagebox.show("A atuação não foi adicionada!", "Erro",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			String errorMessage = "";
			for (String error : atuacaoBusiness.getErrors())
				errorMessage += error;
			Messagebox.show(errorMessage, "Dados insuficientes / inválidos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void limpa() {
		novaAtuacao = new Atuacao();
		BindUtils.postNotifyChange(null, null, this, "novaAtuacao");
	}
	public void notifyAtuacoes() {
		BindUtils.postNotifyChange(null, null, this, "filterAtuacoes");
	}
	
	public void removeFromList(Atuacao atuacao) {
		filterAtuacoes.remove(atuacao);
		todasAtuacoes.remove(atuacao);
		BindUtils.postNotifyChange(null, null, this, "filterTurmas");
	}
	@Command
	public void confirm(@BindingParam("atuacao") Atuacao atuacao) {
		if (atuacaoBusiness.validar(atuacao)) {
			if (!atuacaoBusiness.editar(atuacao))
				Messagebox.show("Não foi possível editar a atuação.",
						"Erro", Messagebox.OK, Messagebox.ERROR);
			editTemp.remove(atuacao.getId());
			atuacao.setEditingStatus(false);
			refreshRowTemplate(atuacao);
		} else {
			String errorMessage = "";
			for (String error : atuacaoBusiness.getErrors())
				errorMessage += error;
			Messagebox.show(errorMessage, "Dados insuficientes / inválidos",
					Messagebox.OK, Messagebox.ERROR);
			}
		}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void delete(@BindingParam("atuacao") final Atuacao atuacao) {
		Messagebox.show(
				"Você tem certeza que deseja excluir a atuação "
						+ atuacao.getCargo() +"  "+ atuacao.getDataInicio()+ "/"+atuacao.getDataTermino()+"?",
						"Confirmação", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							if (atuacaoBusiness.exclui(atuacao)) {
								removeFromList(atuacao);
								Messagebox
										.show("A atuacao foi excluida com sucesso.",
												"Sucesso", Messagebox.OK,
												Messagebox.INFORMATION);
							} else {
								String errorMessage = "A atuacao não pode ser excluída.\n";
								for (String error : atuacaoBusiness.getErrors())
									errorMessage += error;
								Messagebox.show(errorMessage, "Erro",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});
	}


}