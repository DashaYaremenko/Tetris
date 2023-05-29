package com.example.tetris.Model;

import com.example.tetris.InterMove;
import com.example.tetris.TetrisController;
import javafx.scene.layout.Pane;

public class MoveFigure implements InterMove {
    public static final int Move = 25;
    public static final int Size = 25;
    public static int X = Size * 14;
    public static int Y = Size * 24;
    public static int[][] Mesh = new int[X/Size][Y/Size];
    private static Figure object;
    private static Figure nextObj= TetrisController.makeRect();
    private static Pane pane = new Pane();
    public void moveRight(Figure figure) {
        if (figure.A.getX() + Move <= X - Size && figure.B.getX() + Move <= X - Size
                && figure.C.getX() + Move <= X - Size && figure.D.getX() + Move <= X - Size) {
            int moveA = Mesh[((int) figure.A.getX() / Size) + 1][((int) figure.A.getY() / Size)];
            int moveB = Mesh[((int) figure.B.getX() / Size) + 1][((int) figure.B.getY() / Size)];
            int moveC = Mesh[((int) figure.C.getX() / Size) + 1][((int) figure.C.getY() / Size)];
            int moveD = Mesh[((int) figure.D.getX() / Size) + 1][((int) figure.D.getY() / Size)];
            if (moveA == 0 && moveA == moveB && moveB == moveC && moveC == moveD) {
                figure.A.setX(figure.A.getX() + Move);
                figure.B.setX(figure.B.getX() + Move);
                figure.C.setX(figure.C.getX() + Move);
                figure.D.setX(figure.D.getX() + Move);
            }
        }
    }
    public void moveLeft(Figure figure) {
        if (figure.A.getX() - Move >= 0 && figure.B.getX() - Move >= 0 && figure.C.getX() - Move >= 0
                && figure.D.getX() - Move >= 0) {
            int moveA = Mesh[((int) figure.A.getX() / Size) - 1][((int) figure.A.getY() / Size)];
            int moveB = Mesh[((int) figure.B.getX() / Size) - 1][((int) figure.B.getY() / Size)];
            int moveC = Mesh[((int) figure.C.getX() / Size) - 1][((int) figure.C.getY() / Size)];
            int moveD = Mesh[((int) figure.D.getX() / Size) - 1][((int) figure.D.getY() / Size)];
            if (moveA == 0 && moveA == moveB && moveB == moveC && moveC == moveD) {
                figure.A.setX(figure.A.getX() - Move);
                figure.B.setX(figure.B.getX() - Move);
                figure.C.setX(figure.C.getX() - Move);
                figure.D.setX(figure.D.getX() - Move);
            }
        }
    }

    public static void moveDown(Figure figure) {
        if (figure.A.getY() == Y - Size || figure.B.getY() == Y - Size || figure.C.getY() == Y - Size
                || figure.D.getY() == Y - Size || move_A(figure) || move_B(figure) || move_C(figure) || move_D(figure)) {
            Mesh[(int) figure.A.getX() / Size][(int) figure.A.getY() / Size] = 1;
            Mesh[(int) figure.B.getX() / Size][(int) figure.B.getY() / Size] = 1;
            Mesh[(int) figure.C.getX() / Size][(int) figure.C.getY() / Size] = 1;
            Mesh[(int) figure.D.getX() / Size][(int) figure.D.getY() / Size] = 1;
            DeleteLine.removeRows(pane);
            Figure a = nextObj;
            nextObj = TetrisController.makeRect();
            object = a;
            pane.getChildren().addAll(a.A, a.B, a.C, a.D);
            TetrisController.moveKeyPress(a);
        }
        if (figure.A.getY() + Move < Y && figure.B.getY() + Move < Y && figure.C.getY() + Move < Y
                && figure.D.getY() + Move < Y) {
            int movea = Mesh[(int) figure.A.getX() / Size][((int) figure.A.getY() / Size) + 1];
            int moveb = Mesh[(int) figure.B.getX() / Size][((int) figure.B.getY() / Size) + 1];
            int movec = Mesh[(int) figure.C.getX() / Size][((int) figure.C.getY() / Size) + 1];
            int moved = Mesh[(int) figure.D.getX() / Size][((int) figure.D.getY() / Size) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                figure.A.setY(figure.A.getY() + Move);
                figure.B.setY(figure.B.getY() + Move);
                figure.C.setY(figure.C.getY() + Move);
                figure.D.setY(figure.D.getY() + Move);
            }
        }
    }
    private static boolean move_A(Figure figure) {
        return (Mesh[(int) figure.A.getX() / Size][((int) figure.A.getY() / Size) + 1] == 1);
    }
    private static boolean move_B(Figure figure) {
        return (Mesh[(int) figure.B.getX() / Size][((int) figure.B.getY() / Size) + 1] == 1);
    }
    private static boolean move_C(Figure figure) {
        return (Mesh[(int) figure.C.getX() / Size][((int) figure.C.getY() / Size) + 1] == 1);
    }
    private static boolean move_D(Figure figure) {
        return (Mesh[(int) figure.D.getX() / Size][((int) figure.D.getY() / Size) + 1] == 1);
    }

}
