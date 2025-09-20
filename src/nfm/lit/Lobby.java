package nfm.lit;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Lobby {
    private xtGraphics xtgraphics;
    public List<String> lobbyPlayerIds = new ArrayList<>();
    private Graphics2D rd;
    private CheckPoints checkpoints;

    public Lobby(xtGraphics xtgraphics, Graphics2D rd, CheckPoints checkpoints) {
        this.xtgraphics = xtgraphics;
        this.rd = rd;
        this.checkpoints = checkpoints;
    }


    public void lobby(Control control, ContO car[]) {
        rd.setColor(new Color(30, 30, 30, 180));
        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);

        rd.setFont(new Font("SansSerif", Font.BOLD, 24));
        rd.setColor(Color.YELLOW);
        xtgraphics.drawcs(60, "Lobby - Connected Players", 255, 255, 0, 3);

        rd.setFont(new Font("SansSerif", Font.PLAIN, 18));
        rd.setColor(Color.WHITE);
        int y = 120;
        for (String pid : lobbyPlayerIds) {
            xtgraphics.drawcs(y, "Player: " + pid, 255, 255, 255, 3);
            y += 30;
        }

        if (control.enter) {
            checkpoints.stage = 1;
            xtgraphics.serverWriter.println("START");
        }
    }
}
