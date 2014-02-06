package views.gerenciamento.ger_usuarios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import views.principais.tela_de_erro.ErroEncontrado;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.Usuario;

public class GerarPDF {

	public GerarPDF(ArrayList<Usuario> usuarios, String caminho) {

		// criação Do Objeto Documento ...

		Document document = new Document(PageSize.A4, 0, 0, 10, 72);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(caminho
					+ ".pdf"));

			document.open();

			Font fonteTitulo = new Font(FontFamily.COURIER, 28, Font.BOLD);
			Font fonteSubtitulo = new Font(FontFamily.COURIER, 15, Font.NORMAL);

			Paragraph titulo = new Paragraph(
					"Relatório De Usuários Cadastrados", fonteTitulo);

			titulo.setAlignment(Element.ALIGN_CENTER);

			Paragraph subtitulo = new Paragraph("Thallyta Móveis",
					fonteSubtitulo);

			subtitulo.setAlignment(Element.ALIGN_CENTER);
			subtitulo.setSpacingAfter(20);

			document.add(titulo);
			document.add(subtitulo);

			Font f = new Font(FontFamily.COURIER, 6, Font.NORMAL);
			Font f2 = new Font(FontFamily.COURIER, 7, Font.BOLD);

			PdfPTable table = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f, 1.40f });

			table.addCell(new Paragraph("Nome Completo", f2));
			table.addCell(new Paragraph("Data De Cadastro", f2));
			table.addCell(new Paragraph("Apelido", f2));
			table.addCell(new Paragraph("Senha", f2));
			table.addCell(new Paragraph("Pergunta Secreta", f2));
			table.addCell(new Paragraph("Resposta Secreta", f2));
			table.addCell(new Paragraph("Sobre Mim", f2));

			table.setWidthPercentage(95.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < usuarios.size(); i++) {

				table.addCell(new Paragraph(String.valueOf(usuarios.get(i)
						.getNomeCompleto()), f));

				table.addCell(new Paragraph(
						usuarios.get(i).getDataDeCadastro(), f));

				table.addCell(new Paragraph(usuarios.get(i).getApelido(), f));

				table.addCell(new Paragraph("************", f));

				table.addCell(new Paragraph(String.valueOf(usuarios.get(i)
						.getPerguntaSecreta()), f));

				table.addCell(new Paragraph(String.valueOf("************"), f));

				table.addCell(new Paragraph(String.valueOf(usuarios.get(i)
						.getSobreMim()), f));

			}

			document.add(table);

			// Adicionando Informações Gerais ...

			Paragraph resumoFinal = new Paragraph(
					"Total De Usuarios Cadastrados No Sistema: "
							+ usuarios.size(), f);

			resumoFinal.setSpacingBefore(10);
			resumoFinal.setAlignment(Element.ALIGN_CENTER);

			document.add(resumoFinal);

			// Adicionando Rodapé ...

			Font fonteRodape = new Font(FontFamily.COURIER, 12, Font.BOLD);

			Paragraph rodape = new Paragraph("Relatório Gerado Em: "
					+ pegandoDataDoSistema(), fonteRodape);

			rodape.setAlignment(Element.ALIGN_CENTER);
			document.add(rodape);

			document.close();

		}

		catch (DocumentException de) {

			new ErroEncontrado();

		}

		catch (IOException ioe) {

			new ErroEncontrado();

		}

	}

	private String pegandoDataDoSistema() {
		String s = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		s = sdf.format(new Date());

		return s;

	}

}