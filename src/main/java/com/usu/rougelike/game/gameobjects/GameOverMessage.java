package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;

public class GameOverMessage extends GameObject {
    public GameOverMessage(Game game) {
        super(game);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        //Done
        float height = game.getHeight(); // get the height of the game
        float width = game.getWidth(); // get the width of the game
        boolean isPlaying = game.getGameState().get("playing");
        if (isPlaying) return;

        paint.setColor(Color.BLACK);
        canvas.drawRect(width / 8, height / 3 + 50, width * 7 / 8, height * 2 / 3 - 50, paint);

        paint.setColor(Color.RED);
        paint.setTextSize(100);
        canvas.drawText("GAME OVER", width / 4, height / 2 - 70, paint);

        paint.setColor(Color.GRAY);
        canvas.drawCircle(width / 2, height / 2 + 70, 100, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(6);
        canvas.drawLine(width / 2 - 60, height / 2 + 30, width / 2 - 10, height / 2 + 50, paint);
        canvas.drawLine(width / 2 - 60, height / 2 + 50, width / 2 - 10, height / 2 + 30, paint);
        canvas.drawLine(width / 2 + 60, height / 2 + 30, width / 2 + 10, height / 2 + 50, paint);
        canvas.drawLine(width / 2 + 60, height / 2 + 50, width / 2 + 10, height / 2 + 30, paint);
        canvas.drawLine(width / 2 + 50, height / 2 + 100, width / 2 - 50, height / 2 + 100, paint);
    }
}
