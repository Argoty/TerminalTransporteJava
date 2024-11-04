/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Utils;

/**
 *
 * @author PC
 */
public interface IQueue<T> {
    public void enqueue(T dato);
    
    public T dequeue() ;
    
    public T peek();
        
    public boolean isEmpty();
    
    public IQueue<T> clone();
}
