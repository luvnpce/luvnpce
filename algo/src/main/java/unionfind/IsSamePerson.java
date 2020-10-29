package unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 定义每一个Person有三个属性：
 * - number:数字id
 * - lower:小写字母
 * - upper:大写字母
 * 如果两个Person，只要他们三个属性其中有一个是相同的就算同一个人
 *
 * 给定一群Person，判断到底有几个人
 *
 */
public class IsSamePerson {

    public static void main(String[] args) {
    }

    public static int mergePersons(List<Person> Persons) {
        // 创建一个并查集，每个Person都先是独立的
        UnionSet<Person> unionFind = new UnionSet<>(Persons);
        HashMap<Integer, Person> numberMap = new HashMap<>();
        HashMap<String, Person> lowerMap = new HashMap<>();
        HashMap<String, Person> upperMap = new HashMap<>();
        // 逐个遍历，然后去map里面看有没有重复的，如果有，就让这两个在并查集里合并，如果没有，把这个属性添加到Map里
        for(Person Person : Persons) {
            if(numberMap.containsKey(Person.number)) {
                unionFind.union(Person, numberMap.get(Person.number));
            }else {
                numberMap.put(Person.number, Person);
            }
            if(lowerMap.containsKey(Person.lower)) {
                unionFind.union(Person, lowerMap.get(Person.lower));
            }else {
                lowerMap.put(Person.lower, Person);
            }
            if(upperMap.containsKey(Person.upper)) {
                unionFind.union(Person, upperMap.get(Person.upper));
            }else {
                upperMap.put(Person.upper, Person);
            }
        }
        // 向并查集询问，合并之后，还有多少个集合？
        return unionFind.getSetNum();
    }

    public static class UnionSet<Person> {
        public HashMap<Person, Node> nodes;
        public HashMap<Node, Node> parents;
        public HashMap<Node, Integer> sizeMap;

        public UnionSet(List<Person> list) {
            this.nodes = new HashMap<>();
            this.parents = new HashMap<>();
            this.sizeMap = new HashMap<>();

            for (Person person : list) {
                Node node = new Node(person);
                nodes.put(person, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(Person p1, Person p2) {
            if (!nodes.containsKey(p1) || !nodes.containsKey(p2)) {
                return false;
            }
            return findParent(nodes.get(p1)) == findParent(nodes.get(p2));
        }

        public void union(Person p1, Person p2) {
            if (!nodes.containsKey(p1) || !nodes.containsKey(p2)) {
                return;
            }
            Node parent1 = findParent(nodes.get(p1));
            Node parent2 = findParent(nodes.get(p2));
            if (parent1 != parent2) {
                Integer size1 = sizeMap.get(p1);
                Integer size2 = sizeMap.get(p2);
                Node big = size1 > size2 ? parent1 : parent2;
                Node small = big == parent1 ? parent2 : parent1;
                parents.put(small, big);
                sizeMap.put(big, size1 + size2);
                sizeMap.remove(small);
            }
        }

        public int getSetNum() {
            return sizeMap.size();
        }

        public Node findParent(Node node) {
            Stack<Node> stack = new Stack<>();
            while (node != parents.get(node)) {
                stack.push(node);
                node = parents.get(node);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), node);
            }
            return node;
        }

    }

    public static class Node<Person> {
        public Person person;

        public Node(Person p) {
            this.person = p;
        }
    }

    public static class Person {

        public int number;
        public String lower;
        public String upper;

        public Person(int n, String l, String u) {
            this.number = n;
            this.lower = l;
            this.upper = u;
        }
    }
}
