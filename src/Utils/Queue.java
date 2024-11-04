/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.Serializable;

/**
 *
 * @author PC
 */
public class Queue<T> implements IQueue<T>, Serializable {

    private Nodo<T> primero;

    public Queue() {
        this.primero = null;
    }

    @Override
    public void enqueue(T dato) {
        Nodo<T> nuevo = new Nodo(dato);

        if (this.primero == null) {
            this.primero = nuevo;
        } else {
            Nodo<T> aux = this.primero;

            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }

            aux.siguiente = nuevo;
        }
    }

    @Override
    public T dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("La cola esta vacia, no se puede sacar ningun dato");
        }

        T dato = this.primero.dato;
        this.primero = primero.siguiente;
        return dato;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return this.primero.dato;
    }

    @Override
    public boolean isEmpty() {
        return this.primero == null;
    }

    @Override
    public Queue<T> clone() {
        Queue<T> copia = new Queue<>();  

        Nodo<T> actual = this.primero;  

        while (actual != null) {
            copia.enqueue(actual.dato);  
            actual = actual.siguiente;  
        }
        return copia; 
    }

}
