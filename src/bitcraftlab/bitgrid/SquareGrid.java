package bitcraftlab.bitgrid;

import processing.core.PApplet;

public class SquareGrid extends Grid {

  public SquareGrid(PApplet app, boolean flipped, int r, int width, int height) {

  	super(app, flipped, r, width, height);
    setCellSize(r * 2, r * 2);
    
  }


  // there are w * h cells in a square grid
  public int[] createCells() {
    return new int[w*h];
  }
  
  // draw a grid of squares
  public void drawGrid(int[] colors) {

    int x0 = (width - w * dx) / 2;
    int y0 = (height - h * dy) / 2;
    
    for(int y = 0, i = 0; y < h; y++) {
      for(int x = 0; x < w; x++) {
        drawPickableCell(x0 + x * dx, y0 + y * dy, colors[i], i++);
      }
    }  
  }
  
  // draw a single square
  public void drawCellShape(float r, float gap) {
    app.rect(gap, gap, 2 * r - 2 * gap, 2 * r - 2 * gap);
  }
  
  // let's keep it simple
  public int[] getNeighbors(int idx) {
    
    int x = idx % w;
    int y = idx / w;
    
    // array holding a neigborhood of size 5
    int[] a = new int[5];
    
    // the cell itself
    a[0] = idx;
    
    // Neumann neighborhood
    int N = idx - w;
    int S = idx + w;
    int W = idx - 1; 
    int E = idx + 1;
    
    // no wrap-around
    a[1] = (x < w - 1) ? E : OUT;
    a[2] = (y < h - 1) ? S : OUT;
    a[3] = (x > 0) ? W : OUT;
    a[4] = (y > 0) ? N : OUT;

    return a;
    
  }
  
}
