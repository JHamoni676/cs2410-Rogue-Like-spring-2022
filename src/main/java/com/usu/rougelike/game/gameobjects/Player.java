package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;
import com.usu.rougelike.game.gameengine.Input;
import com.usu.rougelike.game.gameengine.Location;

public class Player extends GameObject {
    public Player(Game game) {
        super(game);
        getState().set("hasKey", false);
        getState().set("alive", true);
    }

    @Override
    public void update(long elapsedTime) {
        boolean isAlive = getState().get("alive");
        if (!isAlive) return;
        if (game.getGameState().<String>get("turn") == "player" && !game.getInputQueue().isEmpty()) {
            Input input = game.getInputQueue().remove(0);
            game.clearInputQueue();
            int cellSize = game.getGameState().get("cellSize");
            int numRows = game.getGameState().get("numRows");
            int maxY = cellSize * numRows;
            if (input.location.y >= maxY) return;
            int row = (int)input.location.y / cellSize;
            int col = (int)input.location.x / cellSize;
            Location myLocation = getState().get("coords");
            // check if neighbor
            if (
                    (myLocation.x == col && (myLocation.y == row + 1 || myLocation.y == row - 1)) ||
                    (myLocation.y == row && (myLocation.x == col + 1 || myLocation.x == col - 1))
                )
            {
                //check what we tapped on
                GameObject[][] map = game.getGameState().get("map");
                if (map[row][col] instanceof Barrier) return; // don't move to barriers
                game.getGameState().set("endTurn", true);
                if (map[row][col] == null) {
                    map[row][col] = this;
                    map[(int)myLocation.y][(int)myLocation.x] = null;
                    myLocation.x = col;
                    myLocation.y = row;
                }
                if (map[row][col] instanceof Key) {
                    map[row][col].getState().set("active", false);
                    getState().set("hasKey", true);
                    map[row][col] = null;
                }
                if (map[row][col] instanceof Monster) {
                    map[row][col].getState().set("alive", false);
                    map[row][col] = null;
                }
                if (map[row][col] instanceof BossMonster) {
                    int health = map[row][col].getState().get("health");
                    getState().set("hasKey", true);
                    if (health == 1) { //
                        map[row][col].getState().set("alive", false);
                    } else {
                        map[row][col].getState().set("health", health - 1);
                    }
                }
                boolean hasKey = getState().get("hasKey");
                if (map[row][col] instanceof Door && hasKey) {
                    game.getGameState().set("nextLevel", true);
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        //Done
        boolean hasKey = getState().get("hasKey"); // get whether the player has the key or not
        boolean isAlive = getState().get("alive"); // get whether or not the player is alive
        Location coords = getState().get("coords");
        int cellSize = game.getGameState().get("cellSize");
        int myX = (int)coords.x * cellSize;
        int myY = (int)coords.y * cellSize;

        canvas.translate(myX, myY);

        if (isAlive) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLACK);
            canvas.drawLine(cellSize / 2, cellSize / 4, cellSize / 2,cellSize * 3 / 4, paint);

            canvas.drawLine(cellSize / 2, cellSize * 3 / 4, cellSize / 3, cellSize, paint);
            canvas.drawLine(cellSize / 2, cellSize * 3 / 4, cellSize * 2 / 3, cellSize, paint);

            canvas.drawLine(cellSize / 2, cellSize / 2 + 4, cellSize / 3, cellSize * 3 / 4, paint );
            canvas.drawLine(cellSize / 2, cellSize / 2 + 4, cellSize * 2 / 3, cellSize * 3 / 4, paint );

            paint.setColor(Color.GRAY);
            canvas.drawLine(cellSize * 2 / 3, cellSize * 3 / 4, cellSize - 10, cellSize / 4, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(cellSize / 2, cellSize / 4, 30, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(cellSize / 2, cellSize / 4, 30, paint);

            canvas.drawCircle(cellSize / 2 - 10, cellSize / 4 - 7, 8, paint);
            canvas.drawCircle(cellSize - (cellSize / 2 - 10), cellSize / 4 - 7, 8, paint);

            if(hasKey) {
                paint.setColor(Color.YELLOW);
                canvas.drawLine(cellSize / 3, cellSize * 3 / 4, cellSize / 4, cellSize * 2 / 3, paint);
            }
        } else {
            canvas.rotate(- 90);
            canvas.translate(-cellSize, - (cellSize * 1 / 1000));

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            canvas.drawLine(cellSize / 2, cellSize / 4, cellSize / 2,cellSize * 3 / 4, paint);

            canvas.drawLine(cellSize / 2, cellSize * 3 / 4, cellSize / 3, cellSize, paint);
            canvas.drawLine(cellSize / 2, cellSize * 3 / 4, cellSize * 2 / 3, cellSize, paint);

            canvas.drawLine(cellSize / 2, cellSize / 2 + 4, cellSize / 3, cellSize * 3 / 4, paint );
            canvas.drawLine(cellSize / 2, cellSize / 2 + 4, cellSize * 2 / 3, cellSize * 3 / 4, paint );

            paint.setColor(Color.GRAY);
            canvas.drawLine(cellSize * 2 / 3, cellSize * 3 / 4, cellSize - 10, cellSize / 4, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(cellSize / 2, cellSize / 4, 30, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            paint.setColor(Color.RED);
            canvas.drawCircle(cellSize / 2, cellSize / 4, 30, paint);

            canvas.drawCircle(cellSize / 2 - 10, cellSize / 4 - 7, 8, paint);
            canvas.drawCircle(cellSize - (cellSize / 2 - 10), cellSize / 4 - 7, 8, paint);

            if(hasKey) {
                paint.setColor(Color.YELLOW);
                canvas.drawLine(cellSize / 3, cellSize * 3 / 4, cellSize / 4, cellSize * 2 / 3, paint);
            }
        }
    }
}
