package com.hf.smartlink;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SoundFileBuilder {
	
	private String ssid;
	private String pswd;
	

	private final double PI = 3.1415926535897932384626433832795028841971;

	private final int FRAME_SAP = 44100;

	private boolean loop = false;
	
	private Context ctx;
	
	private MyAudioTrack adtc;
	private AudioTrack tigAduioTrack;
	private ArrayList<SoundBuilderSuccess> sbsls = new ArrayList<SoundFileBuilder.SoundBuilderSuccess>();

	public SoundFileBuilder(Context ctx) {
		this.ctx = ctx;
	}

	public void setmSsidAndPswd(String ssid, String pswd) {
		this.ssid = ssid;
		this.pswd = pswd;
	}

	public void start() {
		loop = true;
		new Thread(new AudioRecordThread()).start();
//		new Thread(new AudioRecordThreadtig()).start();
	}

	public void stop() {
		if (adtc != null) {
			adtc.release();
			loop = false;
		}
		if (tigAduioTrack != null) {
			tigAduioTrack.release();
			loop = false;
		}
	}

	class AudioRecordThread implements Runnable {

		@Override
		public void run() {			
			adtc = new MyAudioTrack(FRAME_SAP, AudioFormat.CHANNEL_OUT_STEREO,
					AudioFormat.ENCODING_PCM_16BIT);
			adtc.init();
			AudioEncoder fb = new AudioEncoder(ssid,pswd);
			double[] mm = fb.getDouble();
			ByteBuffer bf = ByteBuffer.allocate(mm.length * 4);
			
			for (int i = 0, j = 0; i < mm.length; i++, j += 4) {
				
				short tm = (short) (32767 * mm[i]);
					bf.put(shortToByte(tm));
					bf.put(shortToByte(tm));
			}
			
			int i = 6;
			while (loop) {
				ByteBuffer threebf = ByteBuffer.allocate(bf.capacity()*4);
				threebf.put(bf.array());
				threebf.put(bf.array());
				threebf.put(bf.array());
				threebf.put(bf.array());
				adtc.playAudioTrack(threebf.array(), 0, threebf.array().length);
				
			}
			notifyBuilderSuccess();
		}

		public byte[] shortToByte(short number) {
			
			int temp = number;
			byte[] b = new byte[2];
			for (int i = 0; i < b.length; i++) {
				b[i] = new Integer(temp & 0xff).byteValue();//
				// 将最低位保存在最低位
				temp = temp >> 8; // 向右移8位
			}
			return b;
		}
	}

	interface SoundBuilderSuccess {
		public void onSuccess(String fileName);
	}

	public void registSoundBuilderEvent(SoundBuilderSuccess sss) {
		sbsls.add(sss);
	}

	private void notifyBuilderSuccess() {
		ArrayList<SoundBuilderSuccess> tmp = sbsls;
		Iterator<SoundBuilderSuccess> it = tmp.iterator();
		while (it.hasNext()) {
			it.next().onSuccess("send ok");
		}
	}

	private double[] getShortDatas(byte[] data) {
		double[] signal_real = new double[data.length * 8 * 256];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < 8; j++) {
				for (int n = 0; n < 256; n++) {
					int m = (i * 8 + j) * 256 + n;
					signal_real[m] = 0.2 * Math.cos(2 * PI * 18604.7 * m
							/ FRAME_SAP);
					if (getBit(data[i], j)) {
						signal_real[m] += 0.35 * Math.cos(2 * PI * 18949.2 * m
								/ FRAME_SAP);
					} else {
						signal_real[m] += 0.35 * Math.cos(2 * PI * 19293.8 * m
								/ FRAME_SAP);
					}
				}
			}
		}
		return signal_real;
	}

	private boolean getBit(byte b, int i) {
		// 0101 0101
		// 7654 3210
		byte tmp = 1;
		return (b & (tmp << i)) != 0;
	}
	
	public static short byteToShort(byte[] b) { 
        short s = 0; 
        short s0 = (short) (b[0] & 0xff);// 最低位 
        short s1 = (short) (b[1] & 0xff); 
        s1 <<= 8; 
        s = (short) (s0 | s1); 
        return s; 
    }
}
