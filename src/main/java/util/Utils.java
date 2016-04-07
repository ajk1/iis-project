package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.EmptyFSList;
import org.apache.uima.jcas.cas.EmptyFloatList;
import org.apache.uima.jcas.cas.EmptyIntegerList;
import org.apache.uima.jcas.cas.EmptyStringList;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.FloatList;
import org.apache.uima.jcas.cas.IntegerList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.jcas.cas.NonEmptyFloatList;
import org.apache.uima.jcas.cas.NonEmptyIntegerList;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * 
 * @author Kotaro Sakamoto
 * 
 */
public class Utils {
  public static IntegerList fromCollectionToIntegerList(JCas aJCas, Collection<Integer> aCollection) {
    if (aCollection == null) {
      return new EmptyIntegerList(aJCas);
    }
    
    if (aCollection.size() == 0) {
      return new EmptyIntegerList(aJCas);
    }

    NonEmptyIntegerList head = new NonEmptyIntegerList(aJCas);
    NonEmptyIntegerList list = head;
    Iterator<Integer> i = aCollection.iterator();
    while (i.hasNext()) {
      head.setHead(i.next());
      if (i.hasNext()) {
        head.setTail(new NonEmptyIntegerList(aJCas));
        head = (NonEmptyIntegerList) head.getTail();
      } else {
        head.setTail(new EmptyIntegerList(aJCas));
      }
    }

    return list;
  }
  
  public static FloatList fromCollectionToFloatList(JCas aJCas, Collection<Float> aCollection) {
    if (aCollection == null) {
      return new EmptyFloatList(aJCas);
    }
    
    if (aCollection.size() == 0) {
      return new EmptyFloatList(aJCas);
    }

    NonEmptyFloatList head = new NonEmptyFloatList(aJCas);
    NonEmptyFloatList list = head;
    Iterator<Float> i = aCollection.iterator();
    while (i.hasNext()) {
      head.setHead(i.next());
      if (i.hasNext()) {
        head.setTail(new NonEmptyFloatList(aJCas));
        head = (NonEmptyFloatList) head.getTail();
      } else {
        head.setTail(new EmptyFloatList(aJCas));
      }
    }

    return list;
  }
  
  public static StringList fromCollectionToStringList(JCas aJCas, Collection<String> aCollection) {
    if (aCollection == null) {
      return new EmptyStringList(aJCas);
    }
    
    if (aCollection.size() == 0) {
      return new EmptyStringList(aJCas);
    }

    NonEmptyStringList head = new NonEmptyStringList(aJCas);
    NonEmptyStringList list = head;
    Iterator<String> i = aCollection.iterator();
    while (i.hasNext()) {
      head.setHead(i.next());
      if (i.hasNext()) {
        head.setTail(new NonEmptyStringList(aJCas));
        head = (NonEmptyStringList) head.getTail();
      } else {
        head.setTail(new EmptyStringList(aJCas));
      }
    }

    return list;
  }
  
  public static List<Float> fromFloatListToArrayList(FloatList list) {

    if (list == null) {
      return new ArrayList<Float>();
    }

    if (list instanceof EmptyFloatList) {
      return new ArrayList<Float>();
    }

    FloatList tail = list;
    List<Float> buffer = new ArrayList<Float>();
    while (tail instanceof NonEmptyFloatList) {
      NonEmptyFloatList nonEmptyFloatList = (NonEmptyFloatList) tail;
      buffer.add(nonEmptyFloatList.getHead());
      tail = nonEmptyFloatList.getTail();
      if (tail instanceof EmptyFloatList) {
        break;
      }
    }
    return buffer;
  }
  
  public static List<Integer> fromIntegerListToArrayList(IntegerList list) {

    if (list == null) {
      return new ArrayList<Integer>();
    }

    if (list instanceof EmptyIntegerList) {
      return new ArrayList<Integer>();
    }

    IntegerList tail = list;
    List<Integer> buffer = new ArrayList<Integer>();
    while (tail instanceof NonEmptyIntegerList) {
      NonEmptyIntegerList nonEmptyIntegerList = (NonEmptyIntegerList) tail;
      buffer.add(nonEmptyIntegerList.getHead());
      tail = nonEmptyIntegerList.getTail();
      if (tail instanceof EmptyIntegerList) {
        break;
      }
    }
    return buffer;
  }
  
  public static List<Integer> fromIntegerListToLinkedList(IntegerList list) {

    if (list == null) {
      return new LinkedList<Integer>();
    }

    if (list instanceof EmptyIntegerList) {
      return new LinkedList<Integer>();
    }

    IntegerList tail = list;
    List<Integer> buffer = new LinkedList<Integer>();
    while (tail instanceof NonEmptyIntegerList) {
      NonEmptyIntegerList nonEmptyIntegerList = (NonEmptyIntegerList) tail;
      buffer.add(nonEmptyIntegerList.getHead());
      tail = nonEmptyIntegerList.getTail();
      if (tail instanceof EmptyIntegerList) {
        break;
      }
    }
    return buffer;
  }
  
