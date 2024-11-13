/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.Serializable;

/**
 *
 * @author JAVIER ARGOTY
 */
public class Lista<T> implements IList<T>, Serializable {

    private Nodo<T> primero;
    private int size;

    public Lista() {
        this.primero = null;
    }

    @Override
    public void add(T dato) {
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

        this.size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (index == 0) {
            return this.primero.dato;
        } else {
            Nodo<T> aux = this.primero;
            int contador = 0;

            while (contador < index) {
                aux = aux.siguiente;
                contador++;
            }

            return aux.dato;
        }
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (index == 0) {
            this.primero = primero.siguiente;
        } else {
            Nodo<T> aux = this.primero;
            int contador = 0;

            while (contador < (index - 1)) {
                aux = aux.siguiente;
                contador++;
            }

            aux.siguiente = aux.siguiente.siguiente;
        }

        this.size--;
    }

    @Override
    public void remove(T dato) {
        if (this.primero == null) {
            return;
        }

        // Caso especial: si el primer elemento es el que queremos eliminar
        if (this.primero.dato.equals(dato)) {
            this.primero = this.primero.siguiente;
            this.size--;
            return;
        }

        // Recorremos la lista para encontrar el nodo a eliminar
        Nodo<T> aux = this.primero;
        while (aux.siguiente != null) {
            if (aux.siguiente.dato.equals(dato)) {
                aux.siguiente = aux.siguiente.siguiente;
                this.size--;
                return;
            }
            aux = aux.siguiente;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        if (size > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void add(int index, T dato) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Nodo<T> nuevo = new Nodo(dato);
        if (index == 0) {
            // Agrega al siguiente del nuevo el del primero actual
            nuevo.siguiente = primero;
            this.primero = nuevo; // le asigna al primero el nuevo
        } else {
            Nodo<T> aux = primero;
            int contador = 0;

            //Recorrer la lista hasta el nodo anterior al que se quiere agregar el nuevo dato
            while (contador < (index - 1)) {
                // Avanzamos al siguiente nodo, asignando al auxiliar el siguiente de ese auxiliar
                aux = aux.siguiente;
                contador++;
            }

            // Agrega al siguiente del nuevo que se quiere añadir el nodo que esta en la posicion donde va ir el nuevo
            nuevo.siguiente = aux.siguiente;
            // agrega el nuevo al nodo donde va
            aux.siguiente = nuevo;
        }

        size++;
    }

}
