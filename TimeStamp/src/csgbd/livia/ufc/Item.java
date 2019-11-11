package csgbd.livia.ufc;

public class Item {
	
	/*
	 * Classe referente aos itens a 
	 * que serão usados nas transações
	 */

	private String id;
	private int readTimeStamp;
	private int writeTimeStamp;
	
	public Item(String id, int readTimeStamp, int writeTimeStamp) {
		this.id = id;
		this.readTimeStamp = readTimeStamp;
		this.writeTimeStamp = writeTimeStamp;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReadTimeStamp() {
		return readTimeStamp;
	}
	public void setReadTimeStamp(int readTimeStamp) {
		this.readTimeStamp = readTimeStamp;
	}
	public int getWriteTimeStamp() {
		return writeTimeStamp;
	}
	public void setWriteTimeStamp(int writeTimeStamp) {
		this.writeTimeStamp = writeTimeStamp;
	}	
}
