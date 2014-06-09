package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.AtuacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.TipoAtuacao;
import br.ufjf.egresso.persistent.TipoAtuacaoDAO;

public class FbPerfilController {

	private Aluno aluno;
	private AlunoBusiness alunoBusiness = new AlunoBusiness();
	private AtuacaoBusiness atuacaoBusiness = new AtuacaoBusiness();
	private List<Atuacao> empregos = new ArrayList<Atuacao>(),
			projetos = new ArrayList<Atuacao>(),
			formacoes = new ArrayList<Atuacao>();
	private List<Atuacao> filtraEmpregos, filtraProjetos, filtraFormacoes;
	private Atuacao novaAtuacao = new Atuacao();
	private String filterString = "";
	private Atuacao atuacaoAtual, atuacaoEmEdicao;
	private final List<TipoAtuacao> tipoAtuacao = new TipoAtuacaoDAO()
			.getTodas();
	private boolean podeEditar = false;
	private boolean emEdicao = false;

	@Init
	public void init() {
		String facebookId = (String) Executions.getCurrent().getParameter("id");
		if (facebookId != null) {
			aluno = new AlunoBusiness().getAluno(facebookId);
			podeEditar = aluno.getId() == ((Aluno) Sessions.getCurrent()
					.getAttribute("aluno")).getId();
			
			if (aluno != null && aluno.getAtivo() == Aluno.ATIVO) {
				Sessions.getCurrent().setAttribute("aluno", aluno);
				Executions.sendRedirect("/fb/perfil.zul");
			} else {
				if (aluno != null)
					Sessions.getCurrent().setAttribute("aluno", aluno);

				Executions.sendRedirect("/fb/cadastro.zul");
			}
		
		} else {
			aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
			podeEditar = true;
		}

		List<Atuacao> todasAtuacoes = new AtuacaoBusiness().getPorAluno(aluno);
		if (todasAtuacoes != null)
			for (Atuacao a : todasAtuacoes) {
				if (a.getDataTermino() == null)
					atuacaoAtual = a;

				switch (a.getTipoAtuacao().getId()) {
				case TipoAtuacao.EMPREGO:
					empregos.add(a);
					break;
				case TipoAtuacao.PROJETO:
					projetos.add(a);
					break;
				case TipoAtuacao.FORMACAO:
					formacoes.add(a);
					break;
				default:
					System.out.println("ID inválido de Atuação!.");
				}
			}

		filtraEmpregos = empregos;
		filtraProjetos = projetos;
		filtraFormacoes = formacoes;
	}

	@Command
	public void editarAtuacao(
			@BindingParam("editarSalvar") Image imgSalvarEditar,
			@BindingParam("sumir") Vlayout v1,
			@BindingParam("aparecer") Vlayout v2,
			@BindingParam("cancelar") Image imgCancelar,
			@BindingParam("atuacao") Atuacao atuacao) {

		if (!imgCancelar.isVisible()) {
			atuacaoEmEdicao = new Atuacao();
			atuacaoEmEdicao.copy(atuacao);
			imgSalvarEditar.setSrc("/img/confirmar.png");
			BindUtils.postNotifyChange(null, null, this, "atuacaoEmEdicao");
		} else {
			imgSalvarEditar.setSrc("/img/editar.png");
			atuacao.copy(atuacaoEmEdicao);
			atuacaoBusiness.salvaOuEdita(atuacao);
			BindUtils.postNotifyChange(null, null, this, "filtraProjetos");
			BindUtils.postNotifyChange(null, null, this, "filtraEmpregos");
			BindUtils.postNotifyChange(null, null, this, "filtraFormacoes");
		}
		v1.setVisible(!v1.isVisible());
		v2.setVisible(!v2.isVisible());
		imgCancelar.setVisible(!imgCancelar.isVisible());
		emEdicao = imgCancelar.isVisible();
		BindUtils.postNotifyChange(null, null, this, "emEdicao");
	}

