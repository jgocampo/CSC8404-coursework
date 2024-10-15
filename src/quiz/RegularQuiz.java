package quiz;

import question.Question;
import student.Student;
import statistics.Statistics;
import java.util.List;

public final class RegularQuiz extends QuizFactory {
    public RegularQuiz(List<Question> questionPool) {
        super(questionPool);
    }

    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        return new RegularQuiz(selectedQuestions);
    }

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        throw new UnsupportedOperationException("RegularQuiz cannot generate a revision quiz.");
    }

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        Statistics stats = student.getStatistics();

        // Verificar si el estudiante puede tomar el quiz
        if (!stats.canTakeRegularQuiz()) {
            throw new IllegalStateException("Student cannot take more regular quizzes. Final verdict: " + stats.getVerdict());
        }

        // Registrar las preguntas vistas
        for (Question question : questions) {
            stats.recordSeenQuestion(question.getQuestionFormulation());
        }

        // Calcular el puntaje basado en las respuestas correctas
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }
        double score = (double) correctAnswers / questions.size();

        // Registrar el puntaje en las estadísticas
        stats.recordRegularQuizScore(score);

        return score;
    }

}
