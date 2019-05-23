package br.com.hotel.api.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Hospede.class})
public abstract class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;
	private String endereco;
	private long cpf;
	private long rg;
	private long telefone;
	private String email;
	
	public Pessoa() {}
	
	public Pessoa(String nome, String endereco, long cpf, long rg, long telefone, String email) {
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.email = email;
	}
	@XmlElement
	public String getNome() {
		return nome;
	}
	public void setNome(String value) {
		this.nome = value;
	}
	@XmlElement
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String value) {
		this.endereco = value;
	}
	@XmlElement
	public long getCpf() {
		return cpf;
	}
	public void setCpf(long value) {
		this.cpf = value;
	}
	@XmlElement
	public long getRg() {
		return rg;
	}
	public void setRg(long value) {
		this.rg = value;
	}
	@XmlElement
	public long getTelefone() {
		return telefone;
	}
	public void setTelefone(long value) {
		this.telefone = value;
	}
	@XmlElement
	public String getEmail() {
		return email;
	}
	public void setEmail(String value) {
		this.email = value;
	}
}