package views.principais.tela_de_erro;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import views.principais.tela_de_inicio.TelaPrincipal;

public class ErroEncontrado extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel fundo;

	private Icon[] icones = { new ImageIcon(getClass().getResource(
			"jpg/Fundo.jpg")) };

	public ErroEncontrado() {

		try {

			// Adicionando componentes ao Jframe ...

			fundo = new JLabel(icones[0]);
			this.add(fundo);

			// Quando o usuário apertar "X" ...

			this.addWindowListener(new java.awt.event.WindowAdapter() {

				public void windowClosing(java.awt.event.WindowEvent e) {

					dispose();
					new TelaPrincipal();

				}

			});

			// Propriedades da tela ...

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setSize(676, 189);
			this.setLocationRelativeTo(null);
			this.setTitle("Thallyta Móveis - Aviso De Erro");
			this.setVisible(false);

		}

		catch (Exception e) {

			System.exit(0);

		}

	}

}