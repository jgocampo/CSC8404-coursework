package unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import quiz.Quiz;
import quiz.QuizFactory;
import quiz.RegularQuiz;
import quiz.RevisionQuiz;
import student.Student;
import statistics.Statistics;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test is designed for testing a big scenario with multiple quiz attempts
 * and tracking the correct functioning of the system. Including taking revision quizzes,
 * regular quizzes and recording all the attempts information on the student history.
 */

public class MixedQuizTest {

    private List<Question> questionPool;
    private QuizFactory quizFactory;
    private QuizFactory quizFactory1;
    private Student student;


    @BeforeEach
    public void setup() {

        questionPool = new ArrayList<>();


        questionPool.add(new FreeResponseQuestion("What is the capital of Ecuador?", "Quito"));
        questionPool.add(new FreeResponseQuestion("Who wrote the Odyssey?", "Homer"));
        questionPool.add(new FreeResponseQuestion("What is the chemical symbol for water?", "H2O"));
        questionPool.add(new FreeResponseQuestion("Who wrote the Harry Potter books?", "J.K Rowling"));
        questionPool.add(new FreeResponseQuestion("What is the tallest mountain in the world?", "Mount Everest"));
        questionPool.add(new FreeResponseQuestion("Which is the fourth planet in the solar system?", "Mars"));
        questionPool.add(new FreeResponseQuestion("What is the smallest prime number?", "2"));
        questionPool.add(new FreeResponseQuestion("What element does 'O' represent on the periodic table?", "Oxygen"));
        questionPool.add(new FreeResponseQuestion("Who painted the Mona Lisa?", "Leonardo da Vinci"));
        questionPool.add(new FreeResponseQuestion("Who won the World Cup of football in 2006?", "Italy"));

        questionPool.add(new MultipleChoiceQuestion("Which of the following are fruits?", new String[]{"a", "b"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are colors?", new String[]{"c", "d"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are primary colors?", new String[]{"a", "b", "c"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are programming languages?", new String[]{"c", "d", "a"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are mammals?", new String[]{"b", "c"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are countries?", new String[]{"a", "b"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are planets?", new String[]{"d", "a"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are vegetables?", new String[]{"c", "d", "e"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are chemical elements?", new String[]{"c", "e"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are continents?", new String[]{"b", "d"}));

        Calendar cal = Calendar.getInstance();
        cal.set(1998, Calendar.DECEMBER, 10);
        Date birthDate = cal.getTime();
        student = new Student("Jhostin", "Ocampo", birthDate);

        quizFactory = new RevisionQuiz(questionPool, student);

        quizFactory1 = new RegularQuiz(questionPool);

    }


    @Test
    public void testRevisionAndRegularQuizzesWithHistoryValidation() {
        Statistics stats = student.getStatistics();

        // 1. Takes a first revision attempt

        List<Question> revisionQuestions1 = Arrays.asList(
                questionPool.get(0),
                questionPool.get(1),
                questionPool.get(10),
                questionPool.get(11)
        );
        List<String> answers1 = List.of("Quito", "Homer", "a,b", "c,d");

        Quiz revisionQuiz1 = quizFactory.revise(student, 4);


        double revisionScore1 = revisionQuiz1.takeQuiz(student, revisionQuestions1, answers1);
        System.out.println("First revision quiz score: " + revisionScore1);

        // Verdict shouldnt be affected
        assertEquals("TBD", stats.getVerdict());

        // Check the student history is updated
        quizFactory.recordSeenQuestions(student, revisionQuestions1);
        List<Question> recordedHistory1 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory1);
        assertTrue(recordedHistory1.containsAll(revisionQuestions1));

        // 2. First regular quiz attempt
        List<Question> regularQuestions1 = Arrays.asList(
                questionPool.get(2),
                questionPool.get(3),
                questionPool.get(12),
                questionPool.get(13)
        );
        List<String> incorrectAnswers = List.of("Wrong Answer", "Wrong Answer", "Wrong Answer", "Wrong Answer");

        Quiz regularQuiz1 = quizFactory1.generateQuiz(4);

        double regularScore1 = regularQuiz1.takeQuiz(student, regularQuestions1, incorrectAnswers);
        System.out.println("First regular quiz attempt (fail): " + regularScore1);


        assertEquals(0.0, regularScore1);
        assertEquals("TBD", stats.getVerdict());


        quizFactory.recordSeenQuestions(student, regularQuestions1);
        List<Question> recordedHistory2 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory2);
        assertTrue(recordedHistory2.containsAll(regularQuestions1));

        // 3. Second revision quiz attempt
        List<Question> revisionQuestions2 = Arrays.asList(
                questionPool.get(4),
                questionPool.get(5),
                questionPool.get(14),
                questionPool.get(15)
        );
        List<String> answers2 = List.of("Mount Everest", "Mars", "c,b", "a,b");

        Quiz revisionQuiz2 = quizFactory.revise(student, 4);

        double revisionScore2 = revisionQuiz2.takeQuiz(student, revisionQuestions2, answers2);
        System.out.println("Segundo quiz de revisión score: " + revisionScore2);


        assertEquals("TBD", stats.getVerdict());

        // Record seen questions in the student history
        quizFactory.recordSeenQuestions(student, revisionQuestions2);
        List<Question> recordedHistory3 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory3);
        assertTrue(recordedHistory3.containsAll(revisionQuestions2));

        // 4. Take the second regular quiz and pass it
        List<Question> regularQuestions2 = Arrays.asList(
                questionPool.get(6),
                questionPool.get(7),
                questionPool.get(16),
                questionPool.get(17)
        );
        List<String> correctAnswers = List.of("2", "Oxygen", "a,d", "c,d,e");


        Quiz regularQuiz2 = quizFactory1.generateQuiz(4);

        double regularScore2 = regularQuiz2.takeQuiz(student, regularQuestions2, correctAnswers);
        System.out.println("Second regular quiz (pass): " + regularScore2);


        assertEquals(1.0, regularScore2);

        assertEquals("PASS", stats.getVerdict());


        quizFactory.recordSeenQuestions(student, regularQuestions2);
        List<Question> recordedHistory4 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory4);
        assertTrue(recordedHistory4.containsAll(regularQuestions2));
    }

}
