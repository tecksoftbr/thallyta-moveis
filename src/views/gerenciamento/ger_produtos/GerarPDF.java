package views.gerenciamento.ger_produtos;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.UnsupportedLookAndFeelException;

import views.principais.tela_de_erro.ErroEncontrado;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GerarPDF {

	public GerarPDF(ArrayList<modelo.Produto> produtos, String caminho)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// criação Do Objeto Documento ...

		Document document = new Document(PageSize.A4, 0, 0, 10, 72);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(caminho
					+ ".pdf"));

			document.open();

			Font fonteTitulo = new Font(FontFamily.COURIER, 28, Font.BOLD);
			Font fonteSubtitulo = new Font(FontFamily.COURIER, 15, Font.NORMAL);

			Paragraph titulo = new Paragraph(
					"Relatório De Produtos Cadastrados", fonteTitulo);

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
			table.addCell(new Paragraph("Descrição", f2));
			table.addCell(new Paragraph("Categoria", f2));
			table.addCell(new Paragraph("Data De Cadastro", f2));
			table.addCell(new Paragraph("Preço De Compra", f2));
			table.addCell(new Paragraph("Preço De Venda", f2));
			table.addCell(new Paragraph("Quantidade", f2));
			table.addCell(new Paragraph("Marca", f2));
			table.addCell(new Paragraph("Observações", f2));
			table.addCell(new Paragraph("Modelo", f2));
			table.addCell(new Paragraph("Número De Série", f2));
			table.addCell(new Paragraph("Cor", f2));

			table.setWidthPercentage(95.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < produtos.size(); i++) {

				table.addCell(new Paragraph(String.valueOf(produtos.get(i)
						.getCodigo()), f));

				table.addCell(new Paragraph(produtos.get(i).getDescricao(), f));

				table.addCell(new Paragraph(produtos.get(i).getCategoria(), f));

				table.addCell(new Paragraph(
						produtos.get(i).getDataDeCadastro(), f));

				table.addCell(new Paragraph(String.valueOf(produtos.get(i)
						.getPrecoDeCompra()), f));

				table.addCell(new Paragraph(String.valueOf(produtos.get(i)
						.getPrecoDeVenda()), f));

				table.addCell(new Paragraph(String.valueOf(produtos.get(i)
						.getQuantidade()), f));

				table.addCell(new Paragraph(produtos.get(i).getMarca(), f));

				table.addCell(new Paragraph(produtos.get(i).getObservacoes(), f));

				table.addCell(new Paragraph(produtos.get(i).getModelo(), f));

				table.addCell(new Paragraph(produtos.get(i).getNumeroSerie(), f));

				table.addCell(new Paragraph(produtos.get(i).getCor(), f));

			}

			document.add(table);

			// Adicionando Informações Gerais ...

			Paragraph resumoFinal = new Paragraph(
					"Total De Produtos Cadastrados No Sistema: "
							+ produtos.size(), f);

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

		catch (Exception e) {

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