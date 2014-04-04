package br.ufjf.egresso.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * Classe para retornar configurações-padrão.
 * 
 * @author Matheus Marques
 **/
public class ConfHandler {
	private static ConfHandler instancia = null;
	private HashMap<String, String> configuracoes;

	private ConfHandler() {
		try {
			InputStream inputStream = getClass().getResourceAsStream(
					"config.txt");
			String arquivo = IOUtils.toString(inputStream);
			configuracoes = new HashMap<String, String>();
			Pattern patternConf = Pattern.compile("^([A-Z]+\\.[A-Z]+) = (.*)$",
					Pattern.MULTILINE);
			Matcher conf = patternConf.matcher(arquivo);
			while (conf.find()) {
				configuracoes.put(conf.group(1), conf.group(2));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o valor do arquivo de configuração que corresponde à chave dada.
	 * 
	 * @param chave
	 *            Nome da chave
	 * @return {@link String} com o valor da chave
	 */
	public static String getConf(String chave) {
		if (instancia == null)
			instancia = new ConfHandler();
		return instancia.configuracoes.get(chave);
	}
}
