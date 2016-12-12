package com.bancoJavaBot.contaCorrente;

import com.bancoJavaBot.mensagem.Comandos;
import com.bancoJavaBot.mensagem.Mensagens;
import com.bancoJavaBot.objeto.Cliente;
import com.bancoJavaBot.objeto.Conta;
import com.bancoJavaBot.objeto.Detalhe;
import com.bancoJavaBot.objeto.ListaContas;
import com.bancoJavaBot.objeto.ListaMovimentos;
import com.bancoJavaBot.objeto.Mensagem;
import com.bancoJavaBot.objeto.Movimento;

public class CadastroConta {

	Mensagens msgProp;

	public CadastroConta(Mensagens msgProp){
		this.msgProp = msgProp;
	}


	public void abrirNovaConta(Mensagem msg, Detalhe detalhe){
		switch(msg.getFaseComando()){
		case 0:
			msg.setMsgEnviar(msgProp.getValor("MSG.NOVACONTA.UM"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			if (validaNome(msg, detalhe.getCli())){
				msg.setMsgEnviar(msgProp.getValor("MSG.NOVACONTA.DOIS"));
				msg.setFaseComando(msg.getFaseComando() + 1);
			}else{
				msg.setMsgEnviar(msgProp.getValor(".MSG.ERRO.NOCOMANDO") + "\n\n"
						+msgProp.getValor("MSG.NOVACONTA.UM"));
			}
			break;
		case 2:
			if (validaCPF(msg, detalhe.getCli())){

				detalhe.setCnt(novaConta(detalhe.getContas()));

				msg.setMsgEnviar(msgProp.getValor("MSG.NOVACONTA.TRES") + detalhe.getCnt().getNumConta() +"\n\n"
						+ "Ola,  " + detalhe.getCli().getNome() +"\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				detalhe.getClientes().getListaClientes().add(detalhe.getCli());
				detalhe.getMovimentos().getListaMovimento().add(new Movimento("I", "Abertura de conta", 0, detalhe.getCnt().getNumConta()));
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMERO") + "\n\n"
						+msgProp.getValor("MSG.NOVACONTA.DOIS"));
			}
			break;
		}
	}


	public void abrirContaConjunta(Mensagem msg, Detalhe detalhe){
		switch(msg.getFaseComando()){
		case 0:
			msg.setMsgEnviar(msgProp.getValor("MSG.CONTACONJUNTA.UM"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			if (validaConta(msg, detalhe)){
				msg.setMsgEnviar(msgProp.getValor("MSG.CONTACONJUNTA.DOIS"));
				msg.setFaseComando(msg.getFaseComando() + 1);
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMERO") + "\n\n"
						+msgProp.getValor("MSG.NOVACONTA.UM"));
			}
			break;
		case 2:
			if (validaNome(msg, detalhe.getCli())){
				msg.setMsgEnviar(msgProp.getValor("MSG.CONTACONJUNTA.TRES"));
				msg.setFaseComando(msg.getFaseComando() + 1);
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NOCOMANDO") + "\n\n"
						+msgProp.getValor("MSG.CONTACONJUNTA.DOIS"));
			}
			break;
		case 3:
			if (validaCPF(msg, detalhe.getCli())){

				detalhe.setCnt(novaConta(detalhe.getContas()));

				msg.setMsgEnviar("Sua conta conjunta é " + detalhe.getCnt().getNumConta() +"\n\n"
						+ "Ola,  " + detalhe.getCli().getNome() +"\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				detalhe.getClientes().getListaClientes().add(detalhe.getCli());
				detalhe.getMovimentos().getListaMovimento().add(new Movimento("I", "Abertura de conta conjunta", 0, detalhe.getCnt().getNumConta()));

			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMERO") + "\n\n"
						+msgProp.getValor("MSG.CONTACONJUNTA.UM"));
			}
			break;
		}
	}


	public boolean validaNome(Mensagem msg, Cliente cli){
		if(msg.getMsgRecebida().substring(0, 1).equals("/")){
			return false;
		}else{
			cli.setNome(msg.getMsgRecebida());
			return true;
		}
	}

	public boolean validaCPF(Mensagem msg, Cliente cli){
		try{
			cli.setCpf(Integer.parseInt(msg.getMsgRecebida()));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean validaConta(Mensagem msg,Detalhe detalhe){
		try{
			detalhe.setCnt(detalhe.getContas().getListaConta().stream()
					.filter(c1 -> c1.getNumConta() == Integer.parseInt(msg.getMsgRecebida()))
					.findAny().get());

			detalhe.getCli().setNumConta(detalhe.getCnt().getNumConta());
			return true;
		}catch (Exception e ){
			return false;
		}
	}

	public Conta novaConta(ListaContas contas){
		Conta novacnt = new Conta();

		novacnt = contas.getListaConta().stream().max((c1,c2)->Integer.compare(c1.getNumConta(),c2.getNumConta())).get();

		novacnt.setNumConta(novacnt.getNumConta()+1);

		novacnt.setSaldo(0);

		contas.getListaConta().add(novacnt);

		return novacnt;

	}

	public void alterarNome(Mensagem msg, Cliente cli, ListaMovimentos mov, Conta cnt){
		switch(msg.getFaseComando()){
		case 0:
			msg.setMsgEnviar(msgProp.getValor("MSG.ALTERARNOME.NOME"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			if(validaNome(msg, cli)){
				msg.setMsgEnviar(msgProp.getValor("MSG.ALTERARNOME.RETORNO")+"\n\n"
						+ cli.getNome() + "\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				mov.getListaMovimento().add(new Movimento("I", "Alterar nome", 0, cnt.getNumConta()));
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NOCOMANDO") + "\n\n"
						+msgProp.getValor("MSG.ALTERARNOME.NOME"));
			}
			break;
		}

	}

	public void alterarCPF(Mensagem msg, Cliente cli, ListaMovimentos mov, Conta cnt){
		switch(msg.getFaseComando()){
		case 0:
			msg.setMsgEnviar(msgProp.getValor("MSG.ALTERARNOME.CPF"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			if(validaCPF(msg, cli)){
				msg.setMsgEnviar(msgProp.getValor("MSG.ALTERARCPF.RETORNO")+"\n\n"
						+ "Nome : " + cli.getNome() + "\n" 
						+ "Cpf : " + cli.getCpf() + "\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				mov.getListaMovimento().add(new Movimento("I", "Alterar cpf", 0, cnt.getNumConta()));
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMERO") + "\n\n"
						+msgProp.getValor("MSG.ALTERARNOME.CPF"));
			}
			break;
		}

	}

}
