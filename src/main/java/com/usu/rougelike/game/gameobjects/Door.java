package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;
import com.usu.rougelike.game.gameengine.Location;

public class Door extends GameObject {
    public Door(Game game) {
        super(game);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        //Done
        Location coords = getState().get("coords");
        int cellSize = game.getGameState().get("cellSize");
        int myX = (int)coords.x * cellSize;
        int myY = (int)coords.y * cellSize;

        canvas.translate(myX, myY);
        paint.setColor(Color.rgb(102,051,000));
        canvas.drawRect( cellSize / 6, cellSize / 8, cellSize - (cellSize / 6), cellSize, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        canvas.drawRect(cellSize / 6, cellSize / 8, cellSize - (cellSize / 6), cellSize, paint);
        canvas.drawRect(cellSize / 3, cellSize / 8, cellSize - (cellSize / 3), cellSize, paint);
        canvas.drawLine(cellSize / 2, cellSize / 8, cellSize / 2, cellSize, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(cellSize / 4, cellSize / 2, cellSize / 10, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(cellSize / 4, cellSize / 2, cellSize / 10, paint);



    }
}
