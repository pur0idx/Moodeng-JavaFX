package interfaces;

import character.Moodeng;

public interface PowerUpEffect {
    void applyEffect(Moodeng moodeng);
    void removeEffect(Moodeng moodeng);
    String getEffectName();
}