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
    // Genera un quiz regular

    Quiz generateQuiz(int numberOfQuestions);

    // Genera un quiz de revisión
    Quiz revise(Student student, int numberOfQuestions);

    // Calcula el puntaje del quiz
    double takeQuiz(Student student, List<Question> questions, List<String> answers);
}
