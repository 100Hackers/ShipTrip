/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shiptrip;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


/**
 *
 * @author trueBearsFan
 */
public class ShipTrip extends Application {
    
    @Override
    public void start(Stage theStage) {

        theStage.setTitle("GraphicsFX Test");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(682, 682);
        root.getChildren().add(canvas);

        ConcurrentLinkedQueue<String> input = new ConcurrentLinkedQueue<String>();
        ConcurrentLinkedQueue<Player> players = new ConcurrentLinkedQueue<Player>();

        players.add(new Player());

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();

                // only add once... prevent duplicates
                if (!input.contains(code)) {
                    input.add(code);
                }
            }
        });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                input.remove(code);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image pond = new Image("pond.png");
        Image gd = new Image("gd.png");

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                Player mainPlayer = players.peek();
                if (input.contains("LEFT")) {
                    mainPlayer.setX(mainPlayer.getX() - 1);
                }
                if (input.contains("RIGHT")) {
                    mainPlayer.setX(mainPlayer.getX() + 1);
                }
                if (input.contains("UP")) {
                    mainPlayer.setY(mainPlayer.getY() - 1);
                }
                if (input.contains("DOWN")) {
                    mainPlayer.setY(mainPlayer.getY() + 1);
                }
                gc.drawImage(pond, 0, 0);


                for (Player player : players) {
                    gc.drawImage(gd, player.getX(), player.getY());
                }
            }
        }.start();

        theStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
