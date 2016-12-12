package com.bancoJavaBot.contaCorrente;

import com.bancoJavaBot.mensagem.Comandos;
import com.bancoJavaBot.mensagem.Mensagens;
import com.bancoJavaBot.objeto.Cliente;
import com.bancoJavaBot.objeto.Conta;
import com.bancoJavaBot.objeto.ListaClientes;
import com.bancoJavaBot.objeto.ListaContas;
import com.bancoJavaBot.objeto.Mensagem;
import com.bancoJavaBot.objeto.Detalhe;

public class ConsultarConta {

	Mensagens msgProp;

	public ConsultarConta(Mensagens msgProp){
		this.msgProp = msgProp;
	}

	public void acessarConta(Mensagem msg, Detalhe detalhe){

		if (validaConta(msg, detalhe)){
			detalhe.setCli(obterCliente(Integer.parseInt(msg.getMsgRecebida()), detalhe.getClientes()));
			detalhe.setCnt(obterConta(Integer.parseInt(msg.getMsgRecebida()), detalhe.getContas()));

			msg.setMsgEnviar("Ola,  " + detalhe.getCli().getNome() +"\n\n"
					+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
					+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
					+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
					+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
					+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
			msg.setFase(10);
			msg.setFaseComando(0);
			msg.setComando(Comandos.MENU.getComando());
		}else{
			msg.setMsgEnviar(msgProp.getValor(".MSG.ERRO.NUMERO") + "\n\n"
					+msgProp.getValor("MSG.CONTA.UM"));
		}


	}

	public Conta obterConta(int numConta, ListaContas contas){
		return contas.getListaConta().stream()
				.filter(c1 -> c1.getNumConta() == numConta)
				.findAny().get();
	}

	public Cliente obterCliente(int numConta, ListaClientes clientes){
		return clientes.getListaClientes().stream()
				.filter(c1 -> c1.getNumConta() == numConta)
				.findAny().get();
	}

	public boolean validaConta(Mensagem msg, Detalhe detalhe){
		try{
			detalhe.setCnt(detalhe.getContas().getListaConta().stream()
					.filter(c1 -> c1.getNumConta() == Integer.parseInt(msg.getMsgRecebida()))
					.findAny().get());

			detalhe.getCli().setNumConta(detalhe.getCnt().getNumConta());
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
