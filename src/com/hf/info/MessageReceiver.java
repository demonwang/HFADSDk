package com.hf.info;

public class MessageReceiver {

	public static final int RECEIVER_TYPE_SMS = 1;
	public static final int RECEIVER_TYPE_EMAIL = 2;
	private String address;
	private int receiverType = RECEIVER_TYPE_EMAIL;
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the receiverType, one of {@link #RECEIVER_TYPE_SMS}, {@link #RECEIVER_TYPE_EMAIL}
	 */
	public int getReceiverType() {
		return receiverType;
	}
	/**
	 * @param receiverType the receiverType to set, one of {@link #RECEIVER_TYPE_SMS}, {@link #RECEIVER_TYPE_EMAIL}
	 */
	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}
	
	public MessageReceiver(int receiverType, String address) {
		super();
		this.receiverType = receiverType;
		this.address = address;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CaptchaReceiver [address=" + address + ", receiverType="
				+ receiverType + "]";
	}
}
