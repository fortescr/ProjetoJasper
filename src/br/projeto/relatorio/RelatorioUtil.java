package br.projeto.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class RelatorioUtil {

	public static final int PDF = 1;
	public static final int EXCEL = 2;
	public static final int HTML = 3;
	public static final int OPEN_OFFICE = 4;

	public StreamedContent geraRelatorio(Integer tipoRelatorio, String nomeRelatorioJasper, 
			String nomeRelatorioSaida, HashMap parametros){
		StreamedContent arquivoRetorno = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("relatorios");
			String caminhoArquivoJasper = caminhoRelatorio + File.separator + nomeRelatorioJasper+".jasper";
			String caminhoArquivoRelatorio = null;

			File arquivoGerado = null;

			JasperReport relatorioJasper = 
					(JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);

			JasperPrint impressoraJasper = 
					JasperFillManager.fillReport(relatorioJasper, 
							parametros, getConexao());

			JRExporter tipoArquivoExportado = null;
			String extensaoArquivo = "";

			switch(tipoRelatorio){

			case PDF :
				tipoArquivoExportado = new JRPdfExporter();
				extensaoArquivo = "pdf";
				break;

			case HTML :
				tipoArquivoExportado = new JRHtmlExporter();
				extensaoArquivo = "html";
				break;

			case EXCEL :
				tipoArquivoExportado = new JRXlsExporter();
				extensaoArquivo = "xls";
				break;

			case OPEN_OFFICE :
				tipoArquivoExportado = new JROdsExporter();
				extensaoArquivo = "ods";
				break;

			default :
				tipoArquivoExportado = new JRPdfExporter();
				extensaoArquivo = "pdf";
				break;
			}

			caminhoArquivoRelatorio = caminhoRelatorio + 
					File.separator + nomeRelatorioSaida +"."+extensaoArquivo;

			arquivoGerado = new File(caminhoArquivoRelatorio);
			tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
			tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
			tipoArquivoExportado.exportReport();
			arquivoGerado.deleteOnExit();
			InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
			arquivoRetorno = new DefaultStreamedContent(conteudoRelatorio, "application/"+extensaoArquivo, nomeRelatorioSaida+"."+ extensaoArquivo);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return arquivoRetorno;
	}

	private Connection getConexao(){

		Connection connection = null; 
		try { 
			String driverName = "com.mysql.jdbc.Driver"; 
			Class.forName(driverName); 
			String serverName = "localhost"; 
			String mydatabase ="aula";  
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 
			String username = "root"; 
			String password = "bitnamic"; 
			connection = DriverManager.getConnection(url, username, password); 
			
			if (connection != null) { 
				System.out.println("STATUS--->Conectado com sucesso!"); 
			} else { 
				System.out.println("STATUS--->Não foi possivel realizar conexão"); 
			}
			return connection; 
			
		} catch (ClassNotFoundException e) { 
			//Driver não encontrado 
			System.out.println("O driver expecificado nao foi encontrado."); 
			return null; 
		} catch (SQLException e) { 
			//Não conseguindo se conectar ao banco 
			System.out.println("Nao foi possivel conectar ao Banco de Dados."); 
			return null; 
		} 
	}

}
