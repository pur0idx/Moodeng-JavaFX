package interfaces;

import character.Moodeng;

public interface Collectible {
    void onCollect(Moodeng moodeng);
    int getScoreValue();
    void playCollectSound();
}