	@Command
	public void cancelarEdicao(
			@BindingParam("editarSalvar") Image imgSalvarEditar,
			@BindingParam("cancelar") Image imgCancelar,
			@BindingParam("sumir") Vlayout v1,
			@BindingParam("aparecer") Vlayout v2) {
		imgCancelar.setVisible(false);
		imgSalvarEditar.setSrc("/img/editar.png");
		imgSalvarEditar.setVisible(true);
		v1.setVisible(!v1.isVisible());
		v2.setVisible(!v2.isVisible());
		atuacaoEmEdicao = null;
		emEdicao = false;
		BindUtils.postNotifyChange(null, null, this, "emEdicao");
	}

	@Command
	public void marcarAtual(@BindingParam("atual") boolean atual,
			@BindingParam("datebox") Datebox datebox) {
		if (atual) {
			datebox.setValue(null);
			datebox.setDisabled(true);
			atuacaoEmEdicao.setDataTermino(null);
		} else {
			datebox.setDisabled(false);
		}
	}

	@Command
	public void marcarAtualAdd(@BindingParam("atual") boolean atual,
			@BindingParam("datebox") Datebox datebox) {
		if (atual) {
			datebox.setValue(null);
			datebox.setDisabled(true);
			novaAtuacao.setDataTermino(null);
		} else {
			datebox.setDisabled(false);
		}
	}

	public boolean isEmEdicao() {
		return emEdicao;
	}

	public void setEmEdicao(boolean emEdicao) {
		this.emEdicao = emEdicao;
	}

	public Atuacao getAtuacaoEmEdicao() {
		return atuacaoEmEdicao;
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

	@Command
	public void filtraEmpregos() {
		filtraEmpregos = new ArrayList<Atuacao>();
		String filter = filterString.toLowerCase().trim();
		for (Atuacao c : empregos) {
			if (c.getCargo().toLowerCase().contains(filter)
					|| c.getLocal().toLowerCase().contains(filter)) {
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
			if (c.getCargo().toLowerCase().contains(filter)
					|| c.getLocal().toLowerCase().contains(filter)) {
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
			if (c.getCargo().toLowerCase().contains(filter)
					|| c.getLocal().toLowerCase().contains(filter)) {
				filtraFormacoes.add(c);
			}

		}
		BindUtils.postNotifyChange(null, null, this, "filtraFormacoes");
	}

	@Command
	public void mostraDescricao(@BindingParam("label") Label label) {
		label.setVisible(!label.isVisible());
	}

	@Command
	public void adicionaAtuacao(@BindingParam("window") Window window,
			@BindingParam("tipo") int tipo) {
		this.limpa();
		for (TipoAtuacao t : tipoAtuacao)
			if (t.getId() == tipo) {
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
				switch (novaAtuacao.getTipoAtuacao().getId()) {
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
				default:
					System.out.println("ID inválido de Atuação!.");
				}
				Messagebox.show("Atuacão Adicionada!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
				limpa();
			} else {
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
		window.setVisible(false);
	}

	public void limpa() {
		novaAtuacao = new Atuacao();
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
		switch (atuacao.getTipoAtuacao().getId()) {
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
		default:
			System.out.println("ID inválido de Atuação!.");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void excluirAtuacao(@BindingParam("atuacao") final Atuacao atuacao) {
		Messagebox.show("Você tem certeza que deseja excluir a atuação "
				+ atuacao.getCargo() + "?", "Confirmação", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							if (atuacaoBusiness.exclui(atuacao)) {
								removeFromList(atuacao);
								Messagebox.show(
										"A atuação foi excluída com sucesso.",
										"Sucesso", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								String errorMessage = "A atuação não pôde ser excluída.\n";
								for (String error : atuacaoBusiness.getErrors())
									errorMessage += error;
								Messagebox.show(errorMessage, "Erro",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});
	}

	@Command
	public void dataTermino(@BindingParam("checkbox") Checkbox checkbox,
			@BindingParam("datebox") Datebox datebox) {
		datebox.setDisabled(checkbox.isChecked());
		if (checkbox.isChecked())
			datebox.setValue(null);
	}

	@Command
	public void adicionaInteresses(@BindingParam("window") Window window) {
		window.doModal();
	}

	@Command
	public void submitInteresses(@BindingParam("window") Window window) {
		if (alunoBusiness.editar(aluno))
			Messagebox.show("Interesse Adicionado!", "Sucesso", Messagebox.OK,
					Messagebox.INFORMATION);

		window.setVisible(false);
	}

}