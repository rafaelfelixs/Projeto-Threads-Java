import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;


public class PomboCorreio extends JFrame {

	private JPanel contentPane;
	public JLabel imagePassaro;
	public JLabel placa;
	public JLabel textoPlaca;
	public JLabel placaPessoa;
	public JLabel lblPlacadormindo;
	public JLabel lblPlacacolando;

	public JLabel textoPlacaPessoa;

	private JTextField tempoEsperaPessoa; // textField
	private JLabel lblMenu;
	private JTextField numElininarPessoa;

	int posx, posy;


	Buffer buffer_usado;
//	buffer_usado.setMaximoCaixa(10);
//	buffer_usado.setPassaroVoando(false);
	/*public Semaphore s1;
	public Semaphore s2;
	public Semaphore n;
	public Semaphore mutex;
	public Semaphore auxsemaforoCaixaPostal;
	public Semaphore aptoViagem;
	public Semaphore semaforoCaixaPostal;*/
	
	//public static Semaphore s1 = new Semaphore(1);
	//public static Semaphore s2 = new Semaphore(0);
	//public static Semaphore n = new Semaphore(10);
	public static Semaphore mutex = new Semaphore(1);
	//public static Semaphore auxsemaforoCaixaPostal = new Semaphore(1);

	public static Semaphore aptoViagem = new Semaphore(0);
	public static Semaphore semaforoCaixaPostal = new Semaphore(0);
	
	
	
	Random gerador; // variável que auxilia na geração de números randômicos.
	int contadorPessoas = 1;
	private JButton btnEliminarPessoa;
	private JTextField txtTempocargap;
	private JTextField txtTempodescargap;
	private JTextField txtTempoviagemp;
	private JTextField txtNumeromensagensp;

