package teste;

import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;

public class ClasseMain {

	public static void main(String[] args) {

		// Criação de um try/catch para chamar tela de carregamento ...

		try {

			new TelaPrincipal();

		}

		// Caso algum erro ocorra o catch e chamado ...

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

}