package com.hf.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import android.util.Log;

import com.hf.data.HFConfigration;

public class HttpProxy {
	
	private static final int timeOut = HFConfigration.defautTimeout;
	
	public static String reqByHttpPost(String url, Map<String, String> header, String req) throws HFModuleException {
		
		String rsp = "";
		URL postUrl;
		try {
			Log.w("reqByHttpPost", url);
			Log.e("reqByHttpPost", req);
			postUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-javascript->json");
			connection.setRequestProperty("Content-Encoding", "utf-8");
			connection.setConnectTimeout(timeOut);
			connection.setReadTimeout(timeOut);
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			String content = req;
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			rsp = sb.toString();
			Log.e("reqByHttpPost", rsp);
			return rsp;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HFModuleException(HFModuleException.ERR_HTTP_SEND_CMD, e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HFModuleException(HFModuleException.ERR_HTTP_RECV_CMD, e.getMessage());
		}
	}
	
	public static String reqByHttpPost(String req) throws HFModuleException {
		return reqByHttpPost(HFConfigration.cloudServiceUrl, Collections.<String, String>emptyMap(), req);
	}
}
