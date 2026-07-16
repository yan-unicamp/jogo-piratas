package sistema;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Ponto de entrada para rodar o jogo no Desktop (Windows/Mac/Linux).
 * Configura a janela e inicia o JogoPiratas.
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Jogo Piratas");
        config.setWindowedMode(960, 540);
        config.setResizable(true);
        config.setForegroundFPS(0);
        config.useVsync(false); // Desliga a trava do monitor

        new Lwjgl3Application(new JogoPiratas(), config);
    }
}
