import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        //instructions menu
        Label text = new Label("Welcome to Minesweeper!\n");
        text.setText(text.getText() + "You will be given a board full of square tiles to be revealed.\n");
        text.setText(text.getText() + "Each tile can randomly contain a mine.\n");
        text.setText(text.getText() + "Completely reveal the board without hitting a mine to win.\n");
        text.setText(text.getText() + "If you hit a mine, you lose. Mines are revealed at the end.\n\n");
        text.setText(text.getText() + "--- Symbols ---\n");
        text.setText(text.getText() + "Number of mines surrounding a tile: 0 to 8\n");
        text.setText(text.getText() + "Flag: F\nMine: M\nUnrevealed: ?\nIncorrect Flag: X\n\n");
        text.setText(text.getText() + "--- Actions ---\n");
        text.setText(text.getText() + "r [integer] [integer] - Reveals the tile at (x, y)\n");
        text.setText(text.getText() + "f [integer] [integer] - Flags the tile at (x, y)\n");
        StackPane menu = new StackPane(); //make menu
        Button button = new Button("Proceed"); //button to finish reading text

        menu.getChildren().add(text); //add text
        menu.getChildren().add(button); //add button
        text.setFont(new Font("Arial", 20)); //set font and size
        menu.setMargin(button, new Insets(20, 20, 20, 20)); //button margins
        menu.setAlignment(button, Pos.BOTTOM_RIGHT); //align button

        //number input stuff
        TextField width = new TextField();
        menu.getChildren().add(width);
        menu.setMargin(width, new Insets(200, 0, 280, 300));

        TextField height = new TextField();
        menu.getChildren().add(height);
        menu.setMargin(height, new Insets(240, 0, 240, 300));
        
        TextField mines = new TextField();
        menu.getChildren().add(mines);
        menu.setMargin(mines, new Insets(280, 0, 200, 300));

        width.setVisible(false);
        height.setVisible(false);
        mines.setVisible(false);

        Scene scene = new Scene(menu, 640, 480); 
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();

        //button lets user proceed
        button.setOnAction(e -> {
            text.setVisible(false);
        });
        button.setText("Submit");

        //input size and # of mines
    }

    public static void main(String[] args) {
        launch();
    }

}