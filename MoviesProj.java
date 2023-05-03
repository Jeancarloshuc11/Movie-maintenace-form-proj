/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package moviesproj;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/** Jean Huc
 *  5-3-23
 * Final project
 * option 1
 * A GUI program that maintains a list of movies and there categories
 * 
 */
public class MoviesProj extends Application {
    
    
    
    private ArrayList<Movie> movieList = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        
        
        
// Load movies from file
        try {
            File file = new File("movies.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String title = scanner.nextLine();
                String category = scanner.nextLine();
                movieList.add(new Movie(title, category));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading movies.");
            e.printStackTrace();
        }
        
        


       
RadioButton musicalRadioButton = new RadioButton("Musical");
RadioButton comedyRadioButton = new RadioButton("Comedy");
RadioButton dramaRadioButton = new RadioButton("Drama");
RadioButton scifiRadioButton = new RadioButton("Scifi");
RadioButton animatedRadioButton = new RadioButton("Animated");

ToggleGroup toggleGroup = new ToggleGroup();
toggleGroup.getToggles().addAll(musicalRadioButton, comedyRadioButton, dramaRadioButton, scifiRadioButton, animatedRadioButton);

VBox radioButtonsBox = new VBox(10);
radioButtonsBox.getChildren().addAll(musicalRadioButton, comedyRadioButton, dramaRadioButton, scifiRadioButton, animatedRadioButton);

Button listButton = new Button("List movies");
Button addButton = new Button("Add movie");
Button deleteButton = new Button("Delete movie");

TextArea movieArea = new TextArea();
movieArea.setEditable(false);

Label titleLabel = new Label("Title:");
TextField titleField = new TextField();

Label categoryLabel = new Label("Category:");
TextArea categoryField = new TextArea();
categoryField.setPrefColumnCount(10);
categoryField.setPrefRowCount(1);

GridPane inputGridPane = new GridPane();
inputGridPane.add(titleLabel, 0, 0);
inputGridPane.add(titleField, 1, 0);
inputGridPane.add(categoryLabel, 0, 1);
inputGridPane.add(categoryField, 1, 1);

inputGridPane.add(radioButtonsBox, 0, 2, 1, 3);

VBox buttonBox = new VBox(10);
buttonBox.getChildren().addAll(listButton, addButton, deleteButton);

HBox root = new HBox(10);
root.getChildren().addAll(inputGridPane, buttonBox, movieArea);

Scene scene = new Scene(root, 1000, 1000);

primaryStage.setTitle("Movie List");
primaryStage.setScene(scene);
primaryStage.show();







    listButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) {
        ArrayList<Movie> allMovies = new ArrayList<Movie>();
        String text = "";
        try {
            File file = new File("movies.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String title = scanner.nextLine();
                String category = scanner.nextLine();
                allMovies.add(new Movie(title, category));
            }
            scanner.close();
            for (Movie movie : allMovies) {
                movieArea.appendText(movie.getTitle() + "\n");
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while reading from the file.");
            ex.printStackTrace();
        }
        
        for (Movie movie : allMovies) {
            if (comedyRadioButton.isSelected() && movie.getCategory().equals("comedy")) {
                text += movie.getTitle() + " - " + movie.getCategory() + "\n";
            } else if (dramaRadioButton.isSelected() && movie.getCategory().equals("drama")) {
                text += movie.getTitle() + " - " + movie.getCategory() + "\n";
            } else if (musicalRadioButton.isSelected() && movie.getCategory().equals("musical")) {
                text += movie.getTitle() + " - " + movie.getCategory() + "\n";
            } else if (scifiRadioButton.isSelected() && movie.getCategory().equals("scifi")) {
                text += movie.getTitle() + " - " + movie.getCategory() + "\n";
            } else if (animatedRadioButton.isSelected() && movie.getCategory().equals("animated")) {
                text += movie.getTitle() + " - " + movie.getCategory() + "\n";
            } else if (!comedyRadioButton.isSelected() && !dramaRadioButton.isSelected() &&
                    !musicalRadioButton.isSelected() && !scifiRadioButton.isSelected() &&
                    !animatedRadioButton.isSelected()) {
                text += movie.getTitle() + " - " + movie.getCategory() + "\n";
            }
        }
        movieArea.setText(text);
        
        try {
            File file = new File("movies.txt");
            FileWriter writer = new FileWriter(file);
            for (Movie movie : allMovies) {
                writer.write(movie.getTitle() + "\n");
                writer.write(movie.getCategory() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing to the file.");
            ex.printStackTrace();
        }
    }
});

        
        
        

    addButton.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
        String title = titleField.getText();
        String category = categoryField.getText();

        if (title.isEmpty() || category.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in both the title and category fields of movie to add.");
            alert.showAndWait();
            return;
        }

        movieList.add(new Movie(title, category));
        titleField.setText("");
        categoryField.setText("");

        // update the movies.txt file
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("movies.txt", true)))) {
            writer.println(title);
            writer.println(category);
        } catch (IOException ex) {
            System.out.println("An error occurred while writing to the file.");
            ex.printStackTrace();
        }

        // update the movieArea display
        movieArea.setText("");
        for (Movie movie : movieList) {
            movieArea.appendText(movie.getTitle() + " - " + movie.getCategory() + "\n");
        }
    }
});

     
     
     
     






      deleteButton.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
        String selectedTitle = titleField.getText().trim();
        String selectedCategory = categoryField.getText().trim();
        if (selectedTitle.isEmpty() || selectedCategory.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please enter both the title and category of the movie to delete.");
            alert.showAndWait();
        } else {
            Movie movieToRemove = null;
            for (Movie movie : movieList) {
                if (movie.getTitle().equals(selectedTitle) && movie.getCategory().equals(selectedCategory)) {
                    movieToRemove = movie;
                    break;
                }
            }
            if (movieToRemove != null) {
                movieList.remove(movieToRemove);
                movieArea.setText("");
                for (Movie movie : movieList) {
                    movieArea.appendText(movie.getTitle() + " - " + movie.getCategory() + "\n");
                }
                try {
                    FileWriter writer = new FileWriter("movies.txt");
                    for (Movie movie : movieList) {
                        writer.write(movie.getTitle() + "\n");
                        writer.write(movie.getCategory() + "\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    System.out.println("An error occurred while writing to the file.");
                    ex.printStackTrace();
                }
            }
        }
    }
});

    }


    public static void main(String[] args) {
        launch(args);
    }
}

     class Movie {
        private String title;
        private String category;

        public Movie(String title, String category) {
            this.title = title;
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        @Override
public boolean equals(Object object)
{
if (object instanceof Movie)
{
Movie movie2 = (Movie) object;
if
(
title.equals(movie2.getTitle()) &&
category.equals(movie2.getCategory())
)
return true;
}
return false;
}


    
     }



