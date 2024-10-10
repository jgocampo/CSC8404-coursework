package question;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MultipleChoiceQuestion implements Question {
    private final String questionFormulation;
    private final Set<String> correctAnswers;

    public MultipleChoiceQuestion(String questionFormulation, String[] correctAnswers) {
        if (questionFormulation == null || correctAnswers == null || correctAnswers.length < 2 || correctAnswers.length > 4) {
            throw new IllegalArgumentException("Multiple choice questions must have between 2 and 4 correct answers.");
        }
        this.questionFormulation = questionFormulation.trim();
        this.correctAnswers = Arrays.stream(correctAnswers)
                .map(this::normalizeAnswer)
                .collect(Collectors.toSet());
    }

    @Override
    public String getQuestionFormulation() {
        return questionFormulation;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) return false;

        Set<String> givenAnswers = Arrays.stream(answer.split(","))
                .map(this::normalizeAnswer)
                .collect(Collectors.toSet());

        return givenAnswers.equals(correctAnswers);
    }

    // Normaliza la respuesta eliminando espacios adicionales y haciendo todo minúscula
    private String normalizeAnswer(String answer) {
        return answer.trim().replaceAll("\\s+", "").toLowerCase();
    }
}
