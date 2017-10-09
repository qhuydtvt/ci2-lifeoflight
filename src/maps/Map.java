package maps;

import bases.events.EventManager;

import java.util.ArrayList;

/**
 * Created by huynq on 10/9/17.
 */
public class Map {
    ArrayList<MapRow> rows;
    int width;
    int height;

    Player player;

    public Map(String mapContent) {
        mapContent = mapContent.replace("\r", "");
        String[] lines = mapContent.split("\n");

        rows = new ArrayList<>();
        for(String line : lines) {
            MapRow row = new MapRow(line);
            rows.add(row);
        }

        height = lines.length;
        width = lines[0].length();

        int playerX = 0;
        int playerY = 0;

        for (int y = 0; y < rows.size(); y++) {
            MapRow row = rows.get(y);
            playerX = row.findPlayerX();
            if (playerX != -1) {
                playerY = y;
                break;
            }
        }

        player = new Player(playerX, playerY);
    }

    public void pushUI () {
        for(int y = 0; y < height; y++) {
            MapRow row = rows.get(y);

            for(int x = 0; x < width; x ++) {

                if (player.match(x, y)) {
                    EventManager.pushUIMessage(" @ ");
                }
                else {
                    String cell = row.cells[x];
                    if (!cell.equalsIgnoreCase("@"))
                        EventManager.pushUIMessage(String.format(" %s ", cell));
                }
            }

            EventManager.pushUIMessageNewLine("");

        }
    }

    @Override
    public String toString() {
        return "Map{" +
                "rows=" + rows +
                '}';
    }
}
