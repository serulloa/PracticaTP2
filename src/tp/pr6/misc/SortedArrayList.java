package tp.pr6.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comparator<E> _comp;

	//########################################################################
	// Constructores
	//########################################################################

	public SortedArrayList(Comparator<E> comp) {
		super();
		_comp = comp;
	}
	
	public SortedArrayList() {
		_comp = new Comparator<E>() {
			
			@Override 
			public int compare(E o1, E o2) {
				return ((Comparable<E>) o1).compareTo(o2);
			}
		};
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	@Override
	public boolean add(E element) {
		int i = 0;
		
		while (i < size() && _comp.compare(element, get(i)) != 1) i++;
		super.add(i, element);
		
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> elements) {
		for (E element : elements) add(element);
		return true;
	}
	
	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> elements) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}
	
}
