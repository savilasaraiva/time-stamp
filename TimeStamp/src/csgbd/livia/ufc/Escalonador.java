package csgbd.livia.ufc;

import java.util.List;

public class Escalonador {

	public boolean[] Copy(int tamanho) {
		boolean[] vetor = new boolean[tamanho];
		for(int i = 0; i < tamanho; i++) {
			vetor[i] = false;
		}
		return vetor;
	}
	
	public boolean isSerializavel(List<Transacao> transacoes) {
		boolean[] transacoes_finais = Copy(transacoes.size());
		int indice = 0; 
		int cont = 0;
		
		/* 
		 * read_TS(X) - rótulo de tempo de leitura;
		 * write_TS(X) - rótulo de tempo de gravação ou escrita;
		 */
		
		for(Transacao t : transacoes) {
			/*
			 * O rótulo de tempo (TO) compara o rótulo de tempo de T com read_TS(X) e write_TS(X) para garantir que o rótulo da 
			 * transação de tempo não seja violada, ou seja, o protocolo de ordenação de timestamp irá garantir
			 * que qualquer operação read ou write que esteja em conflito sejam executadas por ordem de timestamp. 
			 */
			indice++;
			/*LEITURA*/
			if(t.getOperacao().equals("read")) {								// se operação = ‘READ’ então			
				if(t.getTimeStamp() < t.getItem().getWriteTimeStamp()) { 		// se TS(Tx)< R-TS(item).TS-Write então
					System.out.println("Violação do rótulo do tempo!");
					t.setTimeStamp(t.getTimeStamp()+1); 						// abort(Tx) e restart(Tx) com novo TS;
				}else { 														// se não executa read(item);
					if(t.getItem().getReadTimeStamp() < t.getTimeStamp()) { 	// se R-TS(item).TS-Read < TS(Tx) então
						t.getItem().setReadTimeStamp(t.getTimeStamp());			// R-TS(item).TS-Read = TS(Tx);
																				// atualiza o TS do Read com a transação mais 
																				// nova (garantia de serialização)
						
					}
					transacoes_finais[indice - 1] = true;
				}
			}
			
			/*ESCRITA*/
			else {																// senão operação= ‘WRITE’ então*/
				if(t.getTimeStamp() < t.getItem().getReadTimeStamp() || 		// se TS(Tx)< R-TS(item).TS-Read ou 
						t.getTimeStamp() < t.getItem().getWriteTimeStamp()) {	// TS(Tx)< R-TS(item).TS-Write então
					System.out.println("Conflito entre a ação de gravação"
							+ " e leitura do item: "+ t.getItem().getId());
					t.setTimeStamp(t.getTimeStamp()+1);							// abort(Tx) e restart(Tx) com novo TS;
				}else {															// se não executa write(item);
					t.getItem().setWriteTimeStamp(t.getTimeStamp());			// R-TS(item).TS-Write=TS(Tx);
					transacoes_finais[indice - 1] = true;						// atualiza o TS do Read com a transação mais 
																				// nova (garantia de serialização)
				}
			}
		}
		for(boolean b : transacoes_finais) {
			if(b == true) { // verifica se as transações são serializáveis
				cont++;		// conta transações serializáveis
			}
		}
		if(cont == transacoes.size()) // verifica se todas as transações são serializáveis
			return true;
		
		return false;
	}
	
	/*public void lerArquivo(List<Item> itens, List<Transacao> Tn) {
		
	}*/
}
