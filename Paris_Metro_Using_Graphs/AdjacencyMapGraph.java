import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class AdjacencyMapGraph<V, E>{

    public LinkedList<InnerVertex<V>> vertices;
    public LinkedList<InnerEdge<E>> edges;

    public AdjacencyMapGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }


    public int numVertices() {
        return vertices.size();
    }

    public int numEdges() {
        return edges.size();
    }

    public Iterable<InnerVertex<V>> vertices() {
        return vertices;
    }

    public Iterable<InnerEdge<E>> edges() {
        return edges;
    }

    public int outDegree(InnerVertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    public int inDegree(InnerVertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    public Iterable<InnerEdge<E>> outgoingEdges(InnerVertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values(); // edges are the values in the adjacency map
    }

    public Iterable<InnerEdge<E>> incomingEdges(InnerVertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values(); // edges are the values in the adjacency map
    }

    public InnerEdge<E> getEdge(InnerVertex<V> u, InnerVertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v); // will be null if no edge from u to v
    }

    public InnerVertex<V>[] endVertices(InnerEdge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        return edge.getEndPoints();
    }

    public InnerVertex<V> opposite(InnerVertex<V> v, InnerEdge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        InnerVertex<V>[] endpoints = edge.getEndPoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("Vertex is not incident to this edge");
        }
    }

    public InnerVertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element);
        vertices.add(v);
        return v;
    }

    public InnerEdge<E> insertEdge(InnerVertex<V> u, InnerVertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) == null) {
            InnerEdge<E> e = new InnerEdge<>(u, v, element);
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            origin.getOutgoing().put(v, e);
            dest.getIncoming().put(u, e);
            edges.add(e);
            return e;
        } else {
            throw new IllegalArgumentException("Edge from u to v exists");
        }
    }

    public void removeVertex(InnerVertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
		// remove all incident edges from the graph
		for (InnerEdge<E> e : vert.getOutgoing().values())
			removeEdge(e);
		for (InnerEdge<E> e : vert.getIncoming().values())
			removeEdge(e);
		// remove this vertex from the list of vertices
		vertices.remove(vert);
    }
    public InnerVertex<V> getVertex(V element) {
    for (InnerVertex<V> vertex : vertices) {
        if (vertex.getElement().equals(element)) {
            return vertex;
        }
    }
    return null; // If the vertex with the specified element is not found
}

    public void removeEdge(InnerEdge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
		// remove this edge from vertices' adjacencies
		InnerVertex<V>[] verts = (InnerVertex<V>[]) edge.getEndPoints();
		verts[0].getOutgoing().remove(verts[1]);
		verts[1].getIncoming().remove(verts[0]);
		// remove this edge from the list of edges
		edges.remove(edge);
    }

    private InnerVertex<V> validate(InnerVertex<V> v) {
        if (!(v instanceof InnerVertex)) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        return (InnerVertex<V>) v;
    }

    private InnerEdge<E> validate(InnerEdge<E> e) {
        if (!(e instanceof InnerEdge)) {
            throw new IllegalArgumentException("Invalid edge");
        }
        return (InnerEdge<E>) e;
    }

    public class InnerVertex<V> implements Vertex<V> {
        private V element;
        private ConcurrentHashMap<InnerVertex<V>, InnerEdge<E>> outgoing;
        private ConcurrentHashMap<InnerVertex<V>, InnerEdge<E>> incoming;

        public InnerVertex(V elem) {
            element = elem;
            outgoing = new ConcurrentHashMap<>();
            incoming = new ConcurrentHashMap<>();
        }

        public V getElement() {
            return element;
        }

        public ConcurrentHashMap<InnerVertex<V>, InnerEdge<E>> getOutgoing() {
            return outgoing;
        }

        public ConcurrentHashMap<InnerVertex<V>, InnerEdge<E>> getIncoming() {
            return incoming;
        }

    }

    public class InnerEdge<E> implements Edge<E> {
        private E element;
        private InnerVertex<V>[] endpoints;

        public InnerEdge(InnerVertex<V> u, InnerVertex<V> v, E elem) {
            element = elem;
            endpoints = (InnerVertex<V>[]) new InnerVertex[]{u, v};
        }

        public E getElement() {
            return element;
        }

        public InnerVertex<V>[] getEndPoints() {
            return endpoints;
        }

    }
}