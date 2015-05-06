/**
 * 
 */
package br.com.lptrias.twm.service.algo;

import com.tinkerpop.blueprints.Vertex;

/**
 * Interface of path finding algorithms
 * 
 * @author ltrias
 *
 */
public interface PathFinder {

	Path findPathBetween(Vertex origin, Vertex destination);

}
