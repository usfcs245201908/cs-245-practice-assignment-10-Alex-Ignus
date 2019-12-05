import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GraphImplementation implements Graph {

    private int adjMatrix[][];
    private int numVertices;

    public GraphImplementation(int size) {
        this.numVertices = size;
        adjMatrix = new int[numVertices][numVertices];
    }

    @Override
    public void addEdge(int x, int y) throws Exception {
        if (x <= numVertices && y <= numVertices && x >= 0 && y >= 0) {
            adjMatrix[x][y] = 1;
        } else {
            throw new Exception();
        }
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> schedule = new ArrayList<Integer>();
        int[] sum = new int[numVertices];
        {
            int i = 0;
            while (i < numVertices) {
                int j = 0;
                while (j < numVertices) {
                    sum[j] += adjMatrix[i][j];
                    j++;
                }
                i++;
            }
        }
        IntStream.range(0, numVertices).map(count -> firstFound(sum)).forEach(n -> {
            if (n == -1) {
                System.out.print("Ordering not possible, this is a cycle");
                System.exit(0);
            }
            schedule.add(n);
            sum[n] = -1;
            IntStream.range(0, numVertices).forEach(i -> sum[i] -= adjMatrix[n][i]);
        });
        return schedule;
    }


    private int firstFound(int[] arr) {
        return IntStream.range(0, arr.length).filter(i -> arr[i] == 0).findFirst().orElse(-1);
    }

    @Override
    public List<Integer> neighbors(int vertex) throws Exception {
        if (vertex <= numVertices && vertex >= 0) {
            List<Integer> result = new ArrayList<Integer>();
            int i = 0;
            while (i < numVertices) {
                if (adjMatrix[vertex][i] == 1)
                    result.add(i);
                i++;
            }
            return result;
        } else {
            throw new Exception();
        }
    }
}
