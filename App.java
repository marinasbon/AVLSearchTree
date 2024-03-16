public class App {
    public static void main(String[] args) throws Exception {
        BalancedTreeAVL tree = new BalancedTreeAVL();

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(9);

        System.out.println("Altura da árvore: " + tree.height());

        tree.GeraDOT();

        tree.clear();

        System.out.println("Árvore vazia? " + tree.isEmpty());

        tree.add(9);
        tree.add(8);
        tree.add(7);
        tree.add(6);
        tree.add(5);
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(1);

        LinkedListOfInteger positionsCentral = tree.positionsCentral();
        System.out.println("Conteúdo da árvore usando caminhamento central: " + positionsCentral);

        BalancedTreeAVL clone = tree.clone();

        clone.GeraDOT();
    }
}
