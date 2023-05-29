package com.example.tetris.Model;

import com.example.tetris.TetrisController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
public class DeleteLine extends MoveFigure {
    public static int[][] Mesh=MoveFigure.Mesh;
    public static final int Size=MoveFigure.Size;
    public static int score = 0;
    private static int linesNo = TetrisController.linesNo;
    public static void removeRows(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newRects = new ArrayList<Node>();
        int full = 0;
        for (int i = 0; i < Mesh[0].length; i++) {
            for (int j = 0; j < Mesh.length; j++) {
                if (Mesh[j][i] == 1)
                    full++;
            }
            if (full == Mesh.length)
                lines.add(i);
            full = 0;
        }
        if (lines.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                score += 10;
                linesNo++;

                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() == lines.get(0) * Size) {
                        Mesh[(int) a.getX() / Size][(int) a.getY() / Size] = 0;
                        pane.getChildren().remove(node);
                    } else
                        newRects.add(node);
                }

                for (Node node : newRects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < lines.get(0) * Size) {
                        Mesh[(int) a.getX() / Size][(int) a.getY() / Size] = 0;
                        a.setY(a.getY() + Size);
                    }
                }
                lines.remove(0);
                rects.clear();
                newRects.clear();
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    try {
                        Mesh[(int) a.getX() / Size][(int) a.getY() / Size] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                rects.clear();
            } while (lines.size() > 0);
    }
}
