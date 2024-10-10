package quiz;

import student.Student;
import question.Question;
import java.util.List;

public interface Quiz {
    // Genera un quiz regular
    Quiz generateQuiz(int numberOfQuestions);

    // Genera un quiz de revisión
    Quiz revise(Student student, int numberOfQuestions);

    // Calcula el puntaje del quiz
    double takeQuiz(Student student, List<Question> questions, List<String> answers);
}
