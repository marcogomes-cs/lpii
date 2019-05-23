package br.com.hotel.api.model;

import java.util.ArrayList;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reserva implements Serializable{
	private static final long serialVersionUID = 1L;
	private int cod_reserva;
	private ArrayList<Hospede> hospedes;
	private Quarto quarto;
	private Boolean efetiva;
	private Boolean cancelada;
	private String checkin;
	private String checkout;
	
	@XmlElement
	public int getCod_reserva() {
		return cod_reserva;
	}
	public void setCod_reserva(int value) {
		this.cod_reserva = value;
	}
	@XmlElement
	public ArrayList<Hospede> getHospedes() {
		return hospedes;
	}
	public void setHospedes(ArrayList<Hospede> value) {
		this.hospedes = value;
	}
	@XmlElement
	public Quarto getQuarto() {
		return quarto;
	}
	public void setQuarto(Quarto value) {
		this.quarto = value;
	}
	@XmlElement
	public Boolean getEfetiva() {
		return efetiva;
	}
	public void setEfetiva(Boolean value) {
		this.efetiva = value;
	}
	@XmlElement
	public Boolean getCancelada() {
		return cancelada;
	}
	public void setCancelada(Boolean value) {
		this.cancelada = value;
	}
	@XmlElement
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String value) {
		this.checkin = value;
	}
	@XmlElement
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String value) {
		this.checkout = value;
	}
}