  public static List<Float> fromFloatListToLinkedList(FloatList list) {

    if (list == null) {
      return new LinkedList<>();
    }

    if (list instanceof EmptyFloatList) {
      return new LinkedList<>();
    }

    FloatList tail = list;
    List<Float> buffer = new LinkedList<>();
    while (tail instanceof NonEmptyFloatList) {
      NonEmptyFloatList nonEmptyFloatList = (NonEmptyFloatList) tail;
      buffer.add(nonEmptyFloatList.getHead());
      tail = nonEmptyFloatList.getTail();
      if (tail instanceof EmptyFloatList) {
        break;
      }
    }
    return buffer;
  }
  
  public static List<String> fromStringListToArrayList(StringList list) {

    if (list == null) {
      return new ArrayList<>();
    }

    if (list instanceof EmptyStringList) {
      return new ArrayList<>();
    }

    StringList tail = list;
    List<String> buffer = new ArrayList<>();
    while (tail instanceof NonEmptyStringList) {
      NonEmptyStringList nonEmptyStringList = (NonEmptyStringList) tail;
      buffer.add(nonEmptyStringList.getHead());
      tail = nonEmptyStringList.getTail();
      if (tail instanceof EmptyStringList) {
        break;
      }
    }
    return buffer;
  }
  
  public static List<String> fromStringListToLinkedList(StringList list) {

    if (list == null) {
      return new LinkedList<>();
    }

    if (list instanceof EmptyStringList) {
      return new LinkedList<>();
    }

    StringList tail = list;
    List<String> buffer = new LinkedList<>();
    while (tail instanceof NonEmptyStringList) {
      NonEmptyStringList nonEmptyStringList = (NonEmptyStringList) tail;
      buffer.add(nonEmptyStringList.getHead());
      tail = nonEmptyStringList.getTail();
      if (tail instanceof EmptyStringList) {
        break;
      }
    }
    return buffer;
  }

  @SuppressWarnings("unchecked")
  public static <T extends TOP> List<T> fromFSListToArrayList(FSList list,
          Class<T> classType) {

    if (list == null) {
      return new ArrayList<>();
    }

    if (list instanceof EmptyFSList) {
      return new ArrayList<>();
    }

    FSList tail = list;
    List<T> buffer = new ArrayList<>();
    while (tail instanceof NonEmptyFSList) {
      NonEmptyFSList nonEmptyFSList = (NonEmptyFSList) tail;
      buffer.add((T) nonEmptyFSList.getHead());
      tail = nonEmptyFSList.getTail();
      if (tail instanceof EmptyFSList) {
        break;
      }
    }
    return buffer;
  }

  @SuppressWarnings("unchecked")
  public static <T extends TOP> List<T> fromFSListToLinkedList(FSList list,
          Class<T> classType) {

    if (list == null) {
      return new LinkedList<>();
    }

    if (list instanceof EmptyFSList) {
      return new LinkedList<>();
    }

    FSList tail = list;
    List<T> buffer = new LinkedList<>();
    while (tail instanceof NonEmptyFSList) {
      NonEmptyFSList nonEmptyFSList = (NonEmptyFSList) tail;
      buffer.add((T) nonEmptyFSList.getHead());
      tail = nonEmptyFSList.getTail();
      if (tail instanceof EmptyFSList) {
        break;
      }
    }
    return buffer;
  }

  public static <T extends TOP> FSList fromCollectionToFSList(JCas aJCas, Collection<T> aCollection) {
    if (aCollection == null || aCollection.size() == 0) {
      return new EmptyFSList(aJCas);
    }

    NonEmptyFSList head = new NonEmptyFSList(aJCas);
    NonEmptyFSList list = head;
    Iterator<T> i = aCollection.iterator();
    while (i.hasNext()) {
      head.setHead(i.next());
      if (i.hasNext()) {
        head.setTail(new NonEmptyFSList(aJCas));
        head = (NonEmptyFSList) head.getTail();
      } else {
        head.setTail(new EmptyFSList(aJCas));
      }
    }

    return list;
  }

  public static <T extends TOP> FSArray fromArrayToFSArray(JCas aJCas, T[] array) {
    int size = array.length;
    FSArray fsArray = new FSArray(aJCas, size);
    for (int i = 0; i < size; ++i) {
      fsArray.set(i, array[i]);
    }
    return fsArray;
  }
  
  public static <T extends TOP> FSArray fromCollectionToFSArray(JCas aJCas, List<T> list) {
    int size = list.size();
    FSArray fsArray = new FSArray(aJCas, size);
    for (int i = 0; i < size; ++i) {
      fsArray.set(i, list.get(i));
    }
    return fsArray;
  }
  
  public static <T> List<T> fromPriorityQueueToList(PriorityQueue<T> pq, Class<T> classType) {
	  PriorityQueue<T> temp = new PriorityQueue<T>(pq);
	  List<T> list = new LinkedList<T>();
	  while ( !temp.isEmpty() ) {
		  list.add(temp.remove());
	  }
	  return list;
  }
}
