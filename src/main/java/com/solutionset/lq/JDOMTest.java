package com.solutionset.lq;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;

import com.solutionset.lq.bean.ResponseBean;



public class JDOMTest {
	
	public ResponseBean execute(String string) throws Exception {
		
		ResponseBean rb = new ResponseBean();
		
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(new StringReader(string));
		
		Iterator it = doc.getDescendants();
		
		while (it.hasNext()) {
			
			Content c = (Content)it.next();
			
			switch (c.getCType()) {
				case Element: 
					Element e = (Element)c;

					rb.loadValue(e.getName(), e.getText());
					
					break;
			}
			
		}
		
		return rb;
		
	}

	public static void main(String[] args) throws Exception, IOException {

		JDOMTest jt = new JDOMTest();
		
		String string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE request > <request> <getMbrAccountDetails> <version>5.X</version><database_id>QALAQ63</database_id><appl_id>MBR</appl_id><username>2213148</username><password>61611</password><user_token/><external_tran_id/> <getMbrAccountDetails_input><member_id>W2076012</member_id><lang_code>EN</lang_code></getMbrAccountDetails_input> <element_list><ytd_nights/><ytd_base_points/><ytd_bonus_points/><ytd_points_redeemed/><ytd_other_points/></element_list> <result> <record><ytd_nights>3</ytd_nights><ytd_base_points>0</ytd_base_points><ytd_bonus_points>2750</ytd_bonus_points><ytd_points_redeemed>2250</ytd_points_redeemed><ytd_other_points>-9330</ytd_other_points></record> </result><status><errorcode>0</errorcode><errormsg></errormsg><sql_errorcode></sql_errorcode><sql_errormsg></sql_errormsg></status> <audit><begintime>Wed Apr 10 13:29:04 CDT 2013</begintime><endtime>Wed Apr 10 13:29:04 CDT 2013</endtime> <totaltime>0.625</totaltime><trans_id>Wed Apr 10 13:29:04 CDT 2013</trans_id></audit> </getMbrAccountDetails><!-- SQL prepare and execute elapsed time: 0.625 --> </request><!-- list processing elapsed time: 0.625 --><!-- csn_gateway: 0.656 --><!-- XMLServlet.doPost: 0.656 -->";

		ResponseBean rb = jt.execute(string);
		
		System.out.println(rb.toString());

	}

	
	
	
}
