package unitTest;
import question.FreeResponseQuestion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FreeResponseQuestion class.
 */
public class FreeResponseQuestionTest {


    @Test
    public void testCorrectAnswer() {
        FreeResponseQuestion question = new FreeResponseQuestion("What is the capital of France?", "Paris");
        assertTrue(question.checkAnswer("paris"));
    }


    @Test
    public void testCorrectAnswerWithSpacesAndCase() {
        FreeResponseQuestion question = new FreeResponseQuestion("What is the capital of France?", "Paris");
        assertTrue(question.checkAnswer("  PaRiS "));
    }


    @Test
    public void testIncorrectAnswer() {
        FreeResponseQuestion question = new FreeResponseQuestion("What is the capital of France?", "Paris");
        assertFalse(question.checkAnswer("London"));
    }

    @Test
    public void testNullAnswer() {
        FreeResponseQuestion question = new FreeResponseQuestion("What is the capital of France?", "Paris");
        assertFalse(question.checkAnswer(null));
    }

    @Test
    public void testEmptyAnswer() {
        FreeResponseQuestion question = new FreeResponseQuestion("What is the capital of France?", "Paris");
        assertFalse(question.checkAnswer(""));
    }
}
