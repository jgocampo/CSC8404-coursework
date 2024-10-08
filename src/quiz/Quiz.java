package quiz;

import question.Question;
import student.Student;

import java.util.List;

public interface Quiz {
    Quiz generateQuiz(int numberOfQuestions);  // Método para generar un quiz regular
    Quiz revise(Student student, int numberOfQuestions);  // Método para generar un quiz de revisión
    double takeQuiz(Student student, List<Question> questions, List<String> answers);  // Toma un quiz regular
    double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers);  // Toma un quiz de revisión
}

