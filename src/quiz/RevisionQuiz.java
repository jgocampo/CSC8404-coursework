package quiz;

import question.Question;
import student.Student;
import statistics.Statistics;
import java.util.List;

public final class RevisionQuiz extends QuizFactory {

    public RevisionQuiz(List<Question> questionPool) {
        super(questionPool);
    }

    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        throw new UnsupportedOperationException("RevisionQuiz cannot generate a regular quiz.");
    }

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        return new RevisionQuiz(revisionQuestions);  // Devuelve una nueva instancia de quiz de revisión con las preguntas seleccionadas
    }

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        // Obtener las estadísticas del estudiante
        Statistics stats = student.getStatistics();

        // Verificar si el estudiante es elegible para tomar un quiz de revisión
        if (!stats.canTakeRevisionQuiz()) {
            throw new IllegalStateException("Cannot take more revision quizzes. Final verdict: " + stats.getVerdict());
        }

        // Calcular el puntaje del quiz de revisión
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / questions.size();

        // Registrar el puntaje en las estadísticas (no afecta el veredicto)
        stats.recordRevisionQuizScore(score);

        // Registrar las preguntas vistas para evitar repetición en el futuro
        for (Question question : questions) {
            stats.recordSeenQuestion(question.getQuestionFormulation());
        }

        // Retornar el puntaje obtenido en el quiz de revisión
        return score;
    }

}
