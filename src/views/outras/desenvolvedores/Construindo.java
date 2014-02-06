package views.outras.desenvolvedores;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import views.principais.tela_de_inicio.TelaPrincipal;

public class Construindo extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel fundo;

	public Construindo() {

		// --------------------------------------------------------------------------------------------------------------

		fundo = new JLabel(

				new ImageIcon(
						Construindo.class
								.getResource("/views/outras/desenvolvedores/jpg/Fundo.jpg")));

		getContentPane().add(fundo);

		// Quando o usu�rio apertar "X" ...

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent e) {

				dispose();
				new TelaPrincipal();

			}

		});

		// Propriedades da tela ...

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(856, 565);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Thallyta M�veis - Cadastrando Novo Usu�rio");
		this.setVisible(true);

	}

}