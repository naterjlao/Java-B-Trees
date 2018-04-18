package BTree;

import java.io.IOException;
import java.util.*;
import latex_tree.*;

/**
 * Implementation of a B-Tree in Java. A B-Tree is a generalized form of a 2-3 Tree, whereas the maximum number
 * of children a Node can have is determined by the FANOUT constant specified in the Tree constructor. For every
 * child, there must exist a separator key-value that determines the position of the child and holds the information
 * of the current Node. For every Node, there can be, at most, FANOUT - 1 key values stored. The type of keys that
 * can be stored in the Tree must be Comparable in order to be stored. Management of the Tree is processed through
 * key rotations with siblings, merging and splitting, and recursive handling.
 *
 * There exists dependencies for latex_tree package used for displaying the B-Tree in Latex code.
 *
 * @author Nathaniel Lao
 * @github naterjlao
 */
public class BTree<T extends Comparable<T>> {
    /* Utility Exception */
    private static final RuntimeException UNIMPLEMENTED_EXCEPTION = new RuntimeException("method not implemented");

    /* Private Fields */
    private final int FANOUT;
    private int size;
    private Node root;

    /**
     * Default Constructor - initializes the fanout, numKeys and root Node.
     * @param fanOut the maximum amount of Nodes that a Node can have as children.
     */
    public BTree(int fanOut) {
        FANOUT = fanOut;
        size = 0;
        root = new Node();
    }

    /**
     * @return the number of Nodes present in the Tree
     */
    public int getSize() {
        return size;
    }

    /**
     * Inserts the given key into to Tree following the rules of a B-Tree
     * @param key object of type T to be inserted
     */
    public void insert(T key) {
        root.insert(key);
    }

    /**
     * Node utility class - implements the LatexTreeNode interface for easy output print
     */
    protected class Node implements LatexTreeNode {
        private List<T> keys;
        private List<Node> children;
        private boolean isLeaf;

        Node() {
            keys = new LinkedList<>();
            children = new LinkedList<>();
            isLeaf = true;
        }

        /**
         * Inserts the key to the current subtree. If in the case that the insertion is
         * not possible, the subtree is not affected and the method returns false.
         *
         * @param key object of type <code>T</code> to be inserted into the subtree
         * @return true if the insertion was successful, false if otherwise
         */
        boolean insert(T key) {
            if (isLeaf) {
                //TODO update the size counter
                return insertKeyList(key);
            } else {
                //TODO update the size counter
                //TODO handle non-leaf Nodes
                throw UNIMPLEMENTED_EXCEPTION;
            }
        }

        /**
         * Inserts the given key into the keys List in order. If the key list is full,
         * the list is unaffected and false is returned.
         *
         * @param key object of type T to be inserted in the keys list
         * @return true if the insertion was successful, false otherwise
         */
        boolean insertKeyList(T key) {
            if (!maxChildren()) {
                //TODO Create an insertion method
                return true;
            } else
                return false;
        }

        /**
         * @return true if the maximum amount of children for the current Node has been
         * reached: (i.e. FANOUT - 1)
         */
        boolean maxChildren() {
            return children.size() >= FANOUT - 1;
        }

        /* ******** LatexTreeNode Implementation Methods ************/

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

    /**
     * Generates a latex tree file of the current tree.
     *
     * @param fileName name of the output file
     * @throws IOException if an file exception occurs
     */
    public void generateLatexFile(String fileName) throws IOException {
        LatexTree.makeFile(root,fileName);
    }
}
