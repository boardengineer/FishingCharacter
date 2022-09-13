package theFishing.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import theFishing.cards.fish.AbstractFishCard;

import static theFishing.FishingMod.makeID;
import static theFishing.util.Wiz.atb;
import static theFishing.util.Wiz.makeInHand;

public class TreasureChest extends AbstractFishingCard implements StartupCard {
    public final static String ID = makeID("TreasureChest");
    // intellij stuff skill, self, uncommon, , , , , 20, 10

    public TreasureChest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(AbstractFishCard.returnRandomFish());
    }

    public void upp() {
        upgradeMagicNumber(4);
    }

    @Override
    public boolean atBattleStartPreDraw() {
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.magicNumber, true));
        atb(new GainGoldAction(this.magicNumber));
        return true;
    }
}