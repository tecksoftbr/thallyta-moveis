package views.vendas.realizar_venda;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import modelo.Produto;

public class GerarComprovante {

	public GerarComprovante(int codigoCliente, String nomeCliente,
			String ruaCliente, String numeroCliente, String bairro,
			String complemento, String cidade, String estado, String cep,
			String telefone01, String telefone02, String email,
			ArrayList<Produto> produtosCliente, String formaPagamento,
			String vezesPagamento, String permissaoEntrega,
			String precoEntrega, String dataEntrega, String descontoCompra,
			String valorParcela, double valorTotalCompra, String dataVenda,
			String caminho) {

		// criação Do Objeto Documento ...

		Document document = new Document(PageSize.A4, 0, 0, 10, 72);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(caminho
					+ ".pdf"));

			document.open();

			Font fonteProdutos = new Font(FontFamily.COURIER, 5, Font.NORMAL);

			Font fonteProdutosTítulo = new Font(FontFamily.COURIER, 5,
					Font.BOLD);

			Font fonteInformacoesCompraFont = new Font(FontFamily.COURIER, 7,
					Font.BOLD);

			// ------------------------------------------------------------------------------------------------------------------------------

			Font fonteTitulo = new Font(FontFamily.COURIER, 14, Font.BOLD);

			Paragraph tituloPrincipal = new Paragraph(
					"Comprovante De Venda - Thallyta Móveis", fonteTitulo);

			tituloPrincipal.setIndentationLeft(15);
			tituloPrincipal.setIndentationRight(15);
			tituloPrincipal.setAlignment(Element.ALIGN_CENTER);

			document.add(tituloPrincipal);

			// ------------------------------------------------------------------------------------------------------------------------------

			Font fonteInformacoesLoja = new Font(FontFamily.COURIER, 6,
					Font.NORMAL);

			PdfPTable informacoesDaLoja = new PdfPTable(new float[] { 1.40f,
					1.40f });

			informacoesDaLoja.addCell(new Paragraph(
					"Thallyta Móveis - Requinte e Bom Gosto",
					fonteInformacoesLoja));

			informacoesDaLoja
					.addCell(new Paragraph(
							"Rua: Cordeiro De Farias, 27, Centro",
							fonteInformacoesLoja));

			informacoesDaLoja.addCell(new Paragraph("Cidade: Cupira/PE",
					fonteInformacoesLoja));

			informacoesDaLoja.addCell(new Paragraph("Telefone: (81) 3738-1025",
					fonteInformacoesLoja));

			informacoesDaLoja.setWidthPercentage(95.0f);
			informacoesDaLoja.setHorizontalAlignment(Element.ALIGN_CENTER);
			informacoesDaLoja.setSpacingBefore(20);

			document.add(informacoesDaLoja);

			// ------------------------------------------------------------------------------------------------------------------------------

			Paragraph informacoesSobreCompra = new Paragraph(
					"Informações Sobre a Venda:", fonteInformacoesCompraFont);

			informacoesSobreCompra.setIndentationLeft(15);
			informacoesSobreCompra.setIndentationRight(15);
			informacoesSobreCompra.setSpacingBefore(10);

			document.add(informacoesSobreCompra);

