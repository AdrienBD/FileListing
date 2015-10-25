package message;

public enum MessageTitle {

	LAUNCHABLE(""),
	LAUNCHED("Launched"),
	PROCESSED("Processed"),
	ERROR_ON_EXPORT("Error while writing to file");
	
	private String status;
	
	MessageTitle(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
