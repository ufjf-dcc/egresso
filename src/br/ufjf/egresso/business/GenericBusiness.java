package br.ufjf.egresso.business;

import java.security.NoSuchAlgorithmException;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

public class GenericBusiness {

	public boolean stringPreenchida(String conteudo) {
		return !(conteudo == null || conteudo.trim().length() == 0);
	}

	public String encripta(String expressao) {
		try {
			AbstractChecksum checksum = null;
			checksum = JacksumAPI.getChecksumInstance("whirlpool-1");
			checksum.update(expressao.getBytes());
			return checksum.getFormattedValue();
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
			return null;
		}
	}
}
