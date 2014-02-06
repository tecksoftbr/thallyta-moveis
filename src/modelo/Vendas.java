package modelo;

import java.util.ArrayList;

public class Vendas {

	int codigo;
	String dataVenda;

	double desconto;
	double valorParcela;
	double valorTotal;

	String formaDePagamento;

	int vezes;
	String permissaoParaEntrega;
	double precoEntrega;
	String dataEntrega;

	int codigoCliente;

	String nomeCliente;
	String ruaCliente;
	String numeroCliente;
	String bairroCliente;

	String complementoCliente;
	String cidadeCliente;
	String estadoCliente;
	String cep;

	String telefone01;
	String telefone02;
	String email;

	ArrayList<ProdutoVenda> produtos;

	public ArrayList<ProdutoVenda> getProdutos() {
		return produtos;
	}

	public void setProdutos(ArrayList<ProdutoVenda> produtos) {
		this.produtos = produtos;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public int getVezes() {
		return vezes;
	}

	public void setVezes(int vezes) {
		this.vezes = vezes;
	}

	public String getPermissaoParaEntrega() {
		return permissaoParaEntrega;
	}

	public void setPermissaoParaEntrega(String permissaoParaEntrega) {
		this.permissaoParaEntrega = permissaoParaEntrega;
	}

	public double getPrecoEntrega() {
		return precoEntrega;
	}

	public void setPrecoEntrega(double precoEntrega) {
		this.precoEntrega = precoEntrega;
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getRuaCliente() {
		return ruaCliente;
	}

	public void setRuaCliente(String ruaCliente) {
		this.ruaCliente = ruaCliente;
	}

	public String getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(String numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

	public String getBairroCliente() {
		return bairroCliente;
	}

	public void setBairroCliente(String bairroCliente) {
		this.bairroCliente = bairroCliente;
	}

	public String getComplementoCliente() {
		return complementoCliente;
	}

	public void setComplementoCliente(String complementoCliente) {
		this.complementoCliente = complementoCliente;
	}

	public String getCidadeCliente() {
		return cidadeCliente;
	}

	public void setCidadeCliente(String cidadeCliente) {
		this.cidadeCliente = cidadeCliente;
	}

	public String getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone01() {
		return telefone01;
	}

	public void setTelefone01(String telefone01) {
		this.telefone01 = telefone01;
	}

	public String getTelefone02() {
		return telefone02;
	}

	public void setTelefone02(String telefone02) {
		this.telefone02 = telefone02;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}