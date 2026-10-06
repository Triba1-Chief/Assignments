//No collaborations
import java.util.*;

public class A1_Q3 {

    public static ArrayList<String> Discussion_Board(String[] posts) {
        Map<String, Integer> trackWord = new HashMap<String, Integer>(); //words and number of times said
        Map<String, ArrayList<String>> trackPerson = new HashMap<String, ArrayList<String>>(); //User and words posted
        List<String> mostUsed;


        for (String post : posts) {
            String[] words = post.split(" ");

            if (words.length == 1 || !trackPerson.containsKey(words[0])) { //Keeps track of unique user
                trackPerson.put(words[0], new ArrayList<>());
            }

            for (int i = 1; i < words.length; i++) {
                if (trackWord.containsKey(words[i])) {//increment count for each word
                    trackWord.put(words[i], trackWord.get(words[i]) + 1);
                }
                else {//Place the new word for a start
                    trackWord.put(words[i], 1);
                }

                trackPerson.get(words[0]).add(words[i]); //Adds the word as part of the user records
            }
        }

        //Generating the list
        mostUsed = trackWord.keySet().stream()
				.filter(word -> trackPerson.values().stream().allMatch(words -> words.contains(word))) // Used by all users
                .sorted(Comparator
                        .comparingInt((String word) -> trackWord.get(word)) // Sort by frequency
                        .reversed() // Higher frequency first
                        .thenComparing(Comparator.naturalOrder()) // Lexicographic order if same frequency
                )
                .toList(); // Convert stream to ArrayList

        return new ArrayList<>(mostUsed);
    }
}