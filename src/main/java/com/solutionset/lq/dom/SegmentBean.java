package com.solutionset.lq.dom;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class SegmentBean {
	
	private String path;
	
	public void setPath(String iPath){
		path = iPath;
	}
	
	public String getPath(){
		return path;
	}
	
	public Node execute(Session session) throws PathNotFoundException, RepositoryException{
		
		Node node = session.getNode(path);

		return node;
	}

}
