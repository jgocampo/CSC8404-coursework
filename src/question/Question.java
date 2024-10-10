package question;

public interface Question {
    // Obtener la formulación de la pregunta
    String getQuestionFormulation();

    // Verificar si una respuesta dada es correcta
    boolean checkAnswer(String answer);
}

