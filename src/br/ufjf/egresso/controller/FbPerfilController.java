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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.AtuacaoBusiness;
import br.ufjf.egresso.business.InteresseBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.Interesse;
import br.ufjf.egresso.model.TipoAtuacao;
import br.ufjf.egresso.persistent.TipoAtuacaoDAO;

public class FbPerfilController {

	private Aluno aluno;
	private AtuacaoBusiness atuacaoBusiness = new AtuacaoBusiness();
	private InteresseBusiness interesseBusiness = new InteresseBusiness();
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
	private Interesse novoInteresse = new Interesse();
	private Interesse interesseEmEdicao;
	private List<Interesse> interesses = new ArrayList<Interesse>();

	@Init
	public void init() {
		String facebookId = (String) Executions.getCurrent().getParameter("id");

		if (facebookId != null) {
			aluno = new AlunoBusiness().getAluno(facebookId);
			podeEditar = aluno.getId() == ((Aluno) Sessions.getCurrent()
					.getAttribute("aluno")).getId();
		} else {

			aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
			podeEditar = true;
		}
		interesses = interesseBusiness.getInteresses(aluno);

		if (interesses == null)
			interesses = new ArrayList<Interesse>();
		List<Atuacao> todasAtuacoes = new AtuacaoBusiness().getPorAluno(aluno);
		if (todasAtuacoes != null)
			for (Atuacao a : todasAtuacoes) {
				if (a.getDataTermino() == null)
					atuacaoAtual = a;
				aluno.setAtuacao(a);
				new AlunoBusiness().editar(aluno);

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
			@BindingParam("editarSalvar") Label lbSalvarEditar,
			@BindingParam("sumir") Vlayout v1,
			@BindingParam("aparecer") Vlayout v2,
			@BindingParam("cancelar") Label lbCancelar,
			@BindingParam("atuacao") Atuacao atuacao) {

		if (!lbCancelar.isVisible()) {
			atuacaoEmEdicao = new Atuacao();
			atuacaoEmEdicao.copy(atuacao);
			lbSalvarEditar.setValue("Confirmar");
			BindUtils.postNotifyChange(null, null, this, "atuacaoEmEdicao");
		} else {
			lbSalvarEditar.setValue("Editar");
			atuacao.copy(atuacaoEmEdicao);
			atuacaoBusiness.salvaOuEdita(atuacao);
			BindUtils.postNotifyChange(null, null, this, "filtraProjetos");
			BindUtils.postNotifyChange(null, null, this, "filtraEmpregos");
			BindUtils.postNotifyChange(null, null, this, "filtraFormacoes");
		}
		v1.setVisible(!v1.isVisible());
		v2.setVisible(!v2.isVisible());
		lbCancelar.setVisible(!lbCancelar.isVisible());
		emEdicao = lbCancelar.isVisible();
		BindUtils.postNotifyChange(null, null, this, "emEdicao");
	}

	@Command
	public void cancelarEdicao(
			@BindingParam("editarSalvar") Label lbSalvarEditar,
			@BindingParam("cancelar") Label lbCancelar,
			@BindingParam("sumir") Vlayout v1,
			@BindingParam("aparecer") Vlayout v2) {
		lbCancelar.setVisible(false);
		lbSalvarEditar.setValue("Editar");
		lbSalvarEditar.setVisible(true);
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

	public Interesse getNovoInteresse() {
		return novoInteresse;
	}

	public void setNovoInteresse(Interesse novoInteresse) {
		this.novoInteresse = novoInteresse;
	}

	public Interesse getInteresseEmEdicao() {
		return interesseEmEdicao;
	}

	public void setInteresseEmEdicao(Interesse interesseEmEdicao) {
		this.interesseEmEdicao = interesseEmEdicao;
	}

	public List<Interesse> getInteresses() {
		return interesses;
	}

	public void setInteresses(List<Interesse> interesses) {
		this.interesses = interesses;
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
	public void mostraDescricao(@BindingParam("label") Label label,
			@BindingParam("img") Image img) {
		if (!label.isVisible()) {
			label.setVisible(true);
			img.setSrc("/img/minimize.jpg");
		} else {
			label.setVisible(false);
			img.setSrc("/img/expandIcon.png");
		}

		BindUtils.postNotifyChange(null, null, this, "img");

	}

	@Command
	public void adicionaAtuacao(@BindingParam("window") Window window,
			@BindingParam("tipo") int tipo) {
		this.limpaAtuacao();
		for (TipoAtuacao t : tipoAtuacao)
			if (t.getId() == tipo) {
				novaAtuacao.setTipoAtuacao(t);
				break;
			}
		window.doModal();
	}

	@Command
	public void voltaTurma() {

		Clients.evalJavaScript("volta()");
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

				limpaAtuacao();
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

	public void limpaAtuacao() {
		novaAtuacao = new Atuacao();
		BindUtils.postNotifyChange(null, null, this, "novaAtuacao");
	}

	public void limpaInteresse() {
		novoInteresse = new Interesse();
		BindUtils.postNotifyChange(null, null, this, "novoInteresse");

	}

	public void notificaEmpregos() {
		BindUtils.postNotifyChange(null, null, this, "filtraEmpregos");
	}

	public void notificaInteresses() {
		BindUtils.postNotifyChange(null, null, this, "interesses");
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
							if (atuacao.getCargo().equals(
									aluno.getAtuacao().getCargo())
									&& atuacao.getTipoAtuacao()
											.equals(aluno.getAtuacao()
													.getTipoAtuacao())
									&& atuacao.getDataInicio().equals(
											aluno.getAtuacao().getDataInicio())

									&& atuacao.getLocal().equals(
											aluno.getAtuacao().getLocal())
									&& atuacao.getAluno().equals(
											aluno.getAtuacao().getAluno())
									&& atuacao.getDescricao().equals(
											aluno.getAtuacao().getDescricao())) {
								aluno.setAtuacao(null);
								new AlunoBusiness().editar(aluno);
							}
							if (atuacaoBusiness.exclui(atuacao)) {
								removeFromList(atuacao);

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
		this.limpaInteresse();
		window.doModal();
	}

	@Command
	public void submitInteresses(@BindingParam("window") Window window) {
		novoInteresse.setAluno(aluno);
		interesseBusiness.salvar(novoInteresse);
		interesses.add(novoInteresse);
		notificaInteresses();
		window.setVisible(false);
		this.limpaInteresse();

	}

	@Command
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void excluirInteresses(
			@BindingParam("interesse") final Interesse interesse) {
		Messagebox.show("Você tem certeza que deseja excluir a atuação "
				+ interesse.getInteresse() + "?", "Confirmação", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {

							if (interesseBusiness.exclui(interesse)) {
								interesses.remove(interesse);
								notificaInteresses();

							} else {
								String errorMessage = "O interesse não pôde ser excluído.\n";
								Messagebox.show(errorMessage, "Erro",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});

	}

	@Command
	public void editarInteresse(
			@BindingParam("editarSalvar") Label lbSalvarEditar,
			@BindingParam("sumir") Vlayout v1,
			@BindingParam("aparecer") Vlayout v2,
			@BindingParam("cancelar") Label lbCancelar,
			@BindingParam("interesse") Interesse interesse) {

		if (!lbCancelar.isVisible()) {
			interesseEmEdicao = new Interesse();
			interesseEmEdicao.copy(interesse);
			lbSalvarEditar.setValue("Confirmar");
			BindUtils.postNotifyChange(null, null, this, "interesseEmEdicao");
		} else {
			lbSalvarEditar.setValue("Editar");
			interesse.copy(interesseEmEdicao);
			interesseBusiness.salvaOuEdita(interesse);
			notificaInteresses();
		}
		v1.setVisible(!v1.isVisible());
		v2.setVisible(!v2.isVisible());
		lbCancelar.setVisible(!lbCancelar.isVisible());
		emEdicao = lbCancelar.isVisible();
		BindUtils.postNotifyChange(null, null, this, "emEdicao");
	}

	@Command
	public void cancelarEdicaoInteresse(
			@BindingParam("editarSalvar") Label lbSalvarEditar,
			@BindingParam("cancelar") Label lbCancelar,
			@BindingParam("sumir") Vlayout v1,
			@BindingParam("aparecer") Vlayout v2) {
		lbCancelar.setVisible(false);
		lbSalvarEditar.setValue("Editar");
		lbSalvarEditar.setVisible(true);
		v1.setVisible(!v1.isVisible());
		v2.setVisible(!v2.isVisible());
		interesseEmEdicao = null;

		emEdicao = false;
		BindUtils.postNotifyChange(null, null, this, "emEdicao");
	}

}