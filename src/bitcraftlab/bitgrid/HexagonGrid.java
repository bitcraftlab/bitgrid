package bitcraftlab.bitgrid;

import processing.core.PApplet;

public class HexagonGrid extends Grid {
  
  public HexagonGrid(PApplet app, boolean flipped, int r, int width, int height) {

    super(app, flipped, r, width, height);
    setCellSize((int) (r * app.sqrt(3)), r * 3 / 2);

  }
  
  // there are w * h cells in a hexagon grid
  public int[] createCells() {
    return new int[w*h];
  }

  // draw a grid of hexagons
  public void drawGrid(int[] colors) {
    
    int x0 = (width - w * dx + dx)/2 - r/3;
    int y0 = (height - h * dy)/2 + r * 2/3;
    
    for(int y = 0, i = 0, origin = 0; y < h; y++,  origin = 1 - origin) {
      int tx = (int)((r  * app.sqrt(3) / 2) * origin);
      for(int x = 0; x < w; x++) {
        drawPickableCell(x0 + x * dx + tx, y0 + y * dy, colors[i], i++);
      }
    }
    
  }
  
  
  // draw a single hexagon 
  public void drawCellShape(float r, float gap) {
    
    r -= gap;
    float dy = r / 2;
    float dx = r * app.sqrt(3) / 2;

    //app.scale((float)(r-gap) / (r));
    app.beginShape();
    app.vertex(dx, +dy); app.vertex(0, +r); app.vertex(-dx, dy);
    app.vertex(-dx, -dy); app.vertex(0, -r); app.vertex(dx, -dy);
    app.endShape(app.CLOSE);
    
  }
  
  // hexagon neighborhood
  public int[] getNeighbors(int idx) {
    
    // array holding a neighborhood of size 7
    int[] a = new int[7];
    
    // the cell itsel
    a[0] = idx;
    
    int xmax = w-1;
    int ymax = h-1;
    int x = idx % w;
    int y = idx / w;
    int offset = y % 2 - 1;
    
    // hexagon neighborhood    
    int W = idx - 1; 
    int E = idx + 1;
    int NW = idx - w + offset;
    int SW = idx + w + offset;
    int NE = idx - w + 1 + offset;
    int SE = idx + w + 1 + offset;
    
    // no wrap-around
    a[1] = (x < xmax) ? E : OUT;
    a[2] = (y < ymax && x + offset < xmax) ? SE : OUT;
    a[3] = (y < ymax && x + offset + 1 > 0) ? SW : OUT;
    a[4] = (x > 0) ? W : OUT;
    a[5] = (y > 0 && x + offset + 1 > 0) ? NW : OUT;
    a[6] = (y > 0 && x + offset < xmax) ? NE : OUT;
  
    return a;
    
  }
  

}


