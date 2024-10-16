package quiz;

import student.Student;
import question.Question;
import java.util.List;

/**
 * This is the interface Quiz.
 * This expose the methods of the contract that quizzes will implement.
 *
 */

public interface Quiz {

    Quiz generateQuiz(int numberOfQuestions);

    Quiz revise(Student student, int numberOfQuestions);

    double takeQuiz(Student student, List<Question> questions, List<String> answers);

}
