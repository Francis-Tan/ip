/**
 * Tasks that can be marked as done or not done
 */
public class Task {

    /** Description of what the task entails */
    protected String description;

    /** Is this task done? */
    protected boolean isDone;

    /**
     * Constructs a new not done task with the given description
     * @param description The task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * @return "X" if the task is done, " " otherwise
     */
    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * @return The description of this task
     */
    private String getDescription() {
        return description;
    }

    /**
     * @return The task status and description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }
}
