package org.dpolivaev.testgeneration.engine.utils.internal;

final class LinkedMapEntry<K, V>{
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

	void insertBefore(LinkedMapEntry<K, V> entry){
		if(previous != null)
			previous.next = entry;
		entry.previous = this.previous;
		this.previous = entry;
		entry.next = this;
	}

	void insertAfter(LinkedMapEntry<K, V> entry){
		if(next != null)
			next.previous = entry;
		entry.next = this.next;
		this.next = entry;
		entry.previous = this;
	}
	
	void remove(){
		if(previous != null)
			previous.next = this.next;
		if(next != null)
			next.previous = previous;
	}

}