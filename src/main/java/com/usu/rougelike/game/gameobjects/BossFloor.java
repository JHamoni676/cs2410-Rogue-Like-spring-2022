package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;
import com.usu.rougelike.game.gameengine.Location;

public class BossFloor extends GameObject {
    public BossFloor(Game game) {
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
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(255, 102, 102));
        canvas.drawCircle(cellSize / 4, cellSize / 4,cellSize / 4, paint);
        canvas.drawCircle(cellSize * 3 / 4, cellSize * 3 / 4, cellSize / 4, paint);


        paint.setColor(Color.rgb(204, 204, 204));
        canvas.drawCircle(cellSize * 3 / 4, cellSize / 4, cellSize / 4, paint);
        canvas.drawCircle(cellSize / 4, cellSize * 3 / 4, cellSize / 4, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, cellSize, cellSize, paint);
    }

}
