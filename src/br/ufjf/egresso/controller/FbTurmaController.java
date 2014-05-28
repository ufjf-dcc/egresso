package br.ufjf.egresso.controller;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.PostagemBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Postagem;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.utils.ConfHandler;
import br.ufjf.egresso.utils.FileManager;

/**
 * Classe para controlar a página /fb/turma.zul
 * 
 * @author Jorge Augusto da Silva Moreira
 * 
 */
public class FbTurmaController {

	private List<Aluno> alunos, filtraAlunos;
	private List<List<Aluno>> linhas;
	private List<String> semestres;
	private List<Turma> turmas;
	private TurmaBusiness turmaBusiness = new TurmaBusiness();
	private String pesquisa, descricao, imgExtensao;
	private Turma turma;
	private List<Postagem> postagensTurma;
	private int largura, altura;
	private InputStream imgPostagem;

	@Init
	public void init() {

		turma = ((Aluno) Sessions.getCurrent().getAttribute("aluno"))
				.getTurma();
		turmas = turmaBusiness.getTodas();

		semestres = new ArrayList<String>();
		for (Turma t : turmas) {
			semestres.add(t.getAno() + " " + t.getSemestre() + "º semestre");
		}

		descricao = "Turma do " + turma.getSemestre() + "º semestre de "
				+ turma.getAno();
		alunos = new AlunoBusiness().getAlunos(turma);
		filtraAlunos = alunos;
		postagensTurma = new PostagemBusiness().getPostagens(turma);
	}
	
	public int getAltura(){
		return altura;
	}

	@Command
	public void montaTabela(@BindingParam("event") ClientInfoEvent evt) {
		if (evt != null){
			largura = evt.getDesktopWidth();
			altura = evt.getDesktopHeight() - 180;
			
			BindUtils.postNotifyChange(null, null, this, "largura");
			BindUtils.postNotifyChange(null, null, this, "altura");
		}		
		int inseridos = 0;
		linhas = new ArrayList<List<Aluno>>();

		List<Aluno> linha = new ArrayList<Aluno>();

		for (Aluno a : filtraAlunos) {
			if ((largura - 565) / 180 < (inseridos + 1)) {
				linhas.add(linha);
				inseridos = 0;
				linha = new ArrayList<Aluno>();
			}
			linha.add(a);
			inseridos++;
		}

		if (linha.size() > 0)
			linhas.add(linha);

		BindUtils.postNotifyChange(null, null, this, "postagensTurma");
		BindUtils.postNotifyChange(null, null, this, "linhas");
		Clients.evalJavaScript("fadeIn()");
	}

	public List<List<Aluno>> getLinhas() {
		return linhas;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<String> getSemestres() {
		return semestres;
	}

	public List<Postagem> getPostagensTurma() {
		return postagensTurma;
	}

	public Turma getTurma() {
		return turma;
	}

	public int getLargura() {
		return largura;
	}

	@Command
	@NotifyChange({ "filtraAlunos", "emptyMessage" })
	public void pesquisar() {
		List<Aluno> resultados = new ArrayList<Aluno>();
		for (Aluno aluno : alunos)
			if (aluno.getNome().trim().toLowerCase()
					.contains(pesquisa.trim().toLowerCase()))
				resultados.add(aluno);
		filtraAlunos = resultados;
		montaTabela(null);
	}

	@Command("limparPesquisa")
	public void limparPesquisa() {
		filtraAlunos = alunos;
		montaTabela(null);
	}

	@Command
	public void verPerfil(@BindingParam("facebookId") String facebookId) {
		Executions.sendRedirect("perfil.zul?id=" + facebookId);
	}

	@Command
	public void convidar() {
		Executions.sendRedirect("convida.zul");
	}

	@Command
	public void trocaTurma(@BindingParam("turma") String turmaDesc) {
		Clients.evalJavaScript("fadeOut()");
		turma = turmaBusiness.getTurma(Integer.parseInt(turmaDesc.substring(0,
				turmaDesc.indexOf(" "))), Integer.parseInt(turmaDesc.substring(
				turmaDesc.indexOf("º") - 1, turmaDesc.indexOf("º"))));
		alunos = new AlunoBusiness().getAlunos(turma);
		filtraAlunos = alunos;
		postagensTurma = new PostagemBusiness().getPostagens(turma);
		montaTabela(null);
	}

	/**
	 * Retorna para a página zul a descrição da data da {@link Postagem}.
	 * 
	 * @param dataHora
	 *            Data e hora da publicação da {@link Postagem}.
	 * @param label
	 *            {@link Label} que mostrará a data.
	 */
	@Command
	public void descricaoDataPostagem(
			@BindingParam("dataHora") Timestamp dataHora,
			@BindingParam("label") Label label) {
		if (dataHora
				.toString()
				.substring(0, 11)
				.equals(new Timestamp(new Date().getTime()).toString()
						.substring(0, 11)))
			label.setValue("Hoje, "
					+ new SimpleDateFormat("HH:mm").format(dataHora));
		else
			label.setValue(new SimpleDateFormat("MM/dd/yyyy, HH:mm")
					.format(dataHora));
	}

	/**
	 * Salva a {@link Postagem} criada pelo usuário e atualiza a lista.
	 * 
	 * @param texto
	 *            Texto escrito pelo usuário.
	 * @param imagem
	 *            Endereço da imagem.
	 * @param privado
	 *            Se será ou não exibida para todos os ex-alunos.
	 */
	@Command
	public void postar(@BindingParam("texto") String texto,
			@BindingParam("privado") boolean privado) {
		String imagem = null;
		if (imgPostagem != null)
			imagem = FileManager.saveFileInputSream(imgPostagem, imgExtensao);
		if (imgPostagem == null || (imgPostagem != null && imagem != null)) {
			Postagem postagem = new Postagem((Aluno) Sessions.getCurrent()
					.getAttribute("aluno"), turma, privado, texto, imagem,
					new Timestamp(new Date().getTime()));
			imgPostagem = null;
			if (new PostagemBusiness().salvar(postagem)) {
				postagensTurma.add(0, postagem);
				BindUtils.postNotifyChange(null, null, this, "postagensTurma");
				return;
			}
		}

		Messagebox.show(
				"Não foi possível publicar, tente novamente mais tarde.",
				"Erro", Messagebox.OK, Messagebox.ERROR);

	}

	@Command
	public void upload(@BindingParam("evt") UploadEvent evt) {
		String[] extensoes = new String[] { "jpg", "png", "gif" };

		for (String s : extensoes)
			if (evt.getMedia().getName().contains(s)) {
				imgPostagem = evt.getMedia().getStreamData();
				imgExtensao = s;
				return;
			}

		Messagebox.show("Este não é um arquivo de imagem.", "Formato inválido",
				Messagebox.OK, Messagebox.INFORMATION);
		imgPostagem = null;
	}

	@Command
	public void verImagem(@BindingParam("window") Window window,
			@BindingParam("imgSrc") String imgSrc) {

		((Image) window.getChildren().get(0)).setSrc(ConfHandler
				.getConf("FILE.PATH") + imgSrc);
		window.doModal();
	}

}