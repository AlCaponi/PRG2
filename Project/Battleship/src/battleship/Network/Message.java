package battleship.Network;

public class Message {

	private eMessageType messageType;
	private Object dataContainer;

	public eMessageType getMessageType() {
		return this.messageType;
	}

	/**
	 * 
	 * @param messageType
	 */
	public void setMessageType(eMessageType messageType) {
		this.messageType = messageType;
	}

	public Object getDataContainer() {
		return this.dataContainer;
	}

	/**
	 * 
	 * @param dataContainer
	 */
	public void setDataContainer(Object dataContainer) {
		this.dataContainer = dataContainer;
	}

}