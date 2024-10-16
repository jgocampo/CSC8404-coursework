package statistics;

import student.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * The Statistics class tracks the quiz performance of a student, including their scores,
 * number of attempts, and final verdict. It handles both regular quizzes and revision quizzes,
 * ensuring that the student's progress is recorded and monitored.
 */

public final class Statistics {
    private final Student student;
    private final List<Double> regularQuizScores;
    private final List<Double> revisionQuizScores;
    private int regularAttempts;
    private int revisionAttempts;
    private String verdict;

    /**
     * Constructor for the statistics of the given student. Initially, no quizzes have
     * been taken, and the default final verdict is To Be Determined (TBD).
     *
     * @param student The student whose quiz statistics are tracked.
     */
    public Statistics(Student student) {
        this.student = student;
        this.regularQuizScores = new ArrayList<>();
        this.revisionQuizScores = new ArrayList<>();
        this.verdict = "TBD";  // TBD until a final decision
    }

    public Student getStudent() {
        return student;
    }

    public String getVerdict() {
        return verdict;
    }

    public int getRevisionAttempts(){
        return revisionAttempts;
    }

    public int getRegularAttempts(){
        return regularAttempts;
    }


    /**
     * Records the score of a regular quiz attempt and updates the final verdict accordingly.
     * If the student scores 50% or higher, they pass. If they fail two regular quizzes,
     * the final verdict is FAIL.
     *
     * @param score The score the student achieved on the quiz.
     */

    public void recordRegularQuizScore(double score) {
        if ("PASS".equals(verdict) || "FAIL".equals(verdict)) {
            return;  // No more attempts allowed if a verdict has been reached
        }

        regularQuizScores.add(score);
        regularAttempts++;

        if (score >= 0.5) {
            verdict = "PASS";
        } else if (regularAttempts >= 2) {
            verdict = "FAIL";
        }
    }

    /**
     * Records the score of a revision quiz attempt. Revision quizzes do not affect
     * the student's final verdict, but they are tracked to help improve performance.
     *
     * @param score The score the student achieved on the revision quiz.
     */

    public void recordRevisionQuizScore(double score) {
        if (revisionAttempts >= 2 || "PASS".equals(verdict) || "FAIL".equals(verdict)) {
            return;  // No more revision attempts if limit reached or verdict given
        }

        revisionQuizScores.add(score);
        revisionAttempts++;
    }


    /**
     * Generates a report summarizing the student quiz performance, including the
     * number of attempts and their scores for both regular and revision quizzes.
     *
     * @return A formatted string containing the student's quiz statistics.
     */
    public String generateStatistics() {
        StringBuilder report = new StringBuilder();
        report.append("Statistics for student: ").append(student.getName()).append("\n");
        report.append("Final verdict: ").append(verdict).append("\n");
        report.append("Number of regular quiz attempts: ").append(regularAttempts).append("\n");
        report.append("Number of revision quiz attempts: ").append(revisionAttempts).append("\n");
        report.append("Regular quiz scores: ").append(regularQuizScores).append("\n");
        report.append("Revision quiz scores: ").append(revisionQuizScores).append("\n");
        return report.toString();
    }

    /**
     * Method to check if the student is eligible to take a regular quiz. A student can only
     * take regular quizzes if their final verdict is still "TBD".
     *
     * @return True if the student can take a regular quiz, false otherwise.
     */

    public boolean canTakeRegularQuiz() {
        return "TBD".equals(verdict);
    }

    /**
     * Checks if the student is eligible to take a revision quiz. Students are allowed
     * up to two revision attempts, and they cannot take more if a final verdict has been given.
     *
     * @return True if the student can take a revision quiz, false otherwise.
     */

    public boolean canTakeRevisionQuiz() {
        return "TBD".equals(verdict) && revisionAttempts < 2;
    }

}

