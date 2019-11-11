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
		 * read_TS(X) - r�tulo de tempo de leitura;
		 * write_TS(X) - r�tulo de tempo de grava��o ou escrita;
		 */
		
		for(Transacao t : transacoes) {
			/*
			 * O r�tulo de tempo (TO) compara o r�tulo de tempo de T com read_TS(X) e write_TS(X) para garantir que o r�tulo da 
			 * transa��o de tempo n�o seja violada, ou seja, o protocolo de ordena��o de timestamp ir� garantir
			 * que qualquer opera��o read ou write que esteja em conflito sejam executadas por ordem de timestamp. 
			 */
			indice++;
			/*LEITURA*/
			if(t.getOperacao().equals("read")) {								// se opera��o = �READ� ent�o			
				if(t.getTimeStamp() < t.getItem().getWriteTimeStamp()) { 		// se TS(Tx)< R-TS(item).TS-Write ent�o
					System.out.println("Viola��o do r�tulo do tempo!");
					t.setTimeStamp(t.getTimeStamp()+1); 						// abort(Tx) e restart(Tx) com novo TS;
				}else { 														// se n�o executa read(item);
					if(t.getItem().getReadTimeStamp() < t.getTimeStamp()) { 	// se R-TS(item).TS-Read < TS(Tx) ent�o
						t.getItem().setReadTimeStamp(t.getTimeStamp());			// R-TS(item).TS-Read = TS(Tx);
																				// atualiza o TS do Read com a transa��o mais 
																				// nova (garantia de serializa��o)
						
					}
					transacoes_finais[indice - 1] = true;
				}
			}
			
			/*ESCRITA*/
			else {																// sen�o opera��o= �WRITE� ent�o*/
				if(t.getTimeStamp() < t.getItem().getReadTimeStamp() || 		// se TS(Tx)< R-TS(item).TS-Read ou 
						t.getTimeStamp() < t.getItem().getWriteTimeStamp()) {	// TS(Tx)< R-TS(item).TS-Write ent�o
					System.out.println("Conflito entre a a��o de grava��o"
							+ " e leitura do item: "+ t.getItem().getId());
					t.setTimeStamp(t.getTimeStamp()+1);							// abort(Tx) e restart(Tx) com novo TS;
				}else {															// se n�o executa write(item);
					t.getItem().setWriteTimeStamp(t.getTimeStamp());			// R-TS(item).TS-Write=TS(Tx);
					transacoes_finais[indice - 1] = true;						// atualiza o TS do Read com a transa��o mais 
																				// nova (garantia de serializa��o)
				}
			}
		}
		for(boolean b : transacoes_finais) {
			if(b == true) { // verifica se as transa��es s�o serializ�veis
				cont++;		// conta transa��es serializ�veis
			}
		}
		if(cont == transacoes.size()) // verifica se todas as transa��es s�o serializ�veis
			return true;
		
		return false;
	}
	
	/*public void lerArquivo(List<Item> itens, List<Transacao> Tn) {
		
	}*/
}
