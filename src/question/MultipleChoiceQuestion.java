package question;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a multiple-choice question.
 * The answer for this type of question is a group of
 */
public class MultipleChoiceQuestion implements Question {
    private final String questionFormulation;
    private final Set<String> correctAnswers;

    /**
     * Constructor for MultipleChoiceQuestion.
     *
     * @param questionFormulation the text of the question
     * @param correctAnswers      the correct answers as an array of literals like "a, b, c"
     */
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

    /**
     * Checks if the given answers are correct.
     *
     * @param answer the student's answer in the format "a,b,c"
     * @return true if the answer is correct, false otherwise
     */
    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) return false;

        Set<String> givenAnswers = Arrays.stream(answer.split(","))
                .map(this::normalizeAnswer)
                .collect(Collectors.toSet());

        return givenAnswers.equals(correctAnswers);
    }

    /**
     * Normalizes an answer ignoring spaces and order.
     *
     * @param answer the answer to normalize
     * @return the normalized answer
     */
    private String normalizeAnswer(String answer) {
        return answer.trim().toLowerCase();
    }
}

