package br.com.hotel.api.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Quarto implements Serializable{
	private static final long serialVersionUID = 1L;
	private int numero;
	private int capacidade;
	private Boolean ocupado;
	private Boolean reservado;
	
	@XmlElement
	public int getNumero() {
		return numero;
	}
	public void setNumero(int value) {
		this.numero = value;
	}
	@XmlElement
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int value) {
		this.capacidade = value;
	}
	@XmlElement
	public Boolean getOcupado() {
		return ocupado;
	}
	public void setOcupado(Boolean value) {
		this.ocupado = value;
	}
	@XmlElement
	public Boolean getReservado() {
		return reservado;
	}
	public void setReservado(Boolean value) {
		this.reservado = value;
	}
}
