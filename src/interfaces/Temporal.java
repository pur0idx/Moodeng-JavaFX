package interfaces;

public interface Temporal {
    double getDuration();
    boolean isExpired();
    void startTimer();
    void stopTimer();
}