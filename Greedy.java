
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*
Enunt

2. Sandel este cantaret. El cunoaste n melodii, despre acestea stim
numarul de minute si secunde. Toate aceste informatii sunt
generate aleator in fisierul date.in. Sandel poate canta in cadrul

unui festival timp de s secunde. Realizati un algoritm care sa il
ajute pe Sandel sa cante cat mai multe melodii.

https://drive.google.com/file/d/19JBLM6UnFgDURpg17tMb7gmCs-Iak8xG/view?usp=sharing
*/

public class Greedy {

    public static void main(String[] args) {

        (new Greedy()).problema3CuGigel();
    }


    public void problema3CuGigel() {
        //De aici
        File f = new File("date.in");
        int N = 0;
        int S = 0;
        ArrayList<Song> songs = null;
        try {
            genereazaDateProblema(f);

        } catch (IOException e) {
            System.out.println("Problema la generarea datelor:" + e.getLocalizedMessage());
            System.exit(0);
        }

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            S = Integer.parseInt(line.trim());

            line = br.readLine();
            N = Integer.parseInt(line.trim());
            songs = new ArrayList<>(N);

            int minutes = 0;
            int seconds = 0;
            String name = "";
            String[] tmp = null;
            for (int i = 0; i < N; i++) {
                line = br.readLine();
                tmp = line.split(":");
                minutes = Integer.parseInt(tmp[0]);
                seconds = Integer.parseInt(tmp[1]);
                name = "Song" + String.valueOf(i + 1);
                songs.add(new Song(name, minutes, seconds));
            }

            fr.close();
            br.close();
            f = null;

        } catch (IOException e) {
            System.out.println("Problema la citirea si centralizarea datelor:" + e.getLocalizedMessage());
            System.exit(0);
        }
        //Si pana aici avem O(N)

        //De aici
        songs.sort((song1,song2)->{
            return song1.getDurationInSeconds() - song2.getDurationInSeconds();
        });
        //Si pana aici avem O(N*Log(N))

        //De aici
        ArrayList<Song> sung_songs = new ArrayList<>();
        Song chopped_song = null;
        double chopped_ratio = 0;
        for (Song song : songs) {
            int duration = song.getDurationInSeconds();
            if (S - duration > 0) {
                S = S - duration;
                sung_songs.add(song);
            } else if (S > 0) {
                chopped_ratio = 1.0 * S / duration;
                chopped_song = song;
                S = 0;
                break;
            }
        }
        String extra = "";
        if (chopped_ratio > 0) {
            extra = " and " + String.format("%.2f", chopped_ratio * 100) + "% out of the " + chopped_song.getName();
        }
        System.out.println("Gigel sang " + sung_songs.size() + " songs" + extra);
        //Pana aici avem O(N)

        //De aici 
        sung_songs.forEach(song -> {
            System.out.println("Gigel sang " + song.getName() + " (" + song.getDurationInSeconds() + " seconds)");
        });
        //Pana aici avem O(N)

        //O(N)+O(N*Log(N))+O(N)+O(N) = O(N*Log(N))
    }

  /*  private Song[] sortSongsByDuration(Song[] songs) {
        HashMap<Integer, ArrayList<Song>> map = new HashMap<>();
        int[] durations = new int[songs.length];

        for (int i = 0; i < songs.length; i++) {
            durations[i] = songs[i].getDurationInSeconds();
            ArrayList<Song> songs_with_same_length = map.getOrDefault(durations[i], new ArrayList<>());
            songs_with_same_length.add(songs[i]);
            map.put(durations[i], songs_with_same_length);
        }

        Arrays.sort(durations);
        Song[] sorted_songs = new Song[songs.length];

        for (int i = 0; i < songs.length; i++) {
            ArrayList<Song> songs_with_the_same_length = map.get(durations[i]);
            //adauga in noul array toate cantecele care au lungimea in cauza
            for (int j = 0; j < songs_with_the_same_length.size(); j++) {
                Song sng = songs_with_the_same_length.get(j);
                sorted_songs[i + j] = sng;
            }
            //actualizare index la urmatoarea pozitie libera
            i = i + songs_with_the_same_length.size() - 1;
        }

        return sorted_songs;
    }*/

    private void genereazaDateProblema(File f) throws IOException {
        if (f.exists())
            f.delete();

        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        Random randomTool = new Random();
        int S = randomTool.nextInt(1500) + 1;
        int N = randomTool.nextInt(100) + 1;
        bw.write(String.valueOf(S));
        bw.newLine();
        bw.write(String.valueOf(N));
        bw.newLine();
        for (int i = 0; i < N; i++) {
            int minute = randomTool.nextInt(7) + 1;
            int secunde = randomTool.nextInt(60);
            String secunde_formatate = (secunde<10?"0":"") + String.valueOf(secunde);
            String line = String.valueOf(minute) + ":" + secunde_formatate;
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        fw.close();
    }

  private  class Song {
        private int minutes;
        private int seconds;
        private String name;

        Song(String name, int minutes, int seconds) {
            this.name = name;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public String getName() {
            return this.name;
        }

        public int getDurationInSeconds() {
            return minutes * 60 + seconds;
        }

    }
}

/*
----INPUT (date.in)----
962
76
7:12
3:27
1:2
7:18
4:19
5:50
6:31
5:2
2:47
4:44
3:54
5:35
5:41
3:44
1:44
5:30
1:36
7:11
4:28
6:35
2:40
7:45
5:33
3:8
6:39
3:21
4:40
7:46
3:34
4:51
7:28
5:9
4:9
5:3
3:51
6:51
2:24
1:48
2:11
7:30
2:6
5:52
5:44
2:2
7:54
3:0
2:10
4:42
6:8
2:6
6:21
3:4
3:58
2:48
6:3
3:43
1:53
6:40
6:53
6:18
4:11
3:16
1:4
3:20
1:4
1:1
3:35
3:7
3:39
5:45
7:7
6:19
5:56
5:54
5:49
5:14

----OUTPUT-----
Gigel sang 10 songs and 33.33% out of the Song50
Gigel sang Song66 (61 seconds)
Gigel sang Song3 (62 seconds)
Gigel sang Song63 (64 seconds)
Gigel sang Song65 (64 seconds)
Gigel sang Song17 (96 seconds)
Gigel sang Song15 (104 seconds)
Gigel sang Song38 (108 seconds)
Gigel sang Song57 (113 seconds)
Gigel sang Song44 (122 seconds)
Gigel sang Song41 (126 seconds)
 */
