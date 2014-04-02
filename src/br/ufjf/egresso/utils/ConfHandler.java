package br.ufjf.egresso.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * Classe para retornar configurações-padrão
 * 
 * @author Matheus Marques         
 **/
public class ConfHandler {
	private static ConfHandler instance = null;
	private HashMap<String, String> confs;

	private ConfHandler() {
		try {
			InputStream inputStream = getClass().getResourceAsStream(
					"config.txt");
			String arquivo = IOUtils.toString(inputStream);
			confs = new HashMap<String, String>();
			Pattern patternConf = Pattern.compile("^([A-Z]+\\.[A-Z]+) = (.*)$",
					Pattern.MULTILINE);
			Matcher conf = patternConf.matcher(arquivo);
			while (conf.find()) {
				confs.put(conf.group(1), conf.group(2));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o valor de determinada chave do arquivo de configuração
	 * @param key Nome da chave
	 * @return {@link String} com o valor da chave
	 */
	public static String getConf(String key) {
		if (instance == null)
			instance = new ConfHandler();
		return instance.confs.get(key);
	}
}
