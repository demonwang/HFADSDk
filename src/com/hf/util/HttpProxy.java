package com.hf.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.hf.data.HFConfigration;

public class HttpProxy {
	public static String reqByHttpPost(String req) throws HFModuleException {
		
		String rsp = "";
		URL postUrl;
		try {
			Log.w("reqByHttpPost", HFConfigration.cloudServiceUrl);
			Log.e("reqByHttpPost", req);
			postUrl = new URL(HFConfigration.cloudServiceUrl);
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-javascript->json");
			connection.setRequestProperty("Content-Encoding", "utf-8");
			connection.setConnectTimeout(HFConfigration.defautTimeout);
			connection.setReadTimeout(HFConfigration.defautTimeout);
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
}
