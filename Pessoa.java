import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JLabel;


public class Pessoa implements Runnable {
	private  Buffer o_Buffer;
	/*private  Semaphore s1,s2, n, mutex, aptoViagem;
	Semaphore semaforoCaixaPostal, auxSemaforoCaixaPostal;*/
	private JLabel componente;
	private JLabel textoDormir;
	private JLabel textoColando;

	private Boolean passaroVoando;

	// Atributos próprios da Pessoa
	
	public JLabel getComponente() {
		return componente;
	}

	public void setComponente(JLabel componente) {
		this.componente = componente;
	}

	private int id;
	private int tempoEscrita;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTempoEscrita() {
		return tempoEscrita;
	}

	public void setTempoEscrita(int tempoEscrita) {
		this.tempoEscrita = tempoEscrita;
	}

	public Boolean getPassaroVoando() {
		return passaroVoando;
	}

	public void setPassaroVoando(Boolean passaroVoando) {
		this.passaroVoando = passaroVoando;
	}

	public void passarTempo (int t1)
	{
        long aux; 
        long inicio = System.currentTimeMillis();
        long tempodefinido = t1 * 1000;
        while (inicio + tempodefinido > System.currentTimeMillis())
        {
            aux = System.currentTimeMillis();
        }
	}
	
	// Construtor da Classe Pessoa
	public Pessoa(int idPessoa, JLabel comp, Buffer bufferpassado, int tempoEscrita){
		super();
		componente = comp;
//		textoDormir = txtDormindo;
//		textoColando = txtColando;
		o_Buffer = bufferpassado;
		/*s1 = sem1;
		s2 = sem2;*/
		id = idPessoa;
		this.tempoEscrita = tempoEscrita;
		/*this.n = n;
		this.mutex = mutex;
		this.aptoViagem = aptoViagem;
		semaforoCaixaPostal = sCaixaPostal;
		auxSemaforoCaixaPostal = auxSCaixa;*/
	}

	// Método que a Thread Pessoa executa quando é instanciada.
	public void run() {
		// TODO Auto-generated method stub

		try {
			while(true){

				if(o_Buffer.getEliminarPessoa()==this.getId()){
					System.out.println("Pessoa "+this.getId()+ " foi excluida");

					componente.setText("Pessoa "+this.getId()+ " foi excluida");
					break;
				}
				
				componente.setText("Pessoa "+this.getId()+ " status: escrevendo.. "+this.getTempoEscrita()+"s.");
				System.out.println("Pessoa "+this.getId()+ " escrevendo ...");
				passarTempo(this.getTempoEscrita());
				Mensagem mensagem = new Mensagem();
				mensagem.setPessoa(this);
				mensagem.setCorpo("Mensagem de: "+this.toString());
				mensagem.setId(1);



				/* OBSERVAÇÃO BUFFER LOTAÇÃO CAIXA
				 *  caso a caixa esteja cheia, dorme e espera a caixa esvaziar para poder adicionar
				 */

				
				// PomboCorreio.mutex.acquire(); // bloqueia o mutex
				//System.out.println("QTDE "+o_Buffer.getMaximoCaixa());
				if (PomboCorreio.semaforoCaixaPostal.availablePermits()==0) 
				{
					componente.setText("Pessoa "+this.getId()+ " status: indo dormir");
					System.out.println("Pessoa "+this.getId()+ " vai dormir ...");
				}

                                System.out.println(PomboCorreio.semaforoCaixaPostal.availablePermits());
                                PomboCorreio.semaforoCaixaPostal.acquire();
                                
				componente.setText("Pessoa "+this.getId()+ " status: colando mensagem");
				System.out.println("Pessoa "+this.getId()+ " colando mensagem ..."+o_Buffer.getMaximoCaixa());
				
				PomboCorreio.mutex.acquire();
				o_Buffer.adicionaMensagem(mensagem);
				PomboCorreio.mutex.release();

				// ACORDA O PASSARO CASO ATINJA O NUMERO DE MENSAGENS
				if(o_Buffer.getMaximo() >= o_Buffer.getPombo().getQuantidadeMensagem() && o_Buffer.getPassaroVoando()==false)
				{
					System.out.println("Pombo pode viajar");
					PomboCorreio.aptoViagem.release();
					PomboCorreio.mutex.acquire();
					o_Buffer.setPassaroVoando(true);
					o_Buffer.getPombo().setBolsaMensagens(
							o_Buffer.getQuantidadeAtual().subList(0, (o_Buffer.getPombo().getQuantidadeMensagem()-1)));
					o_Buffer.zerarBuffer();
					PomboCorreio.mutex.release();

				}


				//PomboCorreio.mutex.release(); // libera o mutex
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public JLabel getTextoColando() {
		return textoColando;
	}

	public void setTextoColando(JLabel textoColando) {
		this.textoColando = textoColando;
	}

	public JLabel getTextoDormir() {
		return textoDormir;
	}

	public void setTextoDormir(JLabel textoDormir) {
		this.textoDormir = textoDormir;
	}
}
