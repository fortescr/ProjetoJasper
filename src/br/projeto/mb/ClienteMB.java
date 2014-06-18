package br.projeto.mb;

import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import br.projeto.anotacao.AnotacaoBase;
import br.projeto.entity.Cliente;
import br.projeto.entity.Entity;
import br.projeto.relatorio.RelatorioUtil;

@ManagedBean
@ViewScoped
@AnotacaoBase(baseEntity=Cliente.class)
public class ClienteMB extends BaseMB {
	
	@Override
	public Entity getBaseEntity() {
		return (Cliente) baseEntity;
	}
	
	public StreamedContent getArquivoRetorno(){
		RelatorioUtil rel = new RelatorioUtil();
		HashMap parametros = new HashMap();
		parametros.put("teste", "teste");
		arquivoRetorno = rel.geraRelatorio(tipoRelatorio, "Cherry_1", "clientes_1", parametros);
		
		return arquivoRetorno;
	}

}
