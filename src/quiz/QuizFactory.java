package quiz;

import question.Question;
import student.Student;
import java.util.*;

public abstract class QuizFactory implements Quiz {
    protected final List<Question> questionPool;
    protected final Map<Student, List<Question>> studentHistory;  // Track student quiz history

    public QuizFactory(List<Question> questionPool) {
        if (questionPool == null || questionPool.isEmpty()) {
            throw new IllegalArgumentException("The question pool cannot be empty.");
        }
        this.questionPool = new ArrayList<>(questionPool);
        this.studentHistory = new HashMap<>();
    }

    // Generate a regular quiz with unseen questions
    @Override
    public Quiz generateQuiz(int numberOfQuestions) {
        if (numberOfQuestions < 1 || numberOfQuestions > questionPool.size()) {
            throw new IllegalArgumentException("Invalid number of questions.");
        }

        Collections.shuffle(questionPool);
        List<Question> selectedQuestions = new ArrayList<>(questionPool.subList(0, numberOfQuestions));
        return createQuizInstance(selectedQuestions);
    }

    // Generate a revision quiz with unseen/incorrect questions
    @Override
    public Quiz revise(Student student, int numberOfQuestions) {
        List<Question> revisionQuestions = getUnansweredOrIncorrectQuestions(student);
        Collections.shuffle(revisionQuestions);

        if (revisionQuestions.size() < numberOfQuestions) {
            throw new IllegalArgumentException("Not enough questions available for revision.");
        }

        return createRevisionQuizInstance(revisionQuestions.subList(0, numberOfQuestions), student);
    }

    // Abstract methods to create quiz instances
    protected abstract Quiz createQuizInstance(List<Question> selectedQuestions);
    protected abstract Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student);

    // Track the student's quiz history (unanswered or incorrect questions)
    protected List<Question> getUnansweredOrIncorrectQuestions(Student student) {
        List<Question> history = studentHistory.getOrDefault(student, new ArrayList<>());
        List<Question> unansweredOrIncorrect = new ArrayList<>();

        for (Question question : questionPool) {
            if (!history.contains(question)) {
                unansweredOrIncorrect.add(question);
            }
        }

        return unansweredOrIncorrect;
    }
}
