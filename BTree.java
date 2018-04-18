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
    private final int FANOUT; // the number of children a Node may have
    private final int HOPS; // the maximum number of nearest children that can be used for overflow resolution
    private int size;
    private Node root;

    /**
     * Default Constructor - initializes the FANOUT, HOPS, size and root Node.
     * @param fanOut the maximum amount of Nodes that a Node can have as children
     * @param hops the maximum number of nearest children that can be used for overflow
     *             resolution through key rotations
     */
    public BTree(int fanOut, int hops) {
        FANOUT = fanOut;
        HOPS = hops;
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

        /**
         * Default constructor
         */
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
                /* If the Node is a leaf, try to insert the key into keylist if
                * there is an opening */
                return keyListInsert(key);
            } else {
                /* Find the target child key and Node */
                int targetIndex = keyIndex(key);
                Node target = children.get(targetIndex);

                /* Attempt to insert the key to the target child Node */
                if (target.insert(key)) {
                    return true;
                }
                /* If unsuccessful, manage key rotations */
                else {
                    //TODO manage key rotation
                    throw UNIMPLEMENTED_EXCEPTION;

                }
            }
        }

        /* ******** UTILITY METHODS ***********/

        /**
         * Finds the key index of the current Node where the key is either equal to
         * or greater than the given key. This method can be used for key insertion, by
         * adding the new key into the keys list using the index; or for Node traversal,
         * the index returned is the same position for the Node that would contain the
         * input data.
         *
         * @param key Input object to be compared to
         * @return The index of the key value in the keys list that is at least equal to
         * the given key. Note that if the index = {the size of the key list}, then the key
         * should be inserted into the end of the list.
         */
        private int keyIndex(T key) {
            int index = 0;

            while (index < keys.size() && key.compareTo(keys.get(index)) < 0)
                index++;

            return index;
        }

        /**
         * Inserts the given key into the keys List in order. If the key list is full,
         * the list is unaffected and false is returned.
         *
         * @param key object of type T to be inserted in the keys list
         * @return true if the insertion was successful, false otherwise
         */
        boolean keyListInsert(T key) {
            if (!maxChildren()) {
                keys.add(keyIndex(key),key);
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
