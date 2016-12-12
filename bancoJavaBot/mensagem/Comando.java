package com.bancoJavaBot.mensagem;

import com.bancoJavaBot.contaCorrente.CadastroConta;
import com.bancoJavaBot.contaCorrente.ConsultarConta;
import com.bancoJavaBot.contaCorrente.MovimentoConta;
import com.bancoJavaBot.emprestimo.ContratoEmprestimo;
import com.bancoJavaBot.objeto.Detalhe;
import com.bancoJavaBot.objeto.Mensagem;

public class Comando {

	Mensagens msgProp = new Mensagens();

	public void faseZero(Mensagem msg, Detalhe detalhe){

		if(!msg.getComando().equals(Comandos.CONTA.getComando()) &&
				!msg.getComando().equals(Comandos.CADASTRAR.getComando()) &&
				!msg.getComando().equals(Comandos.START.getComando()) &&
				!msg.getComando().equals(Comandos.INICIO.getComando())){
			msg.setMsgEnviar(comandoInvalido(msg) + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.INICIO") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.CONTA") + "\n"
					+ msgProp.getValor("MSG.COMANDO.CADASTRAR"));

		}else{

			if(msg.getComando().equals(Comandos.CONTA.getComando())){
				msg.setMsgEnviar(msgProp.getValor("MSG.CONTA.UM"));
				msg.setFase(1);
			}

			if(msg.getComando().equals(Comandos.CADASTRAR.getComando())){
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.NOVACONTA") + "\n" 
						+ msgProp.getValor("MSG.COMANDO.CONTACONJUNTA"));
				msg.setFase(1);
			}

