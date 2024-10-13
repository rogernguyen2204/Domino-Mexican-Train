/**
 * A class to represent a single Domino
 * @author Brian Nguyen - nbn10
 */
public class Domino
{
  // this field store the value of the front of the domino
  private final int front;
  // this field store the value of the back of the domino
  private final int back;
  
  /**
   * This constructor initialize the value of the domino
   * @param front  the front value of a domino
   * @param back  the back value of a domino
   */
  public Domino(int front, int back)
  {
    this.front = front;
    this.back = back;
  }
  
  /**
   * Return the value of the front of the domino
   * @return the value of the front of the domino
   */
  public int getFront()
  {
    return front;
  }
  
  /**
   * Return the value of the back of the domino
   * @return the value of the back of the domino
   */
  public int getBack()
  {
    return back;
  }
  
  /**
   * Rotate the value of the front and the back of the domino
   * @return a new Domino with the front and the back rotated
   */
  public Domino rotate()
  {
    return new Domino(getBack(),getFront());
  }
  
  /**
   * Return a string representation of the domino
   * @return a string representation of the domino
   */
  @Override
  public String toString()
  {
    return ("[" + getFront() + "|" + getBack() + "]");
  }
  
  /**
   * Check if two Domino are similar.
   * @param o the domino that this domino wants to compare with
   * @return true if two Dominos are similar. Otherwise, return false.
   */
  @Override
  public boolean equals(Object o)
  {
    if (o instanceof Domino)
    {
      Domino e = (Domino) o;
      return ((this.getFront() == e.getFront()) && (this.getBack() == e.getBack()));
    }
    return false;
  }
}