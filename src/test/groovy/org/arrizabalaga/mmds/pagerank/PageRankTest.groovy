package org.arrizabalaga.mmds.pagerank

import spock.lang.Specification
import Jama.Matrix
import org.arrizabalaga.mmds.pagerank.PageRank

class PageRankTest extends Specification{


	private static final String LINE ="\n----------------\n"

	private static final String H1 = "\n==============\n"

	def "Should build a PageRank object"(){
		given:
		println "$H1 Should build a PageRank object $H1"
		Map graph=['y':['y', 'a'],'a':['y', 'm'],'m':['m']]
		when:
		PageRank pg= new PageRank(graph)
		then:
		notThrown(Exception)
		then:
		pg.m.get(0,0)==0.5
		pg.beta==1.0
		pg.betaVector.get(0,0)==0
	}

	def "Should println a PageRank object"(){

		given:
		println "$H1 Should println a PageRank object $H1"
		Map graph=['y':['y', 'a'],'a':['y', 'm'],'m':['m']]
		when:
		PageRank pg= new PageRank(graph)
		println pg
		then:
		notThrown(Exception)
	}
	
	def "Should build an A matrix"(){
		
		given:
		println "$H1 Should build an A matrix $H1"
		Map graph=['y':['y', 'a'],'a':['y', 'm'],'m':['m']]
		when:
		PageRank pg= new PageRank(graph,0.8)
		println pg
		then:
		notThrown(Exception)
	}

	def "Should do power iteration N times"(){

		given:
		println "$H1 Should do power iteration N times $H1"
		Map graph=['y':['y', 'a'],'a':['y', 'm'],'m':['m']]
		PageRank pg= new PageRank(graph)
		when:
		Matrix result=pg.iterate(1)
		println "Result: \n${result.array*.toString().join('\n')}"
		then:
		result.get(0,0)==(0.5*(1/3) + 0.5*(1/3) + 0*(1/3))
		result.get(1,0)==(0.5*(1/3) + 0*(1/3) + 0*(1/3))
		result.get(2,0)==(0*(1/3) + 0.5*(1/3) + 1*(1/3))
	}

	def "HW-Question 1"(){

		given:
		println "$H1 HW-Question 1 $H1"
		Map graph=['a':['b', 'c'],'b':['c'],'c':['c']]
		PageRank pg= new PageRank(graph,0.7)
		when:		
		Matrix result=pg.iterate(20).times(3)
		println "Result: \n${result.array*.toString().join('\n')}"
		then:
		notThrown(Exception)
	}
	
	def "HW-Question 2"(){
		
		given:
		println "$H1 HW-Question 2 $H1"
		Map graph=['a':['b', 'c'],'b':['c'],'c':['a']]
		PageRank pg= new PageRank(graph,0.85)
		println pg
		when:
		Matrix result=pg.iterate(20)
		println "Result: \n${result.array*.toString().join('\n')}"
		then:
		notThrown(Exception)
	}
	
	def "HW-Question 3"(){
		
		given:
			println "$H1 HW-Question 3 $H1"
			Map graph=['a':['b', 'c'],'b':['c'],'c':['a']]
			PageRank pg= new PageRank(graph)			
			pg.r=new Matrix(3,1,1)
			println pg
		when:
			Matrix result
			5.times{
				result=pg.iterate(it+1)
				println "Result After ${it+1} iterations: \n${result.array*.toString().join('\n')}"
			}
			
			result=pg.iterate(500)
			println "Result After 500 iterations: \n${result.array*.toString().join('\n')}"
		then:
			notThrown(Exception)
	}
}