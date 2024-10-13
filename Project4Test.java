/**
 * This class test all the methods that do not related to GUI in project 4 assignment
 * @author Brian Nguyen - nbn10
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Iterator;

public class Project4Test 
{
  /**
   * Test class LinkedList. Test All method beside getLastNode()
   */
  @Test
  public void testLinkedList()
  {
    // Part 1. Test the static class Node<T> inside LinkedList<T>
    LinkedList.Node<Integer> node1 = new LinkedList.Node<Integer>(1,null);
    LinkedList.Node<Integer> node2 = new LinkedList.Node<Integer>(2,null);
    // 1.1 Test getElement() method
    assertEquals("Incorrect getElement() value", Integer.valueOf(1), node1.getElement());
    
    // 1.2 Test getNext() method
    assertEquals("Incorrect getNext() value", null, node1.getNext());
    
    // 1.3 Test setNext() method
    node1.setNext(node2);
    assertEquals("Incorrect getNext() value after using setNext()", node2, node1.getNext());
    
    // 1.4 Test setElement() method
    node1.setElement(25);
    assertEquals("Incorrect getElement() value after using setElement()", Integer.valueOf(25), node1.getElement());
    
    // Part 2. Test LinkedList<T> class
    LinkedList<Integer> list1 = new LinkedList<>();
    LinkedList<Integer> list2 = new LinkedList<>();
    LinkedList<Integer> list3 = new LinkedList<>();
    LinkedList.Node<Integer> node3 = new LinkedList.Node<Integer>(3,null);
    
    // 1.5 Test getFirstNode() method
    assertEquals("Incorrect result of when using getFirstNode()", null, list1.getFirstNode());
    
    // 1.6 Test setFirstNode() method
    list1.setFirstNode(node3);
    assertEquals("Incorrect node return after using setFirstNode()", node3, list1.getFirstNode());
    
    // 1.7 Test isEmpty() method
    // a. If the list is empty
    assertEquals("Invalid boolean expression for empty list", true, list2.isEmpty());
    // b. If the list is not empty
    assertEquals("Invalid boolean expression for a not empty list", false, list1.isEmpty());
    
    // 1.8 Test addToFront() method
    list1.addToFront(15);
    assertEquals("Invalid element return after adding the element to the front of the list", Integer.valueOf(15), list1.getFirstNode().getElement());
    
    // 1.9 Test addToBack() method
    // a. If the list is empty
    list2.addToBack(10);
    assertEquals("Invalid element return after adding the element to the back of the list", Integer.valueOf(10), list2.getFirstNode().getElement());
    // b. If the list is not empty
    list1.addToBack(100);
    assertEquals("Invalid element return after adding the element to the back of the list", Integer.valueOf(100), list1.getLastNode().getElement());
    
    // 1.10 Test removeFromFront() method
    // a. If the list is empty
    assertEquals("The list is empty, no node to remove", true, list3.isEmpty());
    try {
      list3.removeFromFront();
      fail("Remove the first node from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Remove the first node from empty list threw the wrong type of exception.");
    }
    // b. If the list is not empty
    assertEquals("Invalid element return after removing the element from the front of the list", Integer.valueOf(15), list1.removeFromFront());
    assertEquals("Invalid frist element of the list return after removing the element from the front of the list", Integer.valueOf(3), list1.getFirstNode().getElement());
    
    // 1.11 Test firstElement() method
    // a. If the list is empty
    assertEquals("The list is empty, no element to retrieve", true, list3.isEmpty());
    try {
      list3.firstElement();
      fail("Retrieve the first node from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Retrieve the first node from empty list threw the wrong type of exception.");
    }
    // b. If the list is not empty
    assertEquals("Invalid frist element of the list return", Integer.valueOf(3), list1.firstElement());
  }
  
  /**
   * Part 3 - Test getLastNode() method
   */
  @Test
  public void testGetLastNode()
  {
    LinkedList<Integer> list = new LinkedList<Integer>();
    // Case 0. Test 0. The list is empty
    assertEquals("The list is empty, cannot get the last node", true, list.isEmpty());
    try {
      list.getLastNode();
      fail("Get last node from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Get last node from empty list threw the wrong type of exception.");
    }
    // Case 1. Test 1 and Test First. The list has 1 node and the node is the first node in the list
    list.addToFront(3);
    assertEquals("Invalid last node element return", Integer.valueOf(3), list.getLastNode().getElement());
    // Case 2. Test Many and Test Last
    list.addToBack(2);
    assertEquals("Invalid last node element return", Integer.valueOf(2), list.getLastNode().getElement());
    
    /* Since this is getLastNode() method, test middle does not apply to this method 
    since there is no node act as the middle and the end node at the same time*/
  }
  
  /**
   * Part 4 - Tests the linked list iterator.
   */
  @Test
  public void testListIterator() 
  {
    LinkedList<Integer> list = new LinkedList<Integer>();
    for (int i = 5; i > 0; i--)
    {
      list.addToFront(i);
    }
    /* checking that we get out what we put it */
    int i = 1;
    for (Integer x: list)
    {
      assertEquals("Testing value returned by iterator", new Integer(i++), x);
    }
    if (i != 6)
    {
      fail("The iterator did not run through all the elements of the list");
    }
  }
  
  /**
   * Tests the remove feature of the linked list iterator.
   */
  @Test
  public void testListIteratorRemove() 
  {
    LinkedList<Integer> list = new LinkedList<Integer>();
    for (int i = 5; i > 0; i--)
    {
      list.addToFront(i);
    }
    // Test First: Testing removing the first element through the iterator 
    Iterator<Integer> listIterator = list.iterator();
    listIterator.next();
    listIterator.remove();
    // The list should now be 2 through 5
    int i = 2;
    for (Integer x: list)
    {
      assertEquals("The iterator failed when removing the first element", new
                     Integer(i++), x);
    }
    if (i != 6)
    {
      fail("The iterator failed when removing the first element");
    }
    
    // Test Middle: Testing removing the middle elements
    // Testing removing element 3 
    listIterator = list.iterator();
    listIterator.next();
    listIterator.next();
    listIterator.remove();
    LinkedList.Node<Integer> head = list.getFirstNode();
    assertEquals("Iterator failed to remove middle element", new Integer(2),
                 head.getElement());
    assertEquals("Iterator failed to remove middle element", new Integer(4),
                 head.getNext().getElement());
    assertEquals("Iterator failed to remove middle element", new Integer(5),
                 head.getNext().getNext().getElement());
    assertEquals("Iterator failed to remove middle element", null,
                 head.getNext().getNext().getNext());
    
    // Test last: Testing removing the last element
    while (listIterator.hasNext())
    {
      listIterator.next();
    }
    listIterator.remove();
    head = list.getFirstNode();
    assertEquals("Iterator failed to remove middle element", new Integer(2),
                 head.getElement());
    assertEquals("Iterator failed to remove middle element", new Integer(4),
                 head.getNext().getElement());
    assertEquals("Iterator failed to remove middle element", null,
                 head.getNext().getNext());
    
    /* Case Special: Testing removing before calling next */
    listIterator = list.iterator();
    try 
    {
      listIterator.remove();
      fail("Calling iterator's remove() before calling next() should throw an IllegalStateException");
    }
    catch (IllegalStateException e) 
    {
      // all is good
    }
    catch (Exception e) {
      fail("The wrong exception thrown by the iterator remove() method.");
    }
  }
  
   
  /**
   * Test class Domino
   */
  @Test
  public void testDomino()
  {
    // Create testing instance for class Domino
    Domino d1 = new Domino(4,6);
    Domino d2 = new Domino(4,6);
    
    // 2.1 Test getFront() method
    assertEquals("Incorrect getFront() value", 4, d1.getFront());
    
    // 2.2 Test getBack() method
    assertEquals("Incorrect getBack() value", 6, d1.getBack());
    
    // 2.3 Test rotate() method
    assertEquals("Incorrect new Domino() instance after using rotate", new Domino(6,4), d1.rotate());
    
    // 2.4 Test toString() method
    assertEquals("Incorrect string representation of the Domino", "[4|6]", d1.toString());
    
    // 2.5 Test equals() method
    assertEquals("Incorrect boolean expression when using .equals() method", true, d1.equals(d2));
  }
  
  /**
   * Test class DominoTrain
   */
  @Test
  public void testDominoTrain()
  {
    // Create testing instance for DominoTrain
    DominoTrain train1 = new DominoTrain(9);
    DominoTrain train2 = new DominoTrain(9);
    DominoTrain train3 = new DominoTrain(9);
    Domino d1 = new Domino(9,9);
    Domino d2 = new Domino(8,9);
    Domino d3 = new Domino(9,8);
    
    // 3.1 Test isOpen() method
    assertEquals("Incorrect boolean expression when calling isOpen() method", false, train1.isOpen());
    
    // 3.2 Test setOpen() method
    train1.setOpen(true);
    assertEquals("Incorrect boolean expression when calling isOpen() method after using setOpen()", true, train1.isOpen());
    
    // 3.3 Test getStartValue() method
    assertEquals("Incorrect start value of the train return", 9, train1.getStartValue());
    
    // 3.4 Test addToFront() method
    // Case 1. If the train is empty
    // Case 1.1: Add a double domino that has the same value with starting value of the train
    train1.addToFront(d1);
    assertEquals("Incorrect domino return after adding a Domino to the train",d1, train1.getTrain().getFirstNode().getElement());
    // Case 1.2: Add a domino that has only the "back" value equal to the starting value of the train
    train2.addToFront(d2);
    assertEquals("Incorrect domino return after adding a Domino to the train",d2, train2.getTrain().getFirstNode().getElement());
    /* Case 1.3: Add a domino that has only the "front" value equal to the starting value of the train
     * Need to rotate the domino before adding to the front of the train */
    train3.addToFront(d3);
    assertEquals("Incorrect domino return after adding a Domino to the train",d3.rotate(), train3.getTrain().getFirstNode().getElement());
    
    // Case 2: If the train is not empty
    // Case 2.1: Add a double domino that has the same value with the front of the first domino in the train
    train1.addToFront(new Domino(9,9));
    assertEquals("Incorrect domino return after adding a Domino to the train",new Domino(9,9), train1.getTrain().getFirstNode().getElement());
    // Case 2.2: Add a domino that has only the "back" value equal to the "front" value of the first domino in the train
    train2.addToFront(new Domino(5,8));
    assertEquals("Incorrect domino return after adding a Domino to the train",new Domino(5,8), train2.getTrain().getFirstNode().getElement());
    /* Case 2.3: Add a domino that has only the "front" value equal to the "front" value of the first domino in the train
     * Need to rotate the domino before adding to the front of the train */
    train3.addToFront(new Domino(8,5));
    assertEquals("Incorrect domino return after adding a Domino to the train",new Domino(5,8), train3.getTrain().getFirstNode().getElement());
    
    // 3.5 Test getEndValue() method
    DominoTrain train4 = new DominoTrain(9);
    // a. If the train is empty
    assertEquals("Incorrect end value of the train returned when the train is empty",9, train4.getEndValue());
    // b. If the train is not empty
    train4.addToBackTrain(new Domino(9,7));
    assertEquals("Incorrect end value of the train returned when the train is not empty",7, train4.getEndValue());
    
    // 3.6 Test addToBackTrain() method
    train4.addToBackTrain(new Domino(7,5));
    assertEquals("Incorrect the last domino the train returned when the train is not empty",new Domino(7,5), train4.getTrain().getLastNode().getElement());
    assertEquals("Incorrect end value of the train returned when the train is not empty",5, train4.getEndValue());
    
    // 3.7 Test checkDomino() method
    // a. If the back value of the domino match the current end value of the train
    assertEquals("Incorrect Domino return when using check domino",new Domino(5,9), train4.checkDomino(new Domino(9,5)));
    // b. If the back value of the domino does not match the current end value of the train
    assertEquals("Incorrect Domino return when using check domino",new Domino(6,9), train4.checkDomino(new Domino(6,9)));
  }
}