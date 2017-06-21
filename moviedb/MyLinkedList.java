
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T extends Comparable<T>> implements ListInterface<T> {
	// dummy head
	Node<T> head;
	int numItems;

	public MyLinkedList() {
		head = new Node<T>(null);
	}

    /**
     * {@code Iterable<T>}瑜�援ы쁽�섏뿬 iterator() 硫붿냼�쒕� �쒓났�섎뒗 �대옒�ㅼ쓽 �몄뒪�댁뒪��     * �ㅼ쓬怨�媛숈� �먮컮 for-each 臾몃쾿���쒗깮��蹂����덈떎.
     * 
     * <pre>
     *  for (T item: iterable) {
     *  	item.someMethod();
     *  }
     * </pre>
     * 
     * @see PrintCmd#apply(MovieDB)
     * @see SearchCmd#apply(MovieDB)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<T> iterator() {
    	return new MyLinkedListIterator<T>(this);
    }

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public T first() {
		return head.getNext().getItem();
	}

	@Override
	public void add(T item) {
		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}
	
	public void manItems(int num) {
		numItems += num;
	}
	
	public void sortadd(T item) {
		Node<T> target = head;
		while (target.getNext() != null) {
			if((target.getNext().getItem()).compareTo(item) > 0) //�뺣젹
				break;
			if(target.getNext().getItem().equals(item) == true)
				return;
			target = target.getNext();	
		}
		Node<T> node = new Node<T>(item, target.getNext());
		target.setNext(node);
		numItems += 1;
	}
	
	public Node<T> getHead() {
		return head;
	}
}

class MyLinkedListIterator<T> implements Iterator<T> {
	// FIXME implement this
	// Implement the iterator for MyLinkedList.
	// You have to maintain the current position of the iterator.
	private ListInterface<T> list;
	private Node<T> curr;
	private Node<T> prev;

	public MyLinkedListIterator(ListInterface<T> list) {
		this.list = list;
		this.curr = list.getHead();
		this.prev = null;
	}

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();

		return curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.manItems(-1);
		curr = prev;
		prev = null;
	}
	
	public MyLinkedList<MovieDBItem> getList() {
		return ((Genre) curr).getList();
	}
}
