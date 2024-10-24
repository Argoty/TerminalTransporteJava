/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Utils;

/**
 *
 * @author JAVIER ARGOTY
 */
public interface IList<T> {
    public void add(T dato);
    
    public T get(int index) ;
    
    public void remove(int index);
    
    public int size();
    
    public boolean isEmpty();
    
    public void add(int index, T dato);
    
}
