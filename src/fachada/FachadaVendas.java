package fachada;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Lembrete;
import modelo.Produto;
import modelo.Vendas;
import repositorio.RepositorioVenda;
import views.principais.tela_de_inicio.TelaPrincipal;

public class FachadaVendas {

	private final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private final String usuario = "root";
	private final String senha = "";
	
	public ArrayList<Vendas> vendasCadastradas() throws SQLException{
		
		ArrayList<Vendas> vendasCapturadasArrayList;
		
		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {
			
			vendasCapturadasArrayList = new ArrayList<>();
			
			RepositorioVenda capturando = new RepositorioVenda();
			vendasCapturadasArrayList = capturando.listandoVendas(conexao);
			
		}
		
		return vendasCapturadasArrayList;
		
	}

	public void adicionandoVendas(int codigoCliente, String nomeCliente,
			String ruaCliente, String numeroCliente, String bairro,
			String complemento, String cidade, String estado, String cep,
			String telefone01, String telefone02, String email,
			ArrayList<Produto> produtosCliente, String formaPagamento,
			String vezesPagamento, String permissaoEntrega,
			String precoEntrega, String dataEntrega, String descontoCompra,
			String valorParcela, String valorTotalCompra, String dataVenda)
			throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			if (permissaoEntrega.equals("Sim")) {

				String descrisaoLembrete = "Entregar Mercadoria Do Cliente: "
						+ "\n\nCódigo: "
						+ codigoCliente
						+ "\nNome Completo: "
						+ nomeCliente
						+ "\nRua: "
						+ ruaCliente
						+ ", Nº: "
						+ numeroCliente
						+ "\nBairro: "
						+ bairro
						+ "\nComplemento: "
						+ complemento
						+ "\nCidade: "
						+ cidade
						+ "\nEstado: "
						+ estado
						+ "\nCEP: "
						+ cep
						+ "\n\nTelefone 01:"
						+ telefone01
						+ "\nTelefone 02: "
						+ telefone02
						+ "\nE-mail: "
						+ email
						+ "\n\nProduto (s) Comprado (s) ...\n\n";

				for (int i = 0; i < produtosCliente.size(); i++) {

					descrisaoLembrete = descrisaoLembrete
							+ "Código: "
							+ produtosCliente.get(i).getCodigo()
							+ "\nDescrição - Produto: "
							+ produtosCliente.get(i).getDescricao()
							+ "\nMarca: "
							+ produtosCliente.get(i).getMarca()
							+ "\nModelo: "
							+ produtosCliente.get(i).getModelo()
							+ "\nCor: "
							+ produtosCliente.get(i).getCor()
							+ "\n\nQuantidade: "
							+ produtosCliente.get(i).getQuantidade()
							+ "\nPreço De Venda: "
							+ produtosCliente.get(i).getPrecoDeVenda()
							+ "\n--------------------------------------------------------------------\n";

				}

				descrisaoLembrete = descrisaoLembrete
						+ "Outras Informações:\n\nTipo De Pagamento Realizado: "
						+ formaPagamento + "\nValor Total: " + valorTotalCompra
						+ "\nValor Parcelado: " + valorParcela + "\nVezes: "
						+ vezesPagamento + "\nDesconto: " + descontoCompra
						+ "\n\n...";

				Lembrete lembrete = new Lembrete();

				lembrete.setData_de_aviso(dataEntrega);
				lembrete.setData_de_cadastro(dataVenda);
				lembrete.setTitulo("Entrega De Mercadoria");
				lembrete.setDescricao(descrisaoLembrete);

				FachadaLembretes gravandoLembrete = new FachadaLembretes();
				gravandoLembrete.adicionandoLembreteVenda(lembrete);
				
				TelaPrincipal.adicionandoValoresAsTabelas();

			}

			RepositorioVenda gravandoBD = new RepositorioVenda();

			gravandoBD.adicionandoVendas(codigoCliente, nomeCliente,
					ruaCliente, numeroCliente, bairro, complemento, cidade,
					estado, cep, telefone01, telefone02, email,
					produtosCliente, formaPagamento, vezesPagamento,
					permissaoEntrega, precoEntrega, dataEntrega,
					descontoCompra, valorParcela, valorTotalCompra, dataVenda,
					conexao);

		}

	}

}