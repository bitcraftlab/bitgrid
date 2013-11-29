
///////////////////////////////////
//                               //
//        grid creator           //
//                               //
///////////////////////////////////

// (c) bitcraftlab 2013

// simple demo showing how to use the grid creator of the bitgrid library

//////// Key Map //////////

// Grid Creation

// [g] grid selection
// [+] increase cell radius
// [-] decrease cell radius
// [f] flipp grid

// Grid Renderingm

// [b] Change Border Style


import bitcraftlab.bitgrid.*;

int h = 48;
int r = 16;

color[] cells;
GridCreator gc;
Grid grid;
int gridtype = Grid.TRIANGLE;
boolean border = true;
boolean flipped = true;


void setup() {
  size(800, 500);
  gc = new GridCreator(this);
  colorMode(RGB);
  reset();
}


void draw() {
  background(0);
  grid.draw(cells); 
}


void reset() {

  // ask the grid creator to create a grid
  grid = gc.createGrid(gridtype % 3, flipped, r, width, height);
  
  // ask the grid to create a color array large enough to hold all cells
  cells = grid.createCells();
  
  // fill cells with random colors
  for(int i = 0; i < cells.length; i++) {
    cells[i] = random(1) > .5 ? #ffffff : #666699;
  };
  
  // customize cell rendering border
  grid.setBorderStyle(#000000, 0.2);
  grid.setBorder(border);
  
}


void mousePressed() {
  paintGrid();
}


void mouseDragged() {
  paintGrid(); 
}


// paint grid cells in red
void paintGrid() {
  
  // get cell index at the mouse position
  int idx = grid.indexAt(mouseX, mouseY);
 
  if(idx >= 0) {
    
      println((idx + 1) + "/" + cells.length);
    
    // paint the cell in red
    cells[idx] = #ff0000;
    

    int[] a = grid.getNeighbors(idx);
  
    // paint all neighbors in yellow
    // starting at 1, to skip the cell itself
    for(int i = 1; i < a.length; i++) {
      color c = a[i];
      if(c != Grid.OUT)  cells[a[i]] = #ffff00;  
    }
    
  }
  
}


void keyPressed() {
  switch(key) {
    
    // modify current grid
    case 'b': grid.setBorder(border =!border); return;
      
    // create new grid
    case '+': r++; break;
    case '-': r--; break;
    case 'g': gridtype++; break;
    case 'f': flipped = !flipped; break;

  } 
  
  // limit cell size
  r = constrain(r, 4, 20);
  reset();
  
}





