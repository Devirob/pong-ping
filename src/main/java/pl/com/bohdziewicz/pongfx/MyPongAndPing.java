package pl.com.bohdziewicz.pongfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class MyPongAndPing extends Application {

    private static final int width = 800;

    private static final int height = 600;

    private static final int PLAYER_HEIGHT = 100;

    private static final int PLAYER_WIDTH = 15;

    private static final double BALL_R = 15;

    private int ballYSpeed = 1;

    private int ballXSpeed = 1;

    private double playerOneYPosition = height / 2;

    private double playerTwoYPosition = height / 2;

    private double playerTwoXPosition = width - PLAYER_WIDTH;

    private double playerOneXPosition = 0;

    private double ballXPosition = width / 2;

    private double ballYPosition = height / 2;

    private boolean gameStarted;

    private int scoreP1 = 0;

    private int scoreP2 = 0;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("PONG");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> run(graphicsContext)));
        timeline.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(mouseEvent -> playerOneYPosition = mouseEvent.getY());
//        canvas.setOnMouseClicked(mouseEvent -> gameStarted = true);
        canvas.setOnMouseClicked(event ->
        {
            if (event.getButton() == MouseButton.PRIMARY) {
                gameStarted = true;
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                System.exit(0);
            }
        });

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        timeline.play();
    }

    private void run(GraphicsContext graphicsContext) {

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, width, height);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(25));

        if (gameStarted) {

            ballXPosition += ballXSpeed;
            ballYPosition += ballYSpeed;

            if (ballXPosition < width - width / 4) {
                playerTwoYPosition = ballYPosition - PLAYER_HEIGHT / 2;
            } else {

                playerTwoYPosition =
                        ballYPosition > playerTwoYPosition + PLAYER_HEIGHT / 2 ? playerTwoYPosition += 1 :
                                playerTwoYPosition - 1;
            }

            graphicsContext.fillOval(ballXPosition, ballYPosition, BALL_R, BALL_R);
        } else {
            graphicsContext.setStroke(Color.RED);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.strokeText("Just click", width / 2, height / 2);

            ballXPosition = width / 2;
            ballYPosition = height / 2;

            ballXSpeed = new Random().nextInt(2) == 0 ? new Random().nextInt(5) : new Random().nextInt(5) * -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? new Random().nextInt(5) : new Random().nextInt(5) * -1;
        }

        if (ballYPosition > height || ballYPosition < 0) {
            ballYSpeed *= -1;
        }

        if (ballXPosition > playerTwoXPosition + PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        if (ballXPosition < playerOneXPosition - PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        if (((ballXPosition + BALL_R > playerTwoXPosition) && ballYPosition >= playerTwoYPosition
                && ballYPosition <= playerTwoYPosition + PLAYER_HEIGHT) ||
                ((ballXPosition < playerOneXPosition + PLAYER_WIDTH) && ballYPosition >= playerOneYPosition
                        && ballYPosition <= playerOneYPosition + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        graphicsContext.fillText(scoreP1 + "\t\t\t\t\t\t" + scoreP2, width / 2, 100);
        graphicsContext.fillRect(playerTwoXPosition, playerTwoYPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
        graphicsContext.fillRect(playerOneXPosition, playerOneYPosition, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public static void main(String[] args) {

        launch();
    }
}