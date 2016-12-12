package com.bancoJavaBot.objeto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movimento {
	
	private String data;
	//Date hora;
	private String tipo;
	private String descricao;
	private double valor;
	private int numConta;
	
	public Movimento(String tipo, String descricao, double valor, int numConta){
		String formatoData = "dd/MM";
		//String formatoHora
		Date dataAtual = new Date();
		SimpleDateFormat  data = new SimpleDateFormat(formatoData);
		
		this.data = data.format(dataAtual);
		//this.hora = dataHora;
		this.descricao = descricao;
		this.tipo = tipo;
		this.valor = valor;
		this.numConta = numConta;
	}

	public String getData() {
		return data;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getValor() {
		return valor;
	}

	
	
	public int getNumConta() {
		return numConta;
	}

	public void setNumConta(int numConta) {
		this.numConta = numConta;
	}

	@Override
	public String toString() {
		return "Movimento [data=" + data + ", tipo=" + tipo + ", descricao=" + descricao
				+ ", valor=" + valor + "]";
	}
	
	

}
