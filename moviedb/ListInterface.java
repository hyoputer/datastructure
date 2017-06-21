public interface ListInterface<T> extends Iterable<T> {
	
	public Node<T> getHead();
	
	public void manItems(int num);
	
	public boolean isEmpty();

	public int size();

	public void add(T item);

	public T first();

	public void removeAll();
}
