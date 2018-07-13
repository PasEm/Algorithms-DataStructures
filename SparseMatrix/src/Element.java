public class Element<T> {
    private T value;
    private int indexX, indexY;

    public Element(T value, int indexX, int indexY){
        this.value = value;
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }
}
