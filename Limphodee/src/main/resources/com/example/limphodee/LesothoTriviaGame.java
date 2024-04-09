package com.example.limphodee;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.geometry.Pos;

public class LesothoTriviaGame extends Application {
    private static final String[] questions = {
            "What is the capital of Lesotho?",
            "Which mountain range covers much of Lesotho?",
            "What is the traditional Basotho hat called?",
            "What is the currency of Lesotho?",
            "What is the national animal of Lesotho?",
            "What is the traditional attire worn by Basotho men called?",
            "Who is the current king of Lesotho?"
    };

    private static final String[][] options = {
            {"Maseru", "Morija", "Mohale's Hoek", "Quthing"},
            {"Andes", "Himalayas", "Drakensberg", "Alps"},
            {"Igloo", "Tukul", "Mokorotlo", "Yurt"},
            {"Rand", "Euro", "Loti", "Dollar"},
            {"Elephant", "Springbok", "Basotho Hat", "Eland"},
            {"Kufuor", "Agbada", "Mantsho", "basotho attire"},
            {"Letsie III", "Moshoeshoe II", "Metsi", "Moshoshoe I"}
    };

    private static final String[] correctAnswers = {"Maseru", "Drakensberg", "Mokorotlo", "Loti", "Eland", "Basotho attire", "Letsie III"};

    private static final String[] imagePaths = {
            "/images/maseru.jpg",
            "/images/drakensberg.jpg",
            "/images/Mokorotlo.jpg",
            "/images/lesotho_currency.jpg",
            "/images/eland.jpg",
            "/images/basotho_attire.jpg",
            "/images/letsie_iii.jpg"
    };

    private static final String[] videoPaths = {
            "/videos/Maseru.mp4",
            "/videos/highest mountain.mp4",
            "/videos/sesotho hat.mp4",
            "/videos/Lesotho Currency.mp4",
            "/videos/elaba.mp4",
            "/videos/Lesotho attire.mp4",
            "/videos/Letsie iii.mp4"
    };

    private int currentQuestionIndex = 0;
    private int score = 0;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Lesotho Trivia Game");

        Label questionLabel = new Label();
        questionLabel.setStyle("-fx-font-weight: bold;");

        Label progressLabel = new Label("Question 1 of " + questions.length);
        progressLabel.setStyle("-fx-font-weight: bold;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);

        VBox optionsBox = new VBox(5);
        ToggleGroup toggleGroup = new ToggleGroup();

        GridPane optionsGrid = new GridPane();
        optionsGrid.setVgap(5);

        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            RadioButton optionButton = new RadioButton(options[currentQuestionIndex][i]);
            optionButton.setToggleGroup(toggleGroup);
            final int index = i;
            optionButton.setOnMouseClicked(event -> optionButton.setStyle("-fx-font-weight: bold;"));
            optionsGrid.add(optionButton, 0, i);
        }

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: blue;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"));
        submitButton.setPrefWidth(100); // Set preferred width
        submitButton.setPrefHeight(40); // Set preferred height
        submitButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedAnswer = selectedRadioButton.getText();
                if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
                    score++;
                    showAlert("Correct!", "Your answer is correct.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Incorrect!", "The correct answer is: " + correctAnswers[currentQuestionIndex], Alert.AlertType.ERROR);
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    displayNextQuestion(questionLabel, imageView, optionsGrid, toggleGroup, progressLabel);
                } else {
                    displayScore();
                }
            } else {
                showAlert("No answer selected", "Please select an answer before submitting.", Alert.AlertType.WARNING);
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: blue;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"));
        backButton.setPrefWidth(100); // Set preferred width
        backButton.setPrefHeight(40); // Set preferred height
        backButton.setOnAction(event -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayNextQuestion(questionLabel, imageView, optionsGrid, toggleGroup, progressLabel);
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: blue;");
        nextButton.setOnMouseEntered(e -> nextButton.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white;"));
        nextButton.setOnMouseExited(e -> nextButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"));
        nextButton.setPrefWidth(100); // Set preferred width
        nextButton.setPrefHeight(40); // Set preferred height
        nextButton.setOnAction(event -> {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayNextQuestion(questionLabel, imageView, optionsGrid, toggleGroup, progressLabel);
            }
        });

        HBox navigationButtons = new HBox(10);
        navigationButtons.getChildren().addAll(backButton, nextButton, submitButton);
        navigationButtons.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: lightblue;"); // Set background color
        root.getChildren().addAll(progressLabel, questionLabel, createCenteredImageView(imageView), optionsGrid, navigationButtons);

        MediaView mediaView = new MediaView();
        VBox.setVgrow(mediaView, Priority.ALWAYS);

        Button playVideoButton = new Button("Play Video");
        playVideoButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: blue;");
        playVideoButton.setOnMouseEntered(e -> playVideoButton.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white;"));
        playVideoButton.setOnMouseExited(e -> playVideoButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"));
        playVideoButton.setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            Media video = new Media(getClass().getResource(videoPaths[currentQuestionIndex]).toString());
            mediaPlayer = new MediaPlayer(video);
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
        });

        root.getChildren().addAll(createCenteredMediaView(mediaView), playVideoButton);

        Scene scene = new Scene(root, 700, 600);
        scene.setFill(Color.BLUEVIOLET); // Set background color
        stage.setScene(scene);
        stage.show();

        displayNextQuestion(questionLabel, imageView, optionsGrid, toggleGroup, progressLabel);

        // Load the background music file
        Media backgroundMusic = new Media(getClass().getResource("/audio/national anthem.mp3").toString());

        // Create a media player to play the background music
        MediaPlayer backgroundPlayer = new MediaPlayer(backgroundMusic);

        // Set the volume (optional)
        backgroundPlayer.setVolume(0.5); // Adjust the volume as needed

        // Set the cycle count to indefinite so the music loops
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Play the background music
        backgroundPlayer.play();
    }

    private void displayNextQuestion(Label questionLabel, ImageView imageView, GridPane optionsGrid, ToggleGroup toggleGroup, Label progressLabel) {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            progressLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.length);
            Image image = new Image(getClass().getResourceAsStream(imagePaths[currentQuestionIndex]));
            imageView.setImage(image);
            optionsGrid.getChildren().clear();
            for (int i = 0; i < options[currentQuestionIndex].length; i++) {
                RadioButton optionButton = new RadioButton(options[currentQuestionIndex][i]);
                optionButton.setToggleGroup(toggleGroup);
                final int index = i;
                optionButton.setOnMouseClicked(event -> optionButton.setStyle("-fx-font-weight: bold;"));
                optionsGrid.add(optionButton, 0, i);
            }
        } else {
            displayScore();
        }
    }

    private void displayScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz completed");
        alert.setHeaderText(null);
        alert.setContentText("Your score: " + score + " out of " + questions.length);

        if (score >= 4) {
            alert.setContentText("Congratulations! You scored " + score + " out of " + questions.length + ". Well done!");
        } else {
            alert.setContentText("Sorry! You scored " + score + " out of " + questions.length + ". Better luck next time!");
        }

        alert.showAndWait();
    }

    private StackPane createCenteredImageView(ImageView imageView) {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(imageView);
        return stackPane;
    }

    private StackPane createCenteredMediaView(MediaView mediaView) {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(mediaView);
        return stackPane;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
