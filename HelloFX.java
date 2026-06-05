/*
Title: Minesweeper - Graphic Culminating Assignment
Author: Jim Li
Date Created: Jun 2, 2026
Date Last Modified: Jun 5, 2026
 */

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
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseButton;

public class HelloFX extends Application {
    //game variables, public because they go through methods a lot
    public int width;
    public int height;
    public int mines;

    public int[][] board;
    public boolean[][] revealed;
    public boolean[][] flagged;

    public Button[][] tiles;

    @Override
    public void start(Stage stage) {
        
        //create the menu and set as scene
        StackPane menu = createMenu(stage);
        Scene menuScene = new Scene(menu, 640, 480); 
        stage.setTitle("Minesweeper");
        stage.setScene(menuScene);
        stage.show();
    }

    public void startGame(Stage stage){
        //create the game and set as scene
        StackPane game = createGame(stage);
        Scene gameScene = new Scene(game, width * 25 + 40, height * 25 + 75); 
        stage.setScene(gameScene);
    }

    //method constructs the menu
    public StackPane createMenu(Stage stage){
        StackPane menu = new StackPane(); //initialise the stackpane

        //construct and add main text
        Label text = new Label("Welcome to Minesweeper!\n" +
        "You will be given a board full of square tiles to be revealed.\n" +
        "Each tile can randomly contain a mine.\n" +
        "Completely reveal the board without hitting a mine to win.\n" +
        "If you hit a mine, you lose. Mines are revealed at the end.\n\n" +
        "--- Symbols ---\n" +
        "Number of mines surrounding a tile: 0 to 8\n" +
        "Flag: F\nMine: M\nUnrevealed: ?\nIncorrect Flag: X\n\n" +
        "--- Actions ---\n" +
        "left click - Reveals the tile\n" +
        "right click - Flags the tile\n");
        text.setFont(new Font("Arial", 20)); //set font and size for main text

        //make 2 buttons to get through menu
        //proceed button (for proceeding after reading instructions)
        Button proceedButton = new Button("Proceed");
        StackPane.setMargin(proceedButton, new Insets(20, 20, 20, 20)); //margins
        StackPane.setAlignment(proceedButton, Pos.BOTTOM_RIGHT); //align

        //submit button (for submitting grid dimensions and mines)
        Button submitButton = new Button("Submit"); //button to submit width height and mines
        submitButton.setVisible(false);
        StackPane.setMargin(proceedButton, new Insets(20, 20, 20, 20)); //proceed margins
        StackPane.setAlignment(proceedButton, Pos.BOTTOM_RIGHT); //align button
        StackPane.setMargin(submitButton, new Insets(20, 20, 20, 20)); //submit margins
        StackPane.setAlignment(submitButton, Pos.BOTTOM_RIGHT); //align button

        //number inputs and their position
        //width (spinner between 4 and 40)
        Spinner<Integer> widthSpinner = new Spinner<>(4, 40, 10);
        StackPane.setMargin(widthSpinner, new Insets(195, 100, 285, 300));

        //height (spinner between 4 and 40)
        Spinner<Integer> heightSpinner = new Spinner<>(4, 40, 10);
        StackPane.setMargin(heightSpinner, new Insets(220, 100, 260, 300));

        //mines (positive integer only)
        TextField minesField = new TextField();
        minesField.setText("10"); //10 mine default
        StackPane.setMargin(minesField, new Insets(245, 160, 235, 335));
        //listener stops user from entering non-numbers
        minesField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (!newVal.matches("[0-9]+")) {
           minesField.setText(newVal.replaceAll("[^0-9]+", ""));
        }
        });
        //hide stuff for now
        widthSpinner.setVisible(false);
        heightSpinner.setVisible(false);
        minesField.setVisible(false);

        //extra text thing that I will use for warnings/prompts
        Label text2 = new Label();
        text2.setTextFill(Color.RED);
        text2.setFont(new Font("Arial", 15));
        StackPane.setMargin(text2, new Insets(230, 140, 150, 310));
        StackPane.setAlignment(text2, Pos.CENTER);

        //set proceed button actions
        proceedButton.setOnAction(e -> {
            text.setText("Input width (4 to 40):\nInput height (4 to 40):\n");
            text.setText(text.getText() + "Input mines:\n(minimum 1, max \n# of tiles - 9)");
            StackPane.setMargin(text, new Insets(180, 315, 180, 100));
            widthSpinner.setVisible(true);
            heightSpinner.setVisible(true);
            minesField.setVisible(true);
            proceedButton.setVisible(false);
            submitButton.setVisible(true);
        });

        //set submit button actions
        submitButton.setOnAction(e -> {
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
                submitButton.setVisible(false);
                width = widthSpinner.getValue();
                height = heightSpinner.getValue();
                mines = Integer.parseInt(minesField.getText());
                startGame(stage);
            }
        });
        //add everything
        menu.getChildren().addAll(text, proceedButton, submitButton,
            widthSpinner, heightSpinner, minesField, text2
        );
        return menu;
    }

    public StackPane createGame(Stage stage){
        StackPane game = new StackPane(); //initialise the stackpane
        //create grid for the mine board
        GridPane grid = new GridPane();
        StackPane.setMargin(grid, new Insets(55, 20, 20, 20)); //margin

        //create text for messages and stuff
        Label text = new Label("Click any tile to begin");
        text.setFont(new Font("Arial", 15));
        StackPane.setMargin(text, new Insets(20, 20, 20, 20));
        StackPane.setAlignment(text, Pos.TOP_LEFT);

        //create button array for the grid
        tiles = new Button[height][width]; //button array
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                int row = i;
                int col = j;
                Button tile = new Button("?");
                tile.setPrefSize(25, 25);
                tile.setFocusTraversable(false);
                tile.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY){
                        reveal(row, col);
                    }
                    else if (e.getButton() == MouseButton.SECONDARY){
                        flag(row, col);
                    }
                });
                tiles[row][col] = tile;
                grid.add(tile, col, row);
            }
        }
        game.getChildren().addAll(grid, text);
        return game;
    }

    //method reveals tile
    public static void reveal(int row, int col){

    }

    //method toggles flag on the tile
    public static void flag(int row, int col){

    }

    public static void main(String[] args) {
        launch();
    }
}