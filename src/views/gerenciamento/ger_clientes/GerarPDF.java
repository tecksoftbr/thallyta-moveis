package views.gerenciamento.ger_clientes;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.Cliente;
import views.principais.tela_de_erro.ErroEncontrado;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GerarPDF {

	public GerarPDF(ArrayList<Cliente> clientes, String caminho) {

		Document document = new Document(PageSize.A4, 0, 0, 10, 72);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(caminho
					+ ".pdf"));

			document.open();

			Font fonteTitulo = new Font(FontFamily.COURIER, 20, Font.BOLD);
			Font fonteSubtitulo = new Font(FontFamily.COURIER, 13, Font.NORMAL);

			Paragraph titulo = new Paragraph(
					"Relatório De Clientes Cadastrados", fonteTitulo);

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
					1.40f, 1.40f, 1.40f, 1.40f, 1.40f });

			table.addCell(new Paragraph("Código", f2));
			table.addCell(new Paragraph("Nome Completo", f2));
			table.addCell(new Paragraph("Sexo", f2));
			table.addCell(new Paragraph("CPF", f2));
			table.addCell(new Paragraph("RG", f2));
			table.addCell(new Paragraph("Orgão Emissor", f2));
			table.addCell(new Paragraph("Data De Emissão", f2));
			table.addCell(new Paragraph("Nacionalidade", f2));

			table.setWidthPercentage(98.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < clientes.size(); i++) {

				table.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getCodigo()), f));

				table.addCell(new Paragraph(clientes.get(i).getNomeCompleto(),
						f));

				table.addCell(new Paragraph(clientes.get(i).getSexo(), f));

				table.addCell(new Paragraph(clientes.get(i).getCpf(), f));

				table.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getRg()), f));

				table.addCell(new Paragraph(clientes.get(i).getOrgaoEmissor(),
						f));

				table.addCell(new Paragraph(clientes.get(i).getDataDeEmissao(),
						f));

				table.addCell(new Paragraph(clientes.get(i).getNacionalidade(),
						f));

			}

			document.add(table);

			PdfPTable table2 = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f, 1.40f, 1.40f });

			table2.addCell(new Paragraph("Nome Completo", f2));
			table2.addCell(new Paragraph("Rua", f2));
			table2.addCell(new Paragraph("Número", f2));
			table2.addCell(new Paragraph("Complemento", f2));
			table2.addCell(new Paragraph("Bairro", f2));
			table2.addCell(new Paragraph("Cidade", f2));
			table2.addCell(new Paragraph("Estado", f2));
			table2.addCell(new Paragraph("CEP", f2));

			table2.setWidthPercentage(98.0f);
			table2.setHorizontalAlignment(Element.ALIGN_CENTER);

			table2.setSpacingBefore(10);

			for (int i = 0; i < clientes.size(); i++) {

				table2.addCell(new Paragraph(clientes.get(i).getNomeCompleto(),
						f));

				table2.addCell(new Paragraph(clientes.get(i).getRua(), f));

				table2.addCell(new Paragraph(clientes.get(i).getNumero(), f));

				table2.addCell(new Paragraph(clientes.get(i).getComplemento(),
						f));

				table2.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getBairro()), f));

				table2.addCell(new Paragraph(clientes.get(i).getCidade(), f));

				table2.addCell(new Paragraph(clientes.get(i).getEstado(), f));

				table2.addCell(new Paragraph(clientes.get(i).getCep(), f));

			}

			document.add(table2);

			PdfPTable table3 = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f, 1.40f, 1.40f, 1.40f });

			table3.addCell(new Paragraph("Nome Completo", f2));
			table3.addCell(new Paragraph("Telefone - 01", f2));
			table3.addCell(new Paragraph("Telefone - 02", f2));
			table3.addCell(new Paragraph("E-mail", f2));
			table3.addCell(new Paragraph("Data De Nascimento", f2));
			table3.addCell(new Paragraph("Estado Civil", f2));
			table3.addCell(new Paragraph("Conjugue", f2));
			table3.addCell(new Paragraph("Pai", f2));
			table3.addCell(new Paragraph("Mãe", f2));

			table3.setWidthPercentage(98.0f);
			table3.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < clientes.size(); i++) {

				table3.addCell(new Paragraph(clientes.get(i).getNomeCompleto(),
						f));

				table3.addCell(new Paragraph(clientes.get(i).getTelefone01(), f));

				table3.addCell(new Paragraph(clientes.get(i).getTelefone02(), f));

				table3.addCell(new Paragraph(clientes.get(i).getEmail(), f));

				table3.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getDataDeNascimento()), f));

				table3.addCell(new Paragraph(clientes.get(i).getEstadoCivil(),
						f));

				table3.addCell(new Paragraph(clientes.get(i).getConjugue(), f));

				table3.addCell(new Paragraph(clientes.get(i).getPai(), f));

				table3.addCell(new Paragraph(clientes.get(i).getMae(), f));

			}

			table3.setSpacingBefore(10);
			document.add(table3);

			PdfPTable table4 = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f, 1.40f });

			table4.addCell(new Paragraph("Nome Completo", f2));
			table4.addCell(new Paragraph("Trabalho", f2));
			table4.addCell(new Paragraph("Cargo", f2));
			table4.addCell(new Paragraph("Tempo De Serviço", f2));
			table4.addCell(new Paragraph("Sobre o Trabalho", f2));
			table4.addCell(new Paragraph("Observações Adicionais", f2));
			table4.addCell(new Paragraph("Data De Cadastro", f2));

			table4.setWidthPercentage(98.0f);
			table4.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < clientes.size(); i++) {

				table4.addCell(new Paragraph(clientes.get(i).getNomeCompleto(),
						f));

				table4.addCell(new Paragraph(clientes.get(i).getTrabalho(), f));

				table4.addCell(new Paragraph(clientes.get(i).getCargo(), f));

				table4.addCell(new Paragraph(clientes.get(i).getTempoServico(),
						f));

				table4.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getSobreTrabalho()), f));

				table4.addCell(new Paragraph(clientes.get(i)
						.getObservacoesAdicionais(), f));

				table4.addCell(new Paragraph(clientes.get(i)
						.getDataDeCadastro(), f));

			}

			table4.setSpacingBefore(10);
			document.add(table4);

			PdfPTable table5 = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f, 1.40f, 1.40f });

			table5.addCell(new Paragraph("Nome Completo", f2));
			table5.addCell(new Paragraph("Endereço - Trabalho", f2));
			table5.addCell(new Paragraph("Número - Trabalho", f2));
			table5.addCell(new Paragraph("Complemento - Trabalho", f2));
			table5.addCell(new Paragraph("Bairro - Trabalho", f2));
			table5.addCell(new Paragraph("Cidade - Trabalho", f2));
			table5.addCell(new Paragraph("Estado - Trabalho", f2));

			table5.setWidthPercentage(98.0f);
			table5.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < clientes.size(); i++) {

				table5.addCell(new Paragraph(clientes.get(i).getNomeCompleto(),
						f));

				table5.addCell(new Paragraph(clientes.get(i)
						.getEnderecoOndeTrabalha(), f));

				table5.addCell(new Paragraph(clientes.get(i)
						.getNumeroTrabalho(), f));

				table5.addCell(new Paragraph(clientes.get(i)
						.getComplementoTrabalho(), f));

				table5.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getBairroTrabalho()), f));

				table5.addCell(new Paragraph(clientes.get(i)
						.getCidadeTrabalho(), f));

				table5.addCell(new Paragraph(clientes.get(i)
						.getEstadoTrabalho(), f));

			}

			table5.setSpacingBefore(10);
			document.add(table5);

			PdfPTable table6 = new PdfPTable(new float[] { 1.40f, 1.40f, 1.40f,
					1.40f, 1.40f });

			table6.addCell(new Paragraph("Nome Completo", f2));
			table6.addCell(new Paragraph("CEP - Trabalho", f2));
			table6.addCell(new Paragraph("Telefone - Trabalho", f2));
			table6.addCell(new Paragraph("Fax - Trabalho", f2));
			table6.addCell(new Paragraph("Email - Trabalho", f2));

			table6.setWidthPercentage(98.0f);
			table6.setHorizontalAlignment(Element.ALIGN_CENTER);

			for (int i = 0; i < clientes.size(); i++) {

				table6.addCell(new Paragraph(clientes.get(i).getNomeCompleto(),
						f));

				table6.addCell(new Paragraph(clientes.get(i).getCepTrabalho(),
						f));

				table6.addCell(new Paragraph(clientes.get(i)
						.getTelefoneTrabalho(), f));

				table6.addCell(new Paragraph(clientes.get(i).getFaxTrabalho(),
						f));

				table6.addCell(new Paragraph(String.valueOf(clientes.get(i)
						.getEmailTrabalho()), f));

			}

			table6.setSpacingBefore(10);
			document.add(table6);

			Paragraph resumoFinal = new Paragraph(
					"Total De Clientes Cadastrados No Sistema: "
							+ clientes.size(), f);

			resumoFinal.setSpacingBefore(10);
			resumoFinal.setAlignment(Element.ALIGN_CENTER);

			document.add(resumoFinal);

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