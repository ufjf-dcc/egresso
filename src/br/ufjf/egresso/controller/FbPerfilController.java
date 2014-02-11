package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.AtuacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.TipoAtuacao;
import br.ufjf.egresso.persistent.impl.TipoAtuacaoDAO;

public class FbPerfilController {

	private Aluno aluno;
	private AtuacaoBusiness atuacaoBusiness = new AtuacaoBusiness();
	private List<Atuacao> empregos = new ArrayList<Atuacao>(), projetos = new ArrayList<Atuacao>(), formacoes = new ArrayList<Atuacao>();
	private List<Atuacao> filtraEmpregos, filtraProjetos, filtraFormacoes;
	private Atuacao novaAtuacao = new Atuacao();
	private Map<Integer, Atuacao> editTemp = new HashMap<Integer, Atuacao>();
	private String filterString = "";
	private Atuacao atuacaoAtual;
	private List<TipoAtuacao> tipoAtuacao = new TipoAtuacaoDAO().getTodas();
	private boolean podeEditar = false;

	@Init
	public void init() {
		String facebookId = (String) Executions.getCurrent().getParameter("id");
		if (facebookId != null)
			aluno = new AlunoBusiness().getAluno(facebookId);
		else {
			aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
			podeEditar = true;
		}
		List<Atuacao> todasAtuacoes = new AtuacaoBusiness().getPorAluno(aluno);
		if(todasAtuacoes != null)
			for (Atuacao a : todasAtuacoes){
				if (a.getAtual())
					atuacaoAtual = a;
				
				switch(a.getTipoAtuacao().getId()){
				case TipoAtuacao.EMPREGO:
					empregos.add(a);
					break;
				case TipoAtuacao.PROJETO:
					projetos.add(a);
					break;
				case TipoAtuacao.FORMACAO:
					formacoes.add(a);
					break;
				}
			}
		
		filtraEmpregos = empregos;
		filtraProjetos = projetos;
		filtraFormacoes = formacoes;
	}

