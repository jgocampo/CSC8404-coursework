package question;

/**
 * This class represents a question of the typw Free Response, where the student provide
 * an answer in plain text. It implements the {@link Question} interface, providing the
 * question formulation and a method to check if a given answer is correct.
 *
 */
public class FreeResponseQuestion implements Question {

    /**
     * The text of the question presented to the student.
     */
    private final String questionFormulation;

    /**
     * The correct answer to the Free Response question.
     */
    private final String correctAnswer;

    /**
     * Constructs a new {@code FreeResponseQuestion} with the provided question formulation
     * and the correct answer. Both parameters must be non-null.
     *
     * @param questionFormulation the text of the question
     * @param correctAnswer       the correct answer to the question
     * @throws IllegalArgumentException if the question formulation or answer is null
     */
    public FreeResponseQuestion(String questionFormulation, String correctAnswer) {
        if (questionFormulation == null || correctAnswer == null) {
            throw new IllegalArgumentException("Question formulation and answer cannot be null.");
        }
        this.questionFormulation = questionFormulation.trim();
        this.correctAnswer = normalizeAnswer(correctAnswer);
    }

    /**
     * Returns the formulation of the question that will be presented to the student.
     *
     * @return the question formulation
     */
    @Override
    public String getQuestionFormulation() {
        return questionFormulation;
    }

    /**
     * Checks whether the provided answer matches the correct answer for this question.
     * The comparison is case-insensitive, ignores leading and trailing spaces, and treats
     * multiple spaces as a single space.
     *
     * @param answer the student answer to check
     * @return true or false if the question is correct or not.
     */
    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) return false;
        return normalizeAnswer(answer).equals(correctAnswer);
    }

    /**
     * Method to normalize the answer by trimming it, replacing multiple spaces with a single space,
     * and converting it to lowercase to make the comparison.
     *
     * @param answer the answer to normalize
     * @return the normalized answer
     */
    private String normalizeAnswer(String answer) {
        return answer.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}



