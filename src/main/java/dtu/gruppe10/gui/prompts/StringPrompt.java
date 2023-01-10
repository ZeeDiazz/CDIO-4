package dtu.gruppe10.gui.prompts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StringPrompt extends GUIPrompt<String> {
    protected int inclusiveMinLength;
    protected int inclusiveMaxLength;
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
                    return;
                }

                for (char disallowed : disallowedLetters) {
                    if (pressedKey == disallowed) {
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

        this.inclusiveMinLength = inclusiveMinLength;
        this.inclusiveMaxLength = inclusiveMaxLength;
        this.disallowedChars = disallowedLetters;
    }

    @Override
    public boolean answerAcceptable() {
        int currentAnswerLength = CurrentAnswer.length();

        return currentAnswerLength >= inclusiveMinLength && currentAnswerLength <= inclusiveMaxLength;
    }
}
