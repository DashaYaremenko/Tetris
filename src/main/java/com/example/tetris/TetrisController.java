package com.example.tetris;


import com.example.tetris.Model.Figure;
import com.example.tetris.Model.MoveFigure;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class TetrisController  {
    public static int X = MoveFigure.X;
    public static int Y=MoveFigure.Y;
    public static final int Size=MoveFigure.Size;
    private static int score = 0;
    public static final int Move =MoveFigure.Move;
    public static int[][] Mesh=MoveFigure.Mesh;
    public static int linesNo = 0;
    private static boolean game = true;
    private static int top = 0;
    private static Pane pane = new Pane();
    private static Scene scene = new Scene(pane, X + 150, Y);
    private static Figure nextObj;
    private static Figure object;

    InterMove interMove=new MoveFigure();
    public void Scene(Stage stage){
        for (int[] a : Mesh) {
            Arrays.fill(a, 0);
        }
        Line line = new Line(X, 0, X, Y);
        Text scoretext = new Text("Бали: ");
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(X + 5);
        Text level = new Text("Лінії: ");
        level.setStyle("-fx-font: 20 arial;");
        level.setY(100);
        level.setX(X + 5);
        level.setFill(Color.DARKGREEN);
        pane.getChildren().addAll(scoretext, line, level);

        Figure f = nextObj;
        pane.getChildren().addAll(f.A, f.B, f.C, f.D);
        moveKeyPress(f);
        object = f;
        nextObj =makeRect();
        stage.setScene(scene);
        stage.setTitle("Тетріс");
        stage.show();
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (object.A.getY() == 0 || object.B.getY() == 0 || object.C.getY() == 0
                                || object.D.getY() == 0)
                            top++;
                        else
                            top = 0;
                        if (top == 2) {
                            Text over = new Text("Програли");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial;");
                            over.setY(250);
                            over.setX(10);
                            pane.getChildren().add(over);
                            game = false;
                        }
                        if (top == 15) {
                            System.exit(0);
                        }
                        if (game) {
                            MoveFigure.moveDown(object);
                            scoretext.setText("Бали: " + Integer.toString(score));
                            level.setText("Лінії: " + Integer.toString(linesNo));
                        }
                    }
                });
            }
        };
        t.schedule(task, 0, 300);
    }


    public static Figure makeRect() {
        int block = (int) (Math.random() * 100);
        String name;
        Rectangle A = new Rectangle(Size-1, Size-1), B = new Rectangle(Size-1, Size-1), C = new Rectangle(Size-1, Size-1),
                D = new Rectangle(Size-1, Size-1);
        if (block < 15) {
            A.setX(X / 2 - Size);
            B.setX(X / 2 - Size);
            B.setY(Size);
            C.setX(X / 2);
            C.setY(Size);
            D.setX(X / 2 + Size);
            D.setY(Size);
            name = "j";
        } else if (block < 30) {
            A.setX(X / 2 + Size);
            B.setX(X / 2 - Size);
            B.setY(Size);
            C.setX(X / 2);
            C.setY(Size);
            D.setX(X / 2 + Size);
            D.setY(Size);
            name = "l";
        } else if (block < 45) {
            A.setX(X / 2 - Size);
            B.setX(X / 2);
            C.setX(X / 2 - Size);
            C.setY(Size);
            D.setX(X / 2);
            D.setY(Size);
            name = "o";
        } else if (block < 60) {
            A.setX(X / 2 + Size);
            B.setX(X / 2);
            C.setX(X / 2);
            C.setY(Size);
            D.setX(X / 2 - Size);
            D.setY(Size);
            name = "s";
        } else if (block < 75) {
            A.setX(X / 2 - Size);
            B.setX(X / 2);
            C.setX(X / 2);
            C.setY(Size);
            D.setX(X / 2 + Size);
            name = "t";
        } else if (block < 90) {
            A.setX(X / 2 + Size);
            B.setX(X / 2);
            C.setX(X / 2 + Size);
            C.setY(Size);
            D.setX(X / 2 + Size + Size);
            D.setY(Size);
            name = "z";
        } else {
            A.setX(X / 2 - Size - Size);
            B.setX(X / 2 - Size);
            C.setX(X / 2);
            D.setX(X / 2 + Size);
            name = "i";
        }
        return new Figure(A, B, C, D, name);
    }
    public static void moveKeyPress(Figure figure){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        InterMove.moveRight(figure);
                        break;
                    case DOWN:
                        InterMove.moveDown(figure);
                        score++;
                        break;
                    case LEFT:
                        InterMove.moveLeft(figure);
                        break;
                    case UP:
                        moveTurn(figure);
                        break;
                }
            }
        });
    }
    private static void moveTurn(Figure figure) {
        int f = figure.figure;
        Rectangle a = figure.A;
        Rectangle b = figure.B;
        Rectangle c = figure.C;
        Rectangle d = figure.D;
        switch (figure.getName()) {
            case "J":
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
                    moveRight(figure.A);
                    moveDown(figure.A);
                    moveDown(figure.C);
                    moveLeft(figure.C);
                    moveDown(figure.D);
                    moveDown(figure.D);
                    moveLeft(figure.D);
                    moveLeft(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    moveLeft(figure.D);
                    moveLeft(figure.D);
                    moveUp(figure.D);
                    moveUp(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
                    moveLeft(figure.A);
                    moveUp(figure.A);
                    moveUp(figure.C);
                    moveRight(figure.C);
                    moveUp(figure.D);
                    moveUp(figure.D);
                    moveRight(figure.D);
                    moveRight(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    moveRight(figure.D);
                    moveRight(figure.D);
                    moveDown(figure.D);
                    moveDown(figure.D);
                    figure.changeForm();
                    break;
                }
                break;
            case "L":
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    moveRight(figure.A);
                    moveDown(figure.A);
                    moveUp(figure.C);
                    moveRight(figure.C);
                    moveUp(figure.B);
                    moveUp(figure.B);
                    moveRight(figure.B);
                    moveRight(figure.B);
                    figure.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveRight(figure.B);
                    moveRight(figure.B);
                    moveDown(figure.B);
                    moveDown(figure.B);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    figure.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    moveLeft(figure.A);
                    moveUp(figure.A);
                    moveDown(figure.C);
                    moveLeft(figure.C);
                    moveDown(figure.B);
                    moveDown(figure.B);
                    moveLeft(figure.B);
                    moveLeft(figure.B);
                    figure.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveLeft(figure.B);
                    moveLeft(figure.B);
                    moveUp(figure.B);
                    moveUp(figure.B);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    figure.changeForm();
                    break;
                }
                break;
            case "O":
                break;
            case "S":
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    moveUp(figure.D);
                    moveUp(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    moveDown(figure.D);
                    moveDown(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    moveUp(figure.D);
                    moveUp(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    moveDown(figure.D);
                    moveDown(figure.D);
                    figure.changeForm();
                    break;
                }
                break;
            case "T":
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveDown(figure.D);
                    moveLeft(figure.D);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    figure.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    moveRight(figure.A);
                    moveDown(figure.A);
                    moveLeft(figure.D);
                    moveUp(figure.D);
                    moveUp(figure.C);
                    moveRight(figure.C);
                    figure.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveUp(figure.D);
                    moveRight(figure.D);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    figure.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    moveLeft(figure.A);
                    moveUp(figure.A);
                    moveRight(figure.D);
                    moveDown(figure.D);
                    moveDown(figure.C);
                    moveLeft(figure.C);
                    figure.changeForm();
                    break;
                }
                break;
            case "Z":
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    moveUp(figure.B);
                    moveRight(figure.B);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    moveLeft(figure.D);
                    moveLeft(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    moveDown(figure.B);
                    moveLeft(figure.B);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    moveRight(figure.D);
                    moveRight(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    moveUp(figure.B);
                    moveRight(figure.B);
                    moveLeft(figure.C);
                    moveUp(figure.C);
                    moveLeft(figure.D);
                    moveLeft(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    moveDown(figure.B);
                    moveLeft(figure.B);
                    moveRight(figure.C);
                    moveDown(figure.C);
                    moveRight(figure.D);
                    moveRight(figure.D);
                    figure.changeForm();
                    break;
                }
                break;
            case "I":
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    moveUp(figure.A);
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveRight(figure.A);
                    moveUp(figure.B);
                    moveRight(figure.B);
                    moveDown(figure.D);
                    moveLeft(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    moveDown(figure.A);
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveLeft(figure.A);
                    moveDown(figure.B);
                    moveLeft(figure.B);
                    moveUp(figure.D);
                    moveRight(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    moveUp(figure.A);
                    moveUp(figure.A);
                    moveRight(figure.A);
                    moveRight(figure.A);
                    moveUp(figure.B);
                    moveRight(figure.B);
                    moveDown(figure.D);
                    moveLeft(figure.D);
                    figure.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    moveDown(figure.A);
                    moveDown(figure.A);
                    moveLeft(figure.A);
                    moveLeft(figure.A);
                    moveDown(figure.B);
                    moveLeft(figure.B);
                    moveUp(figure.D);
                    moveRight(figure.D);
                    figure.changeForm();
                    break;
                }
                break;
        }
    }

    private static boolean cB(Rectangle r, int x, int y) {
        boolean x1 = false;
        boolean y1 = false;
        if (x >= 0)
            x1 = r.getX() + x * Move <= X - Size;
        if (x < 0)
            x1 = r.getX() + x * Move >= 0;
        if (y >= 0)
            y1 = r.getY() - y * Move > 0;
        if (y < 0)
            y1 = r.getY() + y * Move < Y;
        return x1 && y1 && Mesh[((int) r.getX() / Size) + x][((int) r.getY() / Size) - y] == 0;
    }
    private static void moveDown(Rectangle r) {
        if (r.getY() - Move > 0)
            r.setY(r.getY() - Move);
    }
    private static void moveLeft(Rectangle r) {
        if (r.getX() - Move >= 0)
            r.setX(r.getX() - Move);
    }
    private static void moveRight(Rectangle r) {
        if (r.getX() + Move <= X - Size)
            r.setX(r.getX() + Move);
    }
    private static void moveUp(Rectangle rect) {
        if (rect.getY() - Move > 0)
            rect.setY(rect.getY() - Move);
    }
}