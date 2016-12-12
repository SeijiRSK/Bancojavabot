package com.bancoJavaBot.programa;

import java.util.List;

import com.bancoJavaBot.objeto.Cliente;
import com.bancoJavaBot.objeto.Conta;
import com.bancoJavaBot.objeto.Detalhe;
import com.bancoJavaBot.objeto.ListaClientes;
import com.bancoJavaBot.objeto.ListaContas;
import com.bancoJavaBot.objeto.Mensagem;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class Main {
	
	public static void main(String[] args) {
		Mensagem mensagem = new Mensagem();
		Detalhe detalhe = new Detalhe();
		
		popularDadosTeste(detalhe.getContas(), detalhe.getClientes());
		
		for(int i = 0; i < detalhe.getContas().getListaConta().size(); i++){
			System.out.println(detalhe.getContas().getListaConta().get(i).toString());
		}
		

		TelegramBot bot = TelegramBotAdapter.build("Coloque aqui sua chave");

		GetUpdatesResponse updatesResponse;

		SendResponse sendResponse;

		BaseResponse baseResponse;

		int m=0;

		while (true){

			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			List<Update> updates = updatesResponse.updates();

			for (Update update : updates) {

				m = update.updateId()+1;
				System.out.println(update.message().text());
				mensagem.setMsgRecebida(update.message().text(), detalhe);
				
				baseResponse = bot.execute(new
						SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

				sendResponse = bot.execute(new SendMessage(update.message().chat().id(),mensagem.getMsgEnviar()));
				
			}
		}
	}
	
	private static void popularDadosTeste(ListaContas contas, ListaClientes clientes){
		
		
		for(int i=0; i<3; i++){
			Cliente cli = new Cliente();
			Conta cnt = new Conta();
			cli.setCpf(1234567+i);
			cli.setNome("Japa" + i);
			cli.setNumConta(i);

			cnt.setNumConta(i);
			cnt.setSaldo(100.0);
			
			contas.getListaConta().add(cnt);
			clientes.getListaClientes().add(cli);
		}
	}
}
