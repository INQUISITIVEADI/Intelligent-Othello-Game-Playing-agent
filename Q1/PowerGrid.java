import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class PowerLine {
    String cityA;
    String cityB;

    public PowerLine(String cityA, String cityB) {
        this.cityA = cityA;
        this.cityB = cityB;
    }
}

// Students can define new classes here
class Pair {
    Integer c;
    PowerLine p;

    public Pair(Integer c, PowerLine p) {
        this.c = c;
        this.p = p;
    }
}

public class PowerGrid {
    int numCities;
    int numLines;
    String[] cityNames;
    PowerLine[] powerLines;

    // Students can define private variables here
    private HashMap<String, Integer> map = new HashMap<String, Integer>();
    private HashMap<String, ArrayList<Pair>> adj = new HashMap<String, ArrayList<Pair>>();
    // private HashMap<String, ArrayList<PowerLine>> adj2 = new HashMap<String,
    int[][] table;
    int[] crtic_paths;
    int[] levels;
    // ArrayList<PowerLine>>();

    public PowerGrid(String filename) throws Exception {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        numCities = Integer.parseInt(br.readLine());
        numLines = Integer.parseInt(br.readLine());
        cityNames = new String[numCities];
        for (int i = 0; i < numCities; i++) {
            cityNames[i] = br.readLine();
        }
        powerLines = new PowerLine[numLines];
        for (int i = 0; i < numLines; i++) {
            String[] line = br.readLine().split(" ");
            powerLines[i] = new PowerLine(line[0], line[1]);
        }

        // TO be completed by students

        for (int i = 0; i < numCities; i++) {
            map.put(cityNames[i], i);
            adj.put(cityNames[i], new ArrayList<Pair>());
            // adj.put(cityNames, null)
        }
        for (int i = 0; i < numLines; i++) {
            // Pair pp = new Pair(1, powerLines[i]);
            adj.get(powerLines[i].cityA).add(new Pair(map.get(powerLines[i].cityB), powerLines[i]));

            adj.get(powerLines[i].cityB).add(new Pair(map.get(powerLines[i].cityA), powerLines[i]));
        }

    }

    private void DFS(int city, boolean[] visited, int[] parent, int[] level, int[] highpoint, int cur_level,
            ArrayList<PowerLine> req) {
        visited[city] = true;
        level[city] = cur_level;
        highpoint[city] = cur_level;
        cur_level += 1;
        for (Pair i : adj.get(cityNames[city])) {
            if (!visited[i.c]) {
                parent[i.c] = city;
                DFS(i.c, visited, parent, level, highpoint, cur_level, req);
                highpoint[city] = Math.min(highpoint[city], highpoint[i.c]);
                if (highpoint[i.c] > level[city]) {
                    req.add(i.p);
                }
            } else {
                if (parent[city] != i.c) {
                    highpoint[city] = Math.min(highpoint[city], highpoint[i.c]);
                }
            }
        }
        // for (Pair i : adj.get(cityNames[city])) {
        // if (parent[city] != i.c) {
        // highpoint[city] = Math.min(highpoint[city], highpoint[i.c]);
        // }
        // if (level[city] > level[i.c]) {
        // if (highpoint[i.c] > level[city]) {
        // req.add(i.p);
        // }
        // }
        // }
    }

