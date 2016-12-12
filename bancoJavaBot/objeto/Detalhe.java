package com.bancoJavaBot.objeto;

public class Detalhe {
	private Cliente cli = new Cliente(); 
	private Conta cnt = new Conta();
	private ListaContas contas= new ListaContas(); 
	private ListaClientes clientes = new ListaClientes();
	private ListaMovimentos movimentos = new ListaMovimentos();
	private Operacao operacao= new Operacao();
	
	public Cliente getCli() {
		return cli;
	}
	public void setCli(Cliente cli) {
		this.cli = cli;
	}
	public Conta getCnt() {
		return cnt;
	}
	public void setCnt(Conta cnt) {
		this.cnt = cnt;
	}
	public ListaContas getContas() {
		return contas;
	}
	public void setContas(ListaContas contas) {
		this.contas = contas;
	}
	public ListaClientes getClientes() {
		return clientes;
	}
	public void setClientes(ListaClientes clientes) {
		this.clientes = clientes;
	}
	public ListaMovimentos getMovimentos() {
		return movimentos;
	}
	public void setMovimentos(ListaMovimentos movimentos) {
		this.movimentos = movimentos;
	}
	public Operacao getOperacao() {
		return operacao;
	}
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	
	
}
