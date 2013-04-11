package com.solutionset.lq.dom;

import java.util.ArrayList;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author adamtrissel
 *
 * DAO like bean object to retrieve article nodes by a list of primaryTags.
 * object needs to be access with a valid session, either remote or local
 * 
 *  tags are supplied by 'pushing' the tags into an array.
 *
 */


public class SegmentationNodeBean {
	
	private ArrayList<String> tagArray = new ArrayList();
	
	/*
	 * Clear the tag array
	 */
	
	public void clearTagArray(){
		
		tagArray.clear();
		
	}
	
	/*
	 * push a new tag on the list
	 */
	
	public void pushTag(String tag){
		
		tagArray.add(tag);
		
	}
	
	/*
	 * create a properly delimited list of the tags in the form
	 * required by the main create method (seperated by or's)
	 */
	
	private String getContainsList(){
		
		ArrayList<String> containsList = new ArrayList<String>();
		
		Iterator<String> it = tagArray.iterator();
		String tag;
		
		while (it.hasNext()) {
			
			tag = it.next();
			
			String containsClause = "PrimaryTag='" + 
			       tag + "'";
			       
			containsList.add(containsClause);
			
		}
		
		String retVal = StringUtils.join(containsList, " or ");

		return retVal;
	}
	
	/*
	 * Create a valid JCR-SQL2 select statement with supplied tags
	 */
	
	private String createSQLStatement(String segmentNode){
		
		String SQL2 = "SELECT * FROM [cq:Page] AS s WHERE ISDESCENDANTNODE([" + segmentNode + "])";
		return SQL2;
		
	}
	
	/*
	 * Call the repo and get the node values, reformat into an array of nodes
	 */
	
	/**
	 * @param session
	 * @param segmentNode
	 * @return
	 * @throws RepositoryException
	 */
	
	public ArrayList<Node> execute(Session session, String segmentNode) throws RepositoryException{

		String selectStatement = createSQLStatement(segmentNode);
		
		QueryManager qMgr = session.getWorkspace().getQueryManager();
		Query query = qMgr.createQuery(selectStatement, Query.JCR_SQL2);
		
		QueryResult result = query.execute();
		
		NodeIterator nodeIter = result.getNodes();
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		while ( nodeIter.hasNext() ) {
		
			Node node = nodeIter.nextNode();
			nodes.add(node);
		
		}

		return nodes;
				
	}	

}
