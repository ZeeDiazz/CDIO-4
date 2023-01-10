package dtu.gruppe10.gui.prompts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class GUIPrompt<T> {
    public final String Prompt;
    public T CurrentAnswer;
    protected KeyListener keyListener;
    protected ArrayList<PromptErrorHandler> errorHandlers;

    public GUIPrompt(String prompt, KeyListener keyListener) {
        this.Prompt = prompt;
        this.keyListener = keyListener;
        this.errorHandlers = new ArrayList<>();
    }

    public GUIPrompt(String prompt) {
        this(prompt, null);
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public void addErrorHandler(PromptErrorHandler errorHandler) {
        this.errorHandlers.add(errorHandler);
    }

    protected void lastInputNotAccepted(String reason) {
        for (PromptErrorHandler handler : errorHandlers) {
            handler.lastInputNotAccepted(reason);
        }
    }

    protected void lastAnswerNotAccepted(String reason) {
        for (PromptErrorHandler handler : errorHandlers) {
            handler.lastAnswerNotAccepted(reason);
        }
    }

    public abstract boolean answerAcceptable();
}
