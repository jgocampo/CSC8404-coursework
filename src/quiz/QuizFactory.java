package quiz;

import question.Question;
import student.Student;
import java.util.*;

/**
 * The QuizFactory is responsible for creating quizzes (both regular and revision). It holds a pool of questions
 * and tracks each student's quiz history to prevent repetition of questions in future quizzes.
 * This class is abstract, meaning specific quiz types need to extend it and provide the details for quiz creation.
 */
public abstract class QuizFactory implements Quiz {
    // A list of questions used in all quizzes
    protected final List<Question> questionPool;
    // Tracks the questions each student has already seen
    protected final Map<Student, List<Question>> studentHistory;

    /**
     * Constructor for QuizFactory. Takes a list of questions as input and initializes the quiz system.
     *
     * @param questionPool The pool of questions from which quizzes will be generated. Must not be null or empty.
     * @throws IllegalArgumentException if the question pool is null or empty.
     */
    public QuizFactory(List<Question> questionPool) {
        if (questionPool == null || questionPool.isEmpty()) {
            throw new IllegalArgumentException("The question pool cannot be empty.");
        }
        this.questionPool = new ArrayList<>(questionPool);
        this.studentHistory = new HashMap<>();
    }

    /**
     * Generates a regular quiz with a specified number of questions. It shuffles the question pool and selects
     * a subset of questions, avoiding any questions that have been seen by the student.
     *
     * @param numberOfQuestions The number of questions for the quiz.
     * @return A new quiz instance with the selected questions.
     * @throws IllegalArgumentException if the number of questions is less than 1 or more than available questions.
     */
    @Override
    public Quiz generateQuiz(int numberOfQuestions) {
        if (numberOfQuestions < 1 || numberOfQuestions > questionPool.size()) {
            throw new IllegalArgumentException("Invalid number of questions.");
        }

        Collections.shuffle(questionPool);  // Shuffle to mix up the questions
        List<Question> selectedQuestions = new ArrayList<>(questionPool.subList(0, numberOfQuestions));
        return createQuizInstance(selectedQuestions);
    }

    /**
     * Generates a revision quiz that only includes questions the student got wrong or hasn't answered before.
     * If there aren't enough questions available for the revision quiz, an exception is thrown.
     *
     * @param student The student who is taking the quiz.
     * @param numberOfQuestions The number of questions in the quiz.
     * @return A new quiz instance with revision questions.
     * @throws IllegalArgumentException if there are not enough unanswered or incorrect questions.
     */
    @Override
    public Quiz revise(Student student, int numberOfQuestions) {
        List<Question> revisionQuestions = getUnansweredOrIncorrectQuestions(student); // Get questions student hasn't answered or got wrong
        Collections.shuffle(revisionQuestions);  // Shuffle to avoid giving them in the same order

        if (revisionQuestions.size() < numberOfQuestions) {
            throw new IllegalArgumentException("Not enough questions available for revision.");
        }

        return createRevisionQuizInstance(revisionQuestions.subList(0, numberOfQuestions), student);
    }

    /**
     * Abstract method for creating a regular quiz instance. Must be implemented by subclasses.
     *
     * @param selectedQuestions The list of selected questions for the quiz.
     * @return A new instance of the quiz.
     */
    protected abstract Quiz createQuizInstance(List<Question> selectedQuestions);

    /**
     * Abstract method for creating a revision quiz instance. Must be implemented by subclasses.
     *
     * @param revisionQuestions The list of selected revision questions.
     * @param student The student taking the quiz.
     * @return A new instance of the revision quiz.
     */
    protected abstract Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student);

    /**
     * Retrieves a list of unanswered or incorrect questions for a given student. It checks the student's
     * quiz history and returns only the questions they haven't answered correctly.
     *
     * @param student The student whose unanswered or incorrect questions are being retrieved.
     * @return A list of questions that the student has not answered or got incorrect in previous quizzes.
     */
    protected List<Question> getUnansweredOrIncorrectQuestions(Student student) {
        List<Question> history = studentHistory.getOrDefault(student, new ArrayList<>());
        List<Question> unansweredOrIncorrect = new ArrayList<>();

        // Go through the entire question pool and add questions not yet seen by the student
        for (Question question : questionPool) {
            if (!history.contains(question)) {
                unansweredOrIncorrect.add(question);
            }
        }

        return unansweredOrIncorrect;
    }
}

