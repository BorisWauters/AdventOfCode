package wauters.java;

public abstract class Solution {

    public final String test = "input/test.txt";
    public final String input;


    protected Solution() {
        String[] path = this.getClass().getCanonicalName().split("\\.");
        this.input = "input/" + path[path.length - 3] + "/" + path[path.length - 2] + ".txt";
    }

    protected abstract void solve(String[] lines);
}
