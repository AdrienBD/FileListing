package message;

public class Message {

	public MessageTitle title;
	
	public Message(MessageTitle title) {
		this.title = title;
	}
	
	public String getStatus() {
		return title.getStatus();
	}
}
