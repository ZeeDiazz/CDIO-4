package dtu.gruppe10.gui.prompts;

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

                if (pressedKey == '\b') {
                    if (CurrentAnswer != null) {
                        CurrentAnswer /= 10;
                        if (CurrentAnswer == 0) {
                            CurrentAnswer = null;
                        }
                    }
                    return;
                }
                else if (pressedKey == '\n') {
                    return; // Ignore 'Enter'
                }
                else if (!Character.isDigit(pressedKey)) {
                    lastInputNotAccepted("Input is not a number");
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
        boolean accepted = true;

        if (CurrentAnswer < InclusiveMinimum) {
            accepted = false;
            lastAnswerNotAccepted("Number too small");
        }
        else if (CurrentAnswer > InclusiveMaximum) {
            accepted = false;
            lastAnswerNotAccepted("Number too big");
        }

        return accepted;
    }
}
