package dtu.gruppe10.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IntegerPrompt extends GUIPrompt<Integer> {
    public final int InclusiveMinimum;
    public final int InclusiveMaximum;
    public IntegerPrompt(String prompt, int inclusiveMinimum, int inclusiveMaximum) {
        super(prompt);


        this.keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char pressedKey = e.getKeyChar();


                if (e.getKeyChar() == '\b') {
                    if (CurrentAnswer != null) {
                        CurrentAnswer /= 10;
                        if (CurrentAnswer == 0) {
                            CurrentAnswer = null;
                        }
                    }
                }
                if (!Character.isDigit(pressedKey)) {
                    return;
                }

                int numericValue = Character.getNumericValue(pressedKey);
                if (CurrentAnswer == null) {
                    CurrentAnswer = numericValue;
                }
                else {
                    CurrentAnswer = CurrentAnswer * 10 + numericValue;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };



        this.InclusiveMinimum = inclusiveMinimum;
        this.InclusiveMaximum = inclusiveMaximum;
    }

    @Override
    public boolean answerAcceptable() {
        return CurrentAnswer >= InclusiveMinimum && CurrentAnswer <= InclusiveMaximum;
    }
}
