package abb;

public class Nodo {

    private Object info;
    private Nodo izq;
    private Nodo der;

    public Nodo(Object elemento) {
        this.info = elemento;
        this.izq = null;
        this.der = null;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

}
