package com.usu.rougelike.game.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.usu.rougelike.game.gameengine.Game;
import com.usu.rougelike.game.gameengine.GameObject;
import com.usu.rougelike.game.gameengine.Location;

import java.util.ArrayList;
import java.util.Collections;

public class BossMonster extends GameObject {
    ArrayList<Integer> pattern = null;
    int turnNumber = 0;
    public BossMonster(Game game) {
        super(game);
        getState().set("alive", true);
        getState().set("health", 6);
        getState().set("level", 1); // will be overridden by game after instantiation
    }

    @Override
    public void update(long elapsedTime) {
        if (pattern == null) {
            pattern = new ArrayList<>();
            pattern.add(0);
            pattern.add(1);
            for (int i = 0; i < getState().<Integer>get("level"); i ++) {
                int move = (int)Math.round(Math.random());
                pattern.add(move);
            }
        }
        boolean isAlive = getState().get("alive");
        String turn = game.getGameState().get("turn");
        if (turn != "monster") return;
        game.getGameState().set("endTurn", true);
        if (!isAlive) return;
        int action = pattern.get(turnNumber % pattern.size());
        if (action == 1) { // move
            turnNumber ++;
            GameObject[][] map = game.getGameState().get("map");
            if (checkForPlayer()) {
                GameObject player = game.getGameObjectWithTag("player");
                Location playerLocation = player.getState().get("coords");
                Location myLocation = getState().get("coords");
                if (myLocation.x != playerLocation.x && myLocation.y != playerLocation.y) {
                    if (myLocation.y < playerLocation.y) {
                        GameObject other = map[(int)myLocation.y + 1][(int)myLocation.x];
                        if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.y = myLocation.y + 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    if (myLocation.y > playerLocation.y) {
                        GameObject other = map[(int)myLocation.y - 1][(int)myLocation.x];
                        if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.y = myLocation.y - 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    if (myLocation.x < playerLocation.x) {
                        GameObject other = map[(int)myLocation.y][(int)myLocation.x + 1];
                        if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x + 1] = null;
                            myLocation.x = myLocation.x + 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    if (myLocation.x > playerLocation.x) {
                        GameObject other = map[(int)myLocation.y][(int)myLocation.x - 1];
                        if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.x = myLocation.x - 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    moveRandom();

                } else if (myLocation.x == playerLocation.x) { // same column
                    if (myLocation.y < playerLocation.y) {
                        GameObject other = map[(int)myLocation.y + 1][(int)myLocation.x];
                        if (other instanceof Player) {
                            // end the game
                            other.getState().set("alive", false);
                            game.getGameState().set("playing", false);
                            return;
                        } else if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.y = myLocation.y + 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    if (myLocation.y > playerLocation.y) {
                        GameObject other = map[(int)myLocation.y - 1][(int)myLocation.x];
                        if (other instanceof Player) {
                            // end the game
                            other.getState().set("alive", false);
                            game.getGameState().set("playing", false);
                            return;
                        } else if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.y = myLocation.y - 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    moveRandom();
                } else if (myLocation.y == playerLocation.y) { // same row
                    if (myLocation.x < playerLocation.x) {
                        GameObject other = map[(int)myLocation.y][(int)myLocation.x + 1];
                        if (other instanceof Player) {
                            // end the game
                            other.getState().set("alive", false);
                            game.getGameState().set("playing", false);
                            return;
                        } else if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.x = myLocation.x + 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    if (myLocation.x > playerLocation.x) {
                        GameObject other = map[(int)myLocation.y][(int)myLocation.x - 1];
                        if (other instanceof Player) {
                            // end the game
                            other.getState().set("alive", false);
                            game.getGameState().set("playing", false);
                            return;
                        } else if (other == null) {
                            map[(int)myLocation.y][(int)myLocation.x] = null;
                            myLocation.x = myLocation.x - 1;
                            map[(int)myLocation.y][(int)myLocation.x] = this;
                            return;
                        }
                    }
                    moveRandom();
                }

            } else {
                moveRandom();
            }
        } else {
            turnNumber ++;
        }
    }

    private void moveRandom() {
        ArrayList<Integer> neighbors = new ArrayList();
        neighbors.add(1);
        neighbors.add(2);
        neighbors.add(3);
        neighbors.add(4);
        Collections.shuffle(neighbors);
        GameObject[][] map = game.getGameState().get("map");
        Location myLocation = getState().get("coords");
        while(!neighbors.isEmpty()) {
            int val = neighbors.remove(0);
            if(val == 1) {
                if (myLocation.y > 0 && map[(int)myLocation.y - 1][(int)myLocation.x] == null) {
                    map[(int)myLocation.y - 1][(int)myLocation.x] = this;
                    map[(int)myLocation.y][(int)myLocation.x] = null;
                    myLocation.y = myLocation.y - 1;
                    return;
                }
            }
            if (val == 2) {
                if (myLocation.x < map[0].length - 1 && map[(int)myLocation.y][(int)myLocation.x + 1] == null) {
                    map[(int)myLocation.y ][(int)myLocation.x + 1] = this;
                    map[(int)myLocation.y][(int)myLocation.x] = null;
                    myLocation.x = myLocation.x + 1;
                    return;
                }
            }
            if(val == 3) {
                if (myLocation.y < map.length - 1 && map[(int)myLocation.y + 1][(int)myLocation.x] == null) {
                    map[(int)myLocation.y + 1][(int)myLocation.x] = this;
                    map[(int)myLocation.y][(int)myLocation.x] = null;
                    myLocation.y = myLocation.y + 1;
                    return;
                }
            }
            if (val == 4) {
                if (myLocation.x > 0 && map[(int)myLocation.y][(int)myLocation.x - 1] == null) {
                    map[(int)myLocation.y ][(int)myLocation.x - 1] = this;
                    map[(int)myLocation.y][(int)myLocation.x] = null;
                    myLocation.x = myLocation.x - 1;
                    return;
                }
            }
        }
    }

    private boolean checkForPlayer() {
        GameObject player = game.getGameObjectWithTag("player");
        Location playerLocation = player.getState().get("coords");
        Location myLocation = getState().get("coords");
        double distance = Math.sqrt(Math.pow(playerLocation.x - myLocation.x, 2) + Math.pow(playerLocation.y - myLocation.y, 2));
        return distance < 5;
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        //Done
        int health = getState().get("health"); // gets how much health the monster has left
        Location coords = getState().get("coords");
        int cellSize = game.getGameState().get("cellSize");
        int myX = (int)coords.x * cellSize;
        int myY = (int)coords.y * cellSize;

        canvas.translate(myX, myY);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(cellSize / 2, cellSize / 2, cellSize / 2, paint);

        boolean alive = getState().get("alive");
        if (alive) {
            paint.setColor(Color.BLACK);
        } else {
            paint.setColor(Color.rgb(102, 204, 51));
        }

        if (health == 5) {
            paint.setColor(Color.rgb(50, 0, 0));
        }
        if (health == 4) {
            paint.setColor(Color.rgb(100, 0, 0));
        }
        if (health == 3) {
            paint.setColor(Color.rgb(150, 0, 0));
        }
        if (health == 2) {
            paint.setColor(Color.rgb(200, 0, 0));
        }
        if (health == 1) {
            paint.setColor(Color.rgb(255, 0, 0));
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        canvas.drawCircle(cellSize / 2, cellSize / 2, cellSize / 2, paint);
        canvas.drawCircle(cellSize / 4 + 10, cellSize / 4 + 10, 20, paint);
        canvas.drawCircle(cellSize * 3 / 4 - 10, cellSize / 4 + 10, 20, paint);
        canvas.drawCircle(cellSize / 4 + 10, cellSize / 4 + 10, 10, paint);
        canvas.drawCircle(cellSize * 3 / 4 - 10, cellSize / 4 + 10, 10, paint);
        canvas.drawLine(cellSize / 4, cellSize * 3 / 4, cellSize * 3 / 4, cellSize * 3 / 4, paint);
    }
}
