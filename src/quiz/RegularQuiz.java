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
        if (!stats.canTakeRegularQuiz()) {
            throw new IllegalStateException("Cannot take more regular quizzes. Final verdict: " + stats.getVerdict());
        }

        // Score calculation logic
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / questions.size();
        stats.recordRegularQuizScore(score);  // Affect final verdict
        stats.recordSeenQuestions(questions.stream().map(Question::getQuestionFormulation).toList());

        return score;
    }
}
