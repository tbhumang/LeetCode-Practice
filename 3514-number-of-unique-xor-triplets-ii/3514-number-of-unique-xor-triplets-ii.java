class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;
        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;
        for(int val : nums){
            boolean[][] next = new boolean[4][MAX];
            for(int k = 0; k <= 3; k++){
                System.arraycopy(dp[k], 0, next[k], 0, MAX);
            }
            for(int used = 0; used <= 3; used++) {
                for(int xr = 0; xr < MAX; xr++){
                    if(!dp[used][xr]) continue;
                    for(int take = 1; take + used <= 3; take++){
                        int add = (take % 2 == 1) ? val : 0;
                        next[used + take][xr ^ add] = true;
                    }
                }
            }
            dp = next;
        }
        int ans = 0;
        for(int xr = 0; xr < MAX; xr++){
            if(dp[3][xr]) ans++;
        }
        return ans;
    }
}