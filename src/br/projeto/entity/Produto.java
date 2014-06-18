package br.projeto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Produto extends DomainEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8773011009394684867L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length=100)
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
