package com.bancoJavaBot.contaCorrente;

import com.bancoJavaBot.mensagem.Comandos;
import com.bancoJavaBot.mensagem.Mensagens;
import com.bancoJavaBot.objeto.Conta;
import com.bancoJavaBot.objeto.ListaMovimentos;
import com.bancoJavaBot.objeto.Mensagem;
import com.bancoJavaBot.objeto.Movimento;

public class MovimentoConta {

	Mensagens msgProp;

	public MovimentoConta(Mensagens msgProp){
		this.msgProp = msgProp;
	}


	public void depoisitar(Mensagem msg, Conta cnt, ListaMovimentos mov){


		switch(msg.getFaseComando()){
		case 0 :
			msg.setMsgEnviar(msgProp.getValor("MSG.DEPOSITO.UM"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			try{
				cnt.setSaldo(cnt.getSaldo() +  Double.parseDouble(msg.getMsgRecebida()));

				msg.setMsgEnviar(msgProp.getValor("MSG.DEPOSITO.DOIS") + cnt.getSaldo() +"\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				mov.getListaMovimento().add(new Movimento("D", "Deposito", Double.parseDouble(msg.getMsgRecebida()),cnt.getNumConta()));
			}catch(Exception e){
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMEROVALOR") + "\n\n"
						+msgProp.getValor("MSG.DEPOSITO.UM"));
			}
			break;
		}

	}

	public void sacar(Mensagem msg, Conta cnt, ListaMovimentos mov){

		switch(msg.getFaseComando()){
		case 0 :
			msg.setMsgEnviar(msgProp.getValor("MSG.SAQUE.UM"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			try{
				double valor = Double.parseDouble(msg.getMsgRecebida()) + 2.5;

				cnt.setSaldo(cnt.getSaldo() - valor);

				msg.setMsgEnviar(msgProp.getValor("MSG.SAQUE.DOIS") + cnt.getSaldo() +"\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				mov.getListaMovimento().add(new Movimento("T", "Taxa Saque", 2.5,cnt.getNumConta()));
				mov.getListaMovimento().add(new Movimento("S", "Saque", Double.parseDouble(msg.getMsgRecebida()),cnt.getNumConta()));
			}catch(Exception e){
				msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMEROVALOR") + "\n\n"
						+msgProp.getValor("MSG.DEPOSITO.UM"));
			}
			break;
		}
	}

	public void exibirExtrato(Mensagem msg, ListaMovimentos mov, Conta cnt){
		String extrato = "";

		mov.getListaMovimento().add(new Movimento("T", "Taxa Extrato", 1.0,cnt.getNumConta()));

		cnt.setSaldo(cnt.getSaldo() - 1.0);

		for (int i=0; i <mov.getListaMovimento().size(); i++){
			if(mov.getListaMovimento().get(i).getNumConta() == cnt.getNumConta()){
				if(!mov.getListaMovimento().get(i).getTipo().equals("I")){
					extrato += mov.getListaMovimento().get(i).getData() + " | "
							+  mov.getListaMovimento().get(i).getTipo() + " | "
							+  mov.getListaMovimento().get(i).getDescricao() + " | "
							+  mov.getListaMovimento().get(i).getValor() + "\n";
				}
			}
		}

		extrato += "\n" + "SALDO ATUAL = " + cnt.getSaldo();

		msg.setMsgEnviar(extrato + "\n\n"
				+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
				+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
				+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
		msg.setFase(10);
		msg.setFaseComando(0);
		msg.setComando(Comandos.MENU.getComando());

	}

	public void exibirMovimentos(Mensagem msg, ListaMovimentos mov, Conta cnt){
		String listaMovimento = "";
		double taxa = 0;
		double saque = 0;
		double deposito = 0;

		for (int i=0; i <mov.getListaMovimento().size(); i++){
			if(mov.getListaMovimento().get(i).getNumConta() == cnt.getNumConta()){
				listaMovimento += mov.getListaMovimento().get(i).getData() + " | "
						+  mov.getListaMovimento().get(i).getTipo() + " | "
						+  mov.getListaMovimento().get(i).getDescricao() + " | "
						+  mov.getListaMovimento().get(i).getValor() + "\n";
				switch(mov.getListaMovimento().get(i).getTipo()){
				case "T":
					taxa = taxa + mov.getListaMovimento().get(i).getValor();
					break;
				case "S":
					saque = saque + mov.getListaMovimento().get(i).getValor();
					break;
				case "D":
					deposito = deposito + mov.getListaMovimento().get(i).getValor();
					break;
				}
			}
		}

		listaMovimento += "\n" + "TOTAL TAXA = " + taxa;
		listaMovimento += "\n" + "TOTAL DEPOSITO = " + deposito;
		listaMovimento += "\n" + "TOTAL SAQUE = " + saque;
		listaMovimento += "\n" + "SALDO ATUAL = " + cnt.getSaldo();

		msg.setMsgEnviar(listaMovimento + "\n\n"
				+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
				+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
				+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
		msg.setFase(10);
		msg.setFaseComando(0);
		msg.setComando(Comandos.MENU.getComando());

	}
}
