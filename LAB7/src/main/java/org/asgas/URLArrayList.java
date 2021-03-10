package org.asgas;
import java.util.Arrays;

public class URLArrayList implements List {
    private Object[] elements;

    @Override
    public int size() { return this.elements.length; }

    @Override
    public boolean isEmpty() { return this.elements.length == 0; }

    @Override
    public Object get(int i) throws IndexOutOfBoundsException { return elements[i]; }

    @Override
    public Object set(int i, Object o) throws IndexOutOfBoundsException {
        Object Obj = elements[i];
        elements[i] = o;
        return Obj;
    }

    @Override
    public void add(int i, Object o) throws IndexOutOfBoundsException {
        if (elements == null) {
            elements = new Object[1];
            elements[0] = o;
        }
        else {
            elements = Arrays.copyOf(elements,elements.length + 1);
            var tmp = Arrays.copyOf(elements,elements.length + 1);
            elements[i] = o;

            for (int j = 0; j < i; j++){
                elements[j] = tmp[j];
            }
            for (int j = i + 1; j < elements.length; j++){
                elements[j] = tmp[j - 1];
            }
        }
    }

    @Override
    public Object remove(int i) throws IndexOutOfBoundsException {
        Object obj;

        if (elements.length == 1) {
            obj = elements[0];
            elements = new Object[0];
        }
        else {
            obj = elements[i];
            var tmp = Arrays.copyOf(elements,elements.length);
            elements = new Object[elements.length - 1];

            for (int j = 0; j < i; j++) {
                elements[j] = tmp[j];
            }

            for (int j = i; j < elements.length; j++) {
                elements[j] = tmp[j + 1];
            }
        }

        return obj;
    }
}