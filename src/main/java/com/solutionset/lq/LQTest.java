package com.solutionset.lq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;
import javax.jcr.Value;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.jcr2spi.operation.CreateConfiguration;
import org.apache.commons.cli.*;

import com.day.cq.commons.jcr.JcrUtil;
import com.solutionset.lq.dom.SegmentationNodeBean;


/**
 * @author adamtrissel
 *
 */
public class LQTest {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	
	public static void main(String[] args) throws Exception {

		//Get command line parameters
		
		String repoURL = null;
		String repoPort = null;
		String userName = null;
		String password = null;
		
		CommandLineParser parser = new PosixParser();
		
		Options options = new Options();
		options.addOption( "r", "repo", true, "repo to run against ." );
		options.addOption( "P", "Port", true, "repo port ." );
		options.addOption( "u", "userName", true, "username ." );
		options.addOption( "p", "password", true, "users password ." );
		
		CommandLine cmd = parser.parse( options, args);
		
		if (cmd.hasOption('r')){
			repoURL = cmd.getOptionValue('r');
		}
		if (cmd.hasOption('P')){
			repoPort = cmd.getOptionValue('P');
		}
		if (cmd.hasOption('u')){
			userName = cmd.getOptionValue('u');
		}
		if (cmd.hasOption('p')){
			password = cmd.getOptionValue('p');
		}

		if (repoURL == null ||
		    repoPort == null ||
		    userName == null ||
		    password == null
			){
			
			System.out.println("Usage: requires: -r[repo], -P[port], -u[userName], -p[password]");
			return;
			
		}
		
		String repoString = repoURL + ":" + repoPort + "/crx/server";
		
		System.out.println("initializing connection to CQ server");
		
		Repository repository = JcrUtils.getRepository(repoString);
		Session session = repository.login(new SimpleCredentials(userName, password.toCharArray()));
		
		LQTest lqt = new LQTest();
		
		System.out.println("calling execute function");
		
		lqt.setSession(session);
		
		lqt.execute();
		
		session.logout();
		
	}
	
	private Session session;
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	private HashMap<String, String> referenceMap = new HashMap<String, String>();

	public HashMap<String, String> getReferenceMap() {
		return referenceMap;
	}

	public void setReferenceMap(HashMap<String, String> referenceMap) {
		this.referenceMap = referenceMap;
	}

	public void execute() throws Exception {
		
		String logicString = new String();
		
		SegmentationNodeBean sb = new SegmentationNodeBean();
		
		ArrayList<Node> segments = sb.execute(session, "/etc/segmentation/geometrixx-outdoors");
		
		Iterator<Node> ni = segments.iterator();
		
		//iterate of over list of Nodes returned. Each should be a valid segment
		//narrowing down the list to "cq:Page" options excludes other types
		
		while (ni.hasNext()){
			
			Node n = ni.next();

			logicString = processSegment("AND", n);
			
			logicString = logicString.replace("AND", "AND\n");
			logicString = logicString.replace("OR", "OR\n");
			
			System.out.println("START ------------------------------------------------------------");
			System.out.println(n.getName() + " : " + n.getPath());
			System.out.println(logicString);
			System.out.println("END --------------------------------------------------------------");
		
		}
		
	}
	
	private String extractLogicFromSegment(Node segment) {

		String logicString = "";
		
		Node baseNode = null;
		
		try {
			
			baseNode = segment.getNode("traits/andpar");
			
			//System.out.println("**" + baseNode.getName());
			
			logicString = processLogic("AND", baseNode);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
		return logicString;
		
	}
	
	private String getReferenceNode(String boolType, String referenceName) throws Exception {
		
		String logicString = "";
		
		if (referenceMap.containsKey(referenceName)){
			
			logicString = referenceMap.get(referenceName);
			
		} else {
		
			Node n = session.getNode(referenceName);
			
			logicString = processSegment(boolType, n);
			
			referenceMap.put(referenceName, logicString);

		}	
			
		return logicString;
		
	}
	
	private String joinFormalString(String formalString, String newString, String boolType){
		
		String newFormalString = "";
		
		if (formalString.equals("")) {
			
			newFormalString = newString + " ";
			
		}
		else {
			
			newFormalString = formalString + " " + boolType + newString;
			
		}
		
		return newFormalString;
		
	}
	
	private String processLogic(String boolType, Node segment) throws Exception{
		
		NodeIterator ni = null;
		boolean isValid = false;
		
		String formalString = new String();
		String tempString = new String();
		
		try {

			ni = segment.getNodes();
			
			isValid = true;
				
		} catch (RepositoryException e) {
			
			isValid = false;
		
		}
		
		if (isValid){
			
			while (ni.hasNext()){
				
				Node subNode = (Node)ni.next();
				
				if (subNode.getName().startsWith("reference")) {
					//System.out.println("\t\t" + subNode.getName() + " : " + subNode.getProperty("segmentPath").getString());

					tempString = getReferenceNode(boolType, subNode.getProperty("segmentPath").getString());
					
					if (!tempString.equals("")){

						formalString = joinFormalString(formalString, tempString, boolType);
						
					}
					
					
				} else {
					
					if (subNode.getName().startsWith("script")) {
						
						//System.out.println("\t\t" + subNode.getName() + " : " + subNode.getProperty("script").getString());
						
						tempString = subNode.getProperty("script").getString();
						
						if (!tempString.equals("")){
							
							formalString = joinFormalString(formalString, tempString, boolType);
							
						}
						
					} else {
						
						if (subNode.getName().startsWith("or")){
							
							tempString = processLogic("OR", subNode);
							
							if (!tempString.equals("")) {
								
								formalString = joinFormalString(formalString, tempString, boolType);
								
							}
							
							
						} else {
							
							if (subNode.getName().startsWith("and")){
								
								tempString = processLogic("AND", subNode);
								
								if (!tempString.equals("")) {
									
									formalString = joinFormalString(formalString, tempString, boolType);
									
								}
								
							} else {
								
								//System.out.println("\t\t" + subNode.getName());
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		if (!formalString.equals("")) {
			formalString = "( " + formalString + " )";
		}
		
		return formalString;
	}

	private String processSegment(String boolType,Node n) throws Exception{
		
		String logicString = "";
		
		//System.out.println(n.getName() + ":" + n.getPath());
		
		if (n.hasNode("jcr:content")){
			
			NodeIterator ni2 = n.getNodes();
			
			while (ni2.hasNext()) {
				
				Node n2 = (Node)ni2.next();
				
				//System.out.println(n2.getName());
				
				if (n2.getName().equals("jcr:content")){
					
					//ok, looks like we have a valid segment
					//now we need to deal with the underlying logic
					//we will pull out scripts, ands, ors and references(perhaps)
					
					logicString = extractLogicFromSegment(n2);

					
				}
				
			}
			
		}		
		
		return logicString;
		
	}


}
