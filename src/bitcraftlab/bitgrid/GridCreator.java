package bitcraftlab.bitgrid;

import processing.core.PApplet;

public class GridCreator {
    
  PApplet app;
  
  public GridCreator(PApplet parent) {
    app = parent;
  }

  public Grid createGrid(int type, int r, int width, int height) {
    return createGrid(type, false, r, width, height);
  }

  public Grid createFlippedGrid(int type, int r, int width, int height) {
    return createGrid(type, true, r, width, height);
  }

  public Grid createGrid(int type, boolean flipped, int r, int width, int height) {
    
    switch(type) {
      case Grid.SQUARE : return new SquareGrid(app, flipped, r, width, height);
      case Grid.HEXAGON: return new HexagonGrid(app, flipped, r, width, height);
      case Grid.TRIANGLE: return new TriangleGrid(app, flipped, r, width, height);
      default: throw(new RuntimeException("Unknown Grid Type: " + type));
    }
  }
 
}
