package theFishing.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.FishingMod;
import theFishing.TheFishing;

import static theFishing.FishingMod.makeImagePath;
import static theFishing.FishingMod.modID;
import static theFishing.util.Wiz.*;
import static theFishing.util.Wiz.adp;

public abstract class AbstractFishingCard extends CustomCard {

    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public AbstractFishingCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, TheFishing.Enums.FISHING_COLOR);
    }

    public AbstractFishingCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString;

        switch (cardType) {
            case ATTACK:
            case POWER:
            case SKILL:
            default:
                textureString = makeImagePath("cards/" + cardName + ".png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void uDesc() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public abstract void upp();

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected boolean isVoyaged() {
        return FishingMod.isThisVoyaged(this);
    }

    protected boolean isSolo() {
        return (adp().hand.size()  == 0 || (adp().hand.size() == 1 && adp().hand.contains(this)));
    }
}
