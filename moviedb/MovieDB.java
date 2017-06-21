import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	MovieList list;
	MyLinkedListIterator<String> it;
	
    public MovieDB() {
    	list = new MovieList();
    }

    public void insert(MovieDBItem item) {
        
        list.add(item.getGenre());
        it = (MyLinkedListIterator<String>) list.iterator();
        while(it.hasNext()) {
        	if(it.next().equals(item.getGenre())) {
        		MyLinkedList<MovieDBItem> titlelist = it.getList();
        		titlelist.sortadd(item);
        	}
        }
    }

    public void delete(MovieDBItem item) {
        
    	it = (MyLinkedListIterator<String>) list.iterator();
    	while(it.hasNext()) {
    		if(it.next().equals(item.getGenre())) {
    			MyLinkedList<MovieDBItem> titlelist = it.getList();
    			Iterator<MovieDBItem> it2 = titlelist.iterator();
    			while(it2.hasNext()) {
    				if(it2.next().equals(item)) {
    					it2.remove();
    					if(titlelist.isEmpty()) {// 한 장르에 더이상 영화가 없을 때
    						it.remove();
    					}
    				}
    			}
    		}
    	}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        it = (MyLinkedListIterator<String>) list.iterator();
        while(it.hasNext()) {
        	it.next();
        	for(MovieDBItem item: it.getList()) {
        		if(item.getTitle().contains(term))
        			results.add(item);
        	}
        }
        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
        it = (MyLinkedListIterator<String>) list.iterator();
        while(it.hasNext()) {
        	it.next();
        	for(MovieDBItem item: it.getList()) {
        		results.add(item);
        	}
        }
    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	private MyLinkedList<MovieDBItem> titlelist = new MyLinkedList<MovieDBItem>();
	
	public Genre(String name) {
		super(name);
	}
	
	@Override
	public int compareTo(Genre o) {
		return this.getItem().compareTo(o.getItem());
	}

	@Override
	public int hashCode() {
		return this.getItem().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genre other = (Genre) obj;
        if (this.getItem() == null) {
            if (other.getItem() != null)
                return false;
        }
        else if(!this.getItem().equals(other.getItem()))
        	return false;
        return true;
	}
	
	public MyLinkedList<MovieDBItem> getList() {
		return this.titlelist;
	}
	
}

class MovieList extends MyLinkedList<String> implements ListInterface<String> {	
	Genre head;
	int numItems;
	
	public MovieList() {
		head = new Genre(null);
	}	

	@Override
	public void add(String item) {
		Genre target = head;
		Genre temp = new Genre(item);
		while (target.getNext() != null) {
			if(((Genre) target.getNext()).compareTo(temp) > 0) //정렬
				break;
			if(((Genre)target.getNext()).equals(temp) == true)
				return;
			target = (Genre) target.getNext();
		}
		temp.setNext(target.getNext());
		target.setNext(temp);
		numItems += 1;
	}
	
	@Override
	public Genre getHead() {
		return head;
	}
}
