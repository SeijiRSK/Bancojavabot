package com.bancoJavaBot.emprestimo;

import com.bancoJavaBot.mensagem.Comandos;
import com.bancoJavaBot.mensagem.Mensagens;
import com.bancoJavaBot.objeto.Conta;
import com.bancoJavaBot.objeto.ListaMovimentos;
import com.bancoJavaBot.objeto.Mensagem;
import com.bancoJavaBot.objeto.Movimento;
import com.bancoJavaBot.objeto.Operacao;

public class ContratoEmprestimo {

	Mensagens msgProp;

	public ContratoEmprestimo(Mensagens msgProp){
		this.msgProp = msgProp;
	}

	public void contratar(Mensagem msg, Operacao ope, Conta cnt, ListaMovimentos mov){

		switch(msg.getFaseComando()){
		case 0 :
			msg.setMsgEnviar(msgProp.getValor("MSG.EMPRESTIMO.SALDO") + cnt.getSaldo() + "\n"
					+ msgProp.getValor("MSG.EMPRESTIMO.UM"));
			msg.setFaseComando(msg.getFaseComando() + 1);
			break;
		case 1:
			if(validaValor(msg, ope, cnt)){
				msg.setMsgEnviar(msgProp.getValor("MSG.EMPRESTIMO.DOIS"));
				msg.setFaseComando(msg.getFaseComando() + 1);
			}
			break;
		case 2:
			if(validaPrazo(msg, ope, cnt)){
				ope.setNumConta(cnt.getNumConta());
				ope.setNumOperacao(ope.getNumOperacao() + 1);
				cnt.setSaldo(cnt.getSaldo() + ope.getValorOperacao());
				cnt.setSaldo(cnt.getSaldo() - 15.0);
				msg.setMsgEnviar(msgProp.getValor("MSG.EMPRESTIMO.TRES") + ope.getNumOperacao()+"\n\n"
						+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
						+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO"));
				msg.setFase(10);
				msg.setFaseComando(0);
				msg.setComando(Comandos.MENU.getComando());
				mov.getListaMovimento().add(new Movimento("I", "Contratação Emprestimo", 0,cnt.getNumConta()));
				mov.getListaMovimento().add(new Movimento("T", "Taxa Emprestimo", 15.0,cnt.getNumConta()));
				mov.getListaMovimento().add(new Movimento("D", "Valor Contratado", ope.getValorOperacao(),cnt.getNumConta()));
				mov.getListaMovimento().add(new Movimento("I", "Saldo Devedor Emprestimo", ope.getSaldoDevedor(),cnt.getNumConta()));
			}
			break;
		}

	}

	public boolean validaValor(Mensagem msg, Operacao ope, Conta cnt){
		try{
			double valorMsg = Double.parseDouble(msg.getMsgRecebida());
			if (valorMsg <= cnt.getSaldo()*40){
				ope.setValorOperacao(valorMsg);
				return true;
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.EMPRESTIMO.VALOROPE") + "\n\n"
						+msgProp.getValor("MSG.EMPRESTIMO.SALDO") + cnt.getSaldo() + "\n"
						+ msgProp.getValor("MSG.EMPRESTIMO.UM"));
				return false;
			}
		}catch(Exception e){
			msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMEROVALOR") + "\n\n"
					+msgProp.getValor("MSG.EMPRESTIMO.SALDO") + cnt.getSaldo() + "\n"
					+ msgProp.getValor("MSG.EMPRESTIMO.UM"));
			return false;
		}
	}

	public boolean validaPrazo(Mensagem msg, Operacao ope, Conta cnt){
		try{
			int valorMsg = Integer.parseInt(msg.getMsgRecebida());
			if (valorMsg <= 30){
				ope.setPrazoMeses(valorMsg);
				return true;
			}else{
				msg.setMsgEnviar(msgProp.getValor("MSG.EMPRESTIMO.VALOROPE") + "\n\n"
						+msgProp.getValor("MSG.EMPRESTIMO.DOIS"));
				return false;
			}
		}catch(Exception e){
			msg.setMsgEnviar(msgProp.getValor("MSG.ERRO.NUMERO") + "\n\n"
					+msgProp.getValor("MSG.EMPRESTIMO.DOIS"));
			return false;
		}
	}

	public void detalharOperacao(Mensagem msg, Operacao ope){
		String mensagem = "";

		mensagem = msgProp.getValor("MSG.EMPRESTIMO.DETALHE") + "\n"
				+ msgProp.getValor("MSG.EMPRESTIMO.DETALHE.NUMOPE") + ope.getNumOperacao()  + "\n"
				+ msgProp.getValor("MSG.EMPRESTIMO.DETALHE.DATA") + ope.getDataContracao()  + "\n"
				+ msgProp.getValor("MSG.EMPRESTIMO.DETALHE.VALORCNTR") + ope.getValorOperacao()  + "\n"
				+ msgProp.getValor("MSG.EMPRESTIMO.DETALHE.PRAZO") + ope.getPrazoMeses()  + "\n"
				+ msgProp.getValor("MSG.EMPRESTIMO.DETALHE.VALORPARCELA") + ope.getValorParcela()  + "\n"
				+ msgProp.getValor("MSG.EMPRESTIMO.DETALHE.SALDODEVOR") + ope.getSaldoDevedor()  + "\n\n"
				+ msgProp.getValor("MSG.COMANDO.DEPOSITO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.SAQUE")+" \n"
				+ msgProp.getValor("MSG.COMANDO.EXTRATO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO")+" \n"
				+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
				+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO");
		msg.setMsgEnviar(mensagem);
		msg.setFase(10);
		msg.setFaseComando(10);
		msg.setComando(Comandos.MENU.getComando());
	}

}
