package org.arrizabalaga.mmds.pagerank

import Jama.Matrix

class PageRank { 
	/**
	 * Original data
	 */
	Map<String,List> data	
	
	List<String> nodes

	Matrix m
	
	Matrix r

	Matrix betaVector

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

	Matrix iterate (int t) {
		Matrix result=this.r
		t.times{
			//result=this.m.times(result)
			result=this.m.times(this.beta).times(result) + this.betaVector
		}
		return result
	}
	
	int getIndex(String key) {
		return this.nodes.indexOf(key)
	}

	String toString(){
		String str=this.m.array*.toString().join("\n")
		String strBetaVector=this.betaVector.array*.toString().join("\n")
		"====PageRank====\ndata:\n$data\nm:\n$str\nbeta:\n${this.beta}\nbetaVector:\n${strBetaVector}\n=============="
	}

	String printEquations(){
		String result=""
		double[][] array=this.m.array
		this.nodes.eachWithIndex{row,i->

			"r_$row="+array[i]


		}
		return result
	}


	
}
