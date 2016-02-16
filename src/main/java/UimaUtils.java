import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.TOP;

public class UimaUtils {

  public static <T extends TOP> List<T> getAnnotations(JCas aJCas, Class<T> clazz) {
    return new ArrayList<T>(JCasUtil.select(aJCas, clazz));
  }

  public static <T extends TOP> List<T> convertFSListToList(FSList fslist, Class<T> clazz) {
    if (fslist == null) {
      return new ArrayList<T>();
    }
    return new ArrayList<T>(FSCollectionFactory.create(fslist, clazz));
  }

  public static <T extends TOP> FSList convertCollectionToFSList(JCas aJCas,
          Collection<T> collection) {
    if (collection == null) {
      return null;
    }
    return FSCollectionFactory.createFSList(aJCas, collection);
  }

  public static <T extends TOP> FSList appendFSList(JCas aJCas, FSList fslist, T item,
          Class<T> clazz) {
    List<T> newList;
    if (fslist == null) {
      newList = new ArrayList<T>();
    } else {
      newList = convertFSListToList(fslist, clazz);
    }
    newList.add(item);
    return convertCollectionToFSList(aJCas, newList);
  }

}
