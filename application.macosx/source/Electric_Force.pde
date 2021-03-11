float[] electricForce(Float[] testCharge) {
  // Takes a test charge and calculates the force on it from all 
  // charges, then sums these to find total force
  float forceXSum = 0;
  float forceYSum = 0;
  
  for (int i = 0; i < charges.size(); i++) {
    // Calculating distance from charge, (r2 is squared distance magnitude)
    int distanceX = int(testCharge[0])-charges.get(i)[0];
    int distanceY = int(testCharge[1])-charges.get(i)[1];
    
    float r2 = pow(distanceX, 2) + pow(distanceY, 2);
    
    // Calculating strength of electric force
    float strength = fMod * charges.get(i)[2]/r2;
    
    // Multiplying strength with normalized distance vector
    // and adding to the total force sum
    forceXSum += strength*distanceX/pow(r2, 0.5);
    forceYSum += strength*distanceY/pow(r2, 0.5); 
    
    // Freezing test charges that have hit charges
    if (pow(r2, 0.5) < 15) {
      forceXSum = 0;
      forceYSum = 0;
      testCharge[2] = 0.0;
      testCharge[3] = 0.0;
    }
  }
  
  // Returnign total electric force vector
  float[] forceVector = {forceXSum, forceYSum};
  
  return forceVector;
}
