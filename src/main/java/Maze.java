import java.util.ArrayList;
import java.util.Collections;

public class Maze {
    private ArrayList<ArrayList<Boolean>> rightWall;
    private ArrayList<ArrayList<Boolean>> bottomWall;
    private ArrayList<ArrayList<Integer>> sets;

    /**
     * @param a - column
     * @param b - row
     */
    Maze(int a, int b) {
        ArrayList<ArrayList<Integer>> sets = new ArrayList<ArrayList<Integer>>();
        rightWall = new ArrayList<>();
        bottomWall = new ArrayList<>();
        for (int j = 0; j < b; j++) {
            newRow(a, j, sets);
            setRightWalls(a, j, sets);
            setBottomWalls(a, b, j, sets);
        }
        endMaze(a, b, sets);
        this.sets = sets;
    }

    private void endMaze(int a, int b, ArrayList<ArrayList<Integer>> sets) {
        for (int i = 0; i < a - 1; i++) {
            if (!sets.get(b - 1).get(i).equals(sets.get(b - 1).get(i + 1))) {
                rightWall.get(b - 1).set(i, false);
                sets.get(b - 1).set(i + 1, sets.get(b - 1).get(i));
            }
        }
    }

    private void setBottomWalls(int a, int b, int j, ArrayList<ArrayList<Integer>> sets) {
        if (j == b - 1) {
            bottomWall.add(j, new ArrayList<>(Collections.nCopies(a, true)));
            return;
        }
        bottomWall.add(new ArrayList<>(Collections.nCopies(a, false)));
        int countWalls = 0, currNum = 0, lengthNum = 0;
        for (int i = 0; i < a; i++) {
            if (currNum != sets.get(j).get(i)) {
                countWalls = 0;
                currNum = sets.get(j).get(i);
                int fin = currNum;
                lengthNum = (int) sets.get(j).stream().filter((num) -> num == fin).count();
            }
            if (countWalls < lengthNum - 1) {
                if (Math.random() < 0.5) {
                    bottomWall.get(j).set(i, true);
                    countWalls++;
                }
            }
        }
    }

    private void setRightWalls(int a, int j, ArrayList<ArrayList<Integer>> sets) {
        rightWall.add(new ArrayList<>(Collections.nCopies(a, false)));
        rightWall.get(j).set(a - 1, true);
        for (int i = 0; i < a - 1; i++) {
            if (sets.get(j).get(i).equals(sets.get(j).get(i + 1))) {
                rightWall.get(j).set(i, true);
            } else {
                if (Math.random() < 0.5)
                    rightWall.get(j).set(i, true);
                else
                    sets.get(j).set(i + 1, sets.get(j).get(i));
            }
        }
    }

    private void newRow(int a, int b, ArrayList<ArrayList<Integer>> sets) {
        ArrayList<Integer> row = new ArrayList<>();
        if (b != 0) {
            row = (ArrayList<Integer>) sets.get(b - 1).clone();
            sets.add(row);
            for (int i = 0; i < row.size(); i++) {
                if (bottomWall.get(b - 1).get(i))
                    row.set(i, 0);
            }
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i) == 0)
                    row.set(i, lessFreePlenty(sets));
            }
        } else {
            for (int i = 1; i <= a; i++)
                row.add(i);
            sets.add(row);
        }
    }

    private int lessFreePlenty(ArrayList<ArrayList<Integer>> arr) {
        for (int i = 1; ; i++) {
            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(j).contains(i))
                    break;
                else if (j == arr.size() - 1)
                    return i;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i <= rightWall.size(); i++) {
            for (int j = 0; j <= rightWall.get(0).size(); j++) {
                if (i == 0) {
                    if(j < rightWall.get(0).size())
                        str.append(" __");
                } else {
                    if (j == 0)
                        str.append("|");
                    else {
                        if (bottomWall.get(i - 1).get(j - 1))
                            str.append("__");
                            //str.append(sets.get(i-1).get(j-1) + "_");
                        else
                            str.append("  ");
                            //str.append(sets.get(i-1).get(j-1) + " ");
                        if (rightWall.get(i - 1).get(j - 1))
                            str.append("|");
                        else
                            str.append(" ");
                    }
                }
            }
            str.append("\n");
        }
        return str.toString();
    }
}
