package statistics;

import student.Student;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Statistics {
    private final Student student;
    private final List<Double> regularQuizScores;
    private final List<Double> revisionQuizScores;
    private final Set<String> seenQuestions;  // Track all questions seen in any quiz
    private int regularAttempts;
    private int revisionAttempts;
    private String verdict;

    // Constructor
    public Statistics(Student student) {
        this.student = student;
        this.regularQuizScores = new ArrayList<>();
        this.revisionQuizScores = new ArrayList<>();
        this.seenQuestions = new HashSet<>();  // Keep track of all questions seen
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

    // Record score for regular quiz and update the final verdict
    public void recordRegularQuizScore(double score) {
        if ("PASS".equals(verdict) || "FAIL".equals(verdict)) {
            return;  // No more attempts allowed if a verdict has been reached
        }

        regularQuizScores.add(score);
        regularAttempts++;

        if (score >= 0.5) {
            verdict = "PASS";
        } else if (regularAttempts >= 2) {
            verdict = "FAIL";  // Fail after two regular quiz failures
        }
    }

    // Record score for revision quiz but do not affect the final verdict
    public void recordRevisionQuizScore(double score) {
        if (revisionAttempts >= 2 || "PASS".equals(verdict) || "FAIL".equals(verdict)) {
            return;  // No more revision attempts if limit reached or verdict given
        }

        revisionQuizScores.add(score);
        revisionAttempts++;
    }

    public boolean hasSeenQuestion(String questionFormulation) {
        return seenQuestions.contains(questionFormulation);
    }

    public void recordSeenQuestion(String questionFormulation) {
        seenQuestions.add(questionFormulation);
    }


    // Generate a report of the student statistics
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

    public boolean canTakeRegularQuiz() {
        return "TBD".equals(verdict);  // Only if no final verdict
    }

    public boolean canTakeRevisionQuiz() {
        return "TBD".equals(verdict) && revisionAttempts < 2;  // Max 2 revision attempts
    }


}

