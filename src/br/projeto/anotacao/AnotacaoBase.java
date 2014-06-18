package br.projeto.anotacao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import br.projeto.entity.Entity;


@Retention(RetentionPolicy.RUNTIME)
public @interface AnotacaoBase {
	
	public Class<? extends Entity> baseEntity() default Entity.class;

}
