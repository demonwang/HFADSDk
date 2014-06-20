package com.hf.cmd;

import java.nio.ByteBuffer;

import com.hf.lib.util.AES;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;

public class T1Message {

	private String key = AES.DEFAULT_KEY_128;
	private Head1 h1;
	private Head2 h2;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Head1 getH1() {
		return h1;
	}

	public void setH1(Head1 h1) {
		this.h1 = h1;
	}

	public Head2 getH2() {
		return h2;
	}

	public void setH2(Head2 h2) {
		this.h2 = h2;
	}



	public class Head1 {
		private byte cmd;
		private Flag flag;
		private String mac;

		public Head1 unpack(byte[] head1) {
			this.setCmd(head1[0]);
			this.setFlag(new Flag().unpack(head1[1]));
			this.setMac(ByteTool.bytes2HexString(ByteTool.copyOfRange(head1, 2,
					8)));
			return this;
		}

		public byte getCmd() {
			return cmd;
		}

		public void setCmd(byte cmd) {
			this.cmd = cmd;
		}

		public Flag getFlag() {
			return flag;
		}

		public void setFlag(Flag flag) {
			this.flag = flag;
		}

		public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public byte[] pack() {
			ByteBuffer bf = ByteBuffer.allocateDirect(8);
			bf.put(cmd);
			bf.put(flag.pack());
			bf.put(ByteTool.hexStringToBytes(mac));
			return bf.array();
		}
	}

	public class Head2 {
		private byte ver;
		private byte rsv;
		private byte[] sn;
		private byte[] fid;
		private byte[] dtype;
		private byte[] t2;

		public Head2 unpack(byte[] head2) throws HFModuleException {
			
			try {
				byte[]	data = AES.decrypt(head2, getKey().getBytes());
			
			setVer(data[0]);
			setRsv(head2[1]);
			setSn(ByteTool.copyOfRange(data, 2, 4));
			setFid(ByteTool.copyOfRange(data, 4, 6));
			setDtype(ByteTool.copyOfRange(data, 6, 8));
			setT2(ByteTool.copyOfRange(data, 8, head2.length));
			return this;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new HFModuleException(HFModuleException.ERR_AES, e.getMessage());
			}
		}

		public byte getVer() {
			return ver;
		}

		public void setVer(byte ver) {
			this.ver = ver;
		}

		public byte getRsv() {
			return rsv;
		}

		public void setRsv(byte rsv) {
			this.rsv = rsv;
		}

		public byte[] getSn() {
			return sn;
		}

		public void setSn(byte[] sn) {
			this.sn = sn;
		}

		public byte[] getFid() {
			return fid;
		}

		public void setFid(byte[] fid) {
			this.fid = fid;
		}

		public byte[] getDtype() {
			return dtype;
		}

		public void setDtype(byte[] dtype) {
			this.dtype = dtype;
		}

		public byte[] getT2() {
			return t2;
		}

		public void setT2(byte[] t2) {
			this.t2 = t2;
		}

		private byte[] pack() throws HFModuleException {
			// TODO Auto-generated method stub
			try{
			ByteBuffer bf = ByteBuffer.allocate(8 + t2.length);
			bf.put(ver);
			bf.put(rsv);
			bf.put(sn);
			bf.put(fid);
			bf.put(dtype);
			bf.put(t2);
			return AES.encrypt(bf.array(), getKey().getBytes());
			}catch(Exception e){
				throw new HFModuleException(HFModuleException.ERR_AES, e.getMessage());
			}
		}
	}

	public class Flag {
		private boolean needReply = false;
		private boolean sendCmd = false;
		private boolean registed = false;
		private boolean includeT2 = false;
		private boolean remoteRegisted = false;
		private boolean encryptType = false;
		private boolean encrypted = false;
		private boolean resv = false;

		public Flag unpack(byte flag) {
			if ((flag & 1) == 1) {
				needReply = true;
			}
			if (((flag >> 1) & 1) == 1) {
				sendCmd = false;
			}
			if (((flag >> 2) & 1) == 1) {
				registed = true;
			}
			if (((flag >> 3) & 1) == 1) {
				includeT2 = true;
			}
			if (((flag >> 4) & 1) == 1) {
				remoteRegisted = true;
			}
			if (((flag >> 5) & 1) == 1) {
				encryptType = true;
			}
			if (((flag >> 6) & 1) == 1) {
				encrypted = true;
			}
			if (((flag >> 7) & 1) == 1) {
				resv = true;
			}
			return this;

		}

		public boolean isNeedReply() {
			return needReply;
		}

		public void setNeedReply(boolean needReply) {
			this.needReply = needReply;
		}

		public boolean isSendCmd() {
			return sendCmd;
		}

		public void setSendCmd(boolean sendCmd) {
			this.sendCmd = sendCmd;
		}

		public boolean isRegisted() {
			return registed;
		}

		public void setRegisted(boolean registed) {
			this.registed = registed;
		}

		public boolean isIncludeT2() {
			return includeT2;
		}

		public void setIncludeT2(boolean includeT2) {
			this.includeT2 = includeT2;
		}

		public boolean isRemoteRegisted() {
			return remoteRegisted;
		}

		public void setRemoteRegisted(boolean remoteRegisted) {
			this.remoteRegisted = remoteRegisted;
		}

		public boolean isEncryptType() {
			return encryptType;
		}

		public void setEncryptType(boolean encryptType) {
			this.encryptType = encryptType;
		}

		public boolean isEncrypted() {
			return encrypted;
		}

		public void setEncrypted(boolean encrypted) {
			this.encrypted = encrypted;
		}

		public boolean isResv() {
			return resv;
		}

		public void setResv(boolean resv) {
			this.resv = resv;
		}

		public byte pack() {
			byte flag = 0;
			if (needReply) {
				flag |= 1;
			}
			if (needReply) {
				flag |= 1 << 1;
			}
			if (sendCmd) {
				flag |= 1 << 2;
			}
			if (registed) {
				flag |= 1 << 3;
			}
			if (remoteRegisted) {
				flag |= 1 << 4;
			}
			if (encryptType) {
				flag |= 1 << 5;
			}
			if (encrypted) {
				flag |= 1 << 6;
			}
			if (resv) {
				flag |= 1 << 7;
			}
			return flag;
		}
	}

	public byte[] pack() throws HFModuleException {
		byte[] head1 = h1.pack();
		byte[] head2 = h2.pack();
		ByteBuffer bf = ByteBuffer.allocate(head1.length + head2.length);
		bf.put(head1);
		bf.put(head2);
		return bf.array();
	}

	public T1Message unpack(byte[] t1) throws HFModuleException {
		h1 = new Head1().unpack(ByteTool.copyOfRange(t1, 0, 8));
		h2 = new Head2().unpack(ByteTool.copyOfRange(t1, 8, t1.length));
		return this;
	}
}
