package org.arrizabalaga.mmds.pagerank

import spock.lang.Specification
import Jama.Matrix
import org.arrizabalaga.mmds.pagerank.PageRank

class PageRankTest extends Specification{


  static final LINE ="\n----------------\n"

  def "Should build a PageRank object"(){

    	given:
        println ""
        Map graph=['y':['y','a'],'a':['y','m'],'m':['m']]      
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
        Map graph=['y':['y','a'],'a':['y','m'],'m':['m']]      
      when: 
        PageRank pg= new PageRank(graph)
        println pg
      then:
        notThrown(Exception)
  }

  def "Should do power iteration N times"(){

      given:
        Map graph=['y':['y','a'],'a':['y','m'],'m':['m']]      
        PageRank pg= new PageRank(graph)
      when: 
        Matrix result=pg.iterate(1)
        println "Result: \n${result.array*.toString().join('\n')}"
      then:
        result.get(0,0)==(0.5*(1/3) + 0.5*(1/3) + 0*(1/3))
        result.get(1,0)==(0.5*(1/3) + 0*(1/3) + 0*(1/3))
        result.get(2,0)==(0*(1/3) + 0.5*(1/3) + 1*(1/3))
  }

  //.array*.toString().join("\n")
}