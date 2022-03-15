package com.slin.study.kotlin.algorithms

import com.slin.study.kotlin.samples.generics.max
import org.junit.Test
import java.util.*

/**
 * 二叉树测试
 *
 * 参考：
 * [打遍天下二叉树.md](https://github.com/xfhy/Android-Notes/blob/master/Blogs/Algorithm/%E6%89%93%E9%81%8D%E5%A4%A9%E4%B8%8B%E4%BA%8C%E5%8F%89%E6%A0%91.md)
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/3/15
 */

class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
) {
    fun print() {
        print("$value ")
    }

    override fun toString(): String {
        return "$value"
    }
}

/**
 *          0
 *      1       2
 *    3   4   5   6
 *   7 8 9 10
 */
private val root = TreeNode(
    0,
    TreeNode(
        1,
        TreeNode(3, TreeNode(7), TreeNode(8)),
        TreeNode(4, TreeNode(9), TreeNode(10))
    ),
    TreeNode(2, TreeNode(5), TreeNode(6))
)

class TreeTest {

    @Test
    fun testPreOrder() {
        preOrderTraversal(root)
    }

    @Test
    fun testInOrder() {
        inOrderTraversal(root)
    }

    @Test
    fun testPostOrder() {
        postOrderTraversal(root)
    }

    @Test
    fun testPreOrderStack() {
        preOrderTraversalWithStack(root)
    }

    @Test
    fun testMiddleOrderStack() {
        middleOrderTraversalWithStack(root)
    }

    @Test
    fun testPostOrderStack() {
        println("${postOrderTraversalWithStack(root)}")
    }

    @Test
    fun testLevelOrderTraversal() {
        levelOrderTraversal(root)
    }

    @Test
    fun testMaxDepth() {
        println(maxDepth(root))
    }

    // 记录每一层的元素
    @Test
    fun testLevelOrderTraversals() {
        println("${levelOrderTraversals(root)}")
    }

}

/*********************** 深度优先遍历 ************************************/
/**
 * 前序遍历
 */
fun preOrderTraversal(node: TreeNode?) {
    node?.let {
        it.print()
        preOrderTraversal(it.left)
        preOrderTraversal(it.right)
    }
}

/**
 * 中序遍历
 */
fun inOrderTraversal(node: TreeNode?) {
    node?.let {
        inOrderTraversal(it.left)
        it.print()
        inOrderTraversal(it.right)
    }
}

/**
 * 后序遍历
 */
fun postOrderTraversal(node: TreeNode?) {
    node?.let {
        postOrderTraversal(it.left)
        postOrderTraversal(it.right)
        it.print()
    }
}

/**
 * 前序遍历——非递归方式
 *
 * 1. 用一个栈来记录访问过的节点
 * 2. 然后从根节点开始往左节点遍历,一直往下,直到左边没有左节点
 * 3. 然后弹栈,继续访问弹出的这个元素的右节点.如果这个右节点有左子树的话,把当前节点当做根节点,继续重复2步骤
 * 4. 直到把所有元素都遍历完成
 *
 */
fun preOrderTraversalWithStack(root: TreeNode) {
    val stack = Stack<TreeNode>()
    var node: TreeNode? = root
    while (node != null || stack.isNotEmpty()) {
        while (node != null) {
            node.print()
            stack.push(node)
            node = node.left
        }
        if (stack.isNotEmpty()) {
            node = stack.pop()
            node = node.right
        }
    }
}

/**
 * 中序遍历——非递归方式
 * 在前序遍历的基础上,只需要稍等改动一下sout的位置即可.从根节点开始找二叉树的最左节点,找到最左节点后访问,
 * 对于每个节点来说,它都是以自己为根的子树的根节点,访问完之后就可以转到右儿子上了.
 */
fun middleOrderTraversalWithStack(root: TreeNode) {
    val stack = Stack<TreeNode>()
    var node: TreeNode? = root
    while (node != null || stack.isNotEmpty()) {
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        if (stack.isNotEmpty()) {
            node = stack.pop()
            node.print()
            node = node.right
        }
    }
}

/**
 * 后序遍历——非递归方式
 * 后序遍历只需要在先序遍历的基础上稍微改改就行了,先序是:中前后,改一下while中的左右压栈顺序就是: 中后前,得到数据之后再反转,即:前后中.就得到了最后的结果
 */
fun postOrderTraversalWithStack(root: TreeNode): List<Int> {
    val list = mutableListOf<Int>()
    val stack = Stack<TreeNode>()

    stack.push(root)
    while (stack.isNotEmpty()) {
        val node = stack.pop()
        list.add(node.value)
        if (node.left != null) {
            stack.push(node.left)
        }
        if (node.right != null) {
            stack.push(node.right)
        }
    }
    list.reverse()
    return list
}

/*********************** 广度优先遍历 ************************************/
/**
 * 广度优先遍历
 * 1. 声明一个队列,将根节点入队
 * 2. 然后将根节点出队,在根节点出队时将根节点的左节点和右节点都入队
 * 3. 循环遍历队列,依次出队,在第2步中的左节点出队时,将自己视为根节点,然后将左右节点入队. 同理,右节点也一样
 * 4. 遍历完队列时,所有节点的左右节点都遍历完了.
 */
fun levelOrderTraversal(root: TreeNode) {
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
        val node = queue.poll()
        node?.let {
            node.print()
            if (node.left != null) {
                queue.offer(node.left)
            }
            if (node.right != null) {
                queue.offer(node.right)
            }
        }
    }
}

/**
 * 二叉树的最大深度
 * 思路: 比较左子树的最大深度和右子树的最大深度,
 * 左子树的最大深度同样也适用于这种思路,右子树的最大深度同样也适用于这种思路
 * 这就很适合递归,在访问到空节点时退出.
 * 最后深度需要+1,因为需要计算上根节点.
 */
fun maxDepth(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    return max(maxDepth(root.left), maxDepth(root.right)) + 1
}

/**
 * 记录每一层的元素
 */
fun levelOrderTraversals(root: TreeNode): List<List<TreeNode>> {
    val tree: MutableList<List<TreeNode>> = mutableListOf()
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
        val size = queue.size
        val list = mutableListOf<TreeNode>()
        for (i in 0 until size) {
            val node = queue.poll()
            node?.let {
                list.add(it)
                node.left?.apply {
                    queue.offer(this)
                }
                node.right?.apply {
                    queue.offer(this)
                }
            }
        }
        tree.add(list)
    }
    return tree
}






