import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DominoUserDataExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Domino domino = new Domino(3, 5);

        Button button = new Button(domino.toString());

        // Set user data to a Domino object
        button.setUserData(domino);

        // Attach an event handler to print user data on button click
        button.setOnAction(e -> {
            Domino clickedDomino = (Domino) button.getUserData();
            System.out.println("Clicked Domino: " + clickedDomino);
        });

        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Domino UserData Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
