package dtu.gruppe10.gui;

public class GUIAnswer<T> {
    protected boolean hasAnswer;
    protected T answer;

    public boolean hasAnswer() {
        return hasAnswer;
    }

    public void setAnswer(Object answer) {
        this.answer = (T)answer;
        hasAnswer = true;
    }

    public T getAnswer() {
        return answer;
    }
}
