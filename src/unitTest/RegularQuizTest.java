package unitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import quiz.RegularQuiz;
import student.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RegularQuiz class, this tests cover all the public methods
 * and the correct functioning of a regular quiz.
 */

public class RegularQuizTest {

    private List<Question> questionPool;
    private RegularQuiz regularQuiz;
    private Student student;

    @BeforeEach
    public void setup() {

        questionPool = new ArrayList<>();
        questionPool.add(new FreeResponseQuestion("What is the capital of Spain?", "Madrid"));
        questionPool.add(new FreeResponseQuestion("What is the chemical formula for water?", "H2O"));
        questionPool.add(new MultipleChoiceQuestion("Which of this countrys is in South America?", new String[]{"a", "c"}));
        questionPool.add(new MultipleChoiceQuestion("Which are Cloud computing providers?", new String[]{"a", "d", "e"}));

        Calendar cal = Calendar.getInstance();
        cal.set(1998, Calendar.DECEMBER, 10);
        Date birthDate = cal.getTime();
        student = new Student("Jhostin", "Ocampo", birthDate);

        regularQuiz = new RegularQuiz(questionPool);
    }

    /**
     * Test generating a regular quiz a taking it, with the
     * spected score.
     */

    @Test
    public void testGenerateAndTakeRegularQuiz() {

        List<Question> quizQuestions = questionPool.subList(0, 3);
        List<String> answers = List.of("Madrid", "H2O", "a,c");

        double score = regularQuiz.takeQuiz(student, quizQuestions, answers);

        assertEquals(1.0, score);
        assertEquals(1, student.getStatistics().getRegularAttempts());


        assertEquals(3, regularQuiz.getStudentHistory().get(student).size());
    }

    /**
     * Test a regular quiz with some correct answers, also that the
     * student history is updated.
     */

    @Test
    public void testRegularQuizWithPartialCorrectAnswers() {

        List<Question> quizQuestions = questionPool.subList(0, 3);
        List<String> answers = List.of("Madrid", "Wrong", "a,c");
        double score = regularQuiz.takeQuiz(student, quizQuestions, answers);

        assertEquals(2.0 / 3.0, score);

        assertEquals(3, regularQuiz.getStudentHistory().get(student).size());
    }

    /**
     * Test a regular quiz with incorrect answers, asserting the
     * spected score to be 0.
     */

    @Test
    public void testRegularQuizWithIncorrectAnswers() {

        List<Question> quizQuestions = questionPool.subList(0, 3);
        List<String> answers = List.of("Wrong", "Wrong", "Wrong");

        double score = regularQuiz.takeQuiz(student, quizQuestions, answers);

        assertEquals(0.0, score);

        assertEquals(3, regularQuiz.getStudentHistory().get(student).size());
    }
}


