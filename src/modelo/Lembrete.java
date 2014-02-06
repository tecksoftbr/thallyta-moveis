package modelo;

public class Lembrete {

	private int codigo;
	private String titulo;
	private String descricao;

	private String data_de_cadastro;
	private String data_de_aviso;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData_de_cadastro() {
		return data_de_cadastro;
	}

	public void setData_de_cadastro(String data_de_cadastro) {
		this.data_de_cadastro = data_de_cadastro;
	}

	public String getData_de_aviso() {
		return data_de_aviso;
	}

	public void setData_de_aviso(String data_de_aviso) {
		this.data_de_aviso = data_de_aviso;
	}

}