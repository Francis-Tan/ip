package duke.services;

import java.util.Arrays;
import java.util.Scanner;

/** Handles reading of user commands */
public class Parser {

    /** Points to the current word being read in the current command */
    private static int currWordIndex = 0;

    /**
     * Receives user's inputs and responds to them until "bye" is entered
     */
    public static void handleUserInputs() {
        Scanner inputScanner = new Scanner(System.in);
        String[] words = Arrays.stream(inputScanner.nextLine().strip().split(" ")).toArray(String[]::new);
        while (!(words.length == 1 && words[0].equals("bye"))) {
            if (words.length > 0) {
                currWordIndex = 1;
                try {
                    if (words.length == 1 && words[0].equals("list")) {
                        TaskList.listTasks(); //could put words.length == 1 cases all here
                    } else if (words[0].equals("todo")) {
                        TaskList.addTodo(words);
                    } else if (words[0].equals("deadline")) {
                        TaskList.addDeadline(words);
                    } else if (words[0].equals("event")) {
                        TaskList.addEvent(words);
                    } else if (words[0].equals("mark")) {
                        TaskList.markTaskAsDone(words);
                    } else if (words[0].equals("unmark")) {
                        TaskList.markTaskAsNotDone(words);
                    } else if (words[0].equals("delete")) {
                        TaskList.deleteTask(words);
                    } else {
                        UI.sayLines(new String[]{"I'm sorry, I don't know what that means"});
                    }
                } catch (IllegalArgumentException e) {
                    UI.sayLines(new String[]{e.getMessage()});
                }
            }
            words = Arrays.stream(inputScanner.nextLine().strip().split(" ")).toArray(String[]::new);
        }
        inputScanner.close();
    }

    /**
     * Retrieves the description argument in the command
     * @param words The words of the command entered, first is some valid command name
     * @param stop The word before which the description ends
     * @return The description specified in words
     * @throws IllegalArgumentException If words specifies an empty description
     */
    public static String getDescription(String[] words, String stop) {
        StringBuilder descBuilder = new StringBuilder();
        boolean emptyDesc = true;

        while (currWordIndex < words.length && !words[currWordIndex].equals(stop)) {
            if (words[currWordIndex].isEmpty()) {
                descBuilder.append(" ");
            } else {
                descBuilder.append(words[currWordIndex]).append(" ");
                emptyDesc = false;
            }
            ++currWordIndex;
        }

        if (emptyDesc) {
            throw new IllegalArgumentException("☹ OOPS!!! Description can't be empty");
        }

        return descBuilder.deleteCharAt(descBuilder.length()-1).toString();
    }

    /**
     * Retrieves the timing argument in the command
     * @param words The words of the command entered, first is some valid command name
     * @param flag The flag that the timing belongs to
     * @return The timing specified in words
     * @throws IllegalArgumentException If words specifies an empty timing or is missing flag
     */
    public static String getTiming(String[] words, String flag) {
        if (currWordIndex >= words.length) {
            throw new IllegalArgumentException("☹ OOPS!!! " + flag + " not found");
        } else if (currWordIndex == words.length - 1) {
            throw new IllegalArgumentException("☹ OOPS!!! Timing for " + flag + " can't be empty");
        }

        ++currWordIndex;
        StringBuilder timingBuilder = new StringBuilder();

        while (currWordIndex < words.length) {
            timingBuilder.append(words[currWordIndex++]).append(" ");
        }

        return timingBuilder.deleteCharAt(timingBuilder.length()-1).toString();
    }

    /**
     * Gets the task number (integer pointing to a task) specified in the command
     *
     * @param words The words of the command entered, first is always some valid command name
     * @return The task number specified
     * @throws IllegalArgumentException If the argument(s) supplied in words isn't an integer from 1 to the number of stored tasks
     */
    public static int getTaskNumber(String[] words) {
        if (TaskList.getTaskCount() == 0) {
            throw new IllegalArgumentException("☹ OOPS!!! No tasks stored for me to do that");
        }

        int taskNumber = 0;

        try {
            taskNumber = (words.length == 2) ? Integer.parseInt(words[currWordIndex]) : 0;
            if (taskNumber <= 0 || taskNumber > TaskList.getTaskCount()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("☹ OOPS!!! The task number must be from 1 to " + TaskList.getTaskCount());
        }

        return taskNumber;
    }
}