    public ArrayList<PowerLine> criticalLines() {
        // System.out.println("cirt");
        /*
         * Implement an efficient algorithm to compute the critical transmission lines
         * in the power grid.
         * 
         * Expected running time: O(m + n), where n is the number of cities and m is the
         * number of transmission lines.
         */
        boolean visited[] = new boolean[numCities];
        int parent[] = new int[numCities];
        int level[] = new int[numCities];
        int highpoint[] = new int[numCities];
        int cur_level = 0;
        ArrayList<PowerLine> req = new ArrayList<PowerLine>();
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                DFS(i, visited, parent, level, highpoint, cur_level, req);
            }
        }
        // for (int i = 0; i < numCities; i++) {
        // System.out.print(level[i] + " ");
        // }
        // System.out.println();
        // for (int i = 0; i < numCities; i++) {
        // System.out.print(highpoint[i] + " ");
        // }
        // System.out.println();
        return req;
    }

    private void DDFS(int city, int[] parent, boolean[] visited, int LOG, int cur_level,
            int[] highpoint) {
        visited[city] = true;
        levels[city] = cur_level;
        highpoint[city] = cur_level;
        cur_level++;
        table[city][0] = parent[city];
        for (int i = 1; i < LOG + 1; i++) {
            table[city][i] = table[table[city][i - 1]][i - 1];
        }
        for (Pair i : adj.get(cityNames[city])) {
            if (!visited[i.c]) {
                parent[i.c] = city;
                DDFS(i.c, parent, visited, LOG, cur_level, highpoint);
                highpoint[city] = Math.min(highpoint[city], highpoint[i.c]);
                // if (highpoint[i.c] > level[city]) {
                // crtic_paths[i.c] = crtic_paths[city] + 1;
                // } else {
                // crtic_paths[i.c] = crtic_paths[city];
                // }
            } else {
                if (parent[city] != i.c) {
                    highpoint[city] = Math.min(highpoint[city], highpoint[i.c]);
                }
            }
        }
    }

    private void DDDFS(int city, boolean[] visited, int[] highpoint, int[] parent) {
        visited[city] = true;
        for (Pair i : adj.get(cityNames[city])) {
            if (!visited[i.c]) {
                if (highpoint[i.c] > levels[city]) {
                    crtic_paths[i.c] = crtic_paths[city] + 1;
                } else {
                    crtic_paths[i.c] = crtic_paths[city];
                }
                DDDFS(i.c, visited, highpoint, parent);
            }
        }
    }

    private int lca_finder(int x, int y, int LOG) {
        // System.out.println(cityNames[x] + " " + cityNames[y] + " " + LOG);
        for (int i = LOG; i > -1; i--) {
            if (levels[x] - (1 << i) >= levels[y]) {
                // System.out.println("here " + cityNames[x] + " " + i);
                x = table[x][i];
            }
        }
        if (x == y) {
            return y;
        }
        for (int i = LOG; i > -1; i--) {
            if (table[x][i] != table[y][i]) {
                x = table[x][i];
                y = table[y][i];
            }
        }
        // System.out.println(cityNames[table[x][0]]);
        return table[x][0];
    }

    public void preprocessImportantLines() {
        /*
         * Implement an efficient algorithm to preprocess the power grid and build
         * required data structures which you will use for the numImportantLines()
         * method. This function is called once before calling the numImportantLines()
         * method. You might want to define new classes and data structures for this
         * method.
         * 
         * Expected running time: O(n * logn), where n is the number of cities.
         */
        boolean visited[] = new boolean[numCities];
        int parent[] = new int[numCities];
        levels = new int[numCities];
        int LOG = (int) Math.ceil(Math.log(numCities) / Math.log(2));
        table = new int[numCities][LOG + 1];
        crtic_paths = new int[numCities];
        int highpoint[] = new int[numCities];
        int cur_level = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                DDFS(i, parent, visited, LOG, cur_level, highpoint);
            }
        }
        // for (int i = 0; i < numCities; i++) {
        // System.out.print(levels[i] + " ");
        // }
        // System.out.println();
        visited = new boolean[numCities];
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                crtic_paths[i] = 0;
                DDDFS(i, visited, highpoint, parent);
            }
        }
        return;
    }

    public int numImportantLines(String cityA, String cityB) {
        // System.out.println("imp");
        /*
         * Implement an efficient algorithm to compute the number of important
         * transmission lines between two cities. Calls to numImportantLines will be
         * made only after calling the preprocessImportantLines() method once.
         * 
         * Expected running time: O(logn), where n is the number of cities.
         */
        int LOG = (int) Math.ceil(Math.log(numCities) / Math.log(2));
        int x = map.get(cityA);
        int y = map.get(cityB);
        int lca;
        if (levels[x] > levels[y]) {

            lca = lca_finder(x, y, LOG);
        } else {
            lca = lca_finder(y, x, LOG);
        }

        return crtic_paths[x] + crtic_paths[y] - 2 * crtic_paths[lca];
    }

    // public static void main(String[] args) {
    // try {
    // PowerGrid obj = new PowerGrid("input.txt");
    // ArrayList<PowerLine> aa = new ArrayList<PowerLine>();
    // aa = obj.criticalLines();
    // System.out.println(aa);
    // // for (PowerLine i : aa) {
    // // System.out.println(i.cityA + " " + i.cityB);
    // // }

    // System.out.println();
    // System.out.println((1 << 2));
    // obj.preprocessImportantLines();
    // for (int i : obj.crtic_paths) {
    // System.out.print(i + " ");
    // }
    // System.out.println(obj.numImportantLines("H", "O"));

    // } catch (Exception e) {
    // System.out.println(e);
    // }
    // }
}