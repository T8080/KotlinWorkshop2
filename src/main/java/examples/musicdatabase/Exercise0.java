package examples.musicdatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import static examples.musicdatabase.Exercise0.Field.*;

/**
 *
 */
public class Exercise0 {
    public static final int MAX_TRACKS = 5000;

    public static void main(String[] args) {
        Track[] tracks = new Track[0];
        try {
            tracks = readDatabase(new Track[MAX_TRACKS]);
        } catch (IOException e) {
            print("unable to load database, please check your files");
        }
        readAndExecuteCommands(tracks);
    }

    /**
     * runs the actual program
     *
     * @param tracks
     */
    private static void readAndExecuteCommands(Track[] tracks) {
        assert tracks != null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            printHelp();
            String[] input = scanner.nextLine().split(" ", 2);
            String argument = "";
            if (input.length > 1) argument = input[1];
            running = executeCommand(input[0], argument, tracks);
        }
    }

    /**
     * prints all available commands
     */
    static void printHelp() {
        assert true;
        print("please enter a command");
        print("available commands: ");
        print("artist <filter>, artist? <cd>, cds <artist>, cd? <title>, " +
                "tag <tag>, track <title>, #cds, tracks <cd>, time, stop");
    }

    /**
     * executes a command
     *
     * @param command  command string
     * @param argument argument for the command, can be empty
     * @param tracks   list of tracks
     * @return false if the program should stop
     */
    static boolean executeCommand(String command, String argument, Track[] tracks) {
        assert command != null && tracks != null : "all arguments must be initialized";
        switch (command) {
            case "artist":
                printTrackDetails(new Field[]{ARTIST},
                        distinctBy(new Field[]{ARTIST},
                                filtered(ARTIST, argument, tracks)));
                return true;
            case "artist?":
                printTrackDetails(new Field[]{ARTIST, CD},
                        distinctBy(new Field[]{ARTIST},
                                filtered(CD, argument, tracks)));
                return true;
            case "cds":
                printTrackDetails(new Field[]{ARTIST, CD, YEAR},
                        distinctBy(new Field[]{CD},
                                filtered(ARTIST, argument, tracks)));
                return true;
            case "cd?":
                printTrackDetails(new Field[]{ARTIST, CD, YEAR},
                        distinctBy(new Field[]{CD},
                                filtered(TITLE, argument, tracks)));
                return true;
            case "tag":
                printTrackDetails(new Field[]{ARTIST, CD, YEAR, TRACK, TIME, TAGS},
                        filtered(TAGS, argument, tracks));
                return true;
            case "track":
                printTrackDetails(Field.values(),
                        filtered(TITLE, argument, tracks));
                return true;
            case "#cds":
                print("total cds: " + distinctBy(new Field[]{CD, ARTIST}, tracks).length);
                return true;
            case "tracks":
                tracks(tracks, argument);
                return true;
            case "time":
                print("total time: " + formatLength(aggregatedLength(tracks)));
                return true;
            case "stop":
                return false;
            default:
                print("unrecognized command");
                return true;
        }
    }

    /**
     * finds all cds with a matching cd title and prints their tracks
     *
     * @param tracks tracks database
     * @param cd     a string filter
     */
    public static void tracks(Track[] tracks, String cd) {
        assert tracks != null && cd != null : "all arguments must be initialized";

        Track[] cds = filtered(Field.CD, cd, distinctBy(new Field[]{CD, ARTIST}, tracks));
        for (int i = 0; i < cds.length; i++) {
            Track track = cds[i];
            print(track.cd + " by " + track.artist + ":" + track.title);
            Track[] tracksInCd = filtered(Field.ARTIST, track.artist, filtered(Field.CD, track.cd, tracks));
            for (int j = 0; j < tracksInCd.length; j++) {
                Track t = tracksInCd[j];
                print("  " + t.track + ": " + t.title + ", " + t.tags + ", " + t.country + ", " + formatLength(t.time));
            }
        }
        print(cds.length + " cds found.");
    }

    /**
     * returns a sublist of tracks in which each track's specified Field contains the Filter string.
     *
     * @param field  the field of a track to filter with
     * @param filter the substring the field should contain
     * @param tracks the total list of tracks
     * @return a sublist of tracks
     */
    public static Track[] filtered(Field field, String filter, Track[] tracks) {
        assert field != null && filter != null && tracks != null : "all arguments must be initialized";
        Track[] filtered = new Track[tracks.length];
        int filteredIndex = 0;
        for (int i = 0; i < tracks.length; i++) {
            if (getTrackField(field, tracks[i]).toLowerCase().contains(filter.toLowerCase())) {
                filtered[filteredIndex] = tracks[i];
                filteredIndex += 1;
            }
        }
        return trimTracks(filtered, filteredIndex);
    }

    /**
     * returns a sublist of tracks in which each occurrence of the specified Fields only occurs once.
     *
     * @param distinctFields the list of fields which in combination should be unique
     * @param tracks         list of tracks
     * @return sublist of tracks
     */
    public static Track[] distinctBy(Field[] distinctFields, Track[] tracks) {
        assert distinctFields != null && tracks != null : "all arguments must be initialized";
        String[] keys = new String[tracks.length];
        Track[] distinctTracks = new Track[tracks.length];
        int fillIndex = 0;
        for (int i = 0; i < tracks.length; i++) {
            String key = "";
            for (int j = 0; j < distinctFields.length; j++) {
                key += getTrackField(distinctFields[j], tracks[i]);
            }
            if (!contains(keys, key)) {
                keys[fillIndex] = key;
                distinctTracks[fillIndex] = tracks[i];
                fillIndex += 1;
            }
        }
        return trimTracks(distinctTracks, fillIndex);
    }

    /**
     * prints the specified Fields for every track in Tracks and prints the length of Track
     *
     * @param fields list of fields to print
     * @param tracks list of tracks
     * @return tracks
     */
    public static int printTrackDetails(Field[] fields, Track[] tracks) {
        assert fields != null && tracks != null : "all arguments must be initialized";
        for (int i = 0; i < tracks.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                System.out.print(getTrackField(fields[j], tracks[i]) + " ");
            }
            print("");
        }
        print(tracks.length + " search results found.");
        return tracks.length;
    }

    /**
     * check if a string value exisists in a string array
     *
     * @param array array
     * @param value value
     * @return value in array
     */
    public static boolean contains(String[] array, String value) {
        assert array != null && value != null : "all arguments must be initialized";
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the aggregated Length of all length objects in Tracks
     *
     * @param tracks tracks
     * @return aggregated Length
     */
    public static Length aggregatedLength(Track[] tracks) {
        assert tracks != null : "all arguments must be initialized";
        Length length = new Length();
        for (int i = 0; i < tracks.length; i++) {
            length = lengthSum(length, tracks[i].time);
        }
        return length;
    }

    enum Field {ARTIST, CD, YEAR, TRACK, TITLE, TAGS, TIME, COUNTRY}

    /**
     * selects the Field from track
     *
     * @param field Field object corresponding with the field from track
     * @param track track
     * @return the specified field as a string
     */
    public static String getTrackField(Field field, Track track) {

        switch (field) {
            case ARTIST:
                return track.artist;
            case CD:
                return track.cd;
            case YEAR:
                return Integer.toString(track.year);
            case TRACK:
                return Integer.toString(track.track);
            case TITLE:
                return track.title;
            case TAGS:
                return track.tags;
            case TIME:
                return formatLength(track.time);
            case COUNTRY:
                return track.country;
        }
        throw new IllegalArgumentException("the Java compiler is too dumb to realize this code can never be reached");
    }

    /**
     * shortcut for System.out.println()
     */
    public static void print(String str) {
        System.out.println(str);
    }

    /**
     * retuns a a sublist of Tracks with indexes 0 till trackcount
     *
     * @param tracks     tracks list
     * @param trackCount upperbound of tracks to copy
     * @return sublist of tracks
     */
    public static Track[] trimTracks(Track[] tracks, int trackCount) {
        assert tracks != null : "arguments must be initialized";
        assert trackCount >= 0 : "trackCount cannot be smaller then zero";
        Track[] newTracks = new Track[trackCount];
        for (int i = 0; i < newTracks.length; i++) {
            newTracks[i] = tracks[i];
        }
        return newTracks;
    }

    /**
     * Calculate the sum of the specified length arguments
     *
     * @param a length
     * @param b another length
     * @return sum
     */
    public static Length lengthSum(Length a, Length b) {
        assert a != null && b != null : "arguments cannot be null";
        Length sum = new Length();
        int seconds = a.seconds + b.seconds;
        sum.seconds = seconds % 60;
        sum.minutes = a.minutes + b.minutes + seconds / 60;
        return sum;
    }

    /**
     * Converts the given string in the format m..m:ss to a Length object
     *
     * @param string
     * @return corresponding length object
     */
    static Length parseLength(String string) {
        assert string != null : "arguments cannot be null";
        String[] parts = string.split(":", 2);
        Length length = new Length();
        length.minutes = Integer.parseInt(parts[0]);
        length.seconds = Integer.parseInt(parts[1]);
        return length;
    }

    /**
     * Converts a given length object into a string in the format m..m:ss
     *
     * @param length
     * @return string representation
     */
    public static String formatLength(Length length) {
        assert length != null : "arguments cannot be null";
        return length.minutes + ":" + length.seconds;
    }

    /**
     * Reads the cd database from the file 'Nummers.txt' into the specified track array
     *
     * @param database
     * @return an array of tracks
     */
    public static Track[] readDatabase(Track[] database) throws IOException {
        assert database != null : "arguments cannot be null";
        Scanner scanner = new Scanner(new FileInputStream("songs.txt"));
        int i = 0;

        while (scanner.hasNextLine()) {
            Track track = new Track();
            track.artist = scanner.nextLine();
            track.cd = scanner.nextLine();
            track.year = scanner.nextInt();
            track.track = scanner.nextInt();
            scanner.nextLine();
            track.title = scanner.nextLine();
            track.tags = scanner.nextLine();
            track.time = parseLength(scanner.nextLine());
            track.country = scanner.nextLine();
            database[i] = track;
            i += 1;
        }

        return trimTracks(database, i);
    }
}