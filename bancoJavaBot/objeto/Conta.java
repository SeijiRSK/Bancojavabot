package com.bancoJavaBot.objeto;

public class Conta {

	private int numConta;
	private double saldo;
	
	public int getNumConta() {
		return numConta;
	}
	public void setNumConta(int numConta) {
		this.numConta = numConta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "Conta [numConta=" + numConta + ", saldo=" + saldo + "]";
	}
	
	
}
