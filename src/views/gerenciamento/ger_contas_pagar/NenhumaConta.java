package views.gerenciamento.ger_contas_pagar;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import views.principais.tela_de_inicio.TelaPrincipal;

public class NenhumaConta extends JFrame implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal;
	private JLabel fundo;

	public NenhumaConta() {

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		fundo = new JLabel();

		fundo.setIcon(new ImageIcon(
				NenhumaConta.class
						.getResource("/views/gerenciamento/ger_contas_pagar/jpg/Nenhuma_Conta.jpg")));

		fundo.setBounds(0, 0, 950, 500);

		fundo.addMouseListener(this);
		fundo.addKeyListener(this);

		painelPrincipal.add(fundo);

		// Quando o usu�rio apertar "X" ...

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent e) {

				dispose();
				new TelaPrincipal();

			}

		});

		// Propriedades da tela ...

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(956, 528);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Thallyta M�veis - Gerenciando Conta (s)");

		this.setContentPane(painelPrincipal);
		this.setVisible(true);

		this.addMouseListener(this);
		this.addKeyListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == fundo) {

			this.dispose();
			new TelaPrincipal();

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				|| keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();
			new TelaPrincipal();

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}