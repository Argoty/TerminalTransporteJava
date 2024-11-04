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
public class Nodo<T> implements Serializable {
    T dato;
    Nodo siguiente;
    
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
    
}
