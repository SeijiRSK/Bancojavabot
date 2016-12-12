package com.bancoJavaBot.mensagem;

public enum Comandos {
	
	SAIR("/sair"),
	INICIO("/inicio"),
	START("/start"),
	MENU("/menu"),
	CONTA("/conta"), 
	CADASTRAR("/cadastrar"),
	NOVACONTA("/novaconta"),
	CONTACONJUNTA("/contaconjunta"),
	DEPOSITO("/deposito"),
	EMPRESTIMO("/emprestimo"),
	CONTRATAR ("/contratar"),
	DETALHECNTR("/detalhecontrato"),
	SAQUE("/saque"),
	NOME("/nome"),
	CPF("/cpf"),
	EXTRATO("/extrato"),
	LISTARMOVIMENTOS("/listarmovimentos"),
	ALTERARCADASTRO("/alterarcadastro");
	
	
	private String comando;
	
	
	Comandos(String comando){
		this.comando = comando;
		
	}

	public String getComando() {
		return comando;
	}


	
}
