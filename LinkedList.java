/**
 * A class to represent a linked list of nodes.  The list is iterable (i.e. we can loop over its data).
 * The nodes are a static nested class. 
 * @author Brian Nguyen - nbn10
 */

import java.util.NoSuchElementException;
import java.util.Iterator;


public class LinkedList<T> implements Iterable<T> {
  /** the first node of the list, or null if the list is empty */
  private Node<T> firstNode;
  
  /**
   * Creates an initially empty linked list
   */
  public LinkedList() {
    firstNode = null;
  }
  
  /**
   * Returns the first node.
   * @return  the first node of the linked list
   */
  protected Node<T> getFirstNode() {
    return firstNode;
  }

  /**
   * Changes the front node.
   * @param node  the node that will be the first node of the new linked list
   */
  protected void setFirstNode(Node<T> node) {
    this.firstNode = node;
  }
  
  /**
   * Returns the last node.
   * @return  the last node of the linked list
   * @throws NoSuchElementException if the list is empty
   */
  protected Node<T> getLastNode()
  {
    
    if (isEmpty()) {
      throw new NoSuchElementException("The linked list is empty.");
    }
    
    Node<T> nodeptr = getFirstNode();
    while (nodeptr.getNext() != null) 
    {
      nodeptr = nodeptr.getNext();
    }
    return nodeptr;
  }

  /**
   * Return whether the list is empty
   * @return true if the list is empty
   */
  public boolean isEmpty() {
    return (getFirstNode() == null);
  }
  
  /**
   * Add an element to the front of the linked list
   * @param element  The element value that wanted to add to the front of the list
   */
  public void addToFront(T element) {
    setFirstNode(new Node<T>(element, getFirstNode()));
  }
  
  /**
   * Add an element to the back of the linked list
   * @param element  The element value that wanted to add to the back of the list
   */
  public void addToBack(T element)
  {
    if (isEmpty())
    {
      setFirstNode(new Node<T>(element,null));
    }
    else
    {
      getLastNode().setNext(new Node<T>(element,null));
    }
  }
  
  /**
   * Removes and returns the element at the front of the linked list
   * @return the element removed from the front of the linked list
   * @throws NoSuchElementException if the list is empty
   */
  public T removeFromFront() {
    if (isEmpty())
    {
      throw new NoSuchElementException();
    }
    else 
    {
      T save = getFirstNode().getElement();
      setFirstNode(getFirstNode().getNext());
      return save;
    }
  }
  
  /**
   * Return the first element of the link list without removing it
   * @return  the first element of the linked list
   * @throws NoSuchElementException if the list is empty
   */
  public T firstElement()
  {
    if (isEmpty())
    {
      throw new NoSuchElementException();
    }
    else
    {
      return getFirstNode().getElement();
    }
  }
  
  /** 
   * Create the method of Iterable to return the iterator for the list
   * @return the iterator for the list
   */
  @Override
  public Iterator<T> iterator() 
  {
    return new Iterator<T>() {
      private Node<T> nodeptr = getFirstNode();
      private Node<T> lastReturned = null;
      
      /**
       * Return true if the node pointer is not null in the linked list, otherwise false
       * @return  true if the node pointer is not null in the linked list, otherwise false
       */ 
      public boolean hasNext() {
        return nodeptr != null;
      }
      
      /**
       * Return the next element in the link list during iteration
       * @return the next element in the link list during iteration
       * @throws NoSuchElementException if the next node in the list is empty
       */ 
      public T next() throws NoSuchElementException{
        if(!hasNext())
        {
          throw new NoSuchElementException();
        }
        lastReturned = nodeptr;
        nodeptr = nodeptr.getNext();
        return lastReturned.getElement();
      }
      
      /**
       * Remove the last element returned by next() method
       * @throws IllegalStateException if the last node return is null
       */
      public void remove() throws IllegalStateException
      {
        // If none of the element has been returned
        if (lastReturned == null)
        {
          throw new IllegalStateException("No element to remove");
        }
        // If the last returned node is the first node in the list
        if (lastReturned == getFirstNode())
        {
          setFirstNode(lastReturned.getNext());
        } 
        else 
        {
          Node<T> nodeptrBeforeLast = getFirstNode();
          while (nodeptrBeforeLast.getNext() != lastReturned)
          {
            nodeptrBeforeLast = nodeptrBeforeLast.getNext();
          }
          nodeptrBeforeLast.setNext(lastReturned.getNext());
          lastReturned = null;
        }
      }
    };
  }
   
  /**
   * The node of a linked list
   */
  public static class Node<T> {
    /** the element stored in the node */
    private T element;
    
    /** a reference to the next node of the list */
    private Node<T> next;
    
    /**
     * the constructor
     * @param element  the element to store in the node
     * @param next  a reference to the next node of the list
     */
    public Node(T element, Node<T> next) {
      this.element = element;
      this.next = next;
    }
    
    /**
     * Returns the element stored in the node
     * @return the element stored in the node
     */
    public T getElement() {
      return element;
    }
    
    /**
     * Returns the next node of the list
     * @return the next node of the list
     */
    public Node<T> getNext() {
      return next;
    }
    
    
    /**
     * Changes the node that comes after this node in the list
     * @param next  the node that should come after this node in the list.  It can be null.
     */
    public void setNext(Node<T> next) {
      this.next = next;
    }
    
    /**
     * Changes the element in the node
     * @param element  the new element to store*/
    public void setElement(T element)
    {
      this.element = element;
    }
  }
}