	public boolean isPodeEditar() {
		return podeEditar;
	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	public List<Atuacao> getFiltraEmpregos() {
		return filtraEmpregos;
	}

	public List<Atuacao> getFiltraProjetos() {
		return filtraProjetos;
	}

	public List<Atuacao> getFiltraFormacoes() {
		return filtraFormacoes;
	}

	public List<TipoAtuacao> getTipoAtuacao() {
		return tipoAtuacao;
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

	public Atuacao getAtuacaoAtual() {
		return atuacaoAtual;
	}

	public void refreshRowTemplate(Atuacao atuacao) {
		BindUtils.postNotifyChange(null, null, atuacao, "editingStatus");
	}

	@Command
	public void filtraEmpregos() {
		filtraEmpregos = new ArrayList<Atuacao>();
		String filter = filterString.toLowerCase().trim();
		for (Atuacao c : empregos) {
			if (c.getCargo().toLowerCase().contains(filter) || c.getLocal().toLowerCase().contains(filter)) {
				filtraEmpregos.add(c);
			}

		}
		BindUtils.postNotifyChange(null, null, this, "filtraEmpregos");
	}
	
	@Command
	public void filtraProjetos() {
		filtraProjetos = new ArrayList<Atuacao>();
		String filter = filterString.toLowerCase().trim();
		for (Atuacao c : projetos) {
			if (c.getCargo().toLowerCase().contains(filter) || c.getLocal().toLowerCase().contains(filter)) {
				filtraProjetos.add(c);
			}

		}
		BindUtils.postNotifyChange(null, null, this, "filtraProjetos");
	}
	
	@Command
	public void filtraFormacoes() {
		filtraFormacoes = new ArrayList<Atuacao>();
		String filter = filterString.toLowerCase().trim();
		for (Atuacao c : formacoes) {
			if (c.getCargo().toLowerCase().contains(filter) || c.getLocal().toLowerCase().contains(filter)) {
				filtraFormacoes.add(c);
			}

		}
		BindUtils.postNotifyChange(null, null, this, "filtraFormacoes");
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
	public void addEmprego(@BindingParam("window") Window window) {
		this.limpa();
		for(TipoAtuacao t : tipoAtuacao)
			if(t.getId() == TipoAtuacao.EMPREGO){
				novaAtuacao.setTipoAtuacao(t);
				break;
			}
		window.doModal();
	}
	
	@Command
	public void addProjeto(@BindingParam("window") Window window) {
		this.limpa();
		for(TipoAtuacao t : tipoAtuacao)
			if(t.getId() == TipoAtuacao.PROJETO){
				novaAtuacao.setTipoAtuacao(t);
				break;
			}
		window.doModal();
	}
	
	@Command
	public void addFormacao(@BindingParam("window") Window window) {
		this.limpa();
		for(TipoAtuacao t : tipoAtuacao)
			if(t.getId() == TipoAtuacao.FORMACAO){
				novaAtuacao.setTipoAtuacao(t);
				break;
			}
		window.doModal();
	}

	@Command
	public void submitAtuacao(@BindingParam("window") final Window window) {
		novaAtuacao.setAluno(aluno);

		if (atuacaoBusiness.validar(novaAtuacao)) {
			if (atuacaoBusiness.salvar(novaAtuacao)) {
				switch(novaAtuacao.getTipoAtuacao().getId()){
				case TipoAtuacao.EMPREGO:
					empregos.add(novaAtuacao);
					filtraEmpregos = empregos;
					notificaEmpregos();
					break;
				case TipoAtuacao.PROJETO:
					projetos.add(novaAtuacao);
					filtraProjetos = projetos;
					notificaProjetos();
					break;
				case TipoAtuacao.FORMACAO:
					formacoes.add(novaAtuacao);
					filtraFormacoes = formacoes;
					notificaFormacoes();
					break;
				}
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
		novaAtuacao.setAtual(false);
		BindUtils.postNotifyChange(null, null, this, "novaAtuacao");
	}

	public void notificaEmpregos() {
		BindUtils.postNotifyChange(null, null, this, "filtraEmpregos");
	}
	
	public void notificaFormacoes() {
		BindUtils.postNotifyChange(null, null, this, "filtraFormacoes");
	}
	
	public void notificaProjetos() {
		BindUtils.postNotifyChange(null, null, this, "filtraProjetos");
	}

	public void removeFromList(Atuacao atuacao) {
		switch(atuacao.getTipoAtuacao().getId()){
		case TipoAtuacao.EMPREGO:
			filtraEmpregos.remove(atuacao);
			empregos.remove(atuacao);
			notificaEmpregos();
			break;
		case TipoAtuacao.PROJETO:
			filtraProjetos.remove(atuacao);
			projetos.remove(atuacao);
			notificaProjetos();
			break;
		case TipoAtuacao.FORMACAO:
			filtraFormacoes.remove(atuacao);
			formacoes.remove(atuacao);
			notificaFormacoes();
			break;
		}
	}

	@Command
	public void confirm(@BindingParam("atuacao") Atuacao atuacao) {
		if (atuacaoBusiness.validar(atuacao)) {
			if (!atuacaoBusiness.editar(atuacao))
				Messagebox.show("Não foi possível editar a atuação.", "Erro",
						Messagebox.OK, Messagebox.ERROR);
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
		Messagebox.show("Você tem certeza que deseja excluir a atuação "
				+ atuacao.getCargo() + "  " + atuacao.getDataInicio() + "/"
				+ atuacao.getDataTermino() + "?", "Confirmação", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							if (atuacaoBusiness.exclui(atuacao)) {
								removeFromList(atuacao);
								Messagebox.show(
										"A atuacao foi excluida com sucesso.",
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

	@Command("limparPesquisaEmprego")
	public void limparPesquisaEmprego() {
		filterString = "";
		filtraEmpregos = empregos;
		BindUtils.postNotifyChange(null, null, this, "filtraEmpregos");
		BindUtils.postNotifyChange(null, null, this, "filterString");
	}
	
	@Command("limparPesquisaProjeto")
	public void limparPesquisaProjeto() {
		filterString = "";
		filtraProjetos = projetos;
		BindUtils.postNotifyChange(null, null, this, "filtraProjetos");
		BindUtils.postNotifyChange(null, null, this, "filterString");
	}
	
	@Command("limparPesquisaFormacao")
	public void limparPesquisaFormacao() {
		filterString = "";
		filtraFormacoes = formacoes;
		BindUtils.postNotifyChange(null, null, this, "filtraFormacoes");
		BindUtils.postNotifyChange(null, null, this, "filterString");
	}

	@Command
	public void dataTermino(@BindingParam("checkbox") Checkbox checkbox,
			@BindingParam("datebox") Datebox datebox) {
		datebox.setDisabled(checkbox.isChecked());
		if (checkbox.isChecked())
			datebox.setValue(null);
	}

}