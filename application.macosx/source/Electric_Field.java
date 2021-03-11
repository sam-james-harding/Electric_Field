import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Electric_Field extends PApplet {



// Charges being simulated - modify list to change simulation
ArrayList<Integer[]> charges = new ArrayList<Integer[]>();

// List of test charges
ArrayList<Float[]> testCharges = new ArrayList<Float[]>();

// Constant modifier for force on test charges
final float fMod = 20;

// Variables to interface between gui and main window
boolean clearTestCharges = false;
boolean clearCharges = false;

public void setup() {
  // Basic window setup
  background(255);
  
  
  createGUI();
  surface.setTitle("Electric Field Simulator");
}

public void draw() {
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
    ellipse(PApplet.parseInt(testCharges.get(i)[0]), PApplet.parseInt(testCharges.get(i)[1]), 5, 5);
    
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

public void mousePressed() {
  // Adds a new test charge upon click, at the click's location
  Float[] newTest = {PApplet.parseFloat(mouseX), PApplet.parseFloat(mouseY), 0.0f, 0.0f};
  testCharges.add(newTest);
}
public float[] electricForce(Float[] testCharge) {
  // Takes a test charge and calculates the force on it from all 
  // charges, then sums these to find total force
  float forceXSum = 0;
  float forceYSum = 0;
  
  for (int i = 0; i < charges.size(); i++) {
    // Calculating distance from charge, (r2 is squared distance magnitude)
    int distanceX = PApplet.parseInt(testCharge[0])-charges.get(i)[0];
    int distanceY = PApplet.parseInt(testCharge[1])-charges.get(i)[1];
    
    float r2 = pow(distanceX, 2) + pow(distanceY, 2);
    
    // Calculating strength of electric force
    float strength = fMod * charges.get(i)[2]/r2;
    
    // Multiplying strength with normalized distance vector
    // and adding to the total force sum
    forceXSum += strength*distanceX/pow(r2, 0.5f);
    forceYSum += strength*distanceY/pow(r2, 0.5f); 
    
    // Freezing test charges that have hit charges
    if (pow(r2, 0.5f) < 15) {
      forceXSum = 0;
      forceYSum = 0;
      testCharge[2] = 0.0f;
      testCharge[3] = 0.0f;
    }
  }
  
  // Returnign total electric force vector
  float[] forceVector = {forceXSum, forceYSum};
  
  return forceVector;
}
/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */
int newX;
int newY;
int newB;

synchronized public void win_draw1(PApplet appc, GWinData data) { //_CODE_:chargeCreator:226677:
  appc.background(230);
} //_CODE_:chargeCreator:226677:

public void newChargeButton_click(GButton source, GEvent event) { //_CODE_:newChargeButton:890271:
  println("newChargeButton - GButton >> GEvent." + event + " @ " + millis());
  Integer[] newCharge = {newX, newY, newB};
  charges.add(newCharge);
} //_CODE_:newChargeButton:890271:

public void bSlider_change(GSlider source, GEvent event) { //_CODE_:bSlider:367377:
  println("xSlider - GSlider >> GEvent." + event + " @ " + millis());
  newB = source.getValueI();
} //_CODE_:bSlider:367377:

public void xSlider_change(GSlider source, GEvent event) { //_CODE_:xSlider:522848:
  println("xSlider - GSlider >> GEvent." + event + " @ " + millis());
  newX = source.getValueI();
} //_CODE_:xSlider:522848:

public void ySlider_change(GSlider source, GEvent event) { //_CODE_:ySlider:696707:
  println("ySlider - GSlider >> GEvent." + event + " @ " + millis());
  newY = source.getValueI();
} //_CODE_:ySlider:696707:

public void clearButton_click(GButton source, GEvent event) { //_CODE_:clearButton:947811:
  println("clearButton - GButton >> GEvent." + event + " @ " + millis());
  clearTestCharges = true;
} //_CODE_:clearButton:947811:

public void clearChargesButton_click(GButton source, GEvent event) { //_CODE_:clearChargesButton:227221:
  println("clearChargesButton - GButton >> GEvent." + event + " @ " + millis());
  clearCharges = true;
  clearTestCharges = true;
} //_CODE_:clearChargesButton:227221:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setMouseOverEnabled(false);
  surface.setTitle("Sketch Window");
  chargeCreator = GWindow.getWindow(this, "Simulator Manager", 0, 0, 300, 300, JAVA2D);
  chargeCreator.noLoop();
  chargeCreator.setActionOnClose(G4P.EXIT_APP);
  chargeCreator.addDrawHandler(this, "win_draw1");
  nChargeLabel = new GLabel(chargeCreator, 100, 0, 114, 20);
  nChargeLabel.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  nChargeLabel.setText("Create New Charge");
  nChargeLabel.setOpaque(false);
  newChargeButton = new GButton(chargeCreator, 100, 160, 117, 30);
  newChargeButton.setText("Create New Charge");
  newChargeButton.addEventHandler(this, "newChargeButton_click");
  bSlider = new GSlider(chargeCreator, 65, 118, 232, 40, 10.0f);
  bSlider.setShowValue(true);
  bSlider.setShowLimits(true);
  bSlider.setLimits(0, -100, 100);
  bSlider.setNumberFormat(G4P.INTEGER, 0);
  bSlider.setOpaque(false);
  bSlider.addEventHandler(this, "bSlider_change");
  xSlider = new GSlider(chargeCreator, 68, 24, 229, 40, 10.0f);
  xSlider.setShowValue(true);
  xSlider.setShowLimits(true);
  xSlider.setLimits(0, 0, 600);
  xSlider.setNumberFormat(G4P.INTEGER, 0);
  xSlider.setOpaque(false);
  xSlider.addEventHandler(this, "xSlider_change");
  ySlider = new GSlider(chargeCreator, 67, 72, 230, 40, 10.0f);
  ySlider.setShowValue(true);
  ySlider.setShowLimits(true);
  ySlider.setLimits(0, 0, 600);
  ySlider.setNumberFormat(G4P.INTEGER, 0);
  ySlider.setOpaque(false);
  ySlider.addEventHandler(this, "ySlider_change");
  label1 = new GLabel(chargeCreator, 2, 125, 62, 27);
  label1.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label1.setText("Electric Charge");
  label1.setOpaque(false);
  label2 = new GLabel(chargeCreator, 2, 78, 64, 27);
  label2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label2.setText("Y coordinate");
  label2.setOpaque(false);
  label3 = new GLabel(chargeCreator, 1, 30, 65, 27);
  label3.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label3.setText("X coordinate");
  label3.setOpaque(false);
  clearButton = new GButton(chargeCreator, 100, 210, 119, 30);
  clearButton.setText("Clear Test Charges");
  clearButton.addEventHandler(this, "clearButton_click");
  clearChargesButton = new GButton(chargeCreator, 100, 260, 116, 30);
  clearChargesButton.setText("Clear All");
  clearChargesButton.addEventHandler(this, "clearChargesButton_click");
  chargeCreator.loop();
}

// Variable declarations 
// autogenerated do not edit
GWindow chargeCreator;
GLabel nChargeLabel; 
GButton newChargeButton; 
GSlider bSlider; 
GSlider xSlider; 
GSlider ySlider; 
GLabel label1; 
GLabel label2; 
GLabel label3; 
GButton clearButton; 
GButton clearChargesButton; 

  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Electric_Field" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
