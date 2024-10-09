package quiz;

import question.Question;
import student.Student;

import java.util.List;

public interface Quiz {
    Quiz generateQuiz(int numberOfQuestions);  // Metodo para generar un quiz regular
    Quiz revise(Student student, int numberOfQuestions);  // Metodo para generar un quiz de revisión
    double takeQuiz(Student student, List<Question> questions, List<String> answers);  // Toma un quiz regular
    double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers);  // Toma un quiz de revisión
}

