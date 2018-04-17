package BTree;

import java.io.IOException;
import java.util.*;
import latex_tree.*;

/**
 * Implementation of a B Tree in Java
 *
 * @author Nathaniel Lao
 */
public class BTree<T extends Comparable<T>> {
    private int fanout, numKeys;
    private Node root;

    /**
     * Default Constructor - initializes the fanout, numKeys and root Node.
     * @param fanout
     */
    public BTree(int fanout) {
        this.fanout = fanout;
        numKeys = fanout - 1;
        root = new Node();
    }

    public void generateLatexFile(String fileName) throws IOException {
        LatexTree.makeFile(root,fileName);
    }

    /**
     * Node utility class - implements the LatexTreeNode interface for easy output print
     */
    protected class Node implements LatexTreeNode {
        private List<T> keys;
        private List<Node> children;

        Node() {
            keys = new LinkedList<>();
            children = new LinkedList<>();
        }

        @Override
        public String getDataString() {
            StringBuffer output = new StringBuffer();
            int i = 0;

            while (i < keys.size()) {
                output.append(keys.get(i).toString());
                i++;
                if (i < keys.size())
                    output.append(", ");
            }

            return output.toString();
        }

        @Override
        public List<LatexTreeNode> getChildren() {
            return new LinkedList<>(children);
        }
    }
}
