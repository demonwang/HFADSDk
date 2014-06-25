package com.hf.cmd;

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