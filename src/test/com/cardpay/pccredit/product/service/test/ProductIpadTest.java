package com.cardpay.pccredit.product.service.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class ProductIpadTest {

	public static void main(String[] args) {
		try {
			System.out
					.println(getHttpResponse("http://192.168.1.242:8080/PCCredit/ipad/product/browse.json"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// get the specified HTTP response for a specified URL.
	private static String getHttpResponse(String url)
			throws ClientProtocolException, IOException {
		// String utfUrl=URLEncoder.encode(url, "utf-8");
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		} finally {
			// shut down the connection manager to ensure immediate deallocation
			// of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

}
