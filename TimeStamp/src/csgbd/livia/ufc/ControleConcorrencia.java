package csgbd.livia.ufc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ControleConcorrencia {
	
	public static void main(String[] args) {
		
		Escalonador S1 = new Escalonador();
		List<Transacao> Tn = new ArrayList<>();
		List<Item> itens = new ArrayList<>();
		
		Item a = new Item("a", 0, 0);
		Item b = new Item("b", 0, 0);
		Item c = new Item("c", 0, 0);
		
		itens.add(a);
		itens.add(b);
		itens.add(c);
		
		//S1.lerArquivo(itens, Tn);
			
		try {
			String urlArquivo = ("C:\\Users\\Alivas\\eclipse-workspace\\TimeStamp\\src\\csgbd\\livia\\ufc\\arquivo_01.txt");
			FileInputStream file = new FileInputStream(urlArquivo);
			InputStreamReader isr = new java.io.InputStreamReader(file);
			BufferedReader br = new BufferedReader(isr);
			String linha;
			do {
				linha = br.readLine();
				if (linha != null) {
					String[] spl = linha.split(" ");
					int timeStamp = Integer.valueOf(spl[0]);
					String id = spl[1];
					String operacao = spl[2];
						
					for (Item item : itens) {
						if(id.equals(item.getId())){
							Transacao trans = new Transacao(timeStamp, item, operacao);
							Tn.add(trans);
						}
					}	
				}
			} while (linha != null);
			file.close();
			isr.close();
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
		for(Transacao transacao : Tn) {			
			System.out.println(transacao.getOperacao() + " " + transacao.getTimeStamp() + " "  + transacao.getItem().getId());
		}
		
		if(S1.isSerializavel(Tn)) {
			System.out.println("O escalonamento das transações é serializável");
		}else {
			System.out.println("O escalonamento das transações não é serializável");
		}

	}	
	
}
