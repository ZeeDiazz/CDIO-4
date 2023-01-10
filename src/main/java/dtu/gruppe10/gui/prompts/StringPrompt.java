package dtu.gruppe10.gui.prompts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StringPrompt extends GUIPrompt<String> {
    public final int InclusiveMinLength;
    public final int InclusiveMaxLength;
    protected char[] disallowedChars;

    public StringPrompt(String prompt, int inclusiveMinLength, int inclusiveMaxLength, char[] disallowedLetters) {
        super(prompt);

        CurrentAnswer = "";

        this.keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char pressedKey = e.getKeyChar();

                if (pressedKey == '\b') {
                    if (!CurrentAnswer.isEmpty()) {
                        CurrentAnswer = CurrentAnswer.substring(0, CurrentAnswer.length() - 2); // Remove last char
                    }
                    return;
                }
                if (!Character.isLetter(pressedKey)) {
                    lastInputNotAccepted("Input is not a letter");
                    return;
                }

                for (char disallowed : disallowedLetters) {
                    if (pressedKey == disallowed) {
                        lastInputNotAccepted("Letter is not allowed");
                        return;
                    }
                }
                CurrentAnswer += pressedKey;
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        this.InclusiveMinLength = inclusiveMinLength;
        this.InclusiveMaxLength = inclusiveMaxLength;
        this.disallowedChars = disallowedLetters;
    }

    @Override
    public boolean answerAcceptable() {
        int currentAnswerLength = CurrentAnswer.length();

        boolean accepted = true;

        if (currentAnswerLength < InclusiveMinLength) {
            accepted = false;
            lastAnswerNotAccepted("Answer too short");
        }
        else if (currentAnswerLength > InclusiveMaxLength) {
            accepted = false;
            lastAnswerNotAccepted("Answer too long");
        }

        return accepted;
    }
}
