
# Game Of Life in Java

***

## Functionality

https://conwaylife.com/wiki/Spacefiller

- Finished:
  - Draw your own start configuration
  - Watch your start configuration evolve
  - Pause the playback and add / remove cells 
  - Save the currently displayed configuration
  - Reset the game to the last saved configuration
  - Clear the game grid
  

- To-do:
  - Scroll on the viewport to increase / decrease cell size
  - Use an 'infinite' grid instead of one limited by the size of the viewport
  - Rewind-mode: Show a configuration that results in the current one
  - Mode switch that lets me change modes. List of all modes:
    - Edit-mode (pauses playback and allows user to modify and save grid)
    - View-mode (watch the playback, move camera (translation and zoom))
    - Rewind-mode (display what a past generation could have looked like)












































- Draw your own start configuration 

    ![Heart](https://github.com/Npulseee/GameOfLife/blob/master/assets/Screenshot%202023-04-15%20003255.jpg?raw=true)
    
    ```java
    public class HelloController {
        //...
        public void initialize() {
            gc = canvas.getGraphicsContext2D();
            canvasSizeX = (int) (canvas.getWidth() / CUBE_SIZE);
            canvasSizeY = (int) (canvas.getHeight() / CUBE_SIZE);
            game = new GameOfLife(canvasSizeX, canvasSizeY);
            gc.setLineWidth(.1);
            game.fillWithRandomValues();
            lastSavedGame.setCanvas(game.getCanvas());
            drawFrame();
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
                int x = (int) (event.getSceneX() /CUBE_SIZE);
                int y = (int) (event.getSceneY() /CUBE_SIZE);
                if ((lastX != x || lastY != y) && x >= 0 && x < canvasSizeX - 1 && y >= 0 && y < canvasSizeY - 1) {
                    lastX = x; lastY = y;
                    drawOverCell(x, y, event);
                }
            });
        }
        //...
    }
    ```
***

## Different Stuff

My favorite search engine is [Duck Duck Go](https://duckduckgo.com "The best search engine for privacy").
That is <https://DuckDuckGo.com>
See the section on [code](#Functionality).

In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends
of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to
eat: it was a [hobbit-hole][1], and that means comfort.

[1]: <https://en.wikipedia.org/wiki/Hobbit#Lifestyle> "Hobbit lifestyles"
