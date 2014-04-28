package org.dpolivaev.tsgen.utils.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LinkedMap <K, V>{
	final static class LinkedMapEntry<K, V>{
		K key;
		V value;
		LinkedMapEntry<K, V> previous;
		LinkedMapEntry<K, V> next;

		public LinkedMapEntry(K key, V value){
			this.key = key;
			this.value = value;
			this.previous = null;
			this.next = null;
		}

		static public <V> V valueOf(LinkedMapEntry<?, V> entry){
			return entry == null ? null : entry.value;
		}

		void insert(LinkedMapEntry<K, V> next){
			next.next = this.next;
			this.next = next;
			next.previous = this;
		}

		void remove(){
			if(previous != null)
				previous.next = this.next;
			if(next != null)
				next.previous = previous;
		}

	}
	final private Map<K, LinkedMapEntry<K, V>> map = new HashMap<>();
	LinkedMapEntry<K, V> firstEntry = null;
	LinkedMapEntry<K, V> lastEntry = null;

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public V get(Object key) {
		return LinkedMapEntry.valueOf(map.get(key));
	}

	public void put(K key, V value) {
		map.get(key).value = value;
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> copyValues() {
        ArrayList<V> arrayList = new ArrayList<V>(map.size());
        for(LinkedMapEntry<K, V> entry = firstEntry; entry != null; entry = entry.next)
		arrayList.add(entry.value);
        return arrayList;
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}

	public void add(K key, V value) {
		if(map.containsKey(key))
			throw new IllegalStateException();
		LinkedMapEntry<K, V> entry = new LinkedMapEntry<>(key, value);
		if(firstEntry == null){
			firstEntry  = lastEntry = entry;
		}
		else{
			lastEntry.insert(entry);
			lastEntry = entry;
		}
		map.put(key, entry);
	}

	public void insertAfter(K afterKey, K key, V value) {
		if(map.containsKey(key))
			throw new IllegalStateException();
		LinkedMapEntry<K, V> afterEntry = map.get(afterKey);
		if(afterEntry ==  null)
			throw new NullPointerException();
		if(afterEntry == lastEntry)
			add(key, value);
		else{
			LinkedMapEntry<K, V> entry = new LinkedMapEntry<>(key, value);
			map.put(key, entry);
			afterEntry.insert(entry);
		}

	}

	public void remove(Object key) {
		LinkedMapEntry<K, V> entry = map.remove(key);
		if(entry != null){
			if(firstEntry == entry)
				firstEntry = entry.next;
			if(lastEntry == entry)
				lastEntry = entry.previous;
			entry.remove();
		}
	}

	public void clear() {
		map.clear();
		firstEntry = lastEntry = null;
	}

}
