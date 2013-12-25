#include <Thing.h>

void setup() {  
  thing.addComponent("speaker",   DIGITAL, 13);
  thing.addMode(0, automation);
  thing.start(-1); 
  Serial.begin(115200);
}

char* all() {
}

void loop() {
  thing.loop();
}

void automation() {
}
