package views.gerenciamento.ger_fornecedores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.Fornecedor;

import views.principais.tela_de_erro.ErroEncontrado;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GerarPDF {

	public GerarPDF(ArrayList<Fornecedor> fornecedores, String caminho) {

		// criação Do Objeto Documento ...

		Document document = new Document(PageSize.A4, 0, 0, 10, 72);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(caminho
					+ ".pdf"));

			document.open();

			Font fonteTitulo = new Font(FontFamily.COURIER, 20, Font.BOLD);
			Font fonteSubtitulo = new Font(FontFamily.COURIER, 13, Font.NORMAL);

			Paragraph titulo = new Paragraph(
					"Relatório De Fornecedores Cadastrados", fonteTitulo);

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
					1.40f, 1.40f, 1.40f, 1.40f, 1.40f, 1.40f, 1.40f, 1.40f,
					1.40f });

			table.addCell(new Paragraph("Código", f2));
			table.addCell(new Paragraph("Empresa", f2));
			table.addCell(new Paragraph("CEP", f2));
			table.addCell(new Paragraph("Rua", f2));
			table.addCell(new Paragraph("Bairro", f2));
			table.addCell(new Paragraph("Cidade", f2));
			table.addCell(new Paragraph("Estado", f2));
			table.addCell(new Paragraph("Telefone", f2));
			table.addCell(new Paragraph("Fax", f2));
			table.addCell(new Paragraph("E-mail", f2));
			table.addCell(new Paragraph("Site", f2));
			table.addCell(new Paragraph("Data De Cadastro", f2));

			table.setWidthPercentage(98.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < fornecedores.size(); i++) {

				table.addCell(new Paragraph(String.valueOf(fornecedores.get(i)
						.getCodigo()), f));

				table.addCell(new Paragraph(fornecedores.get(i).getEmpresa(), f));

				table.addCell(new Paragraph(fornecedores.get(i).getCep(), f));

				table.addCell(new Paragraph(fornecedores.get(i).getRua(), f));

				table.addCell(new Paragraph(String.valueOf(fornecedores.get(i)
						.getBairro()), f));

				table.addCell(new Paragraph(String.valueOf(fornecedores.get(i)
						.getCidade()), f));

				table.addCell(new Paragraph(String.valueOf(fornecedores.get(i)
						.getEstado()), f));

				table.addCell(new Paragraph(fornecedores.get(i).getTelefone(),
						f));

				table.addCell(new Paragraph(fornecedores.get(i)
						.getObservacoes(), f));

				table.addCell(new Paragraph(fornecedores.get(i).getFax(), f));

				table.addCell(new Paragraph(fornecedores.get(i).getEmail(), f));

				table.addCell(new Paragraph(fornecedores.get(i).getPaginaWeb(),
						f));

				table.addCell(new Paragraph(fornecedores.get(i)
						.getDataDeCadastro(), f));

			}

			document.add(table);

			PdfPTable table2 = new PdfPTable(new float[] { 1.40f, 1.40f });

			table2.setSpacingBefore(10);

			table2.addCell(new Paragraph("Empresa", f2));
			table2.addCell(new Paragraph("Observações", f2));

			table2.setWidthPercentage(98.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < fornecedores.size(); i++) {

				table2.addCell(new Paragraph(String.valueOf(fornecedores.get(i)
						.getEmpresa()), f));

				table2.addCell(new Paragraph(String.valueOf(fornecedores.get(i)
						.getObservacoes()), f));

			}

			document.add(table2);

			// Adicionando Informações Gerais ...

			Paragraph resumoFinal = new Paragraph(
					"Total De Produtos Cadastrados No Sistema: "
							+ fornecedores.size(), f);

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