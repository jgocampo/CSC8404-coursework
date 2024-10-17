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
 * Unit tests for the RevisionQuiz class, this tests cover all the public methods
 * and the correct functioning of a revision quiz cases.
 */

public class RevisionQuizTest {

    private List<Question> questionPool;
    private QuizFactory quizFactory;
    private Student student;

    @BeforeEach
    public void setup() {

        questionPool = new ArrayList<>();
        questionPool.add(new FreeResponseQuestion("What is the capital of Spain?", "Madrid"));
        questionPool.add(new FreeResponseQuestion("What is the chemical formula for water?", "H2O"));
        questionPool.add(new FreeResponseQuestion("Who wrote '1984'?", "George Orwell"));
        questionPool.add(new FreeResponseQuestion("What is the largest planet in the Solar System?", "Jupiter"));
        questionPool.add(new FreeResponseQuestion("Who discovered penicillin?", "Alexander Fleming"));
        questionPool.add(new MultipleChoiceQuestion("Which are vegetables?", new String[]{"a", "b", "c"}));
        questionPool.add(new MultipleChoiceQuestion("Which are primary colors?", new String[]{"c","d"}));
        questionPool.add(new MultipleChoiceQuestion("Which are fruits?", new String[]{"d", "a"}));
        questionPool.add(new MultipleChoiceQuestion("Which are chemical elements?", new String[]{"c", "d", "e"}));
        questionPool.add(new MultipleChoiceQuestion("Which are programming languages?", new String[]{"b", "c"}));

        Calendar cal = Calendar.getInstance();
        cal.set(1997, Calendar.JULY, 15);
        Date birthDate = cal.getTime();
        student = new Student("Jhostin", "Smith", birthDate);

        quizFactory = new RevisionQuiz(questionPool, student);
    }

    /**
     * Test that the revise method generates the correct type of revision Quiz
     */
    @Test
    public void testReviseGeneratesCorrectQuiz() {

        QuizFactory regularQuizFactory = new RegularQuiz(questionPool);


        List<String> regularQuizAnswers = List.of("Madrid", "Wrong Answer", "Wrong Answer", "Wrong Answer", "Alexander Fleming");
        regularQuizFactory.generateQuiz(5).takeQuiz(student, questionPool.subList(0, 5), regularQuizAnswers);

        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());


        Quiz revisionQuiz = quizFactory.revise(student, 3);
        List<Question> revisionQuestions = questionPool.subList(1, 4);
        quizFactory.recordSeenQuestions(student, revisionQuestions);

        assertTrue(quizFactory.getStudentHistory().get(student).containsAll(revisionQuestions));
    }

    /**
     * Test to verify that the revision quiz has the correct behavior by
     * recording the attempts, checking the questions seen registered in the
     * student history and the score of the revision doesn't affect the verdict.
     */
    @Test
    public void testTakeRevisionQuiz() {

        List<Question> revisionQuestions1 = Arrays.asList(
                questionPool.get(0),
                questionPool.get(1),
                questionPool.get(5),
                questionPool.get(6)
        );

        List<String> revisionAnswers1 = List.of("Madrid", "H2O", "b,c,a", "c,d");

        Quiz revisionQuiz = quizFactory.revise(student, 4);
        double score = revisionQuiz.takeQuiz(student, revisionQuestions1, revisionAnswers1);

        quizFactory.recordSeenQuestions(student, revisionQuestions1);


        assertEquals(1.0, score);

        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict()); //Check that the verdict hasn't change


        Map<Student, List<Question>> studentHistory = quizFactory.getStudentHistory();

        // THe map of the student history should not be empty o null
        assertFalse(studentHistory.isEmpty());

        List<Question> recordedHistory = studentHistory.get(student);
        assertNotNull(recordedHistory);

        // Check the questions are registered
        assertTrue(recordedHistory.containsAll(revisionQuestions1));
    }

    /**
     * Test to verify that a student can not take more revision quiz attempts,
     * maximum of 2.
     */
    @Test
    public void testCannotTakeMoreThanTwoRevisionQuizzes() {

        quizFactory.revise(student, 4).takeQuiz(student, questionPool.subList(0, 4), List.of("Madrid", "H2O", "a,b,c", "c,d"));


        quizFactory.revise(student, 4).takeQuiz(student, questionPool.subList(4, 8), List.of("a,d", "Jupiter", "c,d,e", "b,c"));

        Statistics stats = student.getStatistics();
        assertFalse(stats.canTakeRevisionQuiz());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                quizFactory.revise(student, 4)
        );

        assertEquals("Cannot take more revision quizzes. Final verdict: TBD", exception.getMessage());
    }

    /**
     * Test to verify the question a student has seen in a revision quiz attempt are recorded
     * and not available for the next attempts.
     */
    @Test
    public void testRevisionQuizDoesNotContainPreviouslySeenQuestions() {

        List<Question> firstRevisionQuestions = Arrays.asList(
                questionPool.get(0),
                questionPool.get(1),
                questionPool.get(5),
                questionPool.get(6)
        );

        List<String> firstRevisionAnswers = List.of("Madrid", "H2O", "a,b,c", "c,d");

        Quiz firstRevisionQuiz = quizFactory.revise(student, 4);
        double score = firstRevisionQuiz.takeQuiz(student, firstRevisionQuestions, firstRevisionAnswers);

        quizFactory.recordSeenQuestions(student, firstRevisionQuestions);


        List<Question> recordedHistory = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory);
        assertTrue(recordedHistory.containsAll(firstRevisionQuestions));


        List<Question> secondRevisionQuestions = Arrays.asList(
                questionPool.get(2),
                questionPool.get(3),
                questionPool.get(7),
                questionPool.get(8)
        );

        // Validate the questions seen are no longer available
        for (Question question : secondRevisionQuestions) {
            assertFalse(recordedHistory.contains(question));
        }

        // Second quiz attempt
        Quiz secondRevisionQuiz = quizFactory.revise(student, 4);
        score = secondRevisionQuiz.takeQuiz(student, secondRevisionQuestions, List.of("George Orwell", "Jupiter", "a,d", "c,d,e"));
        quizFactory.recordSeenQuestions(student, secondRevisionQuestions);

        recordedHistory = quizFactory.getStudentHistory().get(student);
        assertTrue(recordedHistory.containsAll(secondRevisionQuestions));
    }


}



