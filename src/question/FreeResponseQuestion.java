package question;

import java.util.Objects;

public final class FreeResponseQuestion implements Question {
    private final String questionText;
    private final String correctAnswer;

    public FreeResponseQuestion(String questionText, String correctAnswer) {
        if (questionText == null || correctAnswer == null) {
            throw new IllegalArgumentException("Question and answer cannot be null.");
        }
        this.questionText = questionText;
        this.correctAnswer = correctAnswer.trim().toLowerCase();
    }


    public String getQuestionText() {
        return questionText;
    }


    public boolean checkAnswer(String answer) {
        if (answer == null) return false;
        return correctAnswer.equals(answer.trim().replaceAll("\\s+", " ").toLowerCase());
    }


    public String toString() {
        return "Question: " + questionText;
    }
}

