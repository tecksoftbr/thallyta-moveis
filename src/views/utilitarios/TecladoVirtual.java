package views.utilitarios;

import java.io.IOException;
import views.principais.tela_de_erro.ErroEncontrado;

public class TecladoVirtual {

	public TecladoVirtual() {

		try {

			Runtime.getRuntime().exec("cmd /C osk");

		}

		catch (IOException e) {

			new ErroEncontrado();

		}

	}

}