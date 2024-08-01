package command;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {
  // 使满二叉树所有路径值相等的最小代价
  public static int minIncrements(int n, int[] cost) {
    int ans = 0;
    for (int i = n / 2; i > 0; i--) { // 从最后一个非叶节点开始算
      ans += Math.abs(cost[i * 2 - 1] - cost[i * 2]); // 两个子节点变成一样的
      cost[i - 1] += Math.max(cost[i * 2 - 1], cost[i * 2]); // 累加路径和
    }
    return ans;
  }

  // LeetCode-2369 检查数组是否存在有效划分
  // 1.子数组 恰 由 2 个相等元素组成，例如，子数组 [2,2] 。
  // 2.子数组 恰 由 3 个相等元素组成，例如，子数组 [4,4,4] 。
  // 3.子数组 恰 由 3 个连续递增元素组成，并且相邻元素之间的差值为 1 。例如，子数组 [3,4,5] ，但是子数组 [1,3,5] 不符合要求。
  // 如果数组 至少 存在一种有效划分，返回 true ，否则，返回 false 。
  public static boolean validPartition(int[] nums) {
    int n = nums.length;
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;
    for (int i = 1; i <= n; i++) {
      if (i >= 2) {
        dp[i] = dp[i - 2] && validTwo(nums[i - 2], nums[i - 1]);
      }
      if (i >= 3) {
        dp[i] = dp[i] || (dp[i - 3] && validThree(nums[i - 3], nums[i - 2], nums[i - 1]));
      }
    }
    return dp[n];
  }

  public static boolean validTwo(int num1, int num2) {
    return num1 == num2;
  }

  public static boolean validThree(int num1, int num2, int num3) {
    return (num1 == num2 && num1 == num3) || (num1 + 1 == num2 && num2 + 1 == num3);
  }

  // LeetCode-2789. 合并后数组中的最大元素
  // 选中一个同时满足 0 <= i < nums.length - 1 和 nums[i] <= nums[i + 1] 的整数 i 。
  // 将元素 nums[i + 1] 替换为 nums[i] + nums[i + 1] ，并从数组中删除元素 nums[i] 。
  // 返回你可以从最终数组中获得的 最大 元素的值。
  // 示例 1：
  // 输入：nums = [2,3,7,9,3]
  // 输出：21
  // 选中 i = 0 ，得到数组 nums = [5,7,9,3] 。
  // 选中 i = 1 ，得到数组 nums = [5,16,3] 。
  // 选中 i = 0 ，得到数组 nums = [21,3] 。
  // 最终数组中的最大元素是 21 。可以证明我们无法获得更大的元素。
  public static long maxArrayValue(int[] nums) {
    long sum = nums[nums.length - 1];
    for (int i = nums.length - 2; i >= 0; i--) {
      sum = nums[i] <= sum ? nums[i] + sum : nums[i];
    }
    return sum;
  }

  // LeetCode-88. 合并两个有序数组
  // 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
  // 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
  // nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，
  public static void merge(int[] nums1, int m, int[] nums2, int n) {
    int length = nums1.length - 1;
    int index1 = m - 1;
    int index2 = n - 1;
    while (length >= 0) {
      if (index1 >= 0 && index2 >= 0 && nums1[index1] >= nums2[index2]) {
        nums1[length] = nums1[index1];
        index1--;
      } else if (index1 >= 0 && index2 >= 0 && nums1[index1] < nums2[index2]) {
        nums1[length] = nums2[index2];
        index2--;
      } else if (index2 >= 0) {
        nums1[length] = nums2[index2];
        index2--;
      }
      length--;
    }
  }

  public static int removeElement(int[] nums, int val) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      while (right >= 0 && nums[right] == val) {
        right--;
      }
      if (left < right && nums[left] == val) {
        nums[left] = nums[right];
        right--;
      }
      left++;
    }
    return right + 1;
  }

  // LeetCode-1997. 访问完所有房间的第一天
  // 最开始的第 0 天，你访问 0 号房间。给你一个长度为 n 且 下标从 0 开始 的数组 nextVisit 。在接下来的几天中，你访问房间的 次序 将根据下面的 规则 决定：
  // 假设某一天，你访问 i 号房间
  // 如果算上本次访问，访问 i 号房间的次数为 奇数 ，那么 第二天 需要访问 nextVisit[i] 所指定的房间，其中 0 <= nextVisit[i] <= i
  // 如果算上本次访问，访问 i 号房间的次数为 偶数 ，那么 第二天 需要访问 (i + 1) mod n 号房间
  // 请返回你访问完所有房间的第一天的日期编号。题目数据保证总是存在这样的一天。由于答案可能很大，返回对 109 + 7 取余后的结果
  public static int firstDayBeenInAllRooms(int[] nextVisit) {
    int res = -1;
    int index = 0;
    int length = nextVisit.length;
    int num[] = new int[length];
    while (num[length - 1] == 0) {
      num[index]++;
      if (num[index] % 2 == 0) {
        index = (index + 1) % length;
      } else {
        index = nextVisit[index];
      }
      res++;
    }
    return res;
  }

  // LeetCode-2908. 元素和最小的山形三元组
  // 给你一个下标从 0 开始的整数数组 nums 。
  // 如果下标三元组 (i, j, k) 满足下述全部条件，则认为它是一个 山形三元组 ：
  // i < j < k
  // nums[i] < nums[j] 且 nums[k] < nums[j]
  // 请你找出 nums 中 元素和最小 的山形三元组，并返回其 元素和 。如果不存在满足条件的三元组，返回 -1
  public static int minimumSum(int[] nums) {
//    int n = nums.length, res = 1000, mn = 1000;
//    int[] left = new int[n];
//    for (int i = 1; i < n; i++) {
//      left[i] = mn = Math.min(nums[i - 1], mn);
//    }
//
//    int right = nums[n - 1];
//    for (int i = n - 2; i > 0; i--) {
//      if (left[i] < nums[i] && nums[i] > right) {
//        res = Math.min(res, left[i] + nums[i] + right);
//      }
//      right = Math.min(right, nums[i]);
//    }
//
//    return res < 1000 ? res : -1;
    int min = 200;
    int left[] = new int[nums.length];
    for (int i = 1; i < nums.length; i++) {
      left[i] = i == 1 ? nums[i-1] : Math.min(nums[i-1], left[i- 1]);
    }
    int right = nums[nums.length-1];
    for (int i = nums.length - 2; i > 0; i--) {
      if (left[i] < nums[i] && right < nums[i]) {
        min = Math.min(left[i] + nums[i] + right, min);
      }
      right = Math.min(nums[i], right);
    }
    return min == 200 ? -1 : min;
  }

  //  leetCode-1702 修改后的最大二进制字符串
  //  给你一个二进制字符串 binary ，它仅有 0 或者 1 组成。你可以使用下面的操作任意次对它进行修改：
  //  操作 1 ：如果二进制串包含子字符串 "00" ，你可以用 "10" 将其替换。
  //  比方说， "00010" -> "10010"
  //  操作 2 ：如果二进制串包含子字符串 "10" ，你可以用 "01" 将其替换。
  //  比方说， "00010" -> "00001"
  //  请你返回执行上述操作任意次以后能得到的 最大二进制字符串
  public static String maximumBinaryString(String binary) {
    char[] s = binary.toCharArray();
    int j = 0;
    for (int i = 0; i < s.length; i++) {
      if (s[i] == '0') {
        while (j <= i || (j < s.length && s[j] == '1')) {
          j++;
        }
        if (j < s.length) {
          s[i] = '1';
          s[j] = '1';
          s[i+1] = '0';
        }
      }
    }
    return s.toString();
  }

  // -----------------------------------------------------------------------------------------------------------------------
  // leetcode-1766. 互质树
  // 给你一个 n 个节点的树（也就是一个无环连通无向图），节点编号从 0 到 n - 1 ，且恰好有 n - 1 条边，每个节点有一个值。树的 根节点 为 0 号点。
  // 给你一个整数数组 nums 和一个二维数组 edges 来表示这棵树。nums[i] 表示第 i 个点的值，edges[j] = [uj, vj] 表示节点 uj 和节点 vj 在树中有一条边。
  // 当 gcd(x, y) == 1 ，我们称两个数 x 和 y 是 互质的 ，其中 gcd(x, y) 是 x 和 y 的 最大公约数 。
  // 从节点 i 到 根 最短路径上的点都是节点 i 的祖先节点。一个节点 不是 它自己的祖先节点。
  // 请你返回一个大小为 n 的数组 ans ，其中 ans[i]是离节点 i 最近的祖先节点且满足 nums[i] 和 nums[ans[i]] 是 互质的 ，
  // 如果不存在这样的祖先节点，ans[i] 为 -1
  // 例子：
  //           -----
  //          /  0  \
  //          \ (2) /
  //           -----
  //             |
  //           -----
  //          /  1  \
  //          \ (3) /
  //           -----
  //             /\
  //            /  \
  //       -----    -----
  //      /  2  \  /  3  \
  //      \ (3) /  \ (2) /
  //       -----    -----
  // 输入：nums = [2,3,3,2], edges = [[0,1],[1,2],[1,3]]
  // 输出：[-1,0,0,1]
  // 解释：上图中，每个节点的值在括号中表示。
  // - 节点 0 没有互质祖先。
  // - 节点 1 只有一个祖先节点 0 。它们的值是互质的（gcd(2,3) == 1）。
  // - 节点 2 有两个祖先节点，分别是节点 1 和节点 0 。节点 1 的值与它的值不是互质的（gcd(3,3) == 3）
  // 但节点 0 的值是互质的(gcd(2,3) == 1)，所以节点 0 是最近的符合要求的祖先节点。
  // - 节点 3 有两个祖先节点，分别是节点 1 和节点 0 。它与节点 1 互质（gcd(3,2) == 1），所以节点 1 是离它最近的符合要求的祖先节点。
  private static final int MX = 51;
  private static final int[][] coprime = new int[MX][MX];

  static {
    // 预处理
    // coprime[i] 保存 [1, MX) 中与 i 互质的所有元素
    for (int i = 1; i < MX; i++) {
      int k = 0;
      for (int j = 1; j < MX; j++) {
        if (gcd(i,j) == 1) {
          coprime[i][k++] = j;
        }
      }
    }
  }

  private static void dfs(int x, int fa, int depth, List<Integer>[] g, int[] nums, int[] ans, int[] valDepth, int[] valNodeId) {
    // x 的节点值
    int val = nums[x];

    // 计算与 val 互质的祖先节点值中，节点深度最大的节点编号
    int maxDepth = 0;
    for (int j : coprime[val]) {
      if (j == 0) {
        break;
      }
      if (valDepth[j] > maxDepth) {
        maxDepth = valDepth[j];
        ans[x] = valNodeId[j];
      }
    }

    // tmpDepth 和 tmpNodeId 用于恢复现场
    int tmpDepth = valDepth[val];
    int tmpNodeId = valNodeId[val];

    // 保存 val 对应的节点深度和节点编号
    valDepth[val] = depth;
    valNodeId[val] = x;

    // 向下递归
    for (int y : g[x]) {
      if (y != fa) {
        dfs(y, x, depth + 1, g, nums, ans, valDepth, valNodeId);
      }
    }

    // 恢复现场
    valDepth[val] = tmpDepth;
    valNodeId[val] = tmpNodeId;
  }

  public static int[] getCoprimes(int[] nums, int[][] edges) {
    int n = nums.length;
    List<Integer>[] g = new ArrayList[n];
    Arrays.setAll(g, i -> new ArrayList<>());
    for (int[] e : edges) {
      int x = e[0];
      int y = e[1];
      g[x].add(y);
      g[y].add(x);
    }

    int[] ans = new int[n];
    Arrays.fill(ans, -1);
    int[] valDepth = new int[MX];
    int[] valNodeId = new int[MX];
    dfs(0, -1, 1, g, nums, ans, valDepth, valNodeId);
    return ans;
  }

  // --------------------------------------------------------------------------------------------------------

  // leetcode-2923. 找到冠军 I
  // 一场比赛中共有 n 支队伍，按从 0 到  n - 1 编号。
  // 给你一个下标从 0 开始、大小为 n * n 的二维布尔矩阵 grid 。对于满足 0 <= i, j <= n - 1 且 i != j 的所有 i, j ：
  // 如果 grid[i][j] == 1，那么 i 队比 j 队 强 ；否则，j 队比 i 队 强 。
  // 在这场比赛中，如果不存在某支强于 a 队的队伍，则认为 a 队将会是 冠军 。
  // 返回这场比赛中将会成为冠军的队伍。
  // 示例：
  // 输入：grid = [[0,1],[0,0]]
  // 输出：0
  // 解释：比赛中有两支队伍。grid[0][1] == 1 表示 0 队比 1 队强。所以 0 队是冠军。
  public static int findChampion(int[][] grid) {
    // 时间复杂度O(n^2)，空间复杂度O(1)
//    int ans = -1;
//    for (int i = 0; i < grid.length; i++) {
//      ans = i;
//      for (int j = 0; j < grid[i].length; j++) {
//        if (i != j && grid[i][j] == 0) {
//          ans = -1;
//          break;
//        }
//      }
//      if (ans != -1) break;
//    }
//    return ans;
    // 时间复杂度O(n)，空间复杂度O(1)
    int ans = 0;
    for (int i = 0; i < grid.length; i++) {
      if (grid[i][ans] == 1) {
        ans = i;
      }
    }
    return ans;
  }

  // leetcode-2924. 找到冠军 II
  // 一场比赛中共有 n 支队伍，按从 0 到  n - 1 编号。每支队伍也是 有向无环图（DAG） 上的一个节点。
  //给你一个整数 n 和一个下标从 0 开始、长度为 m 的二维整数数组 edges 表示这个有向无环图，其中 edges[i] = [ui, vi] 表示图中存在一条从 ui 队到 vi 队的有向边。
  //从 a 队到 b 队的有向边意味着 a 队比 b 队 强 ，也就是 b 队比 a 队 弱 。
  //在这场比赛中，如果不存在某支强于 a 队的队伍，则认为 a 队将会是 冠军 。
  //如果这场比赛存在 唯一 一个冠军，则返回将会成为冠军的队伍。否则，返回 -1
  // 示例：
  // 输入：n = 3, edges = [[0,1],[1,2]]
  // 输出：0
  // 解释：1 队比 0 队弱。2 队比 1 队弱。所以冠军是 0 队。
  public static int findChampion(int n, int[][] edges) {
    int dregee[] = new int[n];
    for (int i = 0; i < edges.length; i++) {
      dregee[edges[i][1]]++;
    }
    int ans = -1;
    for (int i = 0; i < n; i++) {
      if (dregee[i] == 0) {
        if (ans == -1) {
          ans = i;
        } else {
          return -1;
        }
      }
    }
    return ans;
  }
  // -------------------------------------------------------------------------------------

  // leetcode-54. 螺旋矩阵
  // 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
  public static List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> order = new ArrayList<Integer>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return order;
    }
    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;
    while (top <= bottom && left <= right) {
      for (int column = left; column <= right; column++) {
        order.add(matrix[top][column]);
      }

      for (int row = top + 1; row <= bottom; row++) {
        order.add(matrix[row][right]);
      }

      for (int column = right - 1; column > left; column--) {
        order.add(matrix[bottom][column]);
      }

      for (int row = bottom; row > top; row--) {
        order.add(matrix[row][left]);
      }
      top++;
      left++;
      bottom--;
      right--;
    }
    return order;
  }

  public static void gameOfLife(int[][] board) {
    int column = board[0].length;
    int row = board.length;
    int copy[][] = new int[row][column];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        int sum = 0;
        copy[i][j] = board[i][j];
        if (i - 1 > 0 && j - 1 > 0 && copy[i - 1][j - 1] == 1) {
          sum+= 1;
        }
        if (i - 1 > 0 && copy[i - 1][j] == 1) {
          sum+= 1;
        }
        if (i - 1 > 0 && j + 1 < column && copy[i - 1][j + 1] == 1) {
          sum+= 1;
        }
        if (j - 1 > 0 && copy[i][j - 1] == 1) {
          sum+= 1;
        }
        if (j + 1 < column && board[i][j + 1] == 1) {
          sum+= 1;
        }
        if (i + 1 < row && j - 1 > 0 && board[i + 1][j - 1] == 1) {
          sum+= 1;
        }
        if (i + 1 < row && board[i + 1][j] == 1) {
          sum+= 1;
        }
        if (i + 1 < row && j + 1 < column && board[i + 1][j + 1] == 1) {
          sum+= 1;
        }
        if (sum < 2 || sum > 3) {
          board[i][j] = 0;
        } else if (sum == 3 && board[i][j] == 0) {
          board[i][j] = 1;
        }
      }
    }
  }

  // leetcode 2713. 矩阵中严格递增的单元格数
  // 给你一个下标从 1 开始、大小为 m x n 的整数矩阵 mat，你可以选择任一单元格作为 起始单元格 。
  // 从起始单元格出发，你可以移动到 同一行或同一列 中的任何其他单元格，但前提是目标单元格的值 严格大于 当前单元格的值。
  // 你可以多次重复这一过程，从一个单元格移动到另一个单元格，直到无法再进行任何移动。
  // 请你找出从某个单元开始访问矩阵所能访问的 单元格的最大数量 。
  // 返回一个表示可访问单元格最大数量的整数。
  public static int maxIncreasingCells(int[][] mat) {
    int m = mat.length;
    int n = mat[0].length;
    TreeMap<Integer, List<int[]>> g = new TreeMap<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        // 相同元素放在同一组，统计位置
        g.computeIfAbsent(mat[i][j], k -> new ArrayList<>()).add(new int[]{i, j});
      }
    }

    int ans = 0;
    int[] rowMax = new int[m];
    int[] colMax = new int[n];
    for (List<int[]> pos : g.values()) {
      // 先把所有 f 值都算出来，再更新 rowMax 和 colMax
      int[] fs = new int[pos.size()];
      for (int k = 0; k < pos.size(); k++) {
        int[] p = pos.get(k);
        int i = p[0];
        int j = p[1];
        fs[k] = Math.max(rowMax[i], colMax[j]) + 1;
        ans = Math.max(ans, fs[k]);
      }
      for (int k = 0; k < pos.size(); k++) {
        int[] p = pos.get(k);
        int i = p[0];
        int j = p[1];
        rowMax[i] = Math.max(rowMax[i], fs[k]); // 更新第 i 行的最大 f 值
        colMax[j] = Math.max(colMax[j], fs[k]); // 更新第 j 列的最大 f 值
      }
    }
    return ans;
  }
  //------------------------------------------------------------------------------------------------------------------

  // leetcode-2748. 美丽下标对的数目
  // 给你一个下标从 0 开始的整数数组 nums 。如果下标对 i、j 满足 0 ≤ i < j < nums.length ，如果 nums[i] 的 第一个数字 和 nums[j] 的 最后一个数字 互质 ，则认为 nums[i] 和 nums[j] 是一组 美丽下标对 。
  // 返回 nums 中 美丽下标对 的总数目。
  // 对于两个整数 x 和 y ，如果不存在大于 1 的整数可以整除它们，则认为 x 和 y 互质 。
  // 换而言之，如果 gcd(x, y) == 1 ，则认为 x 和 y 互质，其中 gcd(x, y) 是 x 和 y 的 最大公因数 。
  public static int countBeautifulPairs(int[] nums) {
    int  res = 0;
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        int a = Character.getNumericValue(String.valueOf(nums[i]).charAt(0));
        String str = String.valueOf(nums[j]);
        int b = Character.getNumericValue(str.charAt(str.length() - 1));
        if (gcd(a,b) == 1) {
          res++;
        }
      }
    }
    return res;
  }

  private static int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  // -----------------------------------------------------------------------------------------------------------

  // leetcode-73. 矩阵置零
  // 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
  // 标记数组
  public static void setZeroes(int[][] matrix) {
    boolean[] row = new boolean[matrix.length];
    boolean[] col = new boolean[matrix[0].length];
    for (int i = 0; i < matrix.length; i++){
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == 0) {
          row[i] = col[j] = true;
        }
      }
    }
    for (int i = 0; i < matrix.length; i++){
      for (int j = 0; j < matrix[0].length; j++) {
        if (row[i] || col[j]) {
          matrix[i][j] = 0;
        }
      }
    }
  }

  // leetcode-20. 有效的括号
  // 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
  // 有效字符串需满足：
  // 左括号必须用相同类型的右括号闭合。
  // 左括号必须以正确的顺序闭合。
  // 每个右括号都有一个对应的相同类型的左括号。
  public static boolean isValid(String s) {
    int length = s.length();
    if (length % 2 != 0) return false;
    Map<Character, Character> pairs = new HashMap<Character, Character>() {{
      put(')', '(');
      put(']', '[');
      put('}', '{');
    }};
    Deque<Character> stack = new LinkedList<Character>();
    for (int i = 0; i < length; i++) {
      char ch = s.charAt(i);
      if (pairs.containsKey(ch)) {
        if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
          return false;
        }
        stack.pop();
      } else {
        stack.push(ch);
      }
    }
    return stack.isEmpty();
  }
  // -----------------------------------------------------------------------------------------

  // leetcode-402. 移掉 K 位数字
  // 给你一个以字符串表示的非负整数 num 和一个整数 k ，移除这个数中的 k 位数字，使得剩下的数字最小。请你以字符串形式返回这个最小的数字。
  // 示例 1 ：
  // 输入：num = "1432219", k = 3
  // 输出："1219"
  // 解释：移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219 。
  public static String removeKdigits(String num, int k) {
    Deque<Character> deque = new LinkedList<Character>();
    int length = num.length();
    for (int i = 0; i < length; ++i) {
      char digit = num.charAt(i);
      while (!deque.isEmpty() && k > 0 && deque.peekLast() > digit) {
        deque.pollLast();
        k--;
      }
      deque.offerLast(digit);
    }

    for (int i = 0; i < k; ++i) {
      deque.pollLast();
    }

    StringBuilder ret = new StringBuilder();
    boolean leadingZero = true;
    while (!deque.isEmpty()) {
      char digit = deque.pollFirst();
      if (leadingZero && digit == '0') {
        continue;
      }
      leadingZero = false;
      ret.append(digit);
    }
    return ret.length() == 0 ? "0" : ret.toString();
  }

  // ----------------------------------------------------------------------------------------------------------------

  // leetcode-316. 去除重复字母
  // 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序
  // 最小（要求不能打乱其他字符的相对位置）
  // TODO(有bug)
  public static String removeDuplicateLetters(String s) {
    int[] num = new int[26];
    char[] chars = s.toCharArray();
    boolean[] ch = new boolean[26];
    for (char c : chars) {
      num[c - 'a']++;
    }
    Deque<Character> deque = new LinkedList<Character>();
    for (int i = 0; i < chars.length; ++i) {
      char c = chars[i];
      num[c - 'a']--;
      while (!deque.isEmpty() && !ch[c - 'a'] && c < deque.peek() && num[deque.peek() - 'a'] > 0 ) {
        deque.pop();
      }
      if (!ch[c - 'a']) {
        deque.push(chars[i]);
        ch[c - 'a'] = true;
      }
    }
    StringBuilder str = new StringBuilder();
    while (!deque.isEmpty()) {
      str.append(deque.pollLast());
    }
    return str.toString();
  }

  // ----------------------------------------------------------------------------------------------------------------

  // leetcode-503. 下一个更大元素 II
  // 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
  // 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，
  // 这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
  public static int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];
    Arrays.fill(ans, -1);
    Deque<Integer> st = new ArrayDeque<>();
    for (int i = 0; i < 2 * n; i++) {
      int x = nums[i % n];
      while (!st.isEmpty() && x > nums[st.peek()]) {
        // x 是 nums[st.peek()] 的下一个更大元素
        // 既然 nums[st.peek()] 已经算出答案，则从栈顶弹出
        ans[st.pop()] = x;
      }
      if (i < n) {
        st.push(i);
      }
    }
    return ans;
  }

  // ----------------------------------------------------------------------------------------------------------------

  // leetcode-387. 字符串中的第一个唯一字符
  // 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1
  public static int firstUniqChar(String s) {
    int min = Integer.MAX_VALUE;

    for (char c = 'a'; c <= 'z'; c++) {
      int firstIdx = s.indexOf(c);
      int lastIdx = s.lastIndexOf(c);

      if (firstIdx != -1 && firstIdx == lastIdx) {
        min = Math.min(firstIdx, min);
      }
    }

    return min == Integer.MAX_VALUE? -1 : min;
  }
  // ---------------------------------------------------------------------------------------------------------------\

  // leetcode-26. 删除有序数组中的重复项
  // 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
  // 元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
  // 考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
  // --更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。
  //   nums 的其余元素与 nums 的大小不重要。
  // --返回 k 。
  public static int removeDuplicates(int[] nums) {
    int left = 0, right = 1;
    while (right < nums.length) {
      int val = nums[left];
      while (right < nums.length && nums[left] == nums[right]) {
        right++;
      }
      if (right < nums.length) {
        left++;
        nums[left] = nums[right];
        right++;
      }
    }
    return left + 1;
  }

  // ---------------------------------------------------------------------------------------------------------------


  // leetcode-3101. 交替子数组计数
  // 给你一个二进制数组nums 。
  // 如果一个子数组中 不存在 两个相邻 元素的值相同的情况，我们称这样的子数组为交替子数组 。
  // 返回数组 nums 中交替子数组的数量。
  // 核心思想：枚举交替子数组的右端点下标 i，统计交替子数组的个数，加入答案。
  // 以 nums=[0,1,1,0,1,1] 为例，我们来看看有哪些交替子数组的右端点下标是 i：
  // i	nums[i] 交替子数组	        个数
  // 0	0	[0]	                1
  // 1	1	[1],[0,1]	        2
  // 2	1	[1]	                1
  // 3	0	[0],[1,0]	        2
  // 4	1	[1],[0,1],[1,0,1]	3
  // 5	1	[1]	                1
  // 一般地，如果 nums[i] != nums[i-1], 我们可以把nums[i]加到所有以i−1为右端点的交替子数组的末尾,
  // 所以「以 i 为右端点的交替子数组个数」比「以 i−1 为右端点的交替子数组个数」多 1。
  public static long countAlternatingSubarrays(int[] nums) {
    long ans = 0;
    int cnt = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] != nums[i - 1]) {
        cnt++;
      } else {
        cnt = 1;
      }
      ans += cnt; // 有 cnt 个右端点下标为 i 的交替子数组
    }
    return ans;
  }
  // -----------------------------------------------------------------------------------------------------------------


  // leetcode-125. 验证回文串
  // 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
  // 字母和数字都属于字母数字字符。
  // 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
  // 核心思路：将符合条件的字符存入StringBuilder中，再判断字符串是否对称
  public static boolean isPalindrome(String s) {
    char[] chars = s.toLowerCase().toCharArray();
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < chars.length; i++) {
      if (Pattern.matches("[a-z0-9]", String.valueOf(chars[i]))) {
        str.append(chars[i]);
      }
    }
    int length = str.length();
    for (int j = 0; j < length; j++) {
      if (str.charAt(j) != str.charAt(length - j - 1)) {
        return false;
      }
    }
    return true;
  }
  // -----------------------------------------------------------------------------------------------------------------


  // leetcode-219. 存在重复元素 II
  // 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
  // 如果存在，返回 true ；否则，返回 false 。
  public static boolean containsNearbyDuplicate(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (map.get(nums[i]) != null && Math.abs(map.get(nums[i]) - i) <= k) {
        return true;
      } else {
        map.put(nums[i], i);
      }
    }
    return false;
  }
  // -----------------------------------------------------------------------------------------------------------------


  // leetcode-1958. 检查操作是否合法
  // 给你一个下标从 0 开始的 8 x 8 网格 board ，其中 board[r][c] 表示游戏棋盘上的格子 (r, c) 。
  // 棋盘上空格用 '.' 表示，白色格子用 'W' 表示，黑色格子用 'B' 表示。
  // 游戏中每次操作步骤为：选择一个空格子，将它变成你正在执行的颜色（要么白色，要么黑色）。但是，合法操作必须满足：
  //         涂色后这个格子是好线段的一个端点 （好线段可以是水平的，竖直的或者是对角线）。
  // 好线段指的是一个包含三个或者更多格子（包含端点格子）的线段，线段两个端点格子为同一种颜色 ，
  // 且中间剩余格子的颜色都为另一种颜色 （线段上不能有任何空格子）。你可以在下图找到好线段的例子：
  // 好线段例子：
  //         1、 B   W   W   B
  public static boolean checkMove(char[][] board, int rMove, int cMove, char color) {
    // 向上方
    int count = 1;
    int rowIndex = rMove - 1;
    int colIndex = cMove - 1;
    while (rowIndex >= 0 && colIndex >= 0) {
      char ch = board[rowIndex][colIndex];
      if (ch != '.' && ch != color) {
        count++;
      }
//            if () {
//
//            }
      rowIndex--;
      colIndex--;
    }
    return false;
  }
  // -----------------------------------------------------------------------------------------------------------------


  // leetcode-2970. 统计移除递增子数组的数目 I
  // 给你一个下标从0开始的正整数数组 nums 。如果 nums 的一个子数组满足：移除这个子数组后剩余元素严格递增 ，
  // 那么我们称这个子数组为移除递增子数组。比方说，[5, 3, 4, 6, 7] 中的 [3, 4] 是一个移除递增子数组，
  // 因为移除该子数组后，[5, 3, 4, 6, 7] 变为 [5, 6, 7] ，是严格递增的。
  // 请你返回 nums 中 移除递增 子数组的总数目。注意 ，剩余元素为空的数组也视为是递增的。
  // 子数组指的是一个数组中一段连续的元素序列。
  public static int incremovableSubarrayCount(int[] nums) {
    int res = 0;
    for (int i = 0; i < nums.length; i++) {

    }
    return res;
  }
}
