package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;
import com.usu.rougelike.game.gameengine.Location;

public class Floor extends GameObject {
    public Floor(Game game) {
        super(game);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        //DONE
        Location coords = getState().get("coords");
        int cellSize = game.getGameState().get("cellSize");
        int myX = (int)coords.x * cellSize;
        int myY = (int)coords.y * cellSize;

        canvas.translate(myX, myY);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0, cellSize / 2, cellSize / 2, paint);
        canvas.drawRect(cellSize / 2, cellSize / 2, cellSize, cellSize, paint);

        paint.setColor(Color.rgb(102, 204, 255));
        canvas.drawRect(0, cellSize / 2, cellSize / 2, cellSize, paint);
        canvas.drawRect(cellSize / 2, 0, cellSize, cellSize / 2, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, cellSize, cellSize, paint);
    }
}
