package org.arrizabalaga.mmds.pagerank

import Jama.Matrix

/**
 * A helper class for https://class.coursera.org/mmds-001
 * @author arrizabalaga
 *
 */
class PageRank { 
	/**
	 * Original data
	 */
	Map<String,List> data	
	
	/**
	 * List node keys in order
	 */
	List<String> nodes

	/**
	 * M matrix
	 */
	Matrix m
	
	/**
	 * r vector
	 */
	Matrix r

	
	Matrix betaVector
	
	/**
	 * A matrix
	 */
	Matrix a

	/**
	 * The teleport coef.
	 */
	float beta
	/**
	 * data:
	 * 	[
	 * 		'y':['y','a'],
	 * 		'a':['y','m'],
	 * 		'm':['m']
	 *  ]
	 * 
	 * @param data
	 */
	PageRank(Map data,float beta=1.0) {
		this.data=data
		this.beta=beta
		this.nodes=data.keySet().toList()	
		this.buildM()
		this.buildBetaVector()
		this.buildA()

	}

	private void buildM(){
		int n=this.data.size()
		this.m= new Matrix(n,n,0)
		this.r=new Matrix(n,1,1/n)
		
		int rowIndex,columnIndex
		this.data.each{k,v->			
			double share=1/v.size()
			
			v.each{link->
				int row=getIndex(link)
				int column=getIndex(k)
				m.set(row,column,m.get(row,column)+share)
			}
		}
	}

	private void buildBetaVector(){
		int n=this.nodes.size()
		this.betaVector=new Matrix(n,1,(1-this.beta)/n)
	} 
	
	private void buildA() {
		int n=this.nodes.size()
		double notBeta=1-this.beta
		Matrix oneT=new Matrix(n,n,1/n)
		this.a=this.m.times(this.beta) + oneT.times(notBeta)		
	}

	Matrix iterate (int t) {
		Matrix result=this.r
		t.times{
			result=this.m.times(this.beta).times(result) + this.betaVector
		}
		return result
	}
	
	int getIndex(String key) {
		return this.nodes.indexOf(key)
	}

	String toString(){
		String strM=this.m.array*.toString().join("\n")
		String strBetaVector=this.betaVector.array*.toString().join("\n")
		String strA=this.a.array*.toString().join("\n")
		"====PageRank====\ndata:\n$data\nm:\n$strM\nbeta:\n${this.beta}\nbetaVector:\n${strBetaVector}\na:\n$strA}=============="
	}
	
}
