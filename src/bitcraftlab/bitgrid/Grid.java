
package bitcraftlab.bitgrid;

import processing.core.*;

abstract public class Grid {
     
  public static final int SQUARE = 0, HEXAGON = 1, TRIANGLE = 2;
  public static final int OUT = -1;
    
  int r, width, height;
  int w, h, dx, dy;

  boolean flipped = false;  
  boolean border = true;
  
  int b; // border thickness
  
  PApplet app;
  Picking picking; // using a picking buffer ...  too lazy to do the math ;-)
  
  int border_color = 0xffffffff;
  float border_mix = 0.5f;
  

  public Grid(PApplet app, boolean flipped, int r, int width, int height) {
    this.app = app;
    this.r = r;
    this.flipped = flipped;
    this.width = flipped ? height : width;
    this.height = flipped ? width : height;
    picking = Picking.createPicking(app);
  }

  protected void setCellSize(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
    w = width / dx - 1;
    h = height / dy - 1;
  }
  
  public void setBorder(boolean border) {
    this.border = border; 
  }
  
  public void setBorderStyle(int c, float m) {
    border_color = c;
    border_mix = m;
  }
  
  final public void draw(int[] colors) {
 
    app.smooth();
    b = border ?  app.min(r/4, 4) : 0; 
    app.pushMatrix();
         
    // draw the grid with y-axis and x-axis flipped
    if (flipped) app.applyMatrix(0, 1, 0, 1, 0, 0);

    drawGrid(colors);
    app.popMatrix();
    
  }
  
  // draw a styled cell
  public void drawCell(int x, int y, int c) {
    app.strokeWeight(b);
    app.stroke(app.lerpColor(c,  border_color, border_mix )); 
    app.fill(c);
    app.pushMatrix();
    app.translate(x, y);
    drawCellShape(r, b);
    app.popMatrix();
  }
  
  // call this in your drawGrid function, to make your cells pickable
  final void drawPickableCell(int x, int y, int color, int index) {
     picking.setId(index);
     drawCell(x, y, color);
  }
  

  // return the position of a cell as rendered on screen
  public int indexAt(int screenX, int screenY) {

    // make sure to constrain the coordinates to a valid range
    int x = app.constrain(screenX, 0, flipped ? height -1 : width - 1);
    int y = app.constrain(screenY, 0, flipped ? width -1 : height - 1);

    return picking.getId(x, y);
    
  }
    
  // draw the grid 
  abstract public void drawGrid(int[] colors);
  
  // draw the cell shape   
  abstract public void drawCellShape(float radius, float gap);

  // return the indices of neighbor cells
  abstract public int[] getNeighbors(int idx);

  // return an array large enough to hold all the cells
  abstract public int[] createCells();
  
  // set neighborhood
  // abstract void setNeighborhood(int type, int topology); 
  
}



