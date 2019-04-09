import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class Pombo implements Runnable{

	private JLabel componente;
	/*private Semaphore aptoViagem;*/
	private Buffer o_Buffer;
	/*private Semaphore semaforoCaixPostal;*/

	// Abaixo atributos do pombo 
	private int tempoViagem, tempoCarga, tempoDescarga, quantidadeMensagem;
	private List<Mensagem> bolsaMensagens = new ArrayList<Mensagem>();
	
	public int getQuantidadeMensagem() {
		return quantidadeMensagem;
	}

	public void setQuantidadeMensagem(int quantidadeMensagem) {
		this.quantidadeMensagem = quantidadeMensagem;
	}
	
	public void passarTempo (int t1)
	{
        long aux; 
        long inicio = System.currentTimeMillis();
        long tempodefinido = t1;
        while (inicio + tempodefinido > System.currentTimeMillis())
        {
            aux = System.currentTimeMillis();
        }
	}

	public Pombo(Buffer buffer, JLabel imagemPassaro, int tCarga, int tDescarga, int tViagem, int qtdeMensagem) {
		// TODO Auto-generated constructor stub
		/*this.aptoViagem = aptoViagemP;*/
		o_Buffer = buffer;
		/*semaforoCaixPostal = sCaixPostal;*/
		componente = imagemPassaro;
		tempoCarga = tCarga;
		tempoDescarga = tDescarga;
		tempoViagem = tViagem*2;
		quantidadeMensagem = qtdeMensagem;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		int i;
		try {
			while(true){ // Esse while serve para sempre o pombo fazer a viagem

				if(o_Buffer.getMaximo() >= quantidadeMensagem){
					System.out.println(">>> pode voltar a viajar");
				} else {
                                    System.out.println("O pombo vai durmir Zzz");
                                    PomboCorreio.aptoViagem.acquire(); // Pássaro dorme  
                                } 
                               
                             
                                
	
                                // PomboCorreio.aptoViagem.acquire(); // Pássaro dorme
                                
				passarTempo(tempoCarga); // Pombo carregando as mensagens
                                System.out.println("Pombo carregando as mensagens na bolsa para a viagem");
                                PomboCorreio.semaforoCaixaPostal.release(quantidadeMensagem);
				
				int x = componente.getX();
				componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/ida0.png"));
				componente.repaint();
				componente.setBounds(x, 0, 100, 38);

				int contadorImagensPassaro = 1;
				while(x <= 500){
					if(contadorImagensPassaro == 8) contadorImagensPassaro = 1;
					componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/ida"+contadorImagensPassaro+".gif"));
					componente.repaint();
					componente.setBounds(x, 0, 120, 200);
					passarTempo(tempoCarga); // Pombo viajando
					x = x+1;
					contadorImagensPassaro++;
				}

				// Aqui ele começa a descarregar as mensagens

				/*
				 * 				int contadorMensagem;
				for(contadorMensagem = 0; contadorMensagem<getBolsaMensagens().size()-1;contadorMensagem ++){
					Mensagem m = getBolsaMensagens().get(contadorMensagem);
					System.out.println("DESCARREGANDO MENSAGEM: "+m.getId() + " DA PESSOA: "+m.getPessoa().getId());
				}

				 */
				//Thread.currentThread().sleep(tempoDescarga*1000); //Thread Pombo espera, fazendo o papel de carregar as mensagens
				passarTempo(tempoDescarga); // Pombo descarregando mensagens
				
				while(x >= 0){
					if(contadorImagensPassaro == 8) contadorImagensPassaro = 1;
					componente.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/images/volta"+contadorImagensPassaro+".png"));
					componente.repaint();
					componente.setBounds(x, 0, 120, 200);
					passarTempo(tempoViagem); // Pombo Viajando
					x--;
					contadorImagensPassaro++;
				}

				DateFormat formato = new SimpleDateFormat("HH:mm:ss.SSS");
				System.out.println("hora=: "+formato.toString());
				/*
				 * Após terminar a viagem, pássaro se prepara para voar novamente
				 * atribuição de 0 ao maximo => valor 0 para quantidade de mensagens que ele tem.
				 * o_Buffer.setMaximoCaixa(o_Buffer.getMaximoCaixa()+3) => quando ele volta da cidade B,
				 * 		os passageiros que estavam dormindo com suas mensagens prontas já podem adicionar mais mensagens.
				 */
				PomboCorreio.mutex.acquire();
				o_Buffer.setMaximo(0);
                                System.out.println("ANTES " + o_Buffer.getMaximoCaixa());
				o_Buffer.setMaximoCaixa(o_Buffer.getMaximoCaixa() + quantidadeMensagem); //
                                System.out.println("DEPOIS " + o_Buffer.getMaximoCaixa());
                                //System.out.println("SEMAFORO CAIXA POSTAL ANTES: " + PomboCorreio.semaforoCaixaPostal.availablePermits());
				//PomboCorreio.semaforoCaixaPostal.release(quantidadeMensagem);// Quando a caixa enche, a pessoa dorme. Essa linha acorda quem tava dormindo (Acorda todos, porém a Thread pessoa só permite que 1 pessoa por vez adicione a mensagem.
				//System.out.println("SEMAFORO CAIXA POSTAL DEPOIS: " + PomboCorreio.semaforoCaixaPostal.availablePermits());
                                o_Buffer.setPassaroVoando(false); 
				setBolsaMensagens(new ArrayList<Mensagem>());
				PomboCorreio.mutex.release();
				// A thread passaro está em loop infinito. Após essa última linha ele retorna para começar tudo de novo.
 				// Porém a instrução da primeira linha desse loop é pra ele dormir e só executa o resto (acorda) quanto for acionado.
			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	public JLabel getComponente() {
		return componente;
	}

	public void setComponente(JLabel componente) {
		this.componente = componente;
	}

	public List<Mensagem> getBolsaMensagens() {
		return bolsaMensagens;
	}

	public void setBolsaMensagens(List<Mensagem> list) {
		this.bolsaMensagens = list;
	}

}