			if(msg.getComando().equals(Comandos.INICIO.getComando()) ||	msg.getComando().equals(Comandos.START.getComando())){
				msg.setFase(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.CONTA") + "\n"
						+ msgProp.getValor("MSG.COMANDO.CADASTRAR"));
			}
		}

	}

	public void faseUm(Mensagem msg, Detalhe detalhe){

		if(!msg.getComando().equals(Comandos.NOVACONTA.getComando()) &&
				!msg.getComando().equals(Comandos.CONTACONJUNTA.getComando()) &&
				!msg.getComando().equals(Comandos.CONTA.getComando()) &&
				!msg.getComando().equals(Comandos.START.getComando()) &&
				!msg.getComando().equals(Comandos.INICIO.getComando())){
			msg.setMsgEnviar(comandoInvalido(msg) + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.NOVACONTA") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.CONTACONJUNTA") + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.INICIO"));

		}else{

			if(msg.getComando().equals(Comandos.CONTA.getComando())){
				ConsultarConta acessoConta = new ConsultarConta(msgProp);
				acessoConta.acessarConta(msg, detalhe);
			}

			if(msg.getComando().equals(Comandos.NOVACONTA.getComando())){
				CadastroConta novaConta = new CadastroConta(msgProp);
				novaConta.abrirNovaConta(msg, detalhe);
			}

			if(msg.getComando().equals(Comandos.CONTACONJUNTA.getComando())){
				CadastroConta novaConta = new CadastroConta(msgProp);
				novaConta.abrirContaConjunta(msg, detalhe);
			}

			if(msg.getComando().equals(Comandos.INICIO.getComando()) ||
					msg.getComando().equals(Comandos.START.getComando())){
				msg.setFase(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.CONTA") + "\n"
						+ msgProp.getValor("MSG.COMANDO.CADASTRAR"));
			}

		}
	}

	public void faseDez(Mensagem msg, Detalhe detalhe){

		if(!msg.getComando().equals(Comandos.DEPOSITO.getComando()) &&
				!msg.getComando().equals(Comandos.SAQUE.getComando()) &&
				!msg.getComando().equals(Comandos.EXTRATO.getComando()) &&
				!msg.getComando().equals(Comandos.EMPRESTIMO.getComando()) &&
				!msg.getComando().equals(Comandos.ALTERARCADASTRO.getComando()) &&
				!msg.getComando().equals(Comandos.LISTARMOVIMENTOS.getComando()) &&
				!msg.getComando().equals(Comandos.START.getComando()) &&
				!msg.getComando().equals(Comandos.INICIO.getComando())){
			msg.setMsgEnviar(comandoInvalido(msg) + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.DEPOSITO") + "\n"
					+ msgProp.getValor("MSG.COMANDO.SAQUE") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.EXTRATO") + "\n"
					+ msgProp.getValor("MSG.COMANDO.EMPRESTIMO") + "\n"
					+ msgProp.getValor("MSG.COMANDO.LISTARMOVIMENTOS") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.ALTERARCADASTRO") + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.INICIO"));

		}else{

			if(msg.getComando().equals(Comandos.DEPOSITO.getComando())){
				MovimentoConta movCnt = new MovimentoConta(msgProp);
				movCnt.depoisitar(msg, detalhe.getCnt(), detalhe.getMovimentos());
			}

			if(msg.getComando().equals(Comandos.SAQUE.getComando())){
				MovimentoConta movCnt = new MovimentoConta(msgProp);
				movCnt.sacar(msg, detalhe.getCnt(), detalhe.getMovimentos());
			}

			if(msg.getComando().equals(Comandos.EXTRATO.getComando())){
				MovimentoConta movCnt = new MovimentoConta(msgProp);
				movCnt.exibirExtrato(msg, detalhe.getMovimentos(), detalhe.getCnt());
			}
			
			if(msg.getComando().equals(Comandos.LISTARMOVIMENTOS.getComando())){
				MovimentoConta movCnt = new MovimentoConta(msgProp);
				movCnt.exibirMovimentos(msg, detalhe.getMovimentos(), detalhe.getCnt());
			}

			if(msg.getComando().equals(Comandos.EMPRESTIMO.getComando())){
				msg.setFase(30);
				msg.setFaseComando(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.CONTRATAR") + "\n"
						+ msgProp.getValor("MSG.COMANDO.DETALHARCNTR"));
			}

			if(msg.getComando().equals(Comandos.ALTERARCADASTRO.getComando())){
				msg.setFase(20);
				msg.setFaseComando(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.NOME") + "\n"
						+ msgProp.getValor("MSG.COMANDO.CPF"));
			}

			if(msg.getComando().equals(Comandos.INICIO.getComando()) ||
					msg.getComando().equals(Comandos.START.getComando())){
				msg.setFase(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.CONTA") + "\n"
						+ msgProp.getValor("MSG.COMANDO.CADASTRAR"));
			}


		}


	}

	public void faseVinte(Mensagem msg, Detalhe detalhe){

		if(!msg.getComando().equals(Comandos.NOME.getComando()) &&
				!msg.getComando().equals(Comandos.CPF.getComando()) &&
				!msg.getComando().equals(Comandos.START.getComando()) &&
				!msg.getComando().equals(Comandos.INICIO.getComando())){
			msg.setMsgEnviar(comandoInvalido(msg) + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.NOME") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.CPF") + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.INICIO"));

		}else{

			if(msg.getComando().equals(Comandos.NOME.getComando())){
				CadastroConta novaConta = new CadastroConta(msgProp);
				novaConta.alterarNome(msg, detalhe.getCli(), detalhe.getMovimentos(), detalhe.getCnt());
			}

			if(msg.getComando().equals(Comandos.CPF.getComando())){
				CadastroConta novaConta = new CadastroConta(msgProp);
				novaConta.alterarCPF(msg, detalhe.getCli(), detalhe.getMovimentos(), detalhe.getCnt());
			}

			if(msg.getComando().equals(Comandos.INICIO.getComando()) ||
					msg.getComando().equals(Comandos.START.getComando())){
				msg.setFase(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.CONTA") + "\n"
						+ msgProp.getValor("MSG.COMANDO.CADASTRAR"));
			}
		}

	}
	public void faseTrinta(Mensagem msg, Detalhe detalhe){

		if(!msg.getComando().equals(Comandos.CONTRATAR.getComando()) &&
				!msg.getComando().equals(Comandos.DETALHECNTR.getComando()) &&
				!msg.getComando().equals(Comandos.START.getComando()) &&
				!msg.getComando().equals(Comandos.INICIO.getComando())){
			msg.setMsgEnviar(comandoInvalido(msg) + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.CONTRATAR") + "\n" 
					+ msgProp.getValor("MSG.COMANDO.DETALHARCNTR") + "\n\n"
					+ msgProp.getValor("MSG.COMANDO.INICIO"));

		}else{
			if(msg.getComando().equals(Comandos.CONTRATAR.getComando())){
				ContratoEmprestimo emprestimo = new ContratoEmprestimo(msgProp);
				emprestimo.contratar(msg, detalhe.getOperacao(), detalhe.getCnt(), detalhe.getMovimentos());
			}

			if(msg.getComando().equals(Comandos.DETALHECNTR.getComando())){
				ContratoEmprestimo emprestimo = new ContratoEmprestimo(msgProp);
				emprestimo.detalharOperacao(msg, detalhe.getOperacao());
			}

			if(msg.getComando().equals(Comandos.INICIO.getComando()) ||
					msg.getComando().equals(Comandos.START.getComando())){
				msg.setFase(0);
				msg.setMsgEnviar(msgProp.getValor("MSG.COMANDO.CONTA") + "\n"
						+ msgProp.getValor("MSG.COMANDO.CADASTRAR"));
			}

		}
	}

	public String comandoInvalido(Mensagem msg){
		return "Desculpe, mas seu comando não foi compreendido. \n"
				+ "Poderia repetir o comando?";
	}
}
