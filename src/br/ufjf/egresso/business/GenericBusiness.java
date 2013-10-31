package br.ufjf.egresso.business;

public class GenericBusiness {
		public boolean stringPreenchida(String conteudo) {
			if(conteudo==null)
				return false;
			else if (conteudo.trim().length()==0)
				return false;
			else return true;
		}
}
