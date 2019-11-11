package csgbd.livia.ufc;

public class Transacao {
	private int timeStamp;
	private Item item;
	private String operacao;
	
	public Transacao(int timeStamp, Item item, String operacao) {
		super();
		this.timeStamp = timeStamp;
		this.item = item;
		this.operacao = operacao;
	}
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

}
