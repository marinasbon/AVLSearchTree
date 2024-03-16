
public class LinkedListOfInteger {

    // Classe interna Node
    private class Node {

        public Integer element;
        public Node next;

        public Node(Integer element) {
            this.element = element;
            next = null;
        }

        public Node(Integer element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int count;

    public LinkedListOfInteger() {
        head = null;
        tail = null;
        count = 0;
    }

    public void add(Integer element) {
        Node aux = new Node(element);
        if (head == null) {
            head = aux;
        } else {
            tail.next = aux;
        }
        tail = aux;
        count++;
    }

    public void add(int index, Integer element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node n = new Node(element);
        if (index == 0) { // insere no inicio
            n.next = head;
            head = n;
            if (tail == null) {
                tail = n;
            }
        } else if (index == count) { // insere no final
            tail.next = n;
            tail = n;
        } else { // insere no meio
            Node aux = head;
            for (int i = 0; i < index - 1; i++) {
                aux = aux.next;
            }
            n.next = aux.next;
            aux.next = n;
        }
        count++;
    }

    public Integer get(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = head;
        int c = 0;
        while (c < index) {
            aux = aux.next;
            c++;
        }
        return (aux.element);
    }

    public Integer set(int index, Integer element) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = head;
        for (int i = 0; i < index; i++) {
            aux = aux.next;
        }
        Integer tmp = aux.element;
        aux.element = element;
        return tmp;
    }

    public boolean remove(Integer element) {
        if (element == null) {
            return false;
        }
        if (count == 0) {
            return false;
        }

        if (head.element.equals(element)) {
            head = head.next;
            if (count == 1) {
                tail = null;
            }
            count--;
            return true;
        }

        Node ant = head;
        Node aux = head.next;

        for (int i = 1; i < count; i++) {
            if (aux.element.equals(element)) {
                if (aux == tail) {
                    tail = ant;
                    tail.next = null;
                } else {
                    ant.next = aux.next;
                }
                count--;
                return true;
            }
            ant = ant.next;
            aux = aux.next;
        }

        return false;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public int size() {
        return count;
    }

    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    public Integer removeByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }

        Node aux = head;
        if (index == 0) {
            if (tail == head) {
                tail = null;
            }
            head = head.next;
            count--;
            return aux.element;
        }
        int c = 0;
        while (c < index - 1) {
            aux = aux.next;
            c++;
        }
        Integer element = aux.next.element;
        if (tail == aux.next) {
            tail = aux;
        }
        aux.next = aux.next.next;
        count--;
        return element;
    }

    public int indexOf(Integer element) {
        int index = 0;
        Node aux = head;
        while (aux != null) {
            if (aux.element.equals(element)) {
                return (index);
            }
            aux = aux.next;
            index++;
        }
        return -1;
    }

    public boolean contains(Integer element) {
        Node aux = head;
        while (aux != null) {
            if (aux.element.equals(element)) {
                return (true);
            }
            aux = aux.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        Node aux = head;

        while (aux != null) {
            s.append(aux.element.toString());
            s.append("\n");
            aux = aux.next;
        }

        return s.toString();
    }

    public void reverse() {
        Node aux, ant, auxPrim, auxUlt;
        if (head == null || head == tail) {
            return;
        } else {
            auxPrim = tail;
            auxUlt = head;
            aux = tail;
            while (aux != null) {
                ant = head;
                if (ant == aux) {
                    aux.next = null;
                    aux = null;
                } else {
                    while (ant.next != aux) {
                        ant = ant.next;
                    }
                    aux.next = ant;
                    aux = ant;
                }
            }
            head = auxPrim;
            tail = auxUlt;
        }
    }

    public void insert(Integer element, int index) {
        if (index < 0 || index > count)
            throw new IndexOutOfBoundsException();
        if (count > 0 && index == count)
            tail = insert(element, index, head);
        else
            head = insert(element, index, head);
        if (count == 0)
            tail = head;
        count++;
    }

    private Node insert(Integer element, int pos, Node f) {
        if (pos == 0 || f == null) {
            return new Node(element, f);
        }
        f.next = insert(element, pos - 1, f.next);
        return f;
    }

    public void addInOrder(Integer element) {
        Node aux = head;
        int i = 0;
        while (aux != null) {
            if (aux.element > element) {
                this.add(i, element);
                return;
            }
            aux = aux.next;
            i++;
        }
        this.add(element);
    }

    public void printBackToFront() {
        if (head != null)
            printBackToFront(head);
    }

    private void printBackToFront(Node n) {
        if (n.next == null)
            System.out.println(n.element);
        else {
            printBackToFront(n.next);
            System.out.println(n.element);
        }
    }
}
