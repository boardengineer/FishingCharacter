package theFishing.quest.quests;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theFishing.actions.AbandonQuestAction;
import theFishing.quest.QuestHelper;
import theFishing.util.TexLoader;

import static theFishing.FishingMod.makeImagePath;
import static theFishing.util.Wiz.atb;

public abstract class AbstractQuest {
    public int progress;
    public int goal;
    public boolean removeOnComplete = true;

    private static Texture def = TexLoader.getTexture(makeImagePath("quests/default.png"));

    public Texture progressTex() {
        return def;
    }

    public abstract String getName();

    public abstract String getDescription();

    public abstract void grantReward();

    public AbstractQuest(int goal) {
        this.progress = 0;
        this.goal = goal;
    }

    public void increment() {
        if (progress < goal) {
            progress++;
            if (progress == goal) {
                grantReward();
                if (removeOnComplete)
                    atb(new AbandonQuestAction(this));
                else
                    atb(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            progress = 0;
                        }
                    });
                for (AbstractQuest q : QuestHelper.quests) {
                    if (q != this) {
                        q.onCompleteOtherQuest(this);
                    }
                }
            }
        }
    }

    public void onCardPlayed(AbstractCard card) {

    }

    public void onCardDrawn(AbstractCard card) {

    }

    public void atEndOfTurn() {

    }

    public void onKillEnemy() {

    }

    public void onCompleteOtherQuest(AbstractQuest quest) {

    }

    public void onExplore() {

    }
}
