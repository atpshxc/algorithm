package com.algo.offer;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Offer {
    /*
    在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
    每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
    判断数组中是否含有该整数
     */
    public static boolean Find(int target, int[][] array) {
        if (array.length == 0 || array[0].length == 0) {
            return false;
        }
        int colLen = array[0].length - 1;
        if (target < array[0][0] || target > array[array.length - 1][colLen]) {
            return false;
        }
        int startRow = 0, endRow = array.length;
        while (startRow < endRow) {
            if (search(target, array[startRow])) {
                return true;
            }
            startRow++;
        }
        return false;
    }

    private static boolean search(int target, int[] array) {
        int start = 0, end = array.length - 1;
        int mid = 0;
        while (start <= end) {
            mid = (start + end) / 2;
            if (array[mid] > target) {
                end = mid - 1;
            } else if (array[mid] < target) {
                start = mid + 1;
            } else {
                return true;
            }
        }
        return target == array[mid];
    }

    /*
    请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.
    则经过替换之后的字符串为We%20Are%20Happy。
     */
    public String replaceSpace(StringBuffer str) {
        if (str == null) {
            return null;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.replace(i, i + 1, "%20");
                i += 3;
            }
        }
        return str.toString();
    }

    /*
    输入一个链表，按链表从尾到头的顺序返回一个ArrayList
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> list = new ArrayList();
        fill(list, listNode);
        return list;
    }

    private void fill(ArrayList<Integer> list, ListNode listNode) {
        if (listNode.next == null) {
            list.add(listNode.val);
        } else {
            ListNode cur = listNode;
            fill(list, listNode.next);
            list.add(cur.val);
        }
    }

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /*
    输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
    假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
    例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || pre.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);
        int index = find(in, root.val);

        root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, index + 1), Arrays.copyOfRange(in, 0, index));
        root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, index + 1, pre.length), Arrays.copyOfRange(in, index + 1, in.length));
        return root;
    }

    private int find(int[] in, int val) {
        for (int i = 0; i < in.length; i++) {
            if (val == in[i]) {
                return i;
            }
        }
        return 0;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /*
    用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        int t = stack2.pop();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return t;
    }

    /*
    把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
    输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
    例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
    NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     */
    public int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int t = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < t) {
                return array[i];
            }
        }
        return t;
    }

    public int minNumberInRotateArray1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        if (array.length == 1) {
            return array[0];
        }
        int lo = 0, hi = array.length - 1;
        int mid = (lo + hi) / 2;
        while (array[lo] == array[hi]) {
            lo++;
            hi--;
        }
        while (array[lo] > array[hi]) {
            if (array[lo] <= array[mid]) {
                lo = mid + 1;
            } else if (array[hi] >= array[mid]) {
                hi = mid - 1;
            }
        }
        return array[lo];
    }

    /*
    大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
    n<=39
     */
    HashMap<Integer, Integer> cache = new HashMap<>();

    public int Fibonacci(int n) {
        if (n < 2) return n;
        if (n == 2) return 1;
        int f1, f2;
        Integer t;
        if ((t = cache.get(n - 1)) != null) {
            f1 = t;
        } else {
            f1 = Fibonacci(n - 1);
        }
        if ((t = cache.get(n - 2)) != null) {
            f2 = t;
        } else {
            f2 = Fibonacci(n - 2);
        }
        cache.put(n, f1 + f2);
        return f1 + f2;
    }

    /*
    一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     */
    public int JumpFloor(int target) {
        if (target <= 2) {
            return target;
        }
        int f1, f2;
        Integer t;
        if ((t = cache.get(target - 2)) != null) {
            f1 = t;
        } else {
            f1 = JumpFloor(target - 2);
        }
        if ((t = cache.get(target - 1)) != null) {
            f2 = t;
        } else {
            f2 = JumpFloor(target - 1);
        }
        cache.put(target, f1 + f2);
        return f1 + f2;
    }

    /*
        一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
    f(n) = f(n-1) + f(n-2) + ... + f(1)
    f(n-1) = f(n-2) + f(n-3) + ... + f(1)
    => f(n) = 2 * f(n-1)
     */
    public int JumpFloorII(int target) {
        return 1 << target - 1;
    }

    /*
    我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
    请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     */
    public int RectCover(int target) {
        if (target < 3) {
            return target;
        }
        int f1, f2;
        Integer t;
        if ((t = cache.get(target - 1)) != null) {
            f1 = t;
        } else {
            f1 = RectCover(target - 1);
        }
        if ((t = cache.get(target - 2)) != null) {
            f2 = t;
        } else {
            f2 = RectCover(target - 2);
        }
        cache.put(target, f1 + f2);
        return f1 + f2;
    }

    /*
    输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
     */
    public int NumberOf1(int n) {
        if (n == 0) {
            return 0;
        }
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n >>>= 1;
        }
        return count;
    }

    /*
    给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方.
    保证base和exponent不同时为0
    public double Power(double base, int exponent) {
        return Math.pow(base, exponent);
    }
     */
    public double Power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (base == 0) {
            return 0;
        }
        boolean flag = exponent < 0;
        if (flag) {
            exponent = -exponent;
        }
        double result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return flag ? 1 / result : result;
    }

    /*
    输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
     */
    public void reOrderArray(int[] array) {
        int[] t = new int[array.length];
        int i = 0, j = array.length - 1;
        for (int k = 0; k < array.length; k++) {
            if ((array[k] & 1) == 1) {
                t[i++] = array[k];
            } else {
                t[j--] = array[k];
            }
        }
        j = array.length - 1;
        while (i < j) {
            int x = t[i];
            t[i] = t[j];
            t[j] = x;
            i++;
            j--;
        }
        i = 0;
        while (i < t.length) {
            array[i] = t[i++];
        }
    }

    /*
    输入一个链表，输出该链表中倒数第k个结点。
     */

    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode s = head;
        ListNode f = head;
        //快慢指针法
        while (k-- > 0) {
            if (f == null) {
                return null;
            }
            f = f.next;
        }
        while (f != null) {
            f = f.next;
            s = s.next;
        }
        return s;
    }

    /*
    输入一个链表，反转链表后，输出新链表的表头。
     */
    public ListNode ReverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /*
    输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
     */
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode head = new ListNode(0);
        ListNode t = head;
        while (list1 != null && list2 != null) {
            if (list1.val >= list2.val) {
                t.next = list2;
                list2 = list2.next;
            } else {
                t.next = list1;
                list1 = list1.next;
            }
            t = t.next;
        }
        if (list1 == null) {
            t.next = list2;
        } else {
            t.next = list1;
        }
        return head.next;
    }

    /*
    输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
     */
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }
        return hasSub(root1, root2);
    }

    public boolean hasSub(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 != null) {
            return false;
        }
        if (root2 == null) {
            return true;
        }
        if (root1.val != root2.val) {
            return hasSub(root1.left, root2) || hasSub(root1.right, root2);
        } else if ((root2.left == null || (root1.left != null && root2.left != null && root1.left.val == root2.left.val))
                && (root2.right == null || (root1.right != null && root2.right != null && root1.right.val == root2.right.val))) {
            return hasSub(root1.left, root2.left) && hasSub(root1.right, root2.right);
        } else {
            return hasSub(root1.left, root2) || hasSub(root1.right, root2);
        }
    }

    /*
    操作给定的二叉树，将其变换为源二叉树的镜像。
    二叉树的镜像定义：源二叉树
    	    8
    	   /  \
    	  6   10
    	 / \  / \
    	5  7 9 11
    	镜像二叉树
    	    8
    	   /  \
    	  10   6
    	 / \  / \
    	11 9 7  5
     */
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        Mirror(root.left);
        Mirror(root.right);
    }

    /*
    输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵：
     1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
     */
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }
        ArrayList<Integer> list = new ArrayList<>();
        while (true) {
            for (int i = 0; i < matrix[0].length; i++) {
                list.add(matrix[0][i]);
            }
            if (matrix.length == 1) {
                break;
            }
            int[][] newMatrix = new int[matrix.length - 1][];
            for (int k = 1; k < matrix.length; k++) {
                newMatrix[k - 1] = matrix[k];
            }
            matrix = rotate(newMatrix);
        }
        return list;
    }

    private int[][] rotate(int[][] newMatrix) {
        int[][] matrix = new int[newMatrix[0].length][newMatrix.length];
        int colLen = newMatrix[0].length - 1;
        for (int i = colLen; i >= 0; i--) {
            for (int j = 0; j < newMatrix.length; j++) {
                matrix[colLen - i][j] = newMatrix[j][i];
            }
        }
        return matrix;
    }

    public ArrayList<Integer> printMatrix1(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int x = 0, y = 0, dir = 0;
        int m = matrix.length, n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        ArrayList<Integer> list = new ArrayList<>();
        while (x < m && y < n && !visited[x][y]) {
            list.add(matrix[x][y]);
            visited[x][y] = true;
            if ((x + dx[dir] == m || x + dx[dir] == -1)
                    || (y + dy[dir] == n || y + dy[dir] == -1)
                    || (x + dx[dir] < m && y + dy[dir] < n && visited[x + dx[dir]][y + dy[dir]])) {
                dir = (dir + 1) % 4;
            }
            x += dx[dir];
            y += dy[dir];
        }
        return list;
    }

    /*
    定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
     */
    class StackX {
        class Node {
            int val;
            int min;

            Node(int val, int min) {
                this.val = val;
                this.min = min;
            }
        }

        private Stack<Node> stack = new Stack<>();


        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(new Node(val, val));
            } else {
                Node peek = stack.peek();
                stack.push(new Node(val, Math.min(val, peek.min)));
            }
        }

        public void pop() {
            if (stack.isEmpty()) {
                return;
            }
            stack.pop();
        }

        public int top() {
            return stack.peek().val;
        }

        public int min() {
            return stack.peek().min;
        }
    }

    /*
    输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
    假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
    但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
     */
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || pushA.length == 0 || popA == null || popA.length == 0 || pushA.length != popA.length) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        for (int j = 0; j < popA.length; j++) {
            if (pushA[j] != popA[i]) {
                stack.push(pushA[j]);
            } else {
                i++;
            }
        }
        while (!stack.isEmpty()) {
            if (stack.pop() != popA[i++]) {
                return false;
            }
        }
        return true;
    }

    /*
    从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     */
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList list = new ArrayList();
        if (root == null) {
            return list;
        }
        LinkedList<TreeNode> queue = new LinkedList();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return list;
    }

    /*
    输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
    如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
     */
    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }
        return isTreeBST(sequence, 0, sequence.length - 1);
    }

    private boolean isTreeBST(int[] sequence, int start, int end) {
        if (start >= end) {
            return true;
        }
        int i = start;
        for (; i < end; i++) {
            if (sequence[i] > sequence[end]) {
                break;
            }
        }
        for (int j = i; j < end; j++) {
            if (sequence[j] < sequence[end]) {
                return false;
            }
        }
        return isTreeBST(sequence, start, i - 1) && isTreeBST(sequence, i, end - 1);
    }

    /*
    输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
    路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<Integer> stack = new Stack<>();
        findPath(root, root.val, target, stack, list);
        return list;
    }

    private void findPath(TreeNode node, int sum, int target, Stack<Integer> stack, ArrayList<ArrayList<Integer>> list) {
        stack.push(node.val);
        if (node.left != null) {
            findPath(node.left, sum + node.left.val, target, stack, list);
        }
        if (node.right != null) {
            findPath(node.right, sum + node.right.val, target, stack, list);
        }
        if (node.left == null && node.right == null && sum == target) {
            list.add(new ArrayList<>(stack));
        }
        stack.pop();
    }

    /*
    输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
    返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
     */
    static class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        RandomListNode node = new RandomListNode(pHead.label);
        cloneNext(node, pHead.next);

        RandomListNode head = pHead;
        LinkedList<Integer> list = new LinkedList<>();
        while (head != null) {
            int i = getIndex(pHead, head.random);
            list.offer(i);
            head = head.next;
        }
        head = node;
        while (node != null) {
            RandomListNode t = head;
            Integer index = list.poll();
            while (index-- > 0) {
                t = t.next;
            }
            node.random = t;
            node = node.next;
        }
        return head;
    }

    private void cloneNext(RandomListNode node, RandomListNode next) {
        if (next != null) {
            node.next = new RandomListNode(next.label);
            cloneNext(node.next, next.next);
        }
    }

    private int getIndex(RandomListNode pHead, RandomListNode randomListNode) {
        int i = 0;
        RandomListNode head = pHead;
        while (head != randomListNode) {
            i++;
            head = head.next;
        }
        return i;
    }

    public RandomListNode Clone1(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode p1 = pHead;
        RandomListNode p2 = pHead;
        while (p1 != null) {
            map.put(p1, new RandomListNode(p1.label));
            p1 = p1.next;
        }
        while (p2 != null) {
            if (p2.next != null) {
                map.get(p2).next = map.get(p2.next);
            }
            map.get(p2).random = map.get(p2.random);
            p2 = p2.next;
        }
        return map.get(pHead);
    }

    public RandomListNode Clone2(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        //在原链表节点后复制一个新节点
        RandomListNode p1 = pHead;
        RandomListNode p2;
        while (p1 != null) {
            p2 = p1.next;
            RandomListNode newP1 = new RandomListNode(p1.label);
            p1.next = newP1;
            newP1.next = p2;
            p1 = p2;
        }
        //确认randomNode
        p1 = pHead;
        while (p1 != null) {
            p1.next.random = p1.random.next;
            p1 = p1.next.next;
        }
        //链表拆分
        RandomListNode current = pHead;
        RandomListNode ncurrent = current.next;
        RandomListNode nphead = current.next;

        while (current != null) {
            if (ncurrent.next != null) {
                ncurrent = ncurrent.next = ncurrent.next.next;
                current = current.next.next;
            }
        }
        return nphead;
    }

    /*
    输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        TreeNode node = convertTreeNode(pRootOfTree);
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private TreeNode convertTreeNode(TreeNode node) {
        if (node.left != null) {
            TreeNode left = convertTreeNode(node.left);
            if (left.right != null) {
                left.right.right = node;
                node.left = left.right;
            } else {
                left.right = node;
                node.left = left;
            }
        }
        if (node.right != null) {
            TreeNode right = convertTreeNode(node.right);
            if (right.left != null) {
                node.right = right.left;
                right.left.left = node;
            } else {
                right.left = node;
                node.right = right;
            }
        }
        return node;
    }

    /*
    输入一个字符串,按字典序打印出该字符串中字符的所有排列。
    例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
    输入描述: 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母
     */
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return list;
        }
        if (str.length() == 1) {
            list.add(str);
            return list;
        }
        HashSet<String> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            ArrayList<String> clist = Permutation(sb.append(str).deleteCharAt(i).toString());
            if (clist.size() > 0) {
                for (String s : clist) {
                    set.add(c + s);
                }
            } else {
                set.add(String.valueOf(c));
            }
            sb = new StringBuilder();
        }
        list.addAll(set);
        Collections.sort(list);
        return list;
    }

    /*
    数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
    由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
     */
    public int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.get(array[i]) != null) {
                map.put(array[i], map.get(array[i]) + 1);
            } else {
                map.put(array[i], 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > array.length / 2) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public int MoreThanHalfNum_Solution1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int pre = array[0];
        int count = 1;
        int c = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] != pre) {
                count--;
                if (count == 0) {
                    pre = array[i];
                    count = 1;
                    c++;
                }
            } else {
                count++;
            }
        }
        if (c <= array.length / 2) {
            return pre;
        }
        return 0;
    }

    /*
    输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList list = new ArrayList();
        if (input == null || input.length == 0 || k > input.length || k == 0) {
            return list;
        }
        int[] mins = new int[k];
        mins[0] = input[0];
        int len = 1;
        for (int i = 1; i < input.length; i++) {
            if (len < k) {
                mins[len++] = input[i];
            } else {
                Arrays.sort(mins);
                mins[len - 1] = Math.min(mins[len - 1], input[i]);
            }
        }
        for (int i = 0; i < k; i++) {
            list.add(mins[i]);
        }
        return list;
    }

    public ArrayList<Integer> GetLeastNumbers_Solution1(int[] input, int k) {
        ArrayList list = new ArrayList();
        if (input == null || input.length == 0 || k > input.length || k == 0) {
            return list;
        }
        for (int i = 0; i < k; i++) {
            adjust(input);
            list.add(input[0]);
            input[0] = Integer.MAX_VALUE;
        }
        return list;
    }

    //构造一个大小为k的最大堆，遍历后面元素，如果比堆的最大值小则替换最大值，调整最大堆，适用于数组input特别大的情况
    public ArrayList<Integer> GetLeastNumbers_Solution2(int[] input, int k) {
        ArrayList list = new ArrayList();
        if (input == null || input.length == 0 || k > input.length || k == 0) {
            return list;
        }
        int[] maxHeap = new int[k];
        System.arraycopy(input, 0, maxHeap, 0, k);
        buildMaxHeap(maxHeap);
        for (int i = k; i < input.length; i++) {
            if (input[i] < maxHeap[0]) {
                maxHeap[0] = input[i];
                buildMaxHeap(maxHeap);
            }
        }
        for (int i = 0; i < maxHeap.length; i++) {
            list.add(maxHeap[i]);
        }
        return list;
    }

    private void buildMaxHeap(int[] maxHeap) {
        for (int i = (maxHeap.length - 2) / 2; i >= 0; i--) {
            int parent = i;
            int left = 2 * parent + 1;
            int temp = maxHeap[parent];
            int child = left;
            while (child < maxHeap.length) {
                int right = child + 1;
                if (right < maxHeap.length && maxHeap[right] > maxHeap[child]) {
                    child = right;
                }
                if (maxHeap[parent] > maxHeap[child]) {
                    break;
                }
                maxHeap[parent] = maxHeap[child];
                parent = child;
                child = 2 * parent + 1;
            }
            maxHeap[parent] = temp;
        }
    }

    private void adjust(int[] input) {
        for (int i = (input.length - 2) / 2; i >= 0; i--) {
            adjust(i, input);
        }
    }

    private void adjust(int parent, int[] input) {
        int left = 2 * parent + 1;
        int child = left;
        int temp = input[parent];
        while (child < input.length) {
            int right = child + 1;
            if (right < input.length && input[right] < input[child]) {
                child = right;
            }
            if (input[parent] < input[child]) {
                break;
            }
            input[parent] = input[child];
            parent = child;
            child = 2 * parent + 1;
        }
        input[parent] = temp;
    }

    /*
    HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:在古老的一维模式识别中,
    常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。但是,如果向量中包含负数,是否应该包含某个负数,
    并期望旁边的正数会弥补它呢？例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
    给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int sum = 0;
        int t = 0;
        int i = 0;
        int max = array[0];
        while (i < array.length && array[i] < 0) {
            max = Math.max(max, array[i]);
            i++;
        }
        if (i == array.length) {
            return max;
        }
        for (; i < array.length; i++) {
            if (array[i] < 0 || t < 0) {
                t += array[i];
                if (t > 0) {
                    sum += t;
                    t = 0;
                } else if (t + sum < 0) {
                    t = -sum;
                    continue;
                }
            } else if (t > 0) {
                sum += t;
                t = 0;
            } else {
                sum += array[i];
            }
        }
        return sum;
    }

    public int FindGreatestSumOfSubArray1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i] += array[i - 1] > 0 ? array[i - 1] : 0;
            max = Math.max(max, array[i]);
        }
        return max;
    }

    /*
    求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？
    为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
    ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）。
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        int sum = 0;
        while (n > 0) {
            sum += count(n);
            n--;
        }
        return sum;
    }

    private int count(int n) {
        int sum = 0;
        while (n > 0) {
            if (n % 10 == 1) {
                sum++;
            }
            n /= 10;
        }
        return sum;
    }

    public int NumberOf1Between1AndN_Solution1(int n) {
        int cnt = 0;
        for (int m = 1; m <= n; m *= 10) {
            int a = n / m, b = n % m;
            cnt += (a + 8) / 10 * m + (a % 10 == 1 ? b + 1 : 0);
        }
        return cnt;
    }

    /*
    输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
    例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     */
    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return "";
        }
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        int w = 1;
        while (max / 10 > 0) {
            w++;
            max /= 10;
        }
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            String key = convertNum(numbers[i], w);
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + numbers[i]);
            } else {
                map.put(key, String.valueOf(numbers[i]));
            }
        }
        return map.keySet().stream().sorted().map(key -> map.get(key)).collect(Collectors.joining(""));
    }

    private String convertNum(int number, int w) {
        String s = String.valueOf(number);
        return format("%-" + w + "s", s).replaceAll(" ", "" + s.charAt(s.length() - 1));
    }

    public String PrintMinNumber1(int[] numbers) {
        return Arrays.stream(numbers).mapToObj(i -> String.valueOf(i))
                .sorted((o1, o2) -> (o1 + o2).compareTo(o2 + o1)).collect(Collectors.joining());
    }

    /*
    把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。
    习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
     */
    public int GetUglyNumber_Solution(int index) {
        if (index <= 0) {
            return index;
        }
        int[] result = new int[index];
        result[0] = 1;
        int p2 = 0, p3 = 0, p5 = 0;
        for (int i = 1; i < index; i++) {
            result[i] = Math.min(Math.min(result[p2] * 2, result[p3] * 3), result[p5] * 5);
            if (result[i] == result[p2] * 2) {
                p2++;
            }
            if (result[i] == result[p3] * 3) {
                p3++;
            }
            if (result[i] == result[p5] * 5) {
                p5++;
            }
        }
        return result[index - 1];
    }

    /*
    在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
     */
    public int FirstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        int[] array = new int['z' - 'A' + 1];
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (array[c - 65] == 0) {
                array[c - 65] = i + 1;
            } else if (array[c - 65] > 0) {
                array[c - 65] = -1;
            }
        }
        int k = str.length();
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                k = Math.min(k, array[i]);
            }
        }
        return k - 1;
    }

    /*
    在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
    输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007
    输入描述: 题目保证输入的数组中没有的相同的数字
    数据范围：
	对于%50的数据,size<=10^4
	对于%75的数据,size<=10^5
	对于%100的数据,size<=2*10^5
     */
    public int InversePairs(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            int c = 0;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i]) {
                    c++;
                }
            }
            sum += c;
        }
        return sum % 1000000007;
    }

    public int InversePairs1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] arr = new int[1000000 + 1];
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            arr[array[i]]++;
            for (int j = array[i] + 1; j < arr.length; j++) {
                if (arr[j] != 0) {
                    sum++;
                }
            }
        }
        return sum % 1000000007;
    }

    public int InversePairs2(int[] array) {
        int len = 15625;
        long[] nums = new long[len];
        int k;
        long t;
        long sum = 0;
        long x;
        for (int i = 0; i < array.length; i++) {
            k = array[i] / 64;
            int mod = (array[i] % 64);
            if (mod == 0) {
                x = 1L << 63;
            } else {
                x = 1L << (64 - mod);
            }
            nums[k] = x | nums[k];
            t = (x - 1) & nums[k];
            sum += countOne(t);
            for (int j = k + 1; j < len; j++) {
                sum += countOne(nums[j]);
            }
        }
        return (int) sum;
    }

    private long countOne(long t) {
        int c = 0;
        int s = 64;
        while (s-- > 0) {
            if ((t & 1) == 1) {
                c++;
            }
            t >>>= 1;
        }
        return c;
    }

    private int count;

    public int InversePairs3(int[] array) {
        mergeSort(array, 0, array.length - 1);
        return count;
    }

    private void mergeSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        merge(array, start, mid, end);
    }

    private void merge(int[] array, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int[] temp = new int[end - start + 1];
        int k = 0;
        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
                count = (count + mid - i + 1) % 1000000007;

            }
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= end) {
            temp[k++] = array[j++];
        }
        for (int l = 0; l < k; l++) {
            array[start + l] = temp[l];
        }
    }

    /*
    输入两个链表，找出它们的第一个公共结点。（注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        while (p1 != null) {
            ListNode p2 = pHead2;
            while (p1 != p2 && p2 != null) {
                p2 = p2.next;
            }
            if (p1 == p2) {
                return p1;
            }
            p1 = p1.next;
        }
        return null;
    }

    //让p1/p2遍历相同长度的链表（p1+p2）
    public ListNode FindFirstCommonNode1(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        //如果没有公共节点会死循环，用二个变量控制防止死循环
        boolean b1 = false, b2 = false;
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
            if (p1 == null) {
                if (b1) {
                    return null;
                }
                b1 = true;
                p1 = pHead2;
            }
            if (p2 == null) {
                if (b2) {
                    return null;
                }
                b2 = true;
                p2 = pHead1;
            }
        }
        return p1;
    }

    /*
    统计一个数字在排序数组中出现的次数。
     */
    public int GetNumberOfK(int[] array, int k) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != k) {
                continue;
            }
            count++;
        }
        return count;
    }

    public int GetNumberOfK1(int[] array, int k) {
        int index = Arrays.binarySearch(array, k);
        if (index < 0) return 0;
        int cnt = 1;
        for (int i = index + 1; i < array.length && array[i] == k; i++)
            cnt++;
        for (int i = index - 1; i >= 0 && array[i] == k; i--)
            cnt++;
        return cnt;

    }

    /*
    输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
     */
    public int TreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getHeight(root);
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /*
    输入一棵二叉树，判断该二叉树是否是平衡二叉树
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        if (root == null) {
            return false;
        }
        return Math.abs(getDept(root.left) - getDept(root.right)) <= 1;
    }

    private int getDept(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return Math.max(getDept(node.left), getDept(node.right)) + 1;
    }

    /*
    一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
     */
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            if (set.contains(array[i])) {
                set.remove(array[i]);
            } else {
                set.add(array[i]);
            }
        }
        Integer[] nums = new Integer[2];
        set.toArray(nums);
        num1[0] = nums[0];
        num2[0] = nums[1];
    }

    public void FindNumsAppearOnce1(int[] array, int num1[], int num2[]) {
        int s = array[0];
        for (int i = 1; i < array.length; i++) {
            s ^= array[i];
        }
        int index = 1;
        while ((index & s) == 0) {
            index <<= 1;
        }
        int n1 = 0, n2 = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & index) == 0) {
                n1 ^= array[i];
            } else {
                n2 ^= array[i];
            }
        }
        num1[0] = n1;
        num2[0] = n2;
    }

    /*
    小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。
    但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。
    没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
    现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!

    解：设序列起始值为x，序列长度为k，则x + (x+1) + ...+ (x+k-1)=sum
    利用等差数列求和公式: => ((x + x+k-1) * k)/2 = sum
                        =>  x = (sum*2 + k - k*k)/(2 * k)
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        if ((sum & (sum - 1)) == 0) {
            return list;
        }
        int k = (int) (Math.sqrt(sum << 1));
        while (k >= 2) {
            int a = (sum << 1) + k - k * k;
            int b = k << 1;
            if ((a % b) == 0 && a > b) {
                list.add(getSeq(a / b, k));
            }
            k--;
        }
        return list;
    }

    /*
    n = 2k + 1时，n项连续正数序列的和为S的条件: n & 1 && S / n == 0 解读 逻辑与的左边要求n为奇数，右边要求整个序列的平均数恰好为中间数。
    n = 2k时，n项连续正数序列的和为S的条件：  S % n * 2 == n  解读  S % n 的结果是中间两项左边的那项，乘2刚好是项数。举例，现有S = 39,6个连续正数序列和式能不能为S呢？套用公式，39 % 6 * 2 =6 == 6，我们也知道，这次的序列是 4、5、6、7、8、9，取余的结果为3对应着值为6的那一项，也就是中间项左边的那一项。
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence2(int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int n = (int) (Math.sqrt(sum << 1));
        while (n >= 2) {
            if (((n & 1) == 1 && sum % n == 0) || (sum % n * 2 == n)) {
                ArrayList<Integer> nums = new ArrayList();
                //j用于计数，k用于遍历求值
                for (int j = 0, k = sum / n - (n - 1) / 2; j < n; j++) {
                    nums.add(k++);
                }
                list.add(nums);
            }
            n--;
        }
        return list;
    }

    public ArrayList<ArrayList<Integer>> FindContinuousSequence1(int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int low = 1, high = 2;
        int s;
        int k;
        while (low < high) {
            k = high - low + 1;
            s = (low + high) * k / 2;
            if (s == sum) {
                list.add(getSeq(low, k));
                low++;
            } else if (s < sum) {
                high++;
            } else {
                low++;
            }
        }
        return list;
    }

    private ArrayList<Integer> getSeq(int i, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        while (k-- > 0) {
            list.add(i++);
        }
        return list;
    }

    /*
    输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
     */
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        int start = 0, end = array.length - 1;
        int s;
        ArrayList list = new ArrayList();
        while (start < end) {
            s = array[start] + array[end];
            if (s == sum) {
                list.add(array[start]);
                list.add(array[end]);
                break;
            } else if (s < sum) {
                start++;
            } else {
                end--;
            }
        }
        return list;
    }

    /*
    汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
    对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
    是不是很简单？OK，搞定它！
     */
    public String LeftRotateString(String str, int n) {
        if (n <= 0 || str == null || str.length() == 0) {
            return str;
        }
        n %= str.length();
        return str.substring(n) + str.substring(0, n);
    }

    /*
    牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，
    有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
    正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
     */
    public String ReverseSentence(String str) {
        if (str == null || str.trim().length() <= 1) {
            return str;
        }
        Stack<String> stack = new Stack<>();
        int start = 0;
        int i = 1;
        for (; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                stack.push(str.substring(start, i));
                while (str.charAt(i) == ' ') {
                    stack.push(" ");
                    i++;
                }
                start = i;
            }
        }
        StringBuilder sb = new StringBuilder(str.substring(start, i));
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public String ReverseSentence1(String str) {
        if (str == null || str.trim().length() <= 1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int left = 0, right = 0;
        while (left < str.length()) {
            if (chars[right] == ' ') {
                reverseSentence(chars, left, right - 1);
                right++;
                left = right;
            }
            if (right == str.length() - 1) {
                reverseSentence(chars, left, right);
                break;
            }
            right++;
        }
        reverseSentence(chars, 0, right);
        return String.valueOf(chars);
    }

    private void reverseSentence(char[] chars, int left, int right) {
        while (left < right) {
            char c = chars[left];
            chars[left] = chars[right];
            chars[right] = c;
            left++;
            right--;
        }
    }

    /*
    LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,2个小王(一副牌原本是54张^_^)...
    他随机从中抽出了5张牌,想测测自己的手气,看看能不能抽到顺子,如果抽到的话,他决定去买体育彩票,嘿嘿！！
    “红心A,黑桃3,小王,大王,方片5”,“Oh My God!”不是顺子.....LL不高兴了,他想了想,决定大\小 王可以看成任何数字,
    并且A看作1,J为11,Q为12,K为13。上面的5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),“So Lucky!”。LL决定去买体育彩票啦。
    现在,要求你使用这幅牌模拟上面的过程,然后告诉我们LL的运气如何， 如果牌能组成顺子就输出true，否则就输出false。
    为了方便起见,你可以认为大小王是0。
     */
    public boolean isContinuous(int[] numbers) {
        if (numbers.length == 0) {
            return false;
        }
        Arrays.sort(numbers);
        int zero = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                zero++;
                continue;
            }
            if (i == numbers.length - 1) {
                return true;
            }
            if (numbers[i] == numbers[i + 1]) {
                return false;
            }
            if (numbers[i] + 1 != numbers[i + 1]) {
                if (numbers[i + 1] - numbers[i] > zero + 1) {
                    return false;
                }
                zero -= numbers[i + 1] - numbers[i] - 1;
            }
        }
        return true;
    }

    /*
    每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。
    其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。然后,他随机指定一个数m,让编号为0的小朋友开始报数。
    每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,从他的下一个小朋友开始,
    继续0...m-1报数....这样下去....直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。
    请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)
    如果没有小朋友，请返回-1
     */
    public int LastRemaining_Solution(int n, int m) {
        if (n <= 0) {
            return -1;
        }
        if (n == 1) {
            return 0;
        }
        LinkedList<Integer> queue = new LinkedList();
        int i = 0;
        while (i < n) {
            if ((i + 1) % m != 0) {
                queue.offer(i);
            }
            i++;
        }
        i = (i + 1) % m;
        while (queue.size() != 1) {
            if (i % m != 0) {
                i++;
                queue.offer(queue.poll());
            } else {
                queue.poll();
                i = 1;
            }
        }
        return queue.poll();
    }

    /*
        令f(i)表示有i个人的时候的需要退出的人的编号, 因此f(i) = (f(i-1) + m) % i
     */
    //递归：
    public int LastRemaining_Solution1(int n, int m) {
        if (n <= 0) {
            return -1;
        }
        return n == 1 ? 0 : (LastRemaining_Solution(n - 1, m) + m) % n;
    }

    //递推：
    public int LastRemaining_Solution2(int n, int m) {
        if (n <= 0) {
            return -1;
        }
        int ans = 0;
        for (int i = 2; i <= n; i++) {
            ans = (ans + m) % i;
        }
        return ans;
    }

    /*
    求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     */
    public int Sum_Solution(int n) {
        if (n == 1) {
            return 1;
        }
        return Sum_Solution(n - 1) + n;
    }

    public int Sum_Solution1(int n) {
        int sum = n;
        boolean b = n > 0 && (sum = Sum_Solution(n - 1) + n) > 0;
        return sum;
    }

    /*
    写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
     */
    public int Add(int num1, int num2) {
        int i = 0;
        int s = 0;
        int a, b, c = 0;
        while (i < 32) {
            a = num1 & 1;
            b = num2 & 1;
            if (a == b && a == 1) {
                s |= c << i;
                c = 1;
            } else if (((a | b) & c) == 1) {
                c = 1;
            } else {
                s |= ((a | b | c) << i);
                c = (a | b) & c;
            }
            num1 >>= 1;
            num2 >>= 1;
            i++;
        }
        return s;
    }

    /*
    在计组中，半加器、全加器中：
    两个二进制的相加结果是用一个异或门实现的；
    两个二进制的进位结果是用一个与门来实现的。
     */
    public int Add1(int num1, int num2) {
        return num2 > 0 ? Add1(num1 ^ num2, (num1 & num2) << 1) : num1;
    }

    /*
    将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
     */
    public int StrToInt(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char c;
        int n = 1;
        long s = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            c = str.charAt(i);
            if (c < '0' || c > '9') {
                if (i == 0) {
                    if (c == '-') {
                        return (int) -s;
                    } else if (c == '+') {
                        return (int) s;
                    }
                }
                return 0;
            }
            s += (c - '0') * n;
            if (s > Integer.MAX_VALUE) {
                if (str.charAt(0) == '-' && -s == Integer.MIN_VALUE) {
                    return (int) -s;
                }
                return 0;
            }
            n *= 10;
        }
        return (int) s;
    }

    /*
    在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。
    也不知道每个数字重复几次。请找出数组中任意一个重复的数字。 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，
    那么对应的输出是第一个重复的数字2。
     */
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (numbers == null || numbers.length <= 1) {
            return false;
        }
        boolean[] flags = new boolean[length];
        for (int i = 0; i < length; i++) {
            if (!flags[numbers[i]]) {
                flags[numbers[i]] = true;
            } else {
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }

    /*
    给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。
    不能使用除法。（注意：规定B[0] = A[1] * A[2] * ... * A[n-1]，B[n-1] = A[0] * A[1] * ... * A[n-2];）
    题解：
    设C[i]=A[0]*A[1]*...*A[i-1], D[i]=A[i+1]*[i+2]*...*A[n-1]
    B[i] = C[i] * D[i]
    如下图设A[i]=1, 1左边为C[i], 右边为D[i]
    对于左下角：B0=1, B1=B0*A0, B2=B1*A1, ... Bi=Bi-1 * Ai-1
    对于右下角：Bn-1=1, Bn-2=Bn-1 * An-1...Bi=Bi+1 * Ai+1
    ______________________
 B0 |1|A1|A2|A3|..|..|An-1|
 B1 |A0|1|A2|A3|..|..|An-1|
 B2 |A0|A1|1|__|__|__|..|
  . |..|__|__|_1_|__|__|..|
  . |..|__|__|__|_1_|__|An-1|
  . |..|__|__|__|__|_1_|An-1|
Bn-1 |A0|A1|A2|A3|..|An-2|1|

     */
    public int[] multiply(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }
        int len = A.length;
        int[] result = new int[len];
        result[0] = 1;
        for (int i = 1; i < len; i++) {
            result[i] = result[i - 1] * A[i - 1];
        }
        int temp = 1;
        for (int i = len - 1; i >= 0; i--) {
            result[i] = temp * result[i];
            temp *= A[i];
        }
        return result;
    }

    /*
    请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
    在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
     */
    public boolean match(char[] str, char[] pattern) {
        return matchStr(str, 0, pattern, 0);
    }

    private boolean matchStr(char[] str, int i, char[] pattern, int j) {
        if (i == str.length && j == pattern.length) {
            return true;
        } else if (j == pattern.length) {
            return false;
        }
        boolean next = j + 1 < pattern.length && pattern[j + 1] == '*';
        if (next) {
            if (i < str.length && (str[i] == pattern[j] || pattern[j] == '.')) {
                return matchStr(str, i, pattern, j + 2) || matchStr(str, i + 1, pattern, j);
            } else {
                return matchStr(str, i, pattern, j + 2);
            }
        } else {
            if (i < str.length && (str[i] == pattern[j] || pattern[j] == '.')) {
                return matchStr(str, i + 1, pattern, j + 1);
            } else {
                return false;
            }
        }
    }

    /*
    请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
    但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
     */
    public boolean isNumeric(char[] str) {
        boolean e = false;
        boolean point = false;
        if (str == null || str.length == 0) {
            return false;
        }
        if (str.length == 1 && (str[0] < '0' || str[0] > '9')) {
            return false;
        }
        char c;
        for (int i = 0; i < str.length; i++) {
            c = str[i];
            if (c == '.') {
                if (e || i == 0 || i == str.length - 1 || point || str[i + 1] < '0' || str[i + 1] > '9') {
                    return false;
                }
                point = true;
            } else if (c == 'E' || c == 'e') {
                if (i == 0 || i == str.length - 1 || e || (str[i + 1] != '-' && str[i + 1] != '+' && (str[i + 1] < '0' || str[i + 1] > '9'))) {
                    return false;
                }
                e = true;
            } else if (c == '+') {
                if (i != 0 && str[i - 1] != 'e' && str[i - 1] != 'E') {
                    return false;
                }
            } else if (c == '-') {
                if ((i != 0 && str[i - 1] != 'e' && str[i - 1] != 'E') || i == str.length - 1 || ((str[i + 1] < '0' || str[i + 1] > '9') && str[i + 1] != '.')) {
                    return false;
                }
            } else if (str[i] < '0' || str[i] > '9') {
                return false;
            }
        }
        return true;
    }

    /*
    请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前两个字符"go"时，
    第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
    如果当前字符流没有存在出现一次的字符，返回#字符。
     */
    LinkedList<Character> list = new LinkedList();

    //Insert one char from stringstream
    public void Insert(char ch) {
        if (list.contains(ch)) {
            list.remove((Object) ch);
        } else {
            list.offer(ch);
        }
    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        if (list.isEmpty()) {
            return '#';
        }
        return list.peek();
    }

    /*
    给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。

    题解：设起点到入口点距离为a，入口点到相遇点为b，相遇点再到入口点为c，则相遇时慢指针走了a+b, 快指针走了a+b+c+b
    同时因为快指针是慢指针速度的2倍，所以快指针走的距离可以表示为2倍的慢指针走的，也就是2*(a+b),
    因此 2*(a+b) == a+b+c+b     =>  a==c
    因为a==c，这时只要另一个慢指针从起点出发，原来的慢指针从相遇点出发，当二个慢指针相遇时就是入口点了
     */
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return null;
        }
        ListNode slow = pHead;
        ListNode quick = pHead;
        while (quick != null && quick.next != null) {
            slow = slow.next;
            quick = quick.next.next;
            if (quick == slow) {
                break;
            }
        }
        ListNode slow1 = pHead;
        while (slow != slow1) {
            slow = slow.next;
            slow1 = slow1.next;
        }
        return slow;
    }

    /*
    在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
    例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
     */
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) {
            return null;
        }
        if (pHead.next == null) {
            return pHead;
        }
        ListNode pre = pHead;
        ListNode cur = pre.next;
        boolean flag = false;
        while ((cur != null && pre.val == cur.val) || (flag && cur != null && cur.next != null && cur.val == cur.next.val)) {
            flag = true;
            pre = cur;
            cur = cur.next;
        }
        ListNode head;
        if (flag) {
            head = cur;
        } else {
            head = pre;
        }
        pre = head;
        while (pre != null && pre.next != null) {
            pre.next = deleteDuplication(pre.next);
            pre = pre.next;
        }
        return head;
    }

    /*
    给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     */
    class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }
        if (pNode.right != null) {
            return getLeft(pNode.right);
        } else if (pNode.next != null && pNode.next.left == pNode) {
            return pNode.next;
        } else {
            TreeLinkNode parent = pNode.next;
            if (parent == null) {
                return null;
            }
            TreeLinkNode pparent = parent.next;
            if (pparent.left == parent) {
                return pparent;
            } else if (pparent.next == null) {
                return null;
            } else {
                return pparent.next.left;
            }
        }
    }

    private TreeLinkNode getLeft(TreeLinkNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /*
    请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
     */
    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return true;
        }
        TreeNode root = pRoot;
        if (root.left == null && root.right == null) {
            return true;
        }
        ArrayList<TreeNode> list = new ArrayList();
        list.add(root.left);
        list.add(root.right);
        while (!list.isEmpty()) {
            int size = list.size();
            int i = 0, j = size - 1;
            ArrayList<TreeNode> nodes = new ArrayList();
            while (i < j) {
                TreeNode left = list.get(i++);
                TreeNode right = list.get(j--);
                if (left.val != right.val) {
                    return false;
                }
                if (left.left != null) {
                    if (right.right == null) {
                        return false;
                    }
                    nodes.add(left.left);
                }
                if (left.right != null) {
                    if (right.left == null) {
                        return false;
                    }
                    nodes.add(left.right);
                }
                if (right.left != null) {
                    if (left.right == null) {
                        return false;
                    }
                    nodes.add(right.left);
                }
                if (right.right != null) {
                    if (left.left == null) {
                        return false;
                    }
                    nodes.add(right.right);
                }
            }
            list = nodes;
        }
        return true;
    }

    boolean isSymmetrical1(TreeNode pRoot) {
        return pRoot == null || isSymmetrical(pRoot.left, pRoot.right);
    }

    private boolean isSymmetrical(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else if (left.val != right.val) {
            return false;
        }
        return isSymmetrical(left.left, right.right) && isSymmetrical(left.right, right.left);
    }

    /*
    请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，
    第三行按照从左到右的顺序打印，其他行以此类推。
     */
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<TreeNode>> list = new ArrayList<>();
        if (pRoot == null) {
            return new ArrayList<>();
        }
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(pRoot);
        list.add(nodes);
        boolean left = false;
        while (!list.isEmpty()) {
            nodes = new ArrayList<>();
            ArrayList<TreeNode> treeNodes = list.get(list.size() - 1);
            if (left) {
                for (int i = treeNodes.size() - 1; i >= 0; i--) {
                    TreeNode node = treeNodes.get(i);
                    if (node.left != null) {
                        nodes.add(node.left);
                    }
                    if (node.right != null) {
                        nodes.add(node.right);
                    }
                }
            } else {
                for (int i = treeNodes.size() - 1; i >= 0; i--) {
                    TreeNode node = treeNodes.get(i);
                    if (node.right != null) {
                        nodes.add(node.right);
                    }
                    if (node.left != null) {
                        nodes.add(node.left);
                    }
                }
            }
            if (nodes.isEmpty()) {
                break;
            }
            list.add(nodes);
            left = !left;
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (ArrayList<TreeNode> lists : list) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (TreeNode node : lists) {
                arrayList.add(node.val);
            }
            result.add(arrayList);
        }
        return result;
    }

    /*
    从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行
     */
    ArrayList<ArrayList<Integer>> Print1(TreeNode pRoot) {
        ArrayList<ArrayList<TreeNode>> list = new ArrayList<>();
        if (pRoot == null) {
            return new ArrayList<>();
        }
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(pRoot);
        list.add(nodes);
        while (!list.isEmpty()) {
            nodes = new ArrayList<>();
            ArrayList<TreeNode> treeNodes = list.get(list.size() - 1);
            for (TreeNode node : treeNodes) {
                if (node.left != null) {
                    nodes.add(node.left);
                }
                if (node.right != null) {
                    nodes.add(node.right);
                }
            }
            if (nodes.isEmpty()) {
                break;
            }
            list.add(nodes);
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (
                ArrayList<TreeNode> lists : list) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (TreeNode node : lists) {
                arrayList.add(node.val);
            }
            result.add(arrayList);
        }
        return result;
    }

    /*
    请实现两个函数，分别用来序列化和反序列化二叉树
    二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。
    序列化可以基于先序、中序、后序、层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号表示空节点（#），
    以 ！ 表示一个结点值的结束（value!）。
    二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。
     */
    String Serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        StringBuilder sb = new StringBuilder();
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            ArrayList<TreeNode> list = new ArrayList<>();
            for (TreeNode node : nodes) {
                if (node == null) {
                    sb.append("#");
                    continue;
                } else {
                    sb.append(node.val).append("!");
                }
                if (node.left == null) {
                    list.add(null);
                } else {
                    list.add(node.left);
                }
                if (node.right == null) {
                    list.add(null);
                } else {
                    list.add(node.right);
                }
            }
            nodes = list;
            if (nodes.stream().allMatch(node -> node == null)) {
                break;
            }
        }
        return sb.toString();
    }

    TreeNode Deserialize(String str) {
        if (str == "#") {
            return null;
        }
        ArrayList<String> vals = new ArrayList<>();
        String[] vs = str.split("!");
        for (String s : vs) {
            for (String v : s.split("#")) {
                vals.add(v);
            }
        }
        int i = 0;
        ArrayList<TreeNode> list = new ArrayList<>();
        TreeNode root = new TreeNode(Integer.valueOf(vals.get(i)));
        list.add(root);
        while (i < vals.size() - 1) {
            ArrayList<TreeNode> lists = new ArrayList<>();
            for (TreeNode node : list) {
                if (i + 1 < vals.size()) {
                    String s = vals.get(++i);
                    if (!s.equals("")) {
                        node.left = new TreeNode(Integer.valueOf(s));
                        lists.add(node.left);
                    }
                    if (i + 1 < vals.size()) {
                        s = vals.get(++i);
                        if (!s.equals("")) {
                            node.right = new TreeNode(Integer.valueOf(s));
                            lists.add(node.right);
                        }
                    }
                }
            }
            list = lists;
        }
        return root;
    }

    /*
    给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4
     */
    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(pRoot);
        TreeNode node = pRoot;
        while (node.left != null) {
            stack.push(node.left);
            node = node.left;
        }
        TreeNode pop = null;
        while (k-- > 0) {
            pop = stack.pop();
            if (stack.isEmpty()) {
                break;
            }
            if (pop.right != null) {
                stack.push(pop.right);
            }
        }
        if (k > 0) {
            return KthNode(pop.right, k);
        }
        return pop;
    }

    /*
    如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
    如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，
    使用GetMedian()方法获取当前读取数据的中位数
     */
    PriorityQueue<Integer> low = new PriorityQueue<>();
    PriorityQueue<Integer> high = new PriorityQueue<>((o1, o2) -> o2 - o1);

    public void Insert(Integer num) {
        count++;
        if ((count & 1) == 1) {
            low.offer(num);
            high.offer(low.poll());
        } else {
            high.offer(num);
            low.offer(high.poll());
        }
    }

    public Double GetMedian() {
        if ((count & 1) == 1) {
            return Double.valueOf(high.peek());
        } else {
            return (high.peek() + low.peek()) / 2.0;
        }
    }

    /*
    给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
    那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
    {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
     */
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        if (num == null || size <= 0 || size > num.length) {
            return list;
        }
        int i = 0, j = 0;
        int index = 0;
        while (j - i + 1 <= size && j < num.length) {
            if (num[j] > num[index]) {
                index = j;
            }
            j++;
        }
        j--;
        list.add(num[index]);
        while (j < num.length) {
            i++;
            j++;
            if (j == num.length) {
                break;
            } else if (i > index) {
                index = findMaxIndex(num, i, j);
                list.add(num[index]);
            } else {
                if (num[j] > num[index]) {
                    index = j;
                }
                list.add(num[index]);
            }
        }
        return list;
    }

    private int findMaxIndex(int[] num, int i, int j) {
        int max = i;
        for (int k = i + 1; k <= j; k++) {
            if (num[k] > num[max]) {
                max = k;
            }
        }
        return max;
    }

    /*
    请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
    每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
    例如
    a b c e​
    s f c s
    a d e e
   矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，
   路径不能再次进入该格子。
     */
    class StackFrame {
        private int k;
        private int start;
        private boolean[] visited;

        StackFrame(int k, int start, boolean[] visited) {
            this.k = k;
            this.start = start;
            this.visited = visited;
        }
    }

    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || str == null || matrix.length < str.length) {
            return false;
        }

        for (int i = 0; i < matrix.length; i++) {
            //确认起点
            int k = 0;
            if (matrix[i] == str[k]) {
                Stack<StackFrame> stack = new Stack<>();
                stack.push(new StackFrame(k, i, new boolean[matrix.length]));
                while (!stack.isEmpty()) {
                    StackFrame stackFrame = stack.pop();
                    boolean[] visited = stackFrame.visited;
                    int start = stackFrame.start;
                    k = stackFrame.k;
                    visited[start] = true;
                    while (canMove(++k, start, str, matrix, visited, rows, cols, stack)) {
                        StackFrame next = stack.pop();
                        start = next.start;
                        k = next.k;
                        visited = next.visited;
                        visited[start] = true;
                        if (k >= str.length) {
                            return true;
                        }
                    }
                    if (k >= str.length) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canMove(int k, int start, char[] str, char[] matrix, boolean[] visited, int rows, int cols, Stack<StackFrame> stack) {
        if (k >= str.length) {
            stack.push(new StackFrame(k, start, visited));
            return true;
        }
        int row = start / cols;
        int col = start % cols;
        if (row + 1 < rows && !visited[(row + 1) * cols + col] && matrix[(row + 1) * cols + col] == str[k]) {
            stack.push(new StackFrame(k, (row + 1) * cols + col, visited.clone()));
        }
        if (row - 1 >= 0 && !visited[(row - 1) * cols + col] && matrix[(row - 1) * cols + col] == str[k]) {
            stack.push(new StackFrame(k, (row - 1) * cols + col, visited.clone()));
        }
        if (col + 1 < cols && !visited[row * cols + col + 1] && matrix[row * cols + col + 1] == str[k]) {
            stack.push(new StackFrame(k, row * cols + col + 1, visited.clone()));
        }
        if (col - 1 >= 0 && !visited[row * cols + col - 1] && matrix[row * cols + col - 1] == str[k]) {
            stack.push(new StackFrame(k, row * cols + col - 1, visited.clone()));
        }
        return !stack.isEmpty();
    }

    boolean[] visited;

    public boolean hasPath1(char[] matrix, int rows, int cols, char[] str) {
        visited = new boolean[matrix.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (hasPath(matrix, rows, cols, i, j, str, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPath(char[] matrix, int rows, int cols, int row, int col, char[] str, int len) {
        if (matrix[row * cols + col] != str[len] || visited[row * cols + col]) {
            return false;
        }
        if (len == str.length - 1) {
            return true;
        }
        visited[row * cols + col] = true;
        if (row + 1 < rows && hasPath(matrix, rows, cols, row + 1, col, str, len + 1)) {
            return true;
        }
        if (row - 1 >= 0 && hasPath(matrix, rows, cols, row - 1, col, str, len + 1)) {
            return true;
        }
        if (col + 1 < cols && hasPath(matrix, rows, cols, row, col + 1, str, len + 1)) {
            return true;
        }
        if (col - 1 >= 0 && hasPath(matrix, rows, cols, row, col - 1, str, len + 1)) {
            return true;
        }
        visited[row * cols + col] = false;
        return false;
    }

    /*
    地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
    但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
    但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
     */
    private final int[] dx = {1, -1, 0, 0};
    private final int[] dy = {0, 0, 1, -1};

    //dfs深度遍历
    public int movingCount(int threshold, int rows, int cols) {
        if (threshold < 0 || rows <= 0 || cols <= 0) {
            return 0;
        }
        boolean[][] visited = new boolean[rows][cols];
        return move(threshold, 0, 0, rows, cols, visited) + 1;
    }

    private int move(int threshold, int row, int col, int rows, int cols, boolean[][] visited) {
        visited[row][col] = true;
        int ans = 0;
        for (int i = 0; i < dx.length; i++) {
            int x = row + dx[i];
            int y = col + dy[i];
            if (x >= 0 && x < rows && y >= 0 && y < cols && !visited[x][y] && (getSum(x, y) <= threshold)) {
                ans += move(threshold, x, y, rows, cols, visited) + 1;
            }
        }
        return ans;
    }

    private int getSum(int i, int j) {
        int s = 0;
        while (i > 0) {
            s += i % 10;
            i /= 10;
        }
        while (j > 0) {
            s += j % 10;
            j /= 10;
        }
        return s;
    }


    class Cell {
        int x;
        int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //bfs广度遍历
    public int movingCount1(int threshold, int rows, int cols) {
        if (threshold < 0 || rows <= 0 || cols <= 0) {
            return 0;
        }
        int count = 1;
        boolean[][] visited = new boolean[rows][cols];
        visited[0][0] = true;
        LinkedList<Cell> queue = new LinkedList();
        queue.offer(new Cell(0, 0));
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            int x = cell.x, y = cell.y;
            for (int i = 0; i < dx.length; i++) {
                x = x + dx[i];
                y = y + dy[i];
                if (x >= 0 && x < rows && y >= 0 && y < cols && !visited[x][y] && (getSum(x, y) <= threshold)) {
                    visited[x][y] = true;
                    count++;
                    queue.offer(new Cell(x, y));
                }
            }
        }
        return count;
    }

    /*
    给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1），每段绳子的长度记为k[0],k[1],...,k[m]。
    请问k[0]xk[1]x...xk[m]可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
    （2 <= n <= 60）
     */
    public int cutRope(int target) {
        return cutRope(target, 2);
    }

    private int cutRope(int target, int i) {
        if (i > target) {
            return 0;
        }
        int ans;
        int len = target / i;
        if (target % i == 0) {
            ans = (int) Math.pow(len, i);
        } else {
            ans = (int) (Math.pow(len, i - target % i) * Math.pow(len + 1, target % i));
        }
        return Math.max(cutRope(target, i + 1), ans);
    }

    /*
    数学公式求解：
    a1+a2+...+an=n
    a1*a2*...*an<=((a1+a2+...+an)/m)^m=(n/m)^m
    设f(m)=(n/m)^m=e^(m*ln(n/m))
    f'(m) = (ln(m)-ln(n)+1)*e^(m*ln(n/m))
    令f'(m) = 0  得到极值 m=n/e
     */
    public int cutRope1(int target) {
        if (target == 2) {
            return 1;
        }
        int m = (int) (target * 1.0 / Math.E);
        int x = target / m;
        int y = target / (m + 1);
        int ansx = (int) (Math.pow(x, m - 1) * (target - Math.pow(x, m - 1)));
        int ansy = (int) (Math.pow(y, m) * (target - Math.pow(y, m)));
        int z, ansz;
        if (m > 2) {
            z = target / (m - 2);
            ansz = (int) (Math.pow(z, m - 2) * (target - Math.pow(z, m - 2)));
            return Math.max(ansx, Math.max(ansy, ansz));
        }
        return Math.max(ansx, ansy);
    }

    public static void main(String[] args) {
//        Offer offer = new Offer();
//        int[] a = {2, 3, 4, 2, 6, 2, 5, 1};
//        TreeNode node = new TreeNode(8);
//        TreeNode node1 = new TreeNode(6);
//        TreeNode node2 = new TreeNode(10);
//        TreeNode node3 = new TreeNode(5);
//        TreeNode node4 = new TreeNode(7);
//        TreeNode node5 = new TreeNode(9);
//        TreeNode node6 = new TreeNode(11);
//        node.left = node1;
//        node.right = node2;
//        node1.left = node3;
//        node1.right = node4;
//        node2.left = node5;
//        node2.right = node6;
//        int s = offer.movingCount1(15, 3, 3);
//        System.out.println(s);

        ListNode node = new ListNode(1);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(3);
        node.next = node1;
        node1.next = node2;
        ListNode pre = null;
        ListNode head = node;
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
    }

}