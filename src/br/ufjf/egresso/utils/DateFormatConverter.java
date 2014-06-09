package br.ufjf.egresso.utils;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zul.Label;

/**Classe para converter datas obtidas do banco para o formato dd/MM/yyyy
 * 
 * @author Jorge Augusto da Silva Moreira
 *
 */
public class DateFormatConverter implements Converter<String, String, Label> {

	@Override
	public String coerceToUi(String beanProp, Label component, BindContext ctx) {
		String[] data = beanProp.split("-");
		try {
			return data[2] + "/" + data[1] + "/" + data[0];
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}

	@Override
	public String coerceToBean(String compAttr, Label component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
