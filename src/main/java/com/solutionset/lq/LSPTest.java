package com.solutionset.lq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.solutionset.lq.bean.ResponseBean;
 
public class LSPTest {
 
	public static void main(String[] args) throws Exception {
 
	  try {
 
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"https://mnlaquat.frequencymarketing.com/xmlservlet");
		
		String testString = "";
		
		testString += "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE request ><request><getMbrAccountDetails>";
	    testString += "<database_id>QALAQ63</database_id><appl_id>MBR</appl_id><username>2213148</username><password>61611</password>";
	    testString += "<user_token/><external_tran_id/><getMbrAccountDetails_input><member_id>W2076012</member_id><lang_code>EN</lang_code>";
	    testString += "</getMbrAccountDetails_input><element_list><ytd_nights/><ytd_base_points/><ytd_bonus_points/><ytd_points_redeemed/>";
	    testString += "<ytd_other_points/></element_list></getMbrAccountDetails></request>"; 
 
		StringEntity input = new StringEntity(testString);
		input.setContentType("text/xml");
		postRequest.setEntity(input);
 
		HttpResponse response = httpClient.execute(postRequest);
 
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
		}
 
		BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));
 
		String output;
		String fullString = "";
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			fullString += output;
		}
 
		httpClient.getConnectionManager().shutdown();
 
		JDOMTest jd = new JDOMTest();
		ResponseBean rb = jd.execute(fullString);
		
		System.out.println(rb.toString());
		
	  } catch (MalformedURLException e) {
 
		e.printStackTrace();
 
	  } catch (IOException e) {
 
		e.printStackTrace();
 
	  }
 
	}
 
}