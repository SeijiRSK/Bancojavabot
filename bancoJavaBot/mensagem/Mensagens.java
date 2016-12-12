package com.bancoJavaBot.mensagem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Mensagens {

	private Properties props;
    private String listaMsg = "mensagens.properties";
    public Mensagens(){
            props = new Properties();
            InputStream in = this.getClass().getResourceAsStream(listaMsg);
            try{
                    props.load(in);
                    in.close();
            }
            catch(IOException e){e.printStackTrace();}
    }
    
    public String getValor(String chave){
            return (String)props.getProperty(chave);
    }
}
