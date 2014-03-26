package br.ufjf.egresso.utils;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zul.Label;

public class DateFormatConverter implements Converter<String, String, Label> {

	@Override
	public String coerceToUi(String beanProp, Label component, BindContext ctx) {
		try {
			if (beanProp == "")
				return beanProp;
			String[] data = beanProp.split("-");
			return data[2] + "/" + data[1] + "/" + data[0];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Erro ao formatar string para data");
			return "";
		}
	}

	@Override
	public String coerceToBean(String compAttr, Label component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
