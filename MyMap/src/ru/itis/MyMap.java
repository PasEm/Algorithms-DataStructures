package ru.itis;

import java.util.LinkedList;
import java.util.Objects;

public class MyMap<K, V>{
    private LinkedList<Element>[] array;
    private int arraySize;
    private int size;
    private class Element{
        private K key;
        private V value;

        private Element(K key, V value){
            this.key = key;
            this.value = value;
        }

        private Element(K key){
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return Objects.equals(key, element.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    public MyMap(){
        this.size = 0;
        this.arraySize = 15;
        this.array = new LinkedList[arraySize + 1];
        for (int i = 0; i < arraySize + 1; i++)
            array[i] = new LinkedList<>();
    }

    public void put(K key, V value) {
        Element element = new Element(key, value);
        int index = element.hashCode() & arraySize;
        boolean repeat = false;
        for (int i = 0; i < array[index].size(); i++)
            if (element.hashCode() == array[index].get(i).hashCode() && element.equals(array[index].get(i))) {
                array[index].set(i, element);
                repeat = true;
                break;
            }
        if (!repeat) {
            array[index].add(element);
            this.size++;
        }
    }

    public V getValue(K key){
        Element element = new Element(key);
        int index = element.hashCode() & arraySize;
        for (int i = 0; i < array[index].size(); i++)
            if (array[index].get(i).key == element.key)
                return array[index].get(i).value;
        return null;
    }

    public int size() {
        return size;
    }
}
