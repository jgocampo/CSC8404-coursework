package question;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public final class MultipleChoiceQuestion implements Question {
    private final String questionText;
    private final Set<String> correctAnswers;

    public MultipleChoiceQuestion(String questionText, String[] correctAnswers) {
        if (questionText == null || correctAnswers == null || correctAnswers.length < 2) {
            throw new IllegalArgumentException("Question and at least two correct answers are required.");
        }
        this.questionText = questionText;
        this.correctAnswers = new HashSet<>();
        for (String answer : correctAnswers) {
            this.correctAnswers.add(answer.trim().toLowerCase());
        }
    }

    public String getQuestionText() {
        return questionText;
    }


    public boolean checkAnswer(String answer) {
        if (answer == null) return false;
        Set<String> providedAnswers = new HashSet<>(Arrays.asList(answer.trim().toLowerCase().split("\\s*,\\s*")));
        return correctAnswers.equals(providedAnswers);
    }


    public String toString() {
        return "Multiple Choice Question: " + questionText;
    }
}
