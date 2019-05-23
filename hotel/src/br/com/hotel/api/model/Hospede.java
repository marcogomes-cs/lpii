package br.com.hotel.api.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType; 

@XmlRootElement
@XmlType(name="Hospede")
public class Hospede extends Pessoa {
	private static final long serialVersionUID = 1L;
	private int cod_hospede;
	
	public Hospede() {}

	public Hospede(String nome, String endereco, long cpf, long rg, long telefone, String email) {
		super(nome, endereco, cpf, rg, telefone, email);
	}
	
	@XmlElement
	public int getCod_hospede() {
		return cod_hospede;
	}
	public void setCod_hospede(int value) {
		this.cod_hospede = value;
	}
}
