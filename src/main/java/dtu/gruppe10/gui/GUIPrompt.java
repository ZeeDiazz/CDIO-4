package dtu.gruppe10.gui;

import java.awt.event.KeyListener;

public abstract class GUIPrompt<T> {
    public final String Prompt;
    public T CurrentAnswer;
    protected KeyListener keyListener;

    public GUIPrompt(String prompt, KeyListener keyListener) {
        this.Prompt = prompt;
        this.keyListener = keyListener;
    }

    public GUIPrompt(String prompt) {
        this(prompt, null);
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public abstract boolean answerAcceptable();
}
