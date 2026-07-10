import java.util.Arrays;
import java.util.Comparator;
class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] sorted = new int[n][2];
        for (int i = 0; i < n; i++) {
            sorted[i][0] = nums[i];
            sorted[i][1] = i;
        }
        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));
        int[] order = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = sorted[i][0];
            order[sorted[i][1]] = i;
        }
        int[] component = new int[n];
        for (int i = 1; i < n; i++) {
            component[i] = component[i - 1];
            if (values[i] - values[i - 1] > maxDiff) {
                component[i]++;
            }
        }
        int[] reach = new int[n];
        int right = 0;
        for (int left = 0; left < n; left++) {
            while (right + 1 < n && values[right + 1] - values[left] <= maxDiff) {
                right++;
            }
            reach[left] = right;
        }
        int log = 1;
        while ((1 << log) <= n) {
            log++;
        }
        int[][] jump = new int[log][n];

        for (int i = 0; i < n; i++) {
            jump[0][i] = reach[i];
        }
        for (int k = 1; k < log; k++) {
            for (int i = 0; i < n; i++) {
                jump[k][i] = jump[k - 1][jump[k - 1][i]];
            }
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int u = order[queries[i][0]];
            int v = order[queries[i][1]];
            if (u == v) {
                ans[i] = 0;
                continue;
            }
            if (component[u] != component[v]) {
                ans[i] = -1;
                continue;
            }
            if (u > v) {
                int temp = u;
                u = v;
                v = temp;
            }
            int cur = u;
            int steps = 0;

            for (int k = log - 1; k >= 0; k--) {
                if (jump[k][cur] < v) {
                    cur = jump[k][cur];
                    steps += 1 << k;
                }
            }
            ans[i] = steps + 1;
        }

        return ans;
    }
}