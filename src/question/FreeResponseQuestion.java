package question;

public class FreeResponseQuestion implements Question {
    private final String questionFormulation;
    private final String correctAnswer;

    public FreeResponseQuestion(String questionFormulation, String correctAnswer) {
        if (questionFormulation == null || correctAnswer == null) {
            throw new IllegalArgumentException("Question formulation and answer cannot be null.");
        }
        this.questionFormulation = questionFormulation.trim();
        this.correctAnswer = normalizeAnswer(correctAnswer);  // Normalizar respuesta
    }

    @Override
    public String getQuestionFormulation() {
        return questionFormulation;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) return false;
        return normalizeAnswer(answer).equals(correctAnswer);
    }

    // Normaliza la respuesta eliminando espacios adicionales y haciendo todo minúscula
    private String normalizeAnswer(String answer) {
        return answer.trim().replaceAll("\\s+", " ").toLowerCase();
    }
}


