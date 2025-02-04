/**
 * A class to represent a Domino Train
 * @author Brian Nguyen - nbn10
 */
public class DominoTrain
{
  // This field store the domino train
  private LinkedList<Domino> train;
  // This field store the start value of each train
  private int startValue;
  // This field initalize the open/close status of the train
  private boolean isOpen;
    
  /**
   * Creates an initially empty train and initialize the starting value of each train
   * @param startValue  the starting double domino of the train
   */
  public DominoTrain(int startValue)
  {
    this.startValue = startValue;
    this.train = new LinkedList<Domino>();
    this.isOpen = false;
  }
  
  /**
   * Check if the train is open
   * @return true if the train is open, false otherwise
   */
  public boolean isOpen() {
    return isOpen;
  }
  
  /**
   * Set the status of the train (open or closed)
   * @param isOpen true if the train should be open, false if closed
   */
  public void setOpen(boolean isOpen) {
    this.isOpen = isOpen;
  }
    
  /**
   * return the starting value of the domino train
   * @return  the starting value of the domino train
   */
  public int getStartValue()
  {
    return startValue;
  }
 
  /**
   * return the domino train
   * @return  the domino train
   */
  public LinkedList<Domino> getTrain()
  {
    return train;
  }
  
  /**
   * add a domino to the front of the train
   * @param domino  a domino that want to add to the front of the train
   */
  public void addToFront(Domino domino)
  {
    /* Case 1. If the train is empty*/
    if(getTrain().isEmpty())
    {
      /* Case 1.1: Add a double domino that has the same value with starting value of the train*/
      if ((domino.getFront() == getStartValue()) && (domino.getBack() == getStartValue()))
      {
        getTrain().addToFront(domino);
      }
      /* Case 1.2: Add a domino that has only the "back" value equal to the starting value of the train*/
      else if (domino.getBack() == getStartValue())
      {
        getTrain().addToFront(domino);
      }
      /* Case 1.3: Add a domino that has only the "front" value equal to the starting value of the train*/
      else if (domino.getFront() == getStartValue())
      {
        getTrain().addToFront(domino.rotate());
      }
    }
    /* Case 2: If the train is not empty*/
    else
    {
      /* Case 2.1: Add a double domino that has the same value with the front of the first domino in the train*/
      if ((domino.getFront() == getTrain().getFirstNode().getElement().getFront()) && (domino.getBack() == getTrain().getFirstNode().getElement().getFront()))
      {
        getTrain().addToFront(domino);
      }
      /* Case 2.2: Add a domino that has only the "back" value equal to the "front" value of the first domino in the train*/
      else if (domino.getBack() == getTrain().getFirstNode().getElement().getFront())
      {
        getTrain().addToFront(domino);
      }
      /* Case 2.3: Add a domino that has only the "front" value equal to the "front" value of the first domino in the train*/
      else if (domino.getFront() == getTrain().getFirstNode().getElement().getFront())
      {
        getTrain().addToFront(domino.rotate());
      }
    }
  }
  
  /**
   * get the current end value of the train (the "back" value of the last domino in the train)
   * @return  the current end value of the train (the "back" value of the last domino in the train)
   */
  public int getEndValue()
  {
    if (getTrain().isEmpty())
    {
      return getStartValue();
    }
    return (getTrain().getLastNode().getElement().getBack());
  }
  
  /**
   * Check if this domino can be added to the train
   * @param domino  the domino that want to add
   * @return  true if this domino can be added to the train. Otherwise, false.
   */
  public boolean canAdd(Domino domino)
  {
    if ((domino.getFront() == getEndValue()) || (domino.getBack() == getEndValue()))
    {
      return true;
    }
    return false;
  }
  
  /**
   * Add the domino to the back of the train
   * @param domino  the domino that want to add to the back of the train
   */
  public void addToBackTrain(Domino domino)
  {
    getTrain().addToBack(domino);
  }
  
  /**
   * Check if the back of the domino is equal to the end of the train. 
   * If yes, rotate the domino. If no, keep the domino.
   * @param domino the domino that want to check
   * @return  the domino after being rotate or the same domino input
   */
  public Domino checkDomino(Domino domino)
  {
    if ((domino.getBack() == getEndValue()))
    {
      return domino.rotate();
    }
    return domino;
  }
}