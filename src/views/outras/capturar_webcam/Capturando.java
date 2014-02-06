package views.outras.capturar_webcam;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.cdm.CaptureDeviceManager;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import views.cadastros.cad_produtos.CadastroProdutos;

public class Capturando extends javax.swing.JFrame {
	public static Player player = null;
	public CaptureDeviceInfo di = null; // @jve:decl-index=0:
	public MediaLocator ml = null; // @jve:decl-index=0:
	public JButton capture = null;
	public Buffer buf = null;
	public Image img = null;
	
	public VideoFormat vf = null;
	public BufferToImage btoi = null;
	public String caminho = "C:/Users/KLEYSSON/Desktop/Teste.JPG";
	int width = 150;
	int height = 150;
	int quality = 100;

	private static final long serialVersionUID = 1L;

	/** Creates new form CapturarFotos */
	public Capturando() {
		initComponents();
		initialize();
		this.setVisible(true);
	}

	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		btnCapturar = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		btnCapturar.setIcon(new javax.swing.ImageIcon(
				"W:\\Icones\\btnCapturar.png")); // NOI18N
		btnCapturar.setText("Capturar Foto");
		btnCapturar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCapturarActionPerformed(evt);
			}
		});

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jPanel1.setLayout(new java.awt.BorderLayout());

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														btnCapturar,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														355, Short.MAX_VALUE)
												.addComponent(
														jPanel1,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														355, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										254, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(btnCapturar).addContainerGap()));

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 387) / 2, (screenSize.height - 351) / 2,
				387, 351);
	}// </editor-fold>

	private void initialize() {
		String str2 = "vfw//0";
		di = CaptureDeviceManager.getDevice(str2);
		ml = new MediaLocator("vfw://0");
		try {
			player = Manager.createRealizedPlayer(ml);
			player.start();
			Component comp;
			if ((comp = player.getVisualComponent()) != null) {
				jPanel1.add(comp, BorderLayout.NORTH);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Não Foi Possivel se Conectar ao Dispositivo de Captura!");
		}

	}

	public void gravaImg(Image imagem) {
		try {
			
			JFileChooser chooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);

			int returnVal = chooser.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				System.out.println(chooser.getSelectedFile().getPath());
				caminho = chooser.getSelectedFile().getPath() + ".jpg";
				
			}
			
			ImageIO.write((RenderedImage) imagem, "jpg", new File(caminho));
			JOptionPane
					.showMessageDialog(this, "Imagem Capturada com Sucesso!");
			
			CadastroProdutos.retornandoEnderecoDaWebcam(caminho);
			player.close();
			
			this.dispose();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "não foi possivel encontrar "
					+ "o dispositivo para a captura da imagem.");
			e.printStackTrace();
		}
	}

	private void getCaptura() {
		FrameGrabbingControl fgc = (FrameGrabbingControl) player
				.getControl("javax.media.control.FrameGrabbingControl");
		buf = fgc.grabFrame();
		btoi = new BufferToImage((VideoFormat) buf.getFormat());
		img = btoi.createImage(buf);
		gravaImg(img);
	}

	private void btnCapturarActionPerformed(java.awt.event.ActionEvent evt) {
		getCaptura();
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Capturando().setVisible(true);
			}
		});
	}

	private javax.swing.JButton btnCapturar;
	private javax.swing.JPanel jPanel1;
}