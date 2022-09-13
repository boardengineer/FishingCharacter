package theFishing.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.actions.FullHouseAction;

import java.util.ArrayList;
import java.util.Collections;

import static theFishing.FishingMod.makeID;

public class FullHouse extends AbstractFishingCard {
    public final static String ID = makeID("FullHouse");
    // intellij stuff power, self, rare, , , , , , 

    public FullHouse() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    @Override
    protected Texture getPortraitImage() {
        if (upgraded) {
            return ImageMaster.loadImage("fishingResources/images/cards/FourOfAKind_p.png");
        }
        return super.getPortraitImage();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> sortedList = new ArrayList<>(p.drawPile.group);
        Collections.shuffle(sortedList, MathUtils.random);
        addToBot(new FullHouseAction(sortedList, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            ++timesUpgraded;
            upgraded = true;
            name = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeTitle();
            upp();
            loadCardImage("fishingResources/images/cards/FourOfAKind.png");
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}