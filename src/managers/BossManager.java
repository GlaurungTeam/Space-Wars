package managers;

import entities.enemies.bosses.Boss;
import entities.enemies.bosses.Pedobear;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class BossManager extends EnemyManager {
    private Boss boss;

    public BossManager(PlayerManager playerManager, FuelManager fuelManager, Canvas canvas) {
        super(playerManager, fuelManager);
        this.setBoss(this.initializeBoss(canvas));
    }

    public Boss getBoss() {
        return boss;
    }

    private void setBoss(Boss boss) {
        this.boss = boss;
    }

    public Boss initializeBoss(Canvas canvas) {
        Boss pedobear = new Pedobear(canvas, 3);
        pedobear.setPosition(1095, 360.0);

        return pedobear;
    }

    public void manageBoss(Level level, AnimationTimer timer) {
        Boss boss = this.getBoss();
        if(boss.getHealth() != 0){
            boss.render(level.getGc());
            boss.move();
            updateBossLocation(boss);
        }else{
           //TODO: delete boss
        }
    }

    private void updateBossLocation(Boss boss){
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}
