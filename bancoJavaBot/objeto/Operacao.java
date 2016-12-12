package com.bancoJavaBot.objeto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Operacao {
	
	private int numConta;
	private int numOperacao;
	private String dataContracao;
	private int prazoMeses;
	private double valorOperacao;
	private double valorParcela;
	private double saldoDevedor;
	public int getNumConta() {
		return numConta;
	}
	public void setNumConta(int numConta) {
		this.numConta = numConta;
	}
	public int getNumOperacao() {
		return numOperacao;
	}
	public void setNumOperacao(int numOperacao) {
		String formatoData = "dd/MM/yyyy";
		//String formatoHora
		Date dataAtual = new Date();
		SimpleDateFormat  data = new SimpleDateFormat(formatoData);
		
		this.dataContracao = data.format(dataAtual);
		this.numOperacao = numOperacao;
	}
	
	public String getDataContracao() {
		return dataContracao;
	}

	public int getPrazoMeses() {
		return prazoMeses;
	}
	public void setPrazoMeses(int prazoMeses) {
		this.prazoMeses = prazoMeses;
	}
	public double getValorOperacao() {
		return valorOperacao;
	}
	public void setValorOperacao(double valorOperacao) {
		this.valorOperacao = valorOperacao;
	}
			
	public double getValorParcela() {
		valorParcela = (valorOperacao/prazoMeses)+((valorOperacao/prazoMeses)*0.05);
		return valorParcela;
	}
		
	public double getSaldoDevedor() {
		
		return saldoDevedor = valorParcela * prazoMeses;
	}
	
	

}