	Pombo pb;
	private JTextField txtMaximocaixa;
	private JButton btnConfigurarCaixa;
	private JLabel lblTempoescrita;
	private JLabel lblId;
	private JLabel lblTc;
	private JLabel lblTd;
	private JLabel lblTv;
	private JLabel lblNm;
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PomboCorreio frame = new PomboCorreio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public PomboCorreio() throws InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 470);

		placa = new JLabel("");
		placa.setBounds(0, 300, 64, 64);
		placa.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/placa.png"));

		placaPessoa = new JLabel("");
		placaPessoa.setBounds(820, 250, 240, 124);
		placaPessoa.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/auxMensagem.png"));
		//public static Semaphore n = new Semaphore(10);
		//public static Semaphore mutex = new Semaphore(1);
		//public static Semaphore auxsemaforoCaixaPostal = new Semaphore(1);

		//public static Semaphore aptoViagem = new Semaphore(0);
		//public static Semaphore semaforoCaixaPostal = new Semaphore(0);
		
		lblPlacadormindo = new JLabel("");
		lblPlacadormindo.setBounds(820, 310, 240, 124);
		lblPlacadormindo.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/auxMensagem.png"));
		
		lblPlacacolando = new JLabel("");
		lblPlacacolando.setBounds(820, 370, 240, 124);
		lblPlacacolando.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/auxMensagem.png"));
		
		
		imagePassaro = new JLabel("");
		imagePassaro.setBounds(0, 150, 120, 200);
		imagePassaro.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/teste.png"));



		// cria Jpanel com uma imagem de fundo
		contentPane =  new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage(System.getProperty("user.dir") + "/src/images/planoDeFundo.png");		

				g.drawImage(image, 0, 0, null);
			}
		};


		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textoPlaca = new JLabel("");
		textoPlaca.setHorizontalAlignment(SwingConstants.CENTER);
		textoPlaca.setBounds(4, 312, 60, 16);
		getContentPane().add(textoPlaca);
		textoPlaca.setForeground(Color.white);
		textoPlaca.setText("");

		textoPlacaPessoa = new JLabel("");
		textoPlacaPessoa.setHorizontalAlignment(SwingConstants.CENTER);
		textoPlacaPessoa.setBounds(820, 280, 240, 50);
		getContentPane().add(textoPlacaPessoa);
		textoPlacaPessoa.setText("INICIANDO");
		
		getContentPane().add(placa);
	//	getContentPane().add(placaPessoa);
	//	getContentPane().add(lblPlacacolando);
	//	getContentPane().add(lblPlacadormindo);
		getContentPane().add(imagePassaro);
		System.out.println("INICIANDO...");

		/*
		 * Inicianliza Buffer, Semáforos, Objetos Pessoas e pombo.
		 */


		/*s1 = new Semaphore(1);
		s2 = new Semaphore(0);
		n = new Semaphore(10);
		mutex = new Semaphore(1);
		auxsemaforoCaixaPostal = new Semaphore(1);

		aptoViagem = new Semaphore(0);
		semaforoCaixaPostal = new Semaphore(1);*/


		tempoEsperaPessoa = new JTextField();
		tempoEsperaPessoa.setBounds(840, 57, 50, 19);
		contentPane.add(tempoEsperaPessoa);
		tempoEsperaPessoa.setColumns(10);

		lblMenu = new JLabel("MENU");
		lblMenu.setBounds(970, 26, 70, 15);
		contentPane.add(lblMenu);

		numElininarPessoa = new JTextField();
		numElininarPessoa.setBounds(840, 80, 50, 19);
		contentPane.add(numElininarPessoa);
		numElininarPessoa.setColumns(10);



		//Inicializa 10 Threads Produtoras

		
		JButton btnCriarPessoa = new JButton("Criar Pessoa");
		btnCriarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tempoEsperaPessoa.getText().equals("")) JOptionPane.showMessageDialog(null, "Entre com um número");
				int PtempoEspera = Integer.parseInt(tempoEsperaPessoa.getText());
				if (contadorPessoas == 1) {
					posy = 270;
				}else {
					posy = posy+23;
				}
				posx = 840;
				   JLabel lable23 = new JLabel("Pessoa "+contadorPessoas);
				    lable23.setLocation(840, posy);
	//			    lable23.setBounds(850, 80, 240, 15);

				    lable23.setSize(300, 19);
				    lable23.setOpaque(true);
				    lable23.setBackground(Color.BLUE);
				    add(lable23);
				    validate();
				    repaint();

				Pessoa p = new Pessoa(contadorPessoas, lable23, buffer_usado, PtempoEspera);
				Thread p1 = new Thread(p);
				p1.start(); // inicia Thread Pessoa (ver Classe pessoa)
				contadorPessoas++;
			}
		});



		btnCriarPessoa.setBounds(910, 57, 240, 19);
		contentPane.add(btnCriarPessoa);
		btnEliminarPessoa = new JButton("Eliminar Pessoa");
		
		btnEliminarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(numElininarPessoa.getText().equals("")) JOptionPane.showMessageDialog(null, "Entre com um número");
				else{
					int idPessoa = Integer.parseInt(numElininarPessoa.getText());
					buffer_usado.setEliminarPessoa(idPessoa);
					// aqui prepara para eliminar. só irá eliminar de fato quando a thread for executar
				}

			}
		});

		
		btnEliminarPessoa.setBounds(910, 80, 240, 19);
		contentPane.add(btnEliminarPessoa);
		
		txtTempocargap = new JTextField();
		txtTempocargap.setBounds(840, 140, 50, 19);
		contentPane.add(txtTempocargap);
		txtTempocargap.setColumns(10);
		
		txtTempodescargap = new JTextField();
		txtTempodescargap.setBounds(940, 140, 50, 19);
		contentPane.add(txtTempodescargap);
		txtTempodescargap.setColumns(10);
		
		txtTempoviagemp = new JTextField();
		txtTempoviagemp.setBounds(1040, 140, 50, 19);
		contentPane.add(txtTempoviagemp);
		txtTempoviagemp.setColumns(10);
		
		txtNumeromensagensp = new JTextField();
		txtNumeromensagensp.setBounds(1140, 140, 50, 19);
		contentPane.add(txtNumeromensagensp);
		txtNumeromensagensp.setColumns(10);
		
		JButton btnConfigurarPombo = new JButton("Configurar Pombo");
		btnConfigurarPombo.setBounds(950, 180, 170, 25);

		btnConfigurarPombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//valida os campos de configurar o pombo
				if(txtTempocargap.getText().equals("") || txtTempodescargap.getText().equals("") || txtTempoviagemp.getText().equals("") || txtNumeromensagensp.getText().equals("") ) JOptionPane.showMessageDialog(null, "Erro configuração do Pombo. Entre com dados corretos.");
				else{
					//Construindo Pombo
					int tempoCarga = Integer.parseInt(txtTempocargap.getText());
					int tempoDescarga = Integer.parseInt(txtTempodescargap.getText());
					int tempoViagem = Integer.parseInt(txtTempoviagemp.getText());
					int numeroMensagens = Integer.parseInt(txtNumeromensagensp.getText());
					buffer_usado = new Buffer(textoPlaca, pb);
					buffer_usado.setPassaroVoando(false);
					buffer_usado.setMaximo(0);

					pb = new Pombo(buffer_usado, imagePassaro,tempoCarga, tempoDescarga, tempoViagem, numeroMensagens);
					buffer_usado.setPombo(pb);
					
					
					Thread pombo = new Thread(pb);
					pombo.start(); // Inicia Thread Pombo (ver classe pombo)

					// aqui prepara para eliminar. só irá eliminar de fato quando a thread for executar
				}

			}
		});
		contentPane.add(btnConfigurarPombo);
		
		txtMaximocaixa = new JTextField();
		txtMaximocaixa.setBounds(840, 229, 50, 19);
		contentPane.add(txtMaximocaixa);
		txtMaximocaixa.setColumns(10);
		
		btnConfigurarCaixa = new JButton("Configurar caixa");
		btnConfigurarCaixa.setBounds(900, 229, 170, 19);
		btnConfigurarCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtMaximocaixa.getText().equals("")) JOptionPane.showMessageDialog(null, "Erro configuração do Pombo. Entre com dados corretos.");
				else{
					int maximoCaixa = Integer.parseInt(txtMaximocaixa.getText());
					buffer_usado.setMaximoCaixa(maximoCaixa);
					PomboCorreio.semaforoCaixaPostal.release(maximoCaixa);
					textoPlaca.setText(""+maximoCaixa);

					// aqui prepara para eliminar. só irá eliminar de fato quando a thread for executar
				}

			}
		});
		contentPane.add(btnConfigurarCaixa);
		
		lblTempoescrita = new JLabel("T.E");
		lblTempoescrita.setBounds(810, 59, 40, 15);
		contentPane.add(lblTempoescrita);
		
		lblId = new JLabel("ID");
		lblId.setBounds(820, 80, 70, 15);
		contentPane.add(lblId);
		
		lblTc = new JLabel("T.C");
		lblTc.setBounds(815, 140, 70, 15);
		contentPane.add(lblTc);
		
		lblTd = new JLabel("T.D");
		lblTd.setBounds(915, 140, 70, 15);
		contentPane.add(lblTd);
		
		lblTv = new JLabel("T.V");
		lblTv.setBounds(1015, 140, 70, 15);
		contentPane.add(lblTv);
		
		lblNm = new JLabel("N.M");
		lblNm.setBounds(1110, 140, 70, 15);
		contentPane.add(lblNm);
	}
}
