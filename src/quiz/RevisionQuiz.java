package quiz;

import question.Question;
import student.Student;
import statistics.Statistics;
import java.util.List;

public final class RevisionQuiz extends QuizFactory {



    public RevisionQuiz(List<Question> questionPool, Student student) {
        super(questionPool);
    }

    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        throw new UnsupportedOperationException("RevisionQuiz cannot generate a regular quiz.");
    }

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        return new RevisionQuiz(revisionQuestions, student);  // Devuelve una nueva instancia de quiz de revisión con las preguntas seleccionadas
    }

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        // Verificar si el estudiante puede tomar un quiz de revisión
        if (!student.getStatistics().canTakeRevisionQuiz()) {
            throw new IllegalStateException("Cannot take more revision quizzes. Final verdict: " + student.getStatistics().getVerdict());
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
        student.getStatistics().recordRevisionQuizScore(score);

        // Registrar las preguntas vistas
        recordSeenQuestions(student, questions);

        return score;
    }

}
