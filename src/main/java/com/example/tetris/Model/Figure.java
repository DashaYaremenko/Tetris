package com.example.tetris.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Figure {
    public Rectangle A;
    public Rectangle B;
    public Rectangle C;
    public Rectangle D;
    Color color;
    private String name;
    public int figure = 0;
    public Figure(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
        this.name = name;

        switch (name) {
            case "J":
                color = Color.DARKBLUE;
                break;
            case "L":
                color = Color.LAWNGREEN;
                break;
            case "O":
                color = Color.ORCHID;
                break;
            case "S":
                color = Color.SEAGREEN;
                break;
            case "T":
                color = Color.BLUEVIOLET;
                break;
            case "Z":
                color = Color.PURPLE;
                break;
            case "I":
                color = Color.OLIVEDRAB;
                break;

        }
        this.A.setFill(color);
        this.B.setFill(color);
        this.C.setFill(color);
        this.D.setFill(color);
    }
    public String getName() {
        return name;
    }
    public void changeForm() {
        if (figure != 4) {
            figure++;
        } else {
            figure = 1;
        }
    }

}
