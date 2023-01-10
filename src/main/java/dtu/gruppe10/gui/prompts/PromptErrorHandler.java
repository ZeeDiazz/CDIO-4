package dtu.gruppe10.gui.prompts;

public interface PromptErrorHandler {
    void lastInputNotAccepted(String reason);
    void lastAnswerNotAccepted(String reason);
}
