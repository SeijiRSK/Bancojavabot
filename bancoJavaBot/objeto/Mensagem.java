package com.bancoJavaBot.objeto;

import com.bancoJavaBot.mensagem.Comando;

public class Mensagem {
	
	private String msgRecebida;
	private String msgEnviar;
	private String comando;
	private int faseComando = 0;
	private int fase = 0;

	
	public String getMsgRecebida() {
		return msgRecebida;
	}
	
	public void setMsgRecebida(String msgRecebida, Detalhe detalhe) {
		
		this.msgRecebida = "";
		this.msgEnviar = "";
		
		Comando comandoFase = new Comando();

		this.msgRecebida = msgRecebida;
		
		if (this.comando == null){
			this.comando = "";
		}
		
		

		
		if (this.msgRecebida.substring(0, 1).equals("/")){
			comando = this.msgRecebida;
		}
		
		
		System.out.println(" [Recebendo MENSAGEM:"+ this.msgRecebida + " COMANDO: " + this.comando + " FASE: " + this.fase + " FASE_COMANDO: " + this.faseComando + "]");
		switch(fase){
		case 0:
			comandoFase.faseZero(this, detalhe);
			break;
		case 1:
			comandoFase.faseUm(this, detalhe);
			break;
		case 10 :
			comandoFase.faseDez(this, detalhe);
			break;
		case 20 :
			comandoFase.faseVinte(this, detalhe);
			break;
		case 30 :
			comandoFase.faseTrinta(this, detalhe);
			break;
		}
		
		System.out.println(this.toString());


	}
	public String getMsgEnviar() {
		return msgEnviar;
	}
	
	public void setMsgEnviar(String msgEnviar) {
		this.msgEnviar = msgEnviar;
	}

	
	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public int getFase() {
		return fase;
	}
	public void setFase(int fase) {
		this.fase = fase;
	}
	
	

	public int getFaseComando() {
		return faseComando;
	}

	public void setFaseComando(int faseComando) {
		this.faseComando = faseComando;
	}

	@Override
	public String toString() {
		return "Mensagem [msgRecebida=" + msgRecebida + ", \nmsgEnviar=" + msgEnviar + ", \ncomando=" + comando
				+ ", \nfaseComando=" + faseComando + ", \nfase=" + fase + "]";
	}


	
	

}
