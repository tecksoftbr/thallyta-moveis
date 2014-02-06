package views.utilitarios;

import java.io.IOException;
import views.principais.tela_de_erro.ErroEncontrado;

public class BlocoDeNotas {

	public BlocoDeNotas() {

		try {

			Runtime.getRuntime().exec("notepad");


		}

		catch (IOException e) {

			new ErroEncontrado();

		}

	}

}