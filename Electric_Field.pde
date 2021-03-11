import g4p_controls.*;

// Charges being simulated - modify list to change simulation
ArrayList<Integer[]> charges = new ArrayList<Integer[]>();

// List of test charges
ArrayList<Float[]> testCharges = new ArrayList<Float[]>();

// Constant modifier for force on test charges
final float fMod = 20;

// Variables to interface between gui and main window
boolean clearTestCharges = false;
boolean clearCharges = false;

void setup() {
  // Basic window setup
  background(255);
  size(600, 600);
  
  createGUI();
  surface.setTitle("Electric Field Simulator");
}

void draw() {
  // Drawing charges
  for (int i = 0; i < charges.size(); i++) {
    
    //setting fill colour based on positive or negative
    if (charges.get(i)[2] > 0) {
      fill(255,0,0);
    } else {fill(0,0,255);}
    
    int size = 30;
    
    ellipse(charges.get(i)[0], charges.get(i)[1], size, size);
  }
  
  // Looping through test charges, rendering, and
  // changing position and velocity over time
  fill(0);
  
  for (int i=0; i<testCharges.size(); i++) {
    ellipse(int(testCharges.get(i)[0]), int(testCharges.get(i)[1]), 5, 5);
    
    testCharges.get(i)[0] += testCharges.get(i)[2];
    testCharges.get(i)[1] += testCharges.get(i)[3];
    
    float[] force = electricForce(testCharges.get(i));
    
    testCharges.get(i)[2] += force[0];
    testCharges.get(i)[3] += force[1];
  }
 
  // Looping through test charges and removing any
  // that are off screen, to prevent any later return
  int i = 0;
  
  while (true) {
    if (i==testCharges.size()) {break;}
    
    if (testCharges.get(i)[0] < 0 || testCharges.get(i)[0] > width || testCharges.get(i)[1] < 0 || testCharges.get(i)[1] > height) {
      testCharges.remove(i);
      continue;
    }
    
    i++;
  }
  
  //using gui-main screen interface variables
  if (clearTestCharges) {
    testCharges = new ArrayList<Float[]>();
    background(255);
    clearTestCharges = false;
  }
  
  if (clearCharges) {
    charges = new ArrayList<Integer[]>();
    background(255);
    clearCharges = false;
  }
}

void mousePressed() {
  // Adds a new test charge upon click, at the click's location
  Float[] newTest = {float(mouseX), float(mouseY), 0.0, 0.0};
  testCharges.add(newTest);
}
