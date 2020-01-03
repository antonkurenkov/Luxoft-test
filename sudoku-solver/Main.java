import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[][] board = {
                {0, 1, 3, 8, 0, 0, 4, 0, 5},
                {0, 2, 4, 6, 0, 5, 0, 0, 0},
                {0, 8, 7, 0, 0, 0, 9, 3, 0},
                {4, 9, 0, 3, 0, 6, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 7, 0, 1, 0, 9, 3},
                {0, 6, 9, 0, 0, 0, 7, 4, 0},
                {0, 0, 0, 2, 0, 7, 6, 8, 0},
                {1, 0, 2, 0, 0, 8, 3, 5, 0}
        };
        int[][] board2 = {
                {0, 0, 2, 0, 0, 0, 0, 4, 1},
                {0, 0, 0, 0, 8, 2, 0, 7, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 9},
                {2, 0, 0, 0, 7, 9, 3, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 8, 0},
                {0, 0, 6, 8, 1, 0, 0, 0, 4},
                {1, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 6, 0, 4, 3, 0, 0, 0, 0},
                {8, 5, 0, 0, 0, 0, 4, 0, 0}
        };

        printBoard(board);
        long start = System.currentTimeMillis();
        int[][] solution = solve(board);
        long finish = System.currentTimeMillis();
        long timer = finish - start;
        if (solution != null) {
            printBoard(solution);
        }
        System.out.println((float) timer / 1000);
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] solve(int[][] board) {
        int[][] solution = board.clone();
        if (solveHelper(solution)) return solution;
        else {
            return null;
        }
    }

    public static boolean solveHelper(int[][]solution) {
        Object[][] minPossibleValueCountCell;
        while (true) {
            minPossibleValueCountCell = new Object[0][];
            for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
                for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                    if (solution[rowIndex][columnIndex] != 0) {
                        continue;
                    }
                    TreeSet<Integer> possibleValues = findPossibleValues(rowIndex, columnIndex, solution);
                    int possibleValueCount = possibleValues.size();
                    if (possibleValueCount == 0) {
                        return false;
                    }
                    if (possibleValueCount == 1) {
                        solution[rowIndex][columnIndex] = possibleValues.last();
                    }
                    if (minPossibleValueCountCell.length == 0
                            || possibleValueCount < minPossibleValueCountCell[1].length) {
                        Object[] possibleValuesArr = possibleValues.toArray();
                        minPossibleValueCountCell = new Object[][]{{rowIndex, columnIndex}, possibleValuesArr};

                    }
                }
            }
            if (minPossibleValueCountCell.length == 0) {
                return true;
            } else if (1 < minPossibleValueCountCell[1].length) {
                break;
            }
        }
        int r = (int) minPossibleValueCountCell[0][0];
        int c = (int) minPossibleValueCountCell[0][1];
        for (Object v : minPossibleValueCountCell[1]) {
            int[][] solutionClone = twoDClone(solution);
            solutionClone[r][c] = (int) v;
            if (solveHelper(solutionClone)) {
                for (int i = 0; i < 9; i++) {
                    System.arraycopy(solutionClone[i], 0, solution[i], 0, 9);
                }
                return true;
            }
        }
        return false;
    }

    public static int[][] twoDClone(int[][] solution) {
        int[][] solutionClone = new int[solution.length][];
        for (int i = 0; i < solution.length; i++) {
            solutionClone[i] = solution[i].clone();
        }
        return solutionClone;
    }

    public static TreeSet<Integer> findPossibleValues(int rowIndex, int columnIndex, int[][] solution) {
        TreeSet<Integer> values = new TreeSet<Integer>();
        for (int i = 1; i < 10; i++) {
            values.add(i);
        }
        for (int i = 0; i < 9; i++) {
            values.remove(solution[rowIndex][i]);
        }
        for (int j = 0; j < 9; j++) {
            values.remove(solution[j][columnIndex]);
        }
        int blockRowStart = rowIndex / 3;
        blockRowStart = blockRowStart * 3;
        int blockColumnStart = columnIndex / 3;
        blockColumnStart = blockColumnStart * 3;
        for (int i = blockRowStart; i < blockRowStart + 3; i++) {
            for (int j = blockColumnStart; j < blockColumnStart + 3; j++) {
                values.remove(solution[i][j]);
            }
        }
        return values;
    }
}
