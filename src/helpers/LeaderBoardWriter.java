package helpers;

import utils.Constants;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderBoardWriter {

    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION))) {
            String[] allScores = (String[]) in.readObject();

            for (int i = 0; i < allScores.length; i++) {
                if (i >= 10) {
                    break;
                }
                if (allScores[i] == null) {
                    continue;
                }

                String[] scoreLineArr = allScores[i].split(":");
                String userName = scoreLineArr[0];
                Long result = Long.parseLong(scoreLineArr[1]);
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

            int i = 0;

            String[] scoresToWrite = new String[10];

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
