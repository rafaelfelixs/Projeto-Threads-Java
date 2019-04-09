import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JLabel;


public class Buffer {

	private JLabel textoPlaca;
	private Boolean caixaCheia;
	Semaphore semaforo;
	
	public Buffer(JLabel Jplaca, Pombo pb) {
		setPombo(pb);
		textoPlaca = Jplaca;
		// TODO Auto-generated constructor stub
	}
	private int maximo = 0;
	private int eliminarPessoa;
	private List<Mensagem> quantidadeAtual = new ArrayList<Mensagem>();
	private int maximoCaixa;
	private Boolean passaroVoando;
	private JLabel textoQuantidadeMensagens;
	private Pombo pombo;
	
	
	public void zerarBuffer(){
		int ultimoItem = quantidadeAtual.size()-1;
		this.setQuantidadeAtual(quantidadeAtual.subList((pombo.getQuantidadeMensagem()-1), ultimoItem));
		this.setMaximo(0);
		this.setMaximoCaixa(getMaximoCaixa()+getPombo().getQuantidadeMensagem());
	}
	
	//adiciona na caixa.
	public void adicionaMensagem(Mensagem mensagem){
		this.quantidadeAtual.add(mensagem);
		this.setMaximo(this.getMaximo() + 1); // CRESCE ATÉ ATINGIR 3
		this.setMaximoCaixa(this.getMaximoCaixa() - 1); // CRESCE ATÉ ATINGIR 3
		textoPlaca.setText(""+ PomboCorreio.semaforoCaixaPostal.availablePermits());
		caixaCheia = getMaximoCaixa()==0? true : false;
		setCaixaCheia(caixaCheia);
	}

	
	
	
	
	
	/*
	 * Apenas métodos de getters e setters
	 */
	
	public Boolean getPassaroVoando() {
		return passaroVoando;
	}

	public void setPassaroVoando(Boolean passaroVoando) {
		this.passaroVoando = passaroVoando;
	}
	
	public List<Mensagem> getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(List<Mensagem> list) {
		this.quantidadeAtual = list;
	}

	public int getMaximo() {
		return maximo;
	}

	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}

	public int getMaximoCaixa() {
		return maximoCaixa;
	}

	public void setMaximoCaixa(int maximoCaixa) {
		this.maximoCaixa = maximoCaixa;
	}

	public Boolean getCaixaCheia() {
		return caixaCheia;
	}

	public void setCaixaCheia(Boolean caixaCheia) {
		this.caixaCheia = caixaCheia;
	}

	public int getEliminarPessoa() {
		return eliminarPessoa;
	}

	public void setEliminarPessoa(int eliminarPessoa) {
		this.eliminarPessoa = eliminarPessoa;
	}

	public Pombo getPombo() {
		return pombo;
	}

	public void setPombo(Pombo pombo) {
		this.pombo = pombo;
	}

}
