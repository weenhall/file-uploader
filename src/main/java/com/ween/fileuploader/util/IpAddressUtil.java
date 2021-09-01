package com.ween.fileuploader.util;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class IpAddressUtil {

	public static String getServerIp() throws Exception{
		try(final DatagramSocket socket=new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"),10002);
			return socket.getLocalAddress().getHostAddress();
		}
	}
}
