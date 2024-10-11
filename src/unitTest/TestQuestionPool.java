package unitTest;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;

import java.util.Arrays;
import java.util.List;

public class TestQuestionPool {

    // Pool de preguntas estático para ser usado en todas las pruebas
    public static List<Question> getQuestionPool() {
        return Arrays.asList(
                new FreeResponseQuestion("What is the capital of Ecuador?", "Quito"),
                new FreeResponseQuestion("Which is the fourth planet on the solar system?", "Mars"),
                new FreeResponseQuestion("Who wrote the Odyssey?", "Homer"),
                new FreeResponseQuestion("Who won the World Cup of Football in 2006?", "Italy"),
                new FreeResponseQuestion("Who painted The Last Supper?", "Leonardo da Vinci"),
                new MultipleChoiceQuestion("Which are programming languages?", new String[]{"Java", "Python", "C++"}),
                new MultipleChoiceQuestion("Which are fruits?", new String[]{"Apple", "Banana", "Orange"}),
                new MultipleChoiceQuestion("Which are mammals?", new String[]{"Dog", "Cat", "Elephant"}),
                new MultipleChoiceQuestion("Which are colors?", new String[]{"Red", "Blue", "Green"}),
                new MultipleChoiceQuestion("Which are planets?", new String[]{"Earth", "Mars", "Venus"})
        );
    }
}

