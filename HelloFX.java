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
import javafx.scene.control.Spinner;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        //*instructions menu*
        //construct main text
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
        Button button2 = new Button("Submit"); //button to submit width height and mines
        button2.setVisible(false);

        menu.getChildren().add(text); //add text
        menu.getChildren().add(button); //add button
        menu.getChildren().add(button2); //add button 2
        text.setFont(new Font("Arial", 20)); //set font and size for main text
        StackPane.setMargin(button, new Insets(20, 20, 20, 20)); //button margins
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT); //align button
        StackPane.setMargin(button2, new Insets(20, 20, 20, 20)); //button margins 2
        StackPane.setAlignment(button2, Pos.BOTTOM_RIGHT); //align button 2

        //number input stuff
        //width
        Spinner<Integer> widthSpinner = new Spinner<>(4, 40, 4);
        menu.getChildren().add(widthSpinner);
        StackPane.setMargin(widthSpinner, new Insets(195, 100, 285, 300));
        //height
        Spinner<Integer> heightSpinner = new Spinner<>(4, 40, 4);
        menu.getChildren().add(heightSpinner);
        StackPane.setMargin(heightSpinner, new Insets(220, 100, 260, 300));
        //mines
        TextField minesField = new TextField();
        menu.getChildren().add(minesField);
        StackPane.setMargin(minesField, new Insets(245, 160, 235, 335));
        minesField.textProperty().addListener((obs, oldVal, newVal) -> { //stops user from entering non-numbers
        if (!newVal.matches("[0-9]+")) {
           minesField.setText(newVal.replaceAll("[^0-9]+", ""));
        }
        });

        widthSpinner.setVisible(false);
        heightSpinner.setVisible(false);
        minesField.setVisible(false);

        //extra text thing that I will use for warnings/prompts
        Label text2 = new Label();
        text2.setTextFill(Color.RED);
        text2.setFont(new Font("Arial", 15));
        StackPane.setMargin(text2, new Insets(20, 20, 20, 20));
        menu.getChildren().add(text2);
        text2.setAlignment(Pos.BOTTOM_CENTER);

        //*start the scene*
        Scene scene = new Scene(menu, 640, 480); 
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();

        //button lets user proceed from instructions
        button.setOnAction(e -> {
            text.setText("Input width (4 to 40):\nInput height (4 to 40):\n");
            text.setText(text.getText() + "Input mines:\n(minimum 1, max \n# of tiles - 9)");
            StackPane.setMargin(text, new Insets(180, 315, 180, 100));
            widthSpinner.setVisible(true);
            heightSpinner.setVisible(true);
            minesField.setVisible(true);
            button.setVisible(false);
            button2.setVisible(true);
        });
        
        //button submits values
        button2.setOnAction(e -> {
            if (minesField.getText().isEmpty()){
                text2.setText("Something's missing!");
            } else if (Integer.parseInt(minesField.getText()) < 1){
                text2.setText("Too little mines!");
            } else if (Integer.parseInt(minesField.getText()) > widthSpinner.getValue() * heightSpinner.getValue() - 9){
                text2.setText("Too many mines!");
            } else {
                widthSpinner.setVisible(false);
                heightSpinner.setVisible(false);
                minesField.setVisible(false);
                text.setVisible(false);
                text2.setVisible(false);
                button2.setVisible(false);
            }
        });
        //int width = widthSpinner.getValue();
        //int height = heightSpinner.getValue();
        //int mines = Integer.parseInt(minesField.getText());

        //input size and # of mines


        button.setText("Proceed");
    }

    public static void main(String[] args) {
        launch();
    }

}