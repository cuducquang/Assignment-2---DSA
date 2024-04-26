package Database;

import java.io.*;
import java.util.Set;

public class Map2D {
    private QuadTree quadTree;

    public Map2D(double width, double height, int capacity) {
        this.quadTree = new QuadTree(0, 0, width, height, capacity);
    }



    public void add(int x, int y, Set serviceName) {
        quadTree.insert(new Place(x, y, serviceName));
    }


    public void saveData(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
