package theFishing.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.effects.ColoredBeamEffect;
import theFishing.util.Wiz;

import java.util.ArrayList;

import static theFishing.FishingMod.makeID;
import static theFishing.util.Wiz.atb;

public class FutureProofing extends AbstractFishingCard {
    public final static String ID = makeID("FutureProofing");
    // intellij stuff attack, enemy, common, 7, 2, , , , 1

    public FutureProofing() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 1;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_DEFECT_BEAM"));
        atb(new VFXAction(new ColoredBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.flipHorizontal, Color.RED.cpy(), Color.FIREBRICK.cpy()), 0.4F));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (int i = 0; i < magicNumber; i++) {
                    ArrayList<AbstractCard> possCards = new ArrayList<>();
                    for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
                        if (q.canUpgrade()) {
                            possCards.add(q);
                        }
                    }
                    if (!possCards.isEmpty())
                        Wiz.getRandomItem(possCards, AbstractDungeon.cardRandomRng).upgrade();
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
        uDesc();
    }
}