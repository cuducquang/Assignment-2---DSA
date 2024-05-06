package Database.DataStructure;

import Database.Place;

public class AdjacencyList {
    List<LinkedList<Node>> aList;

    private int size;


    AdjacencyList() {
        aList = new List<>();
        size = 0;
    }

    public <T> void addNode(T value) {
        LinkedList<Node> currentList = new LinkedList<>();
        Node<T> newNode = new Node<>(value);
        currentList.insert(newNode);
        aList.add(currentList);
        size++;
    }

    public <T> void addEdge (int index, T value) {
        LinkedList<Node> currentList = this.getEdges(index);
        Node<T> newNode = new Node<>(value);
        currentList.insert(newNode);
        size++;
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

    public <T> boolean replaceHead(int index, T value) {
        LinkedList<Node> currentList = aList.get(index);
        Node<T> newNode = new Node<>(value);

        if (currentList.insertAt(1, newNode))
            return currentList.removeAt(0);
        return false;
    }

    public int size() {
        return size;
    }

    public void print(){
        for(LinkedList<Node> currentList : aList){
            currentList.reset();
            while (currentList.hasNext()){
                String data = currentList.next().toString();
                System.out.print(data + " -> ");
            }
            System.out.println();
        }
    }

    public Object getHead(int index){
        LinkedList<Node> currentList = aList.get(index);
        Node head = currentList.getHead();
        return head.data;
    }


    public <T> boolean removeNodeAt(int index, T value){
        Node<T> inputNode = new Node<>(value);
        return aList.get(index).remove(inputNode);
    }

    private static class Node<T> {
        T data;

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public static void main(String[] args) {
//      Testing
        AdjacencyList adj = new AdjacencyList();
        Set<String> services = new Set<>();
        services.add("cafe");

        Place place1 = new Place(2, 3, 23343, services);
        Place place2 = new Place(3, 4, 1324423, services);
        Place place3 = new Place(5, 9, 69696960, services);

        Set<String> services1 = new Set<>();
        services1.add("shop");

        Place place4 = new Place(4, 4, 23343, services1);
        Place place5 = new Place(5, 5, 1324423, services1);

//      Add LinkedList
        for (int i = 0; i < 2; i++) {
            adj.addNode(new Place());
        }

        adj.replaceHead(0, place1);
        adj.addEdge(0,place2);
        adj.addEdge(0, place3);

        adj.replaceHead(1, place4);
        adj.addEdge(1, place5);

        adj.removeNodeAt(0, place2);

        adj.print();

        System.out.println("Size: " + adj.size());
        System.out.println();


        Place head = (Place) adj.getHead(0);
        if (head.getServices() == null) {
            System.out.println("ITS EMPTY");
        }
        else {
            System.out.println("ITS NOT EMPTY");
        }


//        LinkedList<Node> aList= adj.getEdges(1);
//
//        aList.reset();
//        while (aList.hasNext()) {
//            System.out.print(aList.next().data + " -> ");
//        }
    }

}

