import java.util.LinkedList;
import java.util.Queue;

public class BalancedTreeAVL {
    private static final class Node {
        public Node left;
        public Node right;
        public Integer element;
        public int height;

        Node(Integer element) {
            left = null;
            right = null;
            this.element = element;
            height = 1;
        }
    }

    private int count;
    private Node root;

    public BalancedTreeAVL() {
        count = 0;
        root = null;
    }

    public void clear() {
        count = 0;
        root = null;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return count;
    }

    public int height() { // Grau de complexidade: O(n)
        return height(root)-1;
    }

    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    private int balance(Node n) { // Grau de complexidade: O(log n)
        return (n == null) ? 0 : height(n.left) - height(n.right); // calcula o fator de equilíbrio através
    }

    public Integer getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }

    public void add(Integer element) { // Grau de complexidade: O(log n)
        root = addRecursive(root, null, element); // chamada recursiva do método private Node addRecursive
        count++;
    }

    private Node addRecursive(Node n, Node father, Integer element) {
        if (n == null) { // confere se o nodo é null
            return new Node(element);
        }

        if (element < n.element) { // compara elemento com elemento do node n para determinar a subárvore
            n.left = addRecursive(n.left, n, element); // elemento menor, subárvore da esquerda
        } else if (element > n.element) {
            n.right = addRecursive(n.right, n, element); // elemento maior, subárvore da direita
        } else {
            return n;
        }

        n.height = 1 + Math.max(height(n.left), height(n.right)); // atualiza altura

        int balance = balance(n); // cálcula o balance

        if (balance > 1 && element < n.left.element) { // rotação direita
            return rightRotate(n);
        }
        if (balance < -1 && element > n.right.element) { // rotação esquerda
            return leftRotate(n);
        }
        if (balance > 1 && element > n.left.element) { // rotação esquerda direita
            n.left = leftRotate(n.left);
            return rightRotate(n);
        }
        if (balance < -1 && element < n.right.element) { // rotação direita esquerda
            n.right = rightRotate(n.right);
            return leftRotate(n);
        }

        return n;
    }

    public void remove(Integer element) {
        root = removeRecursive(root, element);
        if (root != null) {
            count--;
        }
    }

    private Node removeRecursive(Node n, Integer element) {
        if (n == null) {
            return null;
        }

        if (element < n.element) {
            n.left = removeRecursive(n.left, element);
        } else if (element > n.element) {
            n.right = removeRecursive(n.right, element);
        } else {
            if (n.left == null || n.right == null) {
                Node temp = (n.left != null) ? n.left : n.right;

                if (temp == null) {
                    n = null;
                } else {
                    n = temp;
                }
            } else {
                Node next = findNext(n.right);
                n.element = next.element;
                n.right = removeRecursive(n.right, next.element);
            }
        }

        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
            int balance = balance(n);

            if (balance > 1 && balance(n.left) >= 0) {
                return rightRotate(n);
            }
            if (balance > 1 && balance(n.left) < 0) {
                n.left = leftRotate(n.left);
                return rightRotate(n);
            }
            if (balance < -1 && balance(n.right) <= 0) {
                return leftRotate(n);
            }
            if (balance < -1 && balance(n.right) > 0) {
                n.right = rightRotate(n.right);
                return leftRotate(n);
            }
        }

        return n;
    }

    private Node findNext(Node n) {
        Node aux = n;
        while (aux.left != null) {
            aux = aux.left;
        }
        return aux;
    }

    private Node rightRotate(Node n) {
        Node x = n.left;
        Node aux = x.right;

        // rotação
        x.right = n;
        n.left = aux;

        // atualiza as alturas depois da rotação
        n.height = 1 + Math.max(height(n.left), height(n.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x; // x é o root da subárvore rotacionada
    }

    private Node leftRotate(Node n) {
        Node x = n.right;
        Node aux = x.left;
    
        x.left = n;
        n.right = aux;
    
        if (aux != null) {
            aux = n;
        }
    
        n.height = 1 + Math.max(height(n.left), height(n.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
    
        return x; 
    }

    public Integer getParent(Integer element) {
        return getParentRecursive(root, null, element);
    }

    private Integer getParentRecursive(Node n, Node father, Integer element) {
        if (n == null) {
            return null;
        }

        if (element.equals(n.element)) {
            return (father != null) ? father.element : null;
        }

        if (element < n.element) {
            return getParentRecursive(n.left, n, element);
        } else {
            return getParentRecursive(n.right, n, element);
        }
    }

    public boolean contains(Integer element) { // Grau de complexidade: O(log n)
        return containsRecursive(root, element);
    }

    private boolean containsRecursive(Node n, Integer element) {
        if (n == null || element == null) {
            return false;
        }

        if (element.equals(n.element)) {
            return true;
        }

        if (element < n.element) {
            return containsRecursive(n.left, element);
        } else {
            return containsRecursive(n.right, element);
        }
    }

    public boolean searchNodeRef(Integer element) {
        return searchNodeRefRecursive(root, element) != null;
    }

    private Node searchNodeRefRecursive(Node n, Integer element) {
        if (n == null || element == null) {
            return null;
        }

        if (element.equals(n.element)) {
            return n;
        }

        if (element < n.element) {
            return searchNodeRefRecursive(n.left, element);
        } else {
            return searchNodeRefRecursive(n.right, element);
        }
    }

    public BalancedTreeAVL clone() { // Grau de complexidade: O(n)
        BalancedTreeAVL clone = new BalancedTreeAVL();
        clone.root = cloneRecursive(root);
        clone.count = count;
        return clone;
    }
    
    private Node cloneRecursive(Node originalNode) {
        if (originalNode == null) {
            return null;
        }
    
        Node newNode = new Node(originalNode.element);
        newNode.left = cloneRecursive(originalNode.left);
        newNode.right = cloneRecursive(originalNode.right);
        newNode.height = originalNode.height;
    
        return newNode;
    }

    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger pre = new LinkedListOfInteger();
        positionsPreRecursive(root, pre);
        return pre;
    }

    private void positionsPreRecursive(Node n, LinkedListOfInteger pre) {
        if (n != null) {
            pre.add(n.element);
            positionsPreRecursive(n.left, pre);
            positionsPreRecursive(n.right, pre);
        }
    }

    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger central = new LinkedListOfInteger();
        positionsCentralRecursive(root, central);
        return central;
    }

    private void positionsCentralRecursive(Node n, LinkedListOfInteger central) {
        if (n != null) {
            positionsCentralRecursive(n.left, central);
            central.add(n.element);
            positionsCentralRecursive(n.right, central);
        }
    }

    public LinkedListOfInteger positionsPos() {
        LinkedListOfInteger pos = new LinkedListOfInteger();
        positionsPosRecursive(root, pos);
        return pos;
    }

    private void positionsPosRecursive(Node n, LinkedListOfInteger pos) {
        if (n != null) {
            positionsPosRecursive(n.left, pos);
            positionsPosRecursive(n.right, pos);
            pos.add(n.element);
        }
    }

    public LinkedListOfInteger positionsWidth() {
        LinkedListOfInteger width = new LinkedListOfInteger();
        Queue<Node> fila = new LinkedList<>();
        Node n = null;

        fila.add(root);

        if (root != null) {
            while (!fila.isEmpty()) {
                n = fila.remove();
                width.add(n.element);

                if (n.left != null) {
                    fila.add(n.left);
                }
                if (n.right != null) {
                    fila.add(n.right);
                }
            }
        }

        return width;
    }

    private void GeraConexoesDOT(Node nodo) {
        if (nodo == null) {
            return;
        }

        GeraConexoesDOT(nodo.left);
        if (nodo.left != null) {
            System.out.println("\"node" + nodo.element + "\":esq -> \"node" + nodo.left.element + "\" " + "\n");
        }

        GeraConexoesDOT(nodo.right);
        if (nodo.right != null) {
            System.out.println("\"node" + nodo.element + "\":dir -> \"node" + nodo.right.element + "\" " + "\n");
        }
    }

    private void GeraNodosDOT(Node nodo) {
        if (nodo == null) {
            return;
        }
        GeraNodosDOT(nodo.left);
        System.out.println("node" + nodo.element + "[label = \"<esq> | " + nodo.element + " | <dir> \"]" + "\n");
        GeraNodosDOT(nodo.right);
    }

    public void GeraConexoesDOT() {
        GeraConexoesDOT(root);
    }

    public void GeraNodosDOT() {
        GeraNodosDOT(root);
    }

    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline
    public void GeraDOT() {
        System.out.println("digraph g { \nnode [shape = record,height=.1];\n" + "\n");

        GeraNodosDOT();
        System.out.println("");
        GeraConexoesDOT(root);
        System.out.println("}" + "\n");
    }
}