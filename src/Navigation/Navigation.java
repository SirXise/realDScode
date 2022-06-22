package Navigation;

import java.util.*;

public class Navigation {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int i = 0;
        int NoCases = in.nextInt();
        in.nextLine();
        while (i < NoCases) {
            try {
                int NoCon = in.nextInt();
                in.nextLine();
                int size = NoCon*2;
                Graph<String> graph = new Graph<String>(size);
                ArrayList<ArrayList<Integer>> adjList = new ArrayList<>(size);

                String str = "";

                for (int j = 0; j < size; j++) {
                    adjList.add(new ArrayList<>());
                }

                for (int j = 0; j < NoCon; j++) {
                    String data = in.nextLine();
                    String[] conname = data.split(" => ");

                    graph.addVertex(NoCon, conname[0]);
                    graph.addVertex(NoCon, conname[1]);

                    if (graph.addEdge(conname[0], conname[1]) && graph.addEdge(conname[1], conname[0])==true) {
                        int sou = graph.indexVert(conname[0]);
                        int dest = graph.indexVert(conname[1]);
                        addEdge(adjList, sou, dest);
                    }
                }

                int NoQue = in.nextInt();
                in.nextLine();
                int sou, dest;
                ArrayList<Integer> path;

                for (int j = 0; j < NoQue; j++) {
                    String dir = in.nextLine();
                    String[] fromto = dir.split(" -> ");

                    graph.hasVert(fromto[0]);
                    graph.hasVert(fromto[1]);

                    if (graph.hasVert(fromto[0]) == false) {
                        System.out.println("This source is invalid");
                        continue;
                    }
                    if (graph.hasVert(fromto[1]) == false) {
                        System.out.println("This destination is invalid");
                        continue;
                    }

                    sou = graph.indexVert(fromto[0]);
                    dest = graph.indexVert(fromto[1]);

                    if (sou == -1) {
                        System.out.println("This source is invalid");
                    }
                    if (dest == -1) {
                        System.out.println("This destination is invalid");
                    }

                    path = shortPath(adjList, sou, dest, size);

                    if (path == null) {
                        System.out.println("There is no train from " + fromto[0] + " to " + fromto[1]);
                    } else {
                        for (int k = path.size() - 1; k >= 0; k--) {
                            if (k == 0) {
                                str += graph.getAllVertexObjects().get(path.get(k));
                                continue;
                            }
                            str += graph.getAllVertexObjects().get(path.get(k)) + "->";
                        }
                        System.out.println(str);
                        str = "";
                    }
                }

                i++;
            }catch(InputMismatchException e){
                return ;
            }
        }

    }

    private static void addEdge(ArrayList<ArrayList<Integer>> adjList, int i, int j){
        adjList.get(i).add(j);
        adjList.get(j).add(i);
    }

    private static ArrayList<Integer> shortPath(ArrayList<ArrayList<Integer>> adjList, int sou, int dest, int v){
        int[] pred = new int[v];
        int[] dist = new int[v];

        if(!BFS(adjList, sou, dest, v, pred, dist))
            return null;

        ArrayList<Integer> path = new ArrayList<>();
        int go = dest;
        path.add(go);
        while(pred[go]!=-1){
            path.add(pred[go]);
            go = pred[go];
        }
        return path;
    }

    public static boolean BFS(ArrayList<ArrayList<Integer>> adjList, int sou, int dest, int size, int[] pred, int[] dist){
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[size];
        for (int i = 0; i < size; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        visited[sou] = true;
        dist[sou] = 0;
        queue.add(sou);

        while(!queue.isEmpty()){
            int u = queue.remove();
            for (int i = 0; i < adjList.get(u).size(); i++) {
                if(!visited[adjList.get(u).get(i)]){
                    (visited[adjList.get(u).get(i)]) = true;
                    dist[adjList.get(u).get(i)] = dist[u] + 1;
                    pred[adjList.get(u).get(i)] = u;
                    queue.add(adjList.get(u).get(i));
                    if(adjList.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }

}

class Vertex<T extends Comparable<T>> extends ArrayList<T>{
    T vertex;
    int tag, edge, indeg, outdeg;
    Vertex<T> nextVert;
    Edge<T> firstEdge;
    ArrayList<T> neighbour;
    boolean visited;

    public Vertex() {
        this.vertex = null;
        this.indeg = 0;
        this.outdeg = 0;
        this.tag = 0;
        this.edge = 0;
        this.nextVert = null;
        this.firstEdge = null;
    }

    public Vertex(int num, T vInfo, Vertex<T> next) {
        this.vertex = vInfo;
        this.indeg = 0;
        this.outdeg = 0;
        this.tag = 0;
        this.edge = 0;
        this.nextVert = next;
        this.firstEdge = null;
        this.neighbour = new ArrayList<>(num);
        this.visited = false;
    }

    void visit(){
        visited = true;
    }

    void unvisit(){
        visited = false;
    }
}

class Edge<T extends Comparable<T>> {
    Vertex<T> toVert;
    Edge<T> nextEdge;
    boolean visited;

    public Edge() {
        this.toVert = null;
        this.nextEdge = null;
    }

    public Edge(Vertex<T> toVert, Edge<T> nextEdge) {
        this.toVert = toVert;
        this.nextEdge = nextEdge;
    }

    void visit(){
        visited = true;
    }

    void unvisit(){
        visited = false;
    }

}

class Graph<T extends Comparable<T>> extends ArrayList<T>{
    Vertex<T> head;
    int size;
    ArrayList<ArrayList<Integer>> adjList;

    public Graph(){
        head = null;
        size = 0;
    }

    public Graph(int size){
        this.adjList = new ArrayList<>(size);
        head = null;
        size = 0;
        for (int i = 0; i < size; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    public int getSize(){
        return this.size;
    }

    public boolean hasVert(T v){
        if(head == null) return false;
        Vertex<T> temp = head;
        while(temp!=null){
            if(temp.vertex.compareTo(v) == 0)
                return true;
            temp = temp.nextVert;
        }
        return false;
    }

    public int indexVert(T v){
        if(head == null) return -1;
        Vertex<T> temp = head;
        while(temp!=null){
            if(temp.vertex.compareTo(v) == 0)
                return temp.tag;
            temp = temp.nextVert;
        }
        return -1;
    }

    public boolean addVertex(int num, T v){
        int no = 0;
        if(hasVert(v) == false){
            Vertex<T> temp = head;
            Vertex<T> newloc = new Vertex<>(num, v, null);
            if(head == null){
                head = newloc;
                head.tag = no;
            }
            else{
                Vertex<T> previous = head;
                while(temp!=null){
                    no++;
                    previous = temp;
                    temp = temp.nextVert;
                }
                previous.nextVert = newloc;
                previous.nextVert.tag = no;
            }
            size++;
            return true;
        }
        else
            return false;
    }

    public ArrayList<T> getAllVertexObjects(){
        ArrayList<T> list = new ArrayList<>();
        Vertex<T> temp = head;
        int i=0;
        while (temp != null){
            list.add(temp.vertex);
            temp = temp.nextVert;
            i++;
        }
        return list;
    }

    public boolean hasEdge(T sou, T dest){
        if(head == null) return false;
        if(!hasVert(sou) || !hasVert(dest))
            return false;
        Vertex<T> source = head;

        while(source!=null){
            if(source.vertex.compareTo(sou) == 0){
                Edge<T> current = source.firstEdge;
                while(current!=null){
                    if(current.toVert.vertex.compareTo(dest) == 0)
                        return true;
                    current = current.nextEdge;
                }
            }
            source = source.nextVert;
        }
        return false;
    }

    public boolean addEdge(T sou, T dest){
        if(head == null) return false;
        if(!hasVert(sou) || !hasVert(dest))
            return false;
        if(hasEdge(sou,dest) == true)
            return false;
        Vertex<T> source = head;
        while(source!=null){
            if(source.vertex.compareTo(sou) == 0){
                Vertex<T> destination = head;
                while(destination != null){
                    if(destination.vertex.compareTo(dest) == 0){
                        Edge<T> current = source.firstEdge;
                        Edge<T> line = new Edge<T>(destination, current);
                        source.firstEdge = line;
                        source.outdeg++;
                        source.indeg++;
                        source.neighbour.add(dest);
                        return true;
                    }
                    destination = destination.nextVert;
                }
            }
            source = source.nextVert;
        }
        return false;
    }

    public ArrayList<T> getNeighbours(T v){
        if(!hasVert(v)) return null;
        ArrayList<T> list = new ArrayList<>();
        Vertex<T> temp = head;
        while(temp!=null){
            if(temp.vertex.compareTo(v) == 0){
                Edge<T> current = temp.firstEdge;
                while(current!=null){
                    list.add(current.toVert.vertex);
                    current = current.nextEdge;
                }
            }
            temp = temp.nextVert;
        }
        return list;
    }

    public void printEdges(){
        Vertex<T> temp = head;
        int no = 0;

        while(temp!=null){
            System.out.println("# " + no + " " + temp.vertex + " : ");
            Edge<T> current = temp.firstEdge;
            while(current!=null){
                System.out.println("[" + temp.vertex + " , " + current.toVert.vertex + "]");
                current = current.nextEdge;
            }
            System.out.println();
            temp = temp.nextVert;
            no++;
        }
    }

    public ArrayList<T> getNeighboursLocation(T v){
        if(head == null) return null;

        Vertex<T> temp = head;
        while(temp!=null){
            if(temp.vertex.compareTo(v) == 0)
                return temp.neighbour;
            temp = temp.nextVert;
        }
        return null;
    }

    public Vertex getVert(T v){
        if(head == null) return null;
        Vertex<T> temp = head;
        while(temp!=null){
            if(temp.vertex.compareTo(v) == 0)
                return temp;
            temp = temp.nextVert;
        }
        return null;
    }
}
