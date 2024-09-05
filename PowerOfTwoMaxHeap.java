import java.util.ArrayList;
import java.util.List;

public class PowerOfTwoMaxHeap {
    private List<Integer> heap;
    private int childrenExponent; // Equivalent to 'k'

    public PowerOfTwoMaxHeap(int childrenExponent) {
        if (childrenExponent < 0) {
            throw new IllegalArgumentException("childrenExponent must be non-negative");
        }
        this.childrenExponent = childrenExponent;
        this.heap = new ArrayList<>();
    }

    public void insert(int value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    public int popMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        int maxValue = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        siftDown(0);
        return maxValue;
    }

    private void siftUp(int index) {
        int currentIndex = index;
        int parentIndex = (currentIndex - 1) / (1 << childrenExponent); // parent = (index - 1) / 2^k
        while (currentIndex > 0 && heap.get(currentIndex) > heap.get(parentIndex)) {
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = (currentIndex - 1) / (1 << childrenExponent);
        }
    }

    private void siftDown(int index) {
        int currentIndex = index;
        int size = heap.size();
        while (true) {
            int largest = currentIndex;
            int firstChildIndex = (currentIndex * (1 << childrenExponent)) + 1;
            int lastChildIndex = Math.min(firstChildIndex + (1 << childrenExponent), size);

            for (int i = firstChildIndex; i < lastChildIndex; i++) {
                if (i < size && heap.get(i) > heap.get(largest)) {
                    largest = i;
                }
            }

            if (largest != currentIndex) {
                swap(currentIndex, largest);
                currentIndex = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) {
        // Test with different values of childrenExponent
        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(1); // Binary heap
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);
        heap.insert(30);

        System.out.println(heap.popMax()); // Should print 30
        System.out.println(heap.popMax()); // Should print 20

        // Test with a larger childrenExponent
        PowerOfTwoMaxHeap heap4 = new PowerOfTwoMaxHeap(2); // Quad heap
        heap4.insert(10);
        heap4.insert(20);
        heap4.insert(15);
        heap4.insert(30);

        System.out.println(heap4.popMax()); // Should print 30
        System.out.println(heap4.popMax()); // Should print 20
    }
}