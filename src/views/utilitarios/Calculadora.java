package views.utilitarios;

import java.io.IOException;
import views.principais.tela_de_erro.ErroEncontrado;

public class Calculadora {

	public Calculadora() {

		try {

			Runtime.getRuntime().exec("calc.exe");

		}

		catch (IOException e) {

			new ErroEncontrado();

		}

	}

}