package helpers;

import utils.Constants;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardWriter {

    private static final int LEADERBOARD_NAMES_COUNT = 10;
    private static final int USERNAME_INDEX = 0;
    private static final int RESULT_INDEX = 1;
    private static final int START_INDEX = 0;

    public void writeInLeaderboard(String name, long score) throws IOException {
        Map<String, Long> scores = new TreeMap<>();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION))) {
            String[] allScores = (String[]) in.readObject();

            for (int i = 0; i < allScores.length; i++) {
                if (i >= LEADERBOARD_NAMES_COUNT) {
                    break;
                }
                if (allScores[i] == null) {
                    continue;
                }

                String[] scoreLineArr = allScores[i].split(":");
                String userName = scoreLineArr[USERNAME_INDEX];
                Long result = Long.parseLong(scoreLineArr[RESULT_INDEX]);
                scores.put(userName, result);
            }
            scores.put(name, score);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Map.Entry<String, Long>> sortedScores = scores.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue).reversed())
                .collect(Collectors.toCollection(ArrayList::new));

        try (ObjectOutputStream out = new ObjectOutputStream(
                (new FileOutputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION)))) {
            Iterator it = sortedScores.iterator();

            int i = START_INDEX;

            String[] scoresToWrite = new String[LEADERBOARD_NAMES_COUNT];

            while (it.hasNext() && i < scoresToWrite.length) {
                Map.Entry pair = (Map.Entry) it.next();
                scoresToWrite[i] = pair.getKey() + ":" + pair.getValue();
                i++;
            }

            out.writeObject(scoresToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}