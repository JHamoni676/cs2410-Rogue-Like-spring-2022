package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;
import com.usu.rougelike.game.gameengine.Location;

public class Key extends GameObject {
    public Key(Game game) {
        super(game);
        getState().set("active", true);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        //Done
        boolean isActive = getState().get("active");
        if (!isActive) return;

        Location coords = getState().get("coords");
        int cellSize = game.getGameState().get("cellSize");
        int myX = (int)coords.x * cellSize;
        int myY = (int)coords.y * cellSize;

        canvas.translate(myX, myY);

        paint.setColor(Color.rgb(250, 220, 0));
        canvas.drawCircle(cellSize / 2, cellSize * 2 / 3, 20, paint);
        canvas.drawRect(cellSize / 3 + 15, cellSize / 5, cellSize * 2 / 3 - 15, cellSize * 3 / 4, paint);
        canvas.drawRect(cellSize / 3, cellSize / 3, cellSize / 2, cellSize / 4, paint);
        canvas.drawRect(cellSize / 3, cellSize / 3 + 20, cellSize / 2, cellSize / 4 + 20, paint);
    }
}
