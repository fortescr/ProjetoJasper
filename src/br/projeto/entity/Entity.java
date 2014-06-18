package br.projeto.entity;

import java.io.Serializable;

public interface Entity <ID extends Number> extends Serializable {
	
	public ID getId();
	
	public void setId(ID id);

}
