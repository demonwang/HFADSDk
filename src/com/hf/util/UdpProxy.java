package com.hf.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

import com.hf.data.HFConfigration;

public class UdpProxy {
	public static byte[] reqByUdp(String ip,byte[] data) throws HFModuleException{
		byte[] buff = new byte[HFConfigration.macTMsgPacketSize];
		try {
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket sendpkg = new DatagramPacket(data, data.length,InetAddress.getByName(ip),HFConfigration.localUDPPort);
			socket.send(sendpkg);
			DatagramPacket recvpkg = new DatagramPacket(buff, buff.length);
			socket.receive(recvpkg);
			return ByteTool.copyOfRange(buff, 0, recvpkg.getLength());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_SEND_CMD,e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_RECV_CMD,e.getMessage());
		}
	}
	
	public static void reqByBroastCastWithOutRsp(byte[] data,int localPort,int remotePort) throws HFModuleException{
		try {
			DatagramSocket socket = new DatagramSocket(localPort);
			DatagramPacket sendpkg = new DatagramPacket(data, data.length,HFConfigration.broudcastIp,remotePort);
			socket.send(sendpkg);
			socket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_SEND_CMD,e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_RECV_CMD,e.getMessage());
		}
	}
}
