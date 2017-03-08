package managers;

import entities.Constants;
import entities.enemies.bosses.Boss;
import entities.enemies.bosses.Pedobear;
import entities.level.Level;
import javafx.scene.canvas.Canvas;

public class BossManager extends EnemyManager {
    public BossManager(PlayerManager playerManager, FuelManager fuelManager) {
        super(playerManager, fuelManager);
    }

    public Boss initializeBoss(Canvas canvas) {
        Boss pedobear = new Pedobear(canvas, Constants.PEDOBEAR_SPEED);
        pedobear.setPosition(1095, 360.0);

        return pedobear;
    }

    public void manageBoss(Level level) {
        for (Boss boss : level.getBosses()) {
            if (boss.getHealth() != 0) {
                boss.render(level.getGc());
                boss.move();
                updateBossLocation(boss);
            } else {
                //TODO: delete boss
            }
        }
    }

    private void updateBossLocation(Boss boss) {
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}