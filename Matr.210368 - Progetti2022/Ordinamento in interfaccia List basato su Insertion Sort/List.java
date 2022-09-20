package LinkedList;

import java.util.Comparator;  
import java.util.Iterator;
import java.util.ListIterator;

//import sun.tools.jstat.Literal;

public interface List<T> extends Iterable<T>{
	//si ispira liberamente a java.util.List<T>
	
	default int size() {
		int c=0;
		for( T x: this ) c++;
		return c;
	}//size
	
	default void clear() {
		Iterator<T> it=this.iterator();
		while( it.hasNext() ) {
			it.next();
			it.remove();
		}
	}//clear
	
	default boolean contains( T e ) {
		for( T x: this )
			if( x.equals(e) ) return true;
		return false;
	}//contains
	
	default void add( T e ) {
		ListIterator<T> lit=listIterator( size() );
		lit.add( e );
	}//add
	
	default void remove( T e ) {
		Iterator<T> it=iterator();
		while( it.hasNext() ) {
			T x=it.next();
			if( x.equals(e) ) { it.remove(); break; }
		}
	}//remove
	
	default boolean isEmpty() {
		return !listIterator().hasNext();
	}//isEmpty
	
	default boolean isFull() {
		return false;
	}//isFull
	
	static <T> void sort( List<T> lis, Comparator<? super T> c ){//bubble sort
		//default: bubble sort
		if( lis.size()<=1 ) return;
		boolean scambi=true;
		int limite=lis.size(), pus=0;
		while( scambi ) {
			ListIterator<T> lit=lis.listIterator();
			T x=lit.next(), y=null;
			int pos=1; scambi=false; //ottimismo
			while( pos<limite ) {
				y=lit.next();
				if( c.compare(x,y)>0 ) {//x>y
					lit.set(x);
					lit.previous(); lit.previous(); //           y   x^
					lit.set(y);
					lit.next(); lit.next(); //la seconda next equivale a: x=lit.next();
					scambi=true; pus=pos;
				}
				else { 
					x=y; 
				}
				pos++;
			}//while interno
			limite=pus;
		}//while esterno
	}//sort
	
	
	static <T> void sortS(List<T> lis, Comparator<? super T> c, boolean flag) {
		if( lis.size() <= 1) return;
		int size = lis.size();
		ListIterator<T> lit=lis.listIterator();
		for(int i = 1; i<size; i++) {
			T prec =lit.next(), cor = null;
			while(c.compare(cor = lit.next(), prec)<0) {
				lit.previous();
				T temp = lit.previous();
				lit.set(cor);
				lit.next();
				lit.set(temp);
			}
		}
	}//sort (mediante insertion sort)
	
	
	ListIterator<T> listIterator();
	ListIterator<T> listIterator( int pos );
}//List
