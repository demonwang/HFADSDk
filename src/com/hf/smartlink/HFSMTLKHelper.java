package com.hf.smartlink;

import com.hf.ManagerFactory;
import com.hf.data.HFConfigration;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFModuleEventListener;
import com.hf.itf.IHFSMTLKHelper;
import com.hf.util.HFModuleException;
import com.hf.util.UdpProxy;

public class HFSMTLKHelper implements IHFSMTLKHelper, IHFModuleEventListener {
	private boolean v30isConnecting = false;
	private boolean v40isConnecting = false;
	private int HEADER_COUNT = 200;
	private int HEADER_PACKAGE_DELAY_TIME = 10;
	private int HEADER_CAPACITY = 76;

	private int CONTENT_COUNT = 5;
	private int CONTENT_PACKAGE_DELAY_TIME = 50;
	private int CONTENT_CHECKSUM_BEFORE_DELAY_TIME = 100;
	private int CONTENT_GROUP_DELAY_TIME = 500;
	protected int SMART_CONFIG_TIMEOUT = 30000;
	protected static final int DEVICE_COUNT_ONE = 1;
	protected static final int DEVICE_COUNT_MULTIPLE = -1;
	protected int deviceCountMode = DEVICE_COUNT_ONE;
	
	private SoundFileBuilder sfb = new SoundFileBuilder(HFConfigration.appContex);
	
	private OnConnectedListener onConnectedListener;
	
	/**
	 * @param onConnectedListener the onConnectedListener to set
	 */
	public void setOnConnectedListener(OnConnectedListener onConnectedListener) {
		this.onConnectedListener = onConnectedListener;
	}
	
	@Override
	public void startSmartlinkV30(String pswd) throws HFModuleException {
		// TODO Auto-generated method stub
		
		registerModuleEventListener();
		
		v30isConnecting = true;
		int count = 1;
		byte[] header = this.getBytes(HEADER_CAPACITY);
		while (count <= HEADER_COUNT && v30isConnecting) {
			UdpProxy.reqByBroastCastWithOutRsp(header, HFConfigration.smartlinkPort, HFConfigration.smartlinkPort);
			try {
				Thread.sleep(HEADER_PACKAGE_DELAY_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		String pwd = pswd;
		int[] content = new int[pwd.length() + 2];

		content[0] = 89;
		int j = 1;
		for (int i = 0; i < pwd.length(); i++) {
			content[j] = pwd.charAt(i) + 76;
			j++;
		}
		content[content.length - 1] = 86;

		count = 1;
		while (count <= CONTENT_COUNT && v30isConnecting) {
			for (int i = 0; i < content.length; i++) {
				// JCTIP ver2 start end checksum send 3 time;
				int _count = 1;
				if (i == 0 || i == content.length - 1) {
					_count = 3;
				}
				int t = 1;
				while (t <= _count) {
					UdpProxy.reqByBroastCastWithOutRsp(getBytes(content[i]), HFConfigration.smartlinkPort, HFConfigration.smartlinkPort);
					if (i != content.length) {
						try {
							Thread.sleep(CONTENT_PACKAGE_DELAY_TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					t++;
				}
				// mConfigBroadUdp.send(getBytes(content[i]));

				if (i != content.length) {
					try {
						Thread.sleep(CONTENT_PACKAGE_DELAY_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(CONTENT_CHECKSUM_BEFORE_DELAY_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// int checkLength = pwd.length() * 30 + 76;
			// JCTIP ver1
			int checkLength = pwd.length() + 256 + 76;

			// JCTIP ver2
			int t = 1;
			while (t <= 3 && v30isConnecting) {
				UdpProxy.reqByBroastCastWithOutRsp(getBytes(checkLength), HFConfigration.smartlinkPort, HFConfigration.smartlinkPort);
				if (t < 3) {
					try {
						Thread.sleep(CONTENT_PACKAGE_DELAY_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				t++;
			}
			// mConfigBroadUdp.send(getBytes(checkLength));

			try {
				Thread.sleep(CONTENT_GROUP_DELAY_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		System.out.println("_____SMART_LINK_END____");
		v30isConnecting = false;
	}
	private byte[] getBytes(int capacity) {
		byte[] data = new byte[capacity];
		for (int i = 0; i < capacity; i++) {
			data[i] = 5;
		}
		return data;
	}
	@Override
	public void stopSmartlinkV30() {
		// TODO Auto-generated method stub
		unregisterModuleEventListener();
		v30isConnecting = false;
	}

	@Override
	public void startSmartlinkV40(String ssid, String pswd) {
		// TODO Auto-generated method stub
		
		registerModuleEventListener();
		
		sfb.setmSsidAndPswd(ssid, pswd);
		sfb.start();
	}
	@Override
	public void stopSmartlinkV40() {
		// TODO Auto-generated method stub
		unregisterModuleEventListener();
		sfb.stop();
	}
	
	public interface OnConnectedListener {
		public void onConnected(ModuleInfo moduleInfo);
	}
	
	private void registerModuleEventListener() {
		ManagerFactory.getInstance().getModuleManager().registerEventListener(this);
	}
	
	private void unregisterModuleEventListener() {
		ManagerFactory.getInstance().getModuleManager().unregisterEventListener(this);
	}

	@Override
	public void onEvent(String mac, byte[] t2data) {
	}

	@Override
	public void onCloudLogin(boolean loginstat) {
	}

	@Override
	public void onCloudLogout() {
	}

	@Override
	public void onNewDevFind(ModuleInfo moduleInfo) {
		unregisterModuleEventListener();
		if (onConnectedListener != null) {
			onConnectedListener.onConnected(moduleInfo);
		}
	}

	@Override
	public void onLocalDevFind(ModuleInfo mi) {
	}

	@Override
	public void onGPIOEvent(String mac) {
	}

	@Override
	public void onTimerEvent(String mac, byte[] t2data) {
	}

	@Override
	public void onUARTEvent(String mac, byte[] userData, boolean chanle) {
	}
}
