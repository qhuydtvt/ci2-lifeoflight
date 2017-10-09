import bases.Utils;
import bases.events.EventManager;
import bases.inputs.CommandListener;
import bases.inputs.InputManager;
import bases.uis.BaseWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import events.Answer;
import events.Story;
import maps.Map;

import java.util.HashMap;
import java.util.List;

/**
 * Created by huynq on 7/28/17.
 */
public class GameWindow extends BaseWindow {

    HashMap<String, Story> storyMap;
    Story currentStory;
    int currentArc = 0;
    int currentMapIndex = 0;
    Map map;

    @Override
    protected void startup() {
        //1. Load arc
        loadArc(0);

        //2. Register command
        InputManager.instance.addCommandListener(new CommandListener() {
            @Override
            public void onCommandFinished(String command) {
                //0. Pushcommand
                pushCommand(command);
                //1. Check command type
                if (currentStory.isType("input")) {
                    Answer  answer = currentStory.checkAnswer(command);
                    if (answer != null) {
                        Story newStory = storyMap.get(answer.to);

                        //2. Change story if neccessary
                        if (newStory != null) {
                            changeStory(newStory);
                        }
                    }
                }
                else if(currentStory.isType("timeout")) {
                    if (command.equalsIgnoreCase("next"))
                    {
                        Story newStory = storyMap.get(currentStory.time.to);
                        if (newStory != null) {
                            changeStory(newStory);
                        }
                    }
                }
                else if (currentStory.isType("NextArc")) {
                    EventManager.pushClearUI();
                    currentArc++;
                    loadArc(currentArc);
                }
                else if(currentStory.isType("map")) {
                    if (map == null)
                        loadMap(0);
                    map.pushUI();
                }
            }

            @Override
            public void commandChanged(String command) {

            }
        });
    }

    private void loadMap(int mapIndex) {
        String url  = String.format("assets/maps/map_lvl0.txt", mapIndex);
        String content = Utils.loadFileContent(url);
        map = new Map(content);
        System.out.println(map);
    }

    private void pushCommand(String command) {
        EventManager.pushUIMessageNewLine("");
        EventManager.pushUIMessageNewLine(String.format(";#00FF00%s;", command));
        EventManager.pushUIMessageNewLine("");
    }

    private void loadArc(int arcNo) {
        //1 . Re-create map
        storyMap = new HashMap<>();

        //2. Load and parse JSON
        String arcContent = Utils.loadFileContent(String.format("assets/events/event_arc_%s.json", arcNo));
        TypeToken<List<Story>> typeToken = new TypeToken<List<Story>>(){};
        List<Story> stories = new Gson().fromJson(arcContent, typeToken.getType());

        //3 . Dump data into map

        for (Story story: stories) {
            if (storyMap.containsKey(story.ID)) {
                System.out.println(String.format("Duplicate ID %s", story.ID));
            } else {
                storyMap.put(story.ID, story);
            }
        }

        //4. Load first story
        changeStory(stories.get(0));

    }

    private void changeStory(Story story) {
        currentStory = story;
        EventManager.pushUIMessageNewLine(currentStory.message);
        if (currentStory.isType("timeout")) {
            EventManager.pushUIMessageNewLine("Gõ ;#FF0000next; để tiếp tục");
        }
        else if(currentStory.isType("nextarc")) {
            EventManager.pushUIMessageNewLine("Chúc mừng bạn đã kết thúc chuyển phiêu lưu, gõ lệnh bất kỳ để bắt đầu một cuộc phiêu lưu mới");
        }

    }
}
