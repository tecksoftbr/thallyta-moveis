/*
 * 
 * Coisas que faltam fazer nesta tela ...
 * 
 * 		- Tirar o frame, deixar só o centro.
 * 		- Ajeitar a imagem e colocar uma melhor.
 * 		- Quando a barra for enchendo o software deve verificar se o carregamento está mesmo acontecendo.
 * 
 */

package views.principais.carregando_software;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_login.TelaDeLogin;

public class Carregando extends JFrame {
	private static final long serialVersionUID = 1L;

	// Variáveis e objetos de apoio ...

	static final int valorMínimo = 0;

	private JLabel barra, fundoBarra, fundoPrincipal, verificado1, verificado2,
			verificado3, verificado4;

	private JPanel painelDeElementos;

	// Atualiza a barra e apresenta os ícones de ok ...

	public void atualizaBarra(int valor) {

		barra.setBounds(0, 190, valor, 50);

		if (valor == 75) {

			verificado1.setVisible(true);

		}

		if (valor == 180) {

			verificado2.setVisible(true);

		}

		if (valor == 285) {

			verificado3.setVisible(true);

		}

		if (valor == 370) {

			verificado4.setVisible(true);

		}

	}

	// Construtor Da Classe

	public Carregando() {

		try {

			// Adicionando componentes a tela ...

			this.setUndecorated(true);

			painelDeElementos = new JPanel();
			painelDeElementos.setLayout(new BorderLayout());

			// --------------------------------------------------------------------------------------------------------------

			verificado1 = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/png/Banco_De_Dados.png")));

			verificado1.setForeground(Color.WHITE);
			verificado1.setBounds(-18, 160, 200, 30);

			verificado1.setVisible(false);
			painelDeElementos.add(verificado1);

			// --------------------------------------------------------------------------------------------------------------

			verificado2 = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/png/Operacoes.png")));

			verificado2.setForeground(Color.WHITE);
			verificado2.setBounds(103, 160, 200, 30);

			verificado2.setVisible(false);
			painelDeElementos.add(verificado2);

			// --------------------------------------------------------------------------------------------------------------

			verificado3 = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/png/Utilitarios.png")));

			verificado3.setForeground(Color.WHITE);
			verificado3.setBounds(215, 160, 200, 30);

			verificado3.setVisible(false);
			painelDeElementos.add(verificado3);

			// --------------------------------------------------------------------------------------------------------------

			verificado4 = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/png/Views.png")));

			verificado4.setForeground(Color.WHITE);
			verificado4.setBounds(313, 160, 200, 30);

			verificado4.setVisible(false);
			painelDeElementos.add(verificado4);

			// --------------------------------------------------------------------------------------------------------------

			barra = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/png/Barra.png")));

			painelDeElementos.add(barra);

			// --------------------------------------------------------------------------------------------------------------

			fundoBarra = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/png/Fundo_Barra.png")));

			fundoBarra.setBounds(0, 198, 680, 33);
			painelDeElementos.add(fundoBarra);

			// --------------------------------------------------------------------------------------------------------------

			fundoPrincipal = new JLabel(

					new ImageIcon(
							Carregando.class
									.getResource("/views/principais/carregando_software/jpg/Fundo.png")));

			painelDeElementos.add(fundoPrincipal);

			// Propriedades da tela ...

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.setSize(473, 278);
			this.setLocationRelativeTo(null);
			this.setResizable(false);

			this.setContentPane(painelDeElementos);
			this.setTitle("Thallyta Móveis - Carregando Software");
			this.setVisible(true);

			// Construção de um "for" para o carregamento da barra
			// concorrentemente ...

			for (int i = valorMínimo; i <= 470; i++) {

				final int percent = i;

				try {

					SwingUtilities.invokeLater(new Runnable() {

						public void run() {

							atualizaBarra(percent);

						}

					});

					Thread.sleep(6);

				}

				// Caso não seja concluído o carregamento da barra com sucesso
				// ...

				catch (InterruptedException e) {

					new ErroEncontrado();

				}

			}

			// Após o enchimento da barra, se chama a tela de login ...

			this.dispose();
			new TelaDeLogin();

		}

		// Caso o carregamento não seja efetuado com sucesso ...

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

}