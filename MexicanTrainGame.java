/**
 * A class to represent the Mexican Train Game play 
 * @author Brian Nguyen - nbn10
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.*;
import javafx.scene.paint.Color;

public class MexicanTrainGame extends Application
{
  
  // This field stores the default number of player in this game
  private static int numPlayers = 2;
  // This field stores the Pile that contain leftover Dominos after deal them to players
  private LinkedList<Domino> drawPile = new LinkedList<>();    // Have not create getter method
  // This field stores all players'hands in an ArrayList 
  private List<LinkedList<Domino>> playerHands = new ArrayList<>(); // Have not create getter method
  // This field stores all players'stages in an ArrayList
  private List<Stage> playerStages = new ArrayList<>(); // Have not create getter method
  // This field stores all players'trains in an ArrayList
  private List<DominoTrain> allPlayerTrains = new ArrayList<>();
  // This field stores the Mexican Train that start with 9 in this game play
  private DominoTrain theMexicanTrain = new DominoTrain(9); 
  // This field stores the lastClickButton for a certain Domino Button in this program
  private Button lastClickButton = null;
  // This field stores the display of each player's row in the main stage of the program
  private List<HBox> playerRows = new ArrayList<>();
  // This field stores the HBox specifically for the mexicanTrain
  private HBox mexicanTrainHBox = new HBox();
  // This field stores the lastClickDomino in this game
  private Domino lastClickDomino;
  // This field stores the current player number
  private int currentPlayer = 1;
  // This field store boolean variables that decide whether a player has drawn a domino in his turn
  private List<Boolean> hasDrawnThisTurn = new ArrayList<>();
  // This field store a flag that determine if the game has ended or not
  private boolean isGameEnd = false;
  
  /* NOTATION: Since this class is a GUI for my MexicanTrainGame, I decide not to create getter and setter method
   * for the private fields. The reason is because I do not want other people to make changes of my field. According to
   * our instructor, when coding with JavaFx and GUI, we can violate the rules of object- oriented programing, as stated
   * in video Event Driven Programming and Issue with JavaFx on November 15.
   */
  
  /**
   * Initialize the main stage for the game play
   * @param primaryStage  the main stage of the game play
   */
  public void start(Stage primaryStage)
  {
    setUpGame();
    System.out.println("Player 1's turn. By default, Player 1 go first in the game.");
    primaryStage.setTitle("The Mexican Train Game");
    primaryStage.setWidth(800);
    primaryStage.setHeight(250);
    
    // This variable store each player's train and the mexican train representation in the main window
    VBox vbox = new VBox(20); // spacing = 8
    // Create player's trains on the main window depends on the number of players in the game
    for (int i = 1; i <= numPlayers; i++)
    {
      HBox playerRow = createPlayerRow(i);
      playerRows.add(playerRow);
      vbox.getChildren().add(playerRow);
    }
    // Create player's hand stage on the main window depends on the number of players in the game
    for (int i = 1; i <= numPlayers; i++)
    {
      Stage playerStage = createPlayerHandStage(i);
      playerStages.add(playerStage);
      if (i == 1) {
        hasDrawnThisTurn.add(false);
      } else {
        hasDrawnThisTurn.add(true);
      }
      playerStage.show();
    }
    // Add the display of the Mexican Train to the main stage
    vbox.getChildren().add(createMexicanTrainDisplay());
    Scene scene = new Scene(vbox); 
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  /**
   * Display the player's hand at a seperate stage
   * @param playerNumber  the current player number
   * @return  the Stage that display the player's hand
   */
  private Stage createPlayerHandStage(int playerNumber)
  {
    // This variable is a new stage showing player's hand
    Stage playerStage = new Stage();
    // This variable is a Hbox contain the information of the player hand
    HBox playerHBox = new HBox();
    playerHBox.getChildren().add(new Label("Your Domino: "));
    // Add domino buttons of the domino to player's hand stage
    for (Domino domino : playerHands.get(playerNumber - 1))
    {
      // This variable store each domino button that will be added to a player's hand
      Button dominoButton = createDominoButtonForPlayerHand(domino, playerNumber);
      playerHBox.getChildren().add(dominoButton);
    }
    // This variable is a VBox that contains the player's hand display and the "Draw" button
    VBox finalVBox = new VBox(30);
    finalVBox.getChildren().add(playerHBox);
    // This variable is a button "Draw"
    Button drawButton = new Button("Draw");
    // Event Handler of when the "Draw" button being clicked
    drawButton.setOnAction((e) -> {
      drawButtonClickHandle(playerNumber, playerStage,playerHands.get(playerNumber - 1));
    }); 
    finalVBox.getChildren().add(drawButton);
    playerStage.setScene(new Scene(finalVBox, 800, 100));
    playerStage.setTitle("Player " + playerNumber + "'s Hand");
    
    return playerStage;
    
  }
  
  /**
   * Display the player's train on the main stage
   * @param playerNumber  the current player number
   * @return  the HBox that represent the player row at the main stage
   */
  private HBox createPlayerRow(int playerNumber)
  {
    // This variable store the HBox that represent a player row 
    HBox playerRow = new HBox();
    // This variable store the label as a string representation of player's train
    Label playerLabel = new Label("Player " + playerNumber + " Train: ");
    playerRow.getChildren().add(playerLabel);
    // This variable is a new HBox that contains all the domino that will be added to the train
    HBox playerTrainDisplay = new HBox(); 
    playerRow.getChildren().add(playerTrainDisplay);
    // This variable is a button "Add Domino" at the end of each train
    Button addDomino = new Button("Add Domino");
    // Event Handler of when the "Add Domino To  Train" button being clicked
    addDomino.setOnAction( (e) -> { 
      handleAddDominoToTrain(playerNumber);
    });
    playerRow.getChildren().add(addDomino);
    return playerRow;
  }
  
  /**
   * Display the Mexican Train on the main stage 
   * @return  the HBox that represent the Mexican Train
   */
  private HBox createMexicanTrainDisplay()
  {
    // This variable stores the label at the beginning of the train
    Label trainLabel = new Label("Mexican Train: ");
    mexicanTrainHBox.getChildren().add(trainLabel);
    // This variable is a new HBox that contains all the domino that will be added to the train
    HBox mexicanTrainDisplay = new HBox(); 
    mexicanTrainHBox.getChildren().add(mexicanTrainDisplay);
    // This variable is a button "Add Domino To Train" at the end of each train
    Button addDomino = new Button("Add Domino To Train");
    mexicanTrainHBox.getChildren().add(addDomino);
    // Event Handler of when the "Add Domino To  Train" button being clicked
    addDomino.setOnAction( (e) -> { 
      handleAddDominoToMexicanTrain(currentPlayer);
    });
    return mexicanTrainHBox;
  }
  
  /**
   * Handle the "Add Domino" when "Add Domino" button is clicked 
   * @param playerNumber  the Domino that want to add
   */
  private void handleAddDominoToTrain(int playerNumber)
  {
    // If the game (round) is not ended
    if (!isGameEnd)
    {
      // check if a domino is selected in a player's hand
      if (lastClickButton == null)
      {
        System.out.println("Select a domino from your hand first.");
      }
      
      /* Check if this is a legal move or not
       * If yes, add the domino to the Mexican Train
       * Remove the Domino from a player's hand
       * Remove the button from a player's stage
       */
      if (canAddToTrain(lastClickDomino ,playerNumber))
      {
        addDominoToSelectedTrain(lastClickDomino,playerNumber);
        removeDominoFromPlayerHand(lastClickDomino, currentPlayer);
        removeButtonFromPlayerHandStage(lastClickButton, currentPlayer);
        
        /* Check if the player is adding to their own train
         * If yes, closed their train, change the of the train back to default
         * If no, leave that train open
         * Remove the button from a player's stage
         */
        if (currentPlayer == playerNumber) {
          DominoTrain playerTrain = allPlayerTrains.get(playerNumber - 1);
          playerTrain.setOpen(false);
          changeColorNotifyClosedTrain(playerRows.get(playerNumber - 1));
        }
        
        /* Check to see if after adding to the mexican train, the round is continue or not
         * If yes, end the round, no button will have any effect
         * If no, switch to next player
         */
        if (isRoundOver(playerNumber))
        {
          isGameEnd = true;
          System.out.println("Round over. Player " + playerNumber + " win this round.");
        }
        else
        {
          switchToNextPlayer();
        }
      }
      else
      {
        System.out.println("Illegal move.Domino cannot be added to this train");
      }
    }
  }
  
  /**
   * Handle the "Add Domino To Train" when "Add Domino To Train" button is clicked 
   * @param playerNumber  the Domino that want to add
   */
  private void handleAddDominoToMexicanTrain(int playerNumber)
  {
    // If the game (round) is not ended
    if (!isGameEnd)
    {
      // check if a domino is selected in a player's hand
      if (lastClickButton == null)
      {
        System.out.println("Select a domino from your hand first.");
      }
      
      /* Check if this is a legal move or not
       * If yes, add the domino to the Mexican Train
       * Remove the Domino from a player's hand
       * Remove the button from a player's stage
       */
      if (canAddToMexicanTrain(lastClickDomino))
      {
        addDominoToMexicanTrain(lastClickDomino,playerNumber);
        removeDominoFromPlayerHand(lastClickDomino,playerNumber);
        removeButtonFromPlayerHandStage(lastClickButton, playerNumber);
        /* Check to see if after adding to the mexican train, the round is continue or not
         * If yes, end the round, no button will have any effect
         * If no, switch to next player
         */
        if (isRoundOver(playerNumber))
        {
          isGameEnd = true;
          System.out.println("Round over. Player " + playerNumber + " win this round.");
        }
        else
        {
          switchToNextPlayer();
        }
      }
      else
      {
        System.out.println("Illegal move.Domino cannot be added to this train");
      }
    }
  }
  
  /**
   * Handle the "Draw" button when the "Draw" button is clicked 
   * @param playerNumber  the Domino that want to add
   * @param playerStage  the player hand stage 
   * @param playerHand  the player hand
   */
  private void drawButtonClickHandle(int playerNumber, Stage playerStage, LinkedList<Domino> playerHand)
  {
    // If the game (round) is not ended and the pile still contains domino
    if ((!isGameEnd) && (!drawPile.isEmpty()) && (playerNumber == currentPlayer))
    {
      // If the player has not drawn a domino from the pile in this turn
      if (!hasDrawnThisTurn.get(playerNumber - 1)) 
      {
        hasDrawnThisTurn.set(playerNumber - 1, true); // Player has drawn a domino this turn\
        // This variable store the lastest domino being drawn from the pile
        Domino drawnDomino = drawPile.removeFromFront();
        playerHand.addToBack(drawnDomino);
        // This variable is the new button for the new domino being drawn
        Button dominoButton = createDominoButtonForPlayerHand(drawnDomino, playerNumber);
        // Add the new button to the stage
        ((HBox) playerStage.getScene().getRoot().getChildrenUnmodifiable().get(0)).getChildren().add(dominoButton);
        
        // Open this player's train
        DominoTrain playerTrain = allPlayerTrains.get(playerNumber - 1);
        playerTrain.setOpen(true);
        // Change the color of the row to notice that the train is open.
        changeColorNotifyOpenTrain(playerRows.get(playerNumber - 1));
        
        /* Check if this new domino can be added into any trains in the main stage.
         * If yes, change the background color of that button to green
         * If no, switch to next player*/
        if (canAddToTrain(drawnDomino, playerNumber) || canAddToMexicanTrain(drawnDomino)) 
        {
          BackgroundFill fill = new BackgroundFill(Color.GREEN, null, null);
          Background background = new Background(fill);
          dominoButton.setBackground(background);
        } 
        else 
        {
          switchToNextPlayer();
        }
      }
      else 
      {
        System.out.println("You can only draw one domino per turn.");
      }
    }
  }
  
  /**
   * Change color of a train being opened to pink 
   * @param closeTrain  the HBox that contains the presenation of a open train
   */
  private void changeColorNotifyOpenTrain(HBox openTrain)
  {
    BackgroundFill fill = new BackgroundFill(Color.PINK,null,null);
    Background background = new Background(fill);
    openTrain.setBackground(background);
  }
  
  /**
   * Change color of a train being closed to default 
   * @param closeTrain  the HBox that contains the presenation of a close train
   */
  private void changeColorNotifyClosedTrain(HBox closeTrain)
  {
    BackgroundFill fill = new BackgroundFill(null,null,null);
    Background background = new Background(fill);
    closeTrain.setBackground(background);
  }
  
  /**
   * Check if a domino can be added to the any trains 
   * @param selectedDomino  the Domino that want to add
   * @retrun  true if this domino can be added to the train. Otherwise, false
   */
  private boolean canAddToTrain(Domino selectedDomino, int playerNumber)
  {
    // This variable retrieve a player's train
    DominoTrain playerTrain = allPlayerTrains.get(playerNumber - 1);
    // check if the train is open or if it's the player own train
    if (playerTrain.isOpen() || playerTrain == allPlayerTrains.get(currentPlayer - 1))
    {
      return (playerTrain.canAdd(selectedDomino));
    }
    else
    {
      System.out.println("This train is closed. You cannot add the domino to this train");
      return false;
    }
  } 
  
  /**
   * Check if a domino can be added to the Mexican Train
   * @param selectedDomino  the Domino that want to add
   * @retrun  true if this domino can be added to the Mexican Train. Otherwise, false
   */
  private boolean canAddToMexicanTrain(Domino selectedDomino)
  {
      return (theMexicanTrain.canAdd(selectedDomino));
  }
  
  /**
   * Add a Domino to the other trains in the main stage
   * @param selectedDomino  the Domino that want to add
   * @param playerNumber  the current player number
   */
  private void addDominoToSelectedTrain(Domino selectedDomino,int playerNumber)
  {
    // This variable retrieve the train of a player
    DominoTrain playerTrain = allPlayerTrains.get(playerNumber - 1);
    // This variable store the final domino after being checked.
    Domino checkDomino = playerTrain.checkDomino(selectedDomino);
    playerTrain.addToBackTrain(checkDomino); 
    // This variable retrieve each player row in the main stage
    HBox playerRow = playerRows.get(playerNumber - 1);
    /* This variable retrieve the children of the HBox in the main stage that 
     * store the string representation of a domino in the train*/
    HBox playerTrainDisplay = (HBox) playerRow.getChildren().get(1);
    // This variable store the label as a string representation of a domino
    Label dominoLabel = new Label(checkDomino.toString());
    playerTrainDisplay.getChildren().add(dominoLabel);
  }
  
  /**
   * Add a Domino to the Mexican Train in the main stage
   * @param selectedDomino  the Domino that want to add
   * @param playerNumber  the current player number
   */
  private void addDominoToMexicanTrain(Domino selectedDomino,int playerNumber)
  {
    // This variable store the final domino after being checked.
    Domino checkDomino = theMexicanTrain.checkDomino(selectedDomino);
    theMexicanTrain.addToBackTrain(checkDomino);
    /* This variable retrieve the children of the HBox in the main stage that 
     * store the string representation of a domino in the train*/
    HBox mexicanTrainDisplay = (HBox) mexicanTrainHBox.getChildren().get(1);
    // This variable store the label as a string representation of a domino
    Label dominoLabel = new Label(checkDomino.toString());
    mexicanTrainDisplay.getChildren().add(dominoLabel);
  }
  
  
  /**
   * Remove a Domino from a player's hand
   * @param selectedDomino  the Domino that need to be removed
   * @param playerNumber  the current player number
   */
  private void removeDominoFromPlayerHand(Domino selectedDomino, int playerNumber)
  {
    // This variable retrieve a player's hand
    LinkedList<Domino> playerHand = playerHands.get(playerNumber - 1);
    // This variable is an iterator to perform looping
    Iterator<Domino> iterator = playerHand.iterator();
    while (iterator.hasNext()) {
      Domino domino = iterator.next();
      if (domino.equals(selectedDomino)) {
        iterator.remove();
      }
    }
  }
  
  /**
   * Remove a domino button from a player's hand.
   * @param buttonToRemove  the button that need to be removed
   * @param playerNumber  the current player number
   */
  private void removeButtonFromPlayerHandStage(Button buttonToRemove, int playerNumber) {
    // This variable retrieve the HBox children that contain the button
    HBox playerHBox = (HBox) playerStages.get(playerNumber - 1).getScene().getRoot().getChildrenUnmodifiable().get(0);
    playerHBox.getChildren().remove(buttonToRemove);
  }
  
  /**
   * Switch to the next player after a player finishes their turn
   */
  private void switchToNextPlayer()
  {
    currentPlayer++;
    if (currentPlayer > numPlayers)
    {
      currentPlayer = 1;
    }
    // Reset the flag of whether this player has drawn a domino in their turn or not
    hasDrawnThisTurn.set(currentPlayer - 1, false);
    // Print out the turn of the current player
    System.out.println("Player " + currentPlayer + "'s turn.");
  }
  
  
  /**
   * Create a domino button for each domino in player's hand.
   * @param domino  the domino that need a button
   * @param playerNumber  the current player number
   * @return a button of a domino
   */
  private Button createDominoButtonForPlayerHand(Domino domino, int playerNumber)
  {
    // This variable is a button that has the string representation of a domino
    Button dominoButton = new Button(domino.toString());
    // Event Handle when a player click on the domino
    dominoButton.setOnAction( (e) -> {
      if (playerNumber == currentPlayer)
      {
        // Change the back ground of the domino being clicked to Yellow
        Background originalBackground = dominoButton.getBackground();
        BackgroundFill fill = new BackgroundFill(Color.YELLOW,null,null);
        Background background = new Background(fill);
        dominoButton.setBackground(background);
        // Revert back to original when player select another domino button
        if (lastClickButton != null && lastClickButton != dominoButton)
        {
          lastClickButton.setBackground(originalBackground);
        }
        lastClickButton = dominoButton;
        
        // store the last clicked Domino 
        lastClickDomino = domino;
      }
    }                          
    );
    
    return dominoButton;
  }
 
  /**
   * Set up the game by create and shuffle dominos, deal dominos to player, and initialize the players'trains.
   */
  private void setUpGame()
  {
    createAndShuffleDomino();
    dealDominosToPlayer(numPlayers);
    
    // initialize train
    for (int i = 0; i < numPlayers; i++)
    {
      allPlayerTrains.add(new DominoTrain(9));
    }
  }
  
  /**
   * Create the all the dominos for this round play. Left out the [9|9] domino, which serve as the start value 
   * of the round
   */
  private void createAndShuffleDomino() 
  {
    // this variable stores all of the domino in an ArrayList
    List<Domino> allDominos = new ArrayList<>();
    // Create all of the dominos except for the [9|9] domino
    for (int i = 0; i <=9; i++)
    {
      for (int j = 0; j <= 9; j++)
      {
        if (!(i == 9 && j == 9))
        {
          allDominos.add(new Domino(i,j));
        }
      }
    }
    // Shuffle all of the dominos
    Collections.shuffle(allDominos);
    // Add all of the domino to the draw pile
    for (Domino domino : allDominos)
    {
      drawPile.addToFront(domino);
    }
  }
  
  /**
   * Deal domino to players. Each player has 12 dominos on the hand at the beginning of the game
   * @param numPlayers  the number of player in this game
   */
  private void dealDominosToPlayer(int numPlayers) 
  {
    for (int i = 0; i < numPlayers; i++)
    {
      // This variable store a single player hand
      LinkedList<Domino> playerHand = new LinkedList<>();
      playerHands.add(playerHand);
    }
    
    for (int i = 0; i < 12; i++)
    {
      for (int j = 0; j < numPlayers; j++)
      {
        if (!drawPile.isEmpty())
        {
          // This variable store the lastest domino being drawn from the pile
          Domino domino = drawPile.removeFromFront();
          playerHands.get(j).addToFront(domino);
        }
      }
    }
  }
  
  /**
   * Returns whether the round is over or not
   * @param playerNumber  the current player number
   * @return  true if the round is over. Otherwise, false
   */
  private boolean isRoundOver(int playerNumber)
  {
    return (playerHands.get(playerNumber - 1).isEmpty());
  }
  
  /**
   * This is the main method, will run the MexicanTrainGame play
   * @param args  input the number of player in the game
   */
  public static void main(String[] args)
  {
    // If the user input a value, check if that value is legal
    if (args.length > 0)
    {
      try
      {
      numPlayers = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException e)
      {
        System.out.println("Invalid input for the number of players. Please enter a number between 2 and 4");
      }
    }
    // If the player input a number that less than 2 and greater than 4, run the default case.
    if (numPlayers < 2 || numPlayers > 4)
    {
      System.out.println("Invalid number of players. Running default of 2 players.");
      numPlayers = 2;
    }
    Application.launch(args);
  }
  
}