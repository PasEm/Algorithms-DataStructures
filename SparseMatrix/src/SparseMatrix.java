import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class SparseMatrix<T extends Number> implements Matrix<T>, Serializable {
    private LinkedList<Node> listMatrix;
    private int height, width;
    private class Node{
        Number value;
        int x, y;

        Node(T value, int x, int y){
            this.value = value;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || this.getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public SparseMatrix(T[][] matrix){
        this.height = this.width = 0;
        this.listMatrix = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                if (!matrix[i][j].equals(0)){
                    listMatrix.add(new Node(matrix[i][j], i, j));
                }
            }
        }
    }

    @Override
    public void multiplication(SparseMatrix<T> matrix) throws IllegalArgumentException {
        if (this.width != matrix.height)
            throw new IllegalArgumentException();
    }

    @Override
    public void resultant(SparseMatrix<T> matrix) throws IllegalArgumentException {
        if (matrix.height != this.height && matrix.width != this.width)
            throw new IllegalArgumentException;
        for (int i = 0; i < matrix.listMatrix.size(); i++){
            if (this.listMatrix.contains(matrix.listMatrix.get(i))){
                if (this.listMatrix.get(i).value instanceof Integer) {
                    this.listMatrix.get(i).value = this.listMatrix.get(i).value.intValue() - matrix.listMatrix.get(i).value.intValue();
                } else {
                    if (this.listMatrix.get(i).value instanceof Double){
                        this.listMatrix.get(i).value = this.listMatrix.get(i).value.doubleValue() - matrix.listMatrix.get(i).value.doubleValue();
                    } else {
                        if (this.listMatrix.get(i).value instanceof Long){
                            this.listMatrix.get(i).value = this.listMatrix.get(i).value.longValue() - matrix.listMatrix.get(i).value.doubleValue();
                        } else {
                            if (this.listMatrix.get(i).value instanceof Double){
                                this.listMatrix.get(i).value = this.listMatrix.get(i).value.doubleValue() - matrix.listMatrix.get(i).value.doubleValue();
                            } else {
                                if (this.listMatrix.get(i).value instanceof Double){
                                    this.listMatrix.get(i).value = this.listMatrix.get(i).value.doubleValue() - matrix.listMatrix.get(i).value.doubleValue();
                                } else {
                                    if (this.listMatrix.get(i).value instanceof Double){
                                        this.listMatrix.get(i).value = this.listMatrix.get(i).value.doubleValue() - matrix.listMatrix.get(i).value.doubleValue();
                                    }
                }
            }
        }
    }

    @Override
    public void transposition() {
        for (Node node: listMatrix){
            int previousX = node.x;
            node.x = node.y;
            node.y = previousX;
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException{
        stream.defaultWriteObject();
        stream.writeInt(height);
        stream.writeInt(width);
        stream.writeObject(listMatrix);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
        stream.defaultReadObject();
        this.height = stream.readInt();
        this.width = stream.readInt();
        this.listMatrix = (LinkedList<Node>) stream.readObject();
    }
}
