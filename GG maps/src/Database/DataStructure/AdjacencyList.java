package Database.DataStructure;

public class AdjacencyList {
    List<LinkedList<Node>> aList;

    AdjacencyList() {
        aList = new List<>();
    }

    public void addNode(Node node) {
        LinkedList<Node> currentList = new LinkedList<>();
        currentList.insert(node);
        aList.add(currentList);
    }

    public void addEdge (int index, Node node) {
        LinkedList<Node> currentList = this.getEdges(index);
        currentList.insert(node);
    }

    public boolean checkEdge (int src, int dst) {
        LinkedList<Node> currentList = aList.get(src);
        Node dstNode = aList.get(dst).get(0);

        for(Node node : currentList) {
            if(node == dstNode) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Node> getEdges(int index){
        return aList.get(index);
    }

    public void print(){
        for(LinkedList<Node> currentList : aList){
            currentList.reset();
            while (currentList.hasNext()){
                String data = currentList.next().data;
                System.out.print(data + " -> ");
            }
            System.out.println();
        }
    }

    private static class Node {
        String data;

        public Node(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
//      Testing
        AdjacencyList adj = new AdjacencyList();

//      Add LinkedList
        adj.addNode(new Node("Cafe"));
        adj.addNode(new Node("Shopping"));
        adj.addNode(new Node("School"));


        adj.addEdge(0, new Node("Starbucks"));
        adj.addEdge(0, new Node("Highlands"));
        adj.addEdge(0, new Node("The Coffee House"));
        adj.addEdge(1, new Node("Van Hanh Mall"));
        adj.addEdge(2, new Node("RMIT"));
        adj.addEdge(2, new Node("TDT"));

        adj.print();
        System.out.println();

        LinkedList<Node> aList= adj.getEdges(1);

        aList.reset();
        while (aList.hasNext()) {
            System.out.print(aList.next().data + " -> ");
        }
    }

}

