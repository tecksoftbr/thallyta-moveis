package views.gerenciamento.ger_contas_pagar;

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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GerarPDF {

	private double valorTotal = 0;

	public GerarPDF(ArrayList<modelo.ContasPagar> contas, String caminho) {

		// criação Do Objeto Documento ...

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream(caminho
					+ ".pdf"));

			document.open();

			Font fonteTitulo = new Font(FontFamily.COURIER, 30, Font.BOLD);
			Font fonteSubtitulo = new Font(FontFamily.COURIER, 15, Font.NORMAL);

			Paragraph titulo = new Paragraph("Relatório De Contas a Pagar",
					fonteTitulo);

			titulo.setAlignment(Element.ALIGN_CENTER);

			Paragraph subtitulo = new Paragraph("Thallyta Móveis",
					fonteSubtitulo);

			subtitulo.setAlignment(Element.ALIGN_CENTER);
			subtitulo.setSpacingAfter(20);

			document.add(titulo);
			document.add(subtitulo);

			Font f = new Font(FontFamily.COURIER, 6, Font.NORMAL);
			Font f2 = new Font(FontFamily.COURIER, 8, Font.BOLD);

			PdfPTable table = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f });

			table.addCell(new Paragraph("Tipo", f2));
			table.addCell(new Paragraph("Número Do Documento", f2));
			table.addCell(new Paragraph("Data De Entrada", f2));
			table.addCell(new Paragraph("Data De Vencimento", f2));
			table.addCell(new Paragraph("Valor", f2));
			table.addCell(new Paragraph("Origem", f2));

			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < contas.size(); i++) {

				table.addCell(new Paragraph(contas.get(i).getTipoConta(), f));

				table.addCell(new Paragraph(contas.get(i).getNumeroDocumento(),
						f));

				table.addCell(new Paragraph(contas.get(i).getDataEntrada(), f));

				table.addCell(new Paragraph(contas.get(i).getDataVencimento(),
						f));

				table.addCell(new Paragraph(String.valueOf(contas.get(i)
						.getValor()), f));

				table.addCell(new Paragraph(contas.get(i).getOrigem(), f));

				valorTotal = valorTotal + contas.get(i).getValor();

			}

			document.add(table);

			// Adicionando Informações Gerais ...

			Paragraph resumoFinal = new Paragraph(
					"Total De Contas Cadastradas No Sistema: " + contas.size()
							+ ", Valor Total De Todos Os Pagamentos: R$ "
							+ valorTotal + " " + real(), f);

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

	private String real() {

		if (valorTotal == 1.0) {

			return "real";

		}

		else if (valorTotal == 0.0) {

			return "";

		}

		else {

			return "reais";

		}

	}

}