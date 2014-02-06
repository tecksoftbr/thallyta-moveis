package views.outras.sobre_software;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import modelo.Ajuda;

import fachada.FachadaAjuda;
import views.principais.tela_de_erro.ErroEncontrado;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

public class Sobre extends JInternalFrame implements MouseListener {
	private static final long serialVersionUID = 1L;

	private JLabel fundo;
	private JPanel painelPrincipal;
	private JComboBox<String> topicos;
	private JTextArea campo;
	private JButton botaoOk;

	private static ArrayList<Ajuda> ajudas = new ArrayList<>();
	private Color corDotexto = new Color(139, 139, 139);

	public Sobre() throws SQLException, PropertyVetoException {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		FachadaAjuda pegandoAjudas = new FachadaAjuda();
		ajudas = pegandoAjudas.listandoAjuda();

		painelPrincipal = new JPanel();

		botaoOk = new JButton("Ok");
		botaoOk.setBounds(384, 127, 90, 35);

		botaoOk.addMouseListener(this);
		painelPrincipal.setLayout(null);
		painelPrincipal.add(botaoOk);

		topicos = new JComboBox<String>();
		topicos.setBounds(24, 127, 348, 35);
		topicos.setEditable(false);

		topicos.addMouseListener(this);
		topicos.addItem("Selecione ...");

		for (int i = 0; i < ajudas.size(); i++) {

			topicos.addItem(ajudas.get(i).getTitulo());

		}

		painelPrincipal.add(topicos);

		campo = new JTextArea();
		campo.setFocusable(false);

		campo.setForeground(corDotexto);
		campo.setLineWrap(true);
		campo.setBounds(24, 174, 450, 227);

		painelPrincipal.add(campo);
		
		JButton btnVoltar = new JButton("Fechar");
		
		btnVoltar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
			}
			
		});
		
		btnVoltar.setBounds(24, 413, 450, 35);
		painelPrincipal.add(btnVoltar);

		fundo = new JLabel(

				new ImageIcon(
						Sobre.class
								.getResource("/views/outras/sobre_software/jpg/Fundo.JPG")));
		fundo.setBounds(0, 0, 500, 500);

		painelPrincipal.add(fundo);

		// Quando o usuário apertar "X" ...

		this.addInternalFrameListener(new InternalFrameListener()

		{
			public void internalFrameClosed(InternalFrameEvent e) {

			}

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {

				dispose();

			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {

			}

		});

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setContentPane(painelPrincipal);
		this.setSize(506, 528);

		this.setClosable(true);
		this.setSelected(true);
		
		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Ajuda Sobre o Software");
		this.setVisible(true);

		campo.requestFocus();

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == botaoOk) {

			if (topicos.getSelectedItem().equals("Selecione ...")) {


			}

			else {

				int posicao = topicos.getSelectedIndex() - 1;

				campo.setText(ajudas.get(posicao).getDescricao());

			}

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
}