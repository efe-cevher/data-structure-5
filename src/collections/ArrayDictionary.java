package collections;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayDictionary<K, V> implements Dictionary<K, V> 
{
	private Entry<K,V>[] dictionary;
	private int size;
	private boolean initialized = false;
	private final static int DEFAULT_CAPACITY = 25;
	
	public ArrayDictionary()
	{
		this(DEFAULT_CAPACITY);
	}
	

	public ArrayDictionary(int initialCapacity) 
	{
		@SuppressWarnings("unchecked")
		Entry<K, V>[] tempDictionary = (Entry<K, V>[])new Entry[initialCapacity];
	    dictionary = tempDictionary;
	    size = 0;
	    initialized = true;
	}

	public V add(K key, V value) 
	{
		checkInitialization();
		V result = null;
		
		if((key == null) || (value == null))
		{
			throw new IllegalArgumentException();
		}
		else
		{
			int keyIndex = locateIndex(key);
			
			if (keyIndex < size)
			{
				result = dictionary[keyIndex].getValue();
				dictionary[keyIndex].setValue(value);
			}
			else
			{
				dictionary[size] = new Entry<K,V>(key, value);
				size++;
				ensureCapacity();
			}
		}
		return result;
	}
	
	public V remove(K key) 
	{
		checkInitialization();
		V result = null;
		int keyIndex = locateIndex(key);
		if (keyIndex < size)
		{
			result = dictionary[keyIndex].getValue();
			dictionary[keyIndex] = dictionary[size-1];
			dictionary[size-1] = null;
			size--;
		}
		return result;
	}

	public V getValue(K key) 
	{
		V result = null;
		int keyIndex = locateIndex(key);
		if (keyIndex < size)
		{
			result = dictionary[keyIndex].getValue();
		}
		return result;
	}

	public boolean contains(K key) 
	{
		boolean result = false;
		int keyIndex = locateIndex(key);
		if (keyIndex < size)
		{
			result = true;
		}
		return result;
	}

	public Iterator<K> getKeyIterator() 
	{
		
		return null;
	}

	public Iterator<V> getValueIterator() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEmpty() 
	{
		boolean result = true;
		if(size == 0)
		{
			result = false;
		}
		return result;
	}

	public int getSize() 
	{
		return size;
	}

	public void clear() {
		int lastIndex = size-1;
		for (; lastIndex > -1; lastIndex--) 
		{
			dictionary[lastIndex] = null;
		}
	}
	
	private class Entry<K,V>
	{
		private K key;
		private V value;
		
		private Entry(K searchKey, V dataValue)
		{
			key = searchKey;
			value = dataValue;
		}
		
		private K getKey()
		{
			return key;
		}
		
		private V getValue()
		{
			return value;
		}
		
		private void setValue(V dataValue)
		{
		 	value = dataValue;
		}
	}
	
	private int locateIndex(K key) 
	{
		int index = 0;
		while ((index < size) && !key.equals(dictionary[index].getKey()))
		{
			index++;
		}
		return index;
	}


	private void ensureCapacity() {
		if(size == dictionary.length)
		{
			int newLength = 2 * dictionary.length;
			dictionary = Arrays.copyOf(dictionary, newLength);
		}
		
	}
	
	
    private void checkInitialization()
    {
        if (!initialized)
            throw new SecurityException ("ArrayStack object is not initialized properly.");
    }
	
   

}
