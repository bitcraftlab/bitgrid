package bitcraftlab.bitgrid;

import processing.core.PApplet;

public class TriangleGrid extends Grid {
  
  int origin; // global variable (ouch)

  public TriangleGrid(PApplet app, boolean flipped, int r, int width, int height) {

    super(app, flipped, r, width, height);
    setCellSize(r * 2,  (int) (r * app.sqrt(3)) );

  }

  // there are 2 * w * h cells in a hexagon grid
  public int[] createCells() {
    return new int[ 2 * w * h ];
  }
  
  // draw a grid of triangles
  public void drawGrid(int[] colors) {

    int x0 = (width - w * dx) / 2 + r/2;
    int y0 = (height - h * dy) / 2 + dy/2;
    
    origin = 0;
    for(int y = 0, i = 0; y < h * 2; y++, origin++) {
      int tx = r * (origin % 2);
      for(int x = 0; x < w; x++) {
        drawPickableCell(x0 + x * dx + tx, y0 + (y/2) * dy,  colors[i], i++);
      }
    }
    
  }
  
  // draw a single equi-lateral triangle
  public void drawCellShape(float r, float gap) {
    
    float dy = r * app.sqrt(3);
    
    // flip the triangle if necessary    
    if(origin % 4  == 1 || origin % 4 == 2) app.scale(1, -1);
    
    // rescale the triangle to create the gap
    if(gap != 0) {
      app.pushMatrix();
      app.translate(0, -dy/2 + dy/3);
      app.scale((float) (r - gap - 1) /r );
      app.triangle(-r, -dy/3, 0, dy*2/3, r, -dy/3);
      app.popMatrix();
    } else {
      app.triangle(-r, -dy/2, 0, dy/2, r, -dy/2);
    }
    
  }
   
  public int[] getNeighbors(int idx) {

    int xmax = w-1;
    int ymax = (2*h);
    int x = idx % w;
    int y = idx / w;
    
    boolean wrap1 = ((y + 1) / 2) % 2 == 0;
    boolean wrap2 = y % 2 == 0;
    
    // array holding a neighborhood of 4 cells
    int[] a = new int[4];
    
    // the cell itself 
    a[0] = idx;

    if(wrap1) {
      
      // triangle stands on a corner
      int N = idx - 2 * w;
      int SE = wrap2 ? ( idx + w ) : ( idx - w + 1);
      int SW = wrap2 ? ( idx + w - 1 ) : ( idx - w ) ;
      
      // assign neighbors
      a[1] = (y > 0) ? N : OUT;
      a[2] = (y < ymax && x < xmax) ? SE : OUT;
      a[3] = (y < ymax && x > 0) ? SW : OUT;
     
    } else {
      
      // triangle standing on its base
      int S = idx + 2 * w;
      int NW = wrap2 ? ( idx + w - 1 ) : ( idx - w );
      int NE = wrap2 ? ( idx + w ) : ( idx - w + 1);
      
      // assign neighbors
      a[1] = (y < ymax - 2 ) ? S : OUT;
      a[2] = (y > 0  && x < xmax) ? NE : OUT;
      a[3] = (y > 0 && x >  0) ? NW : OUT;

    }
    
    return a;

  }


}