			PdfPTable table = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f, 1.40f, 1.40f });

			table.addCell(new Paragraph("Código", fonteProdutosTítulo));
			table.addCell(new Paragraph("Descrição", fonteProdutosTítulo));
			table.addCell(new Paragraph("Preço - UN", fonteProdutosTítulo));
			table.addCell(new Paragraph("Quantidade", fonteProdutosTítulo));
			table.addCell(new Paragraph("Marca", fonteProdutosTítulo));
			table.addCell(new Paragraph("Modelo", fonteProdutosTítulo));
			table.addCell(new Paragraph("Número De Série", fonteProdutosTítulo));
			table.addCell(new Paragraph("Cor", fonteProdutosTítulo));

			for (int i = 0; i < produtosCliente.size(); i++) {

				table.addCell(new Paragraph(""
						+ produtosCliente.get(i).getCodigo(), fonteProdutos));

				table.addCell(new Paragraph(produtosCliente.get(i)
						.getDescricao(), fonteProdutos));

				table.addCell(new Paragraph(""
						+ produtosCliente.get(i).getPrecoDeVenda(),
						fonteProdutos));

				table.addCell(new Paragraph(""
						+ produtosCliente.get(i).getQuantidade(), fonteProdutos));

				table.addCell(new Paragraph(produtosCliente.get(i).getMarca(),
						fonteProdutos));

				table.addCell(new Paragraph(produtosCliente.get(i).getModelo(),
						fonteProdutos));

				table.addCell(new Paragraph(produtosCliente.get(i)
						.getNumeroSerie(), fonteProdutos));

				table.addCell(new Paragraph(produtosCliente.get(i).getCor(),
						fonteProdutos));

			}

			table.setWidthPercentage(95.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(10);

			document.add(table);

			PdfPTable tabelaInformacoesVenda = new PdfPTable(new float[] {
					1.40f, 1.40f, 1.40f, 1.40f, 1.40f });

			tabelaInformacoesVenda.setSpacingBefore(10);

			tabelaInformacoesVenda.addCell(new Paragraph(
					"Permissão Para Entrega: " + permissaoEntrega,
					fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Data De Entrega: "
					+ dataEntrega, fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Preço Entrega: "
					+ precoEntrega, fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Data - Venda: "
					+ pegandoDataDoSistema(), fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph(
					"Quantidade - Produtos: " + produtosCliente.size(),
					fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Forma De Pagamento: "
					+ formaPagamento, fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Vezes: "
					+ vezesPagamento, fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Desconto: "
					+ descontoCompra, fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Valor - Parcela: "
					+ valorParcela, fonteProdutos));

			tabelaInformacoesVenda.addCell(new Paragraph("Valor - Total: "
					+ valorTotalCompra, fonteProdutos));

			tabelaInformacoesVenda.setWidthPercentage(95.0f);
			tabelaInformacoesVenda.setHorizontalAlignment(Element.ALIGN_CENTER);

			document.add(tabelaInformacoesVenda);

			Font fonteInformacoesCliente = new Font(FontFamily.COURIER, 7,
					Font.BOLD);

			Paragraph informacoesSobreCliente = new Paragraph(
					"Informações Sobre o Cliente:", fonteInformacoesCliente);

			informacoesSobreCliente.setSpacingBefore(10);
			informacoesSobreCliente.setIndentationLeft(15);
			informacoesSobreCliente.setIndentationRight(15);

			PdfPTable informacoesCliente = new PdfPTable(new float[] { 1.40f,
					1.40f });

			informacoesCliente.addCell(new Paragraph(
					"Código: " + codigoCliente, fonteProdutos));
			informacoesCliente.addCell(new Paragraph("Nome Completo: "
					+ nomeCliente, fonteProdutos));
			informacoesCliente.addCell(new Paragraph("Telefone (s): "
					+ telefone01 + " , " + telefone02, fonteProdutos));
			informacoesCliente.addCell(new Paragraph("E-mail: " + email,
					fonteProdutos));
			informacoesCliente.addCell(new Paragraph("Endereço: " + ruaCliente
					+ ", " + numeroCliente, fonteProdutos));
			informacoesCliente.addCell(new Paragraph("Bairro: " + bairro,
					fonteProdutos));
			informacoesCliente.addCell(new Paragraph("Cidade: " + cidade,
					fonteProdutos));
			informacoesCliente.addCell(new Paragraph("Estado: " + estado,
					fonteProdutos));

			informacoesCliente.setSpacingBefore(10);
			informacoesCliente.setWidthPercentage(95.0f);
			informacoesCliente.setHorizontalAlignment(Element.ALIGN_CENTER);

			document.add(informacoesSobreCliente);
			document.add(informacoesCliente);

			Paragraph observacoesFinais = new Paragraph(
					"Observações Finais: _______________"
							+ "___________________________________________________________________"
							+ "________________________________________________"
							+ "___________________________________________________________"
							+ "___________________________________________________________"
							+ "___________________________________________________________"
							+ "___________________________________________________________"
							+ "___________________________________________________________"
							+ "___________________________________________________________"
							+ "___________________________________________________________"
							+ "_____________________", fonteProdutos);

			observacoesFinais.setSpacingBefore(10);
			observacoesFinais.setIndentationLeft(15);
			observacoesFinais.setIndentationRight(15);

			document.add(observacoesFinais);

			document.close();

		}

		catch (FileNotFoundException e) {

			new ErroEncontrado();

		}

		catch (DocumentException e) {

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