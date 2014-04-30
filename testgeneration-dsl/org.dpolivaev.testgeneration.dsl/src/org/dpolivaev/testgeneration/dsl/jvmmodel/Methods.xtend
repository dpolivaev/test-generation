package org.dpolivaev.testgeneration.dsl.jvmmodel

import java.util.HashMap
import org.eclipse.xtext.xbase.XExpression
 import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*
 
class Methods{
	val map = new HashMap<String, String>
	
	def put(String prefix, XExpression expression, String name) {
		val text = expression.node.text
		map.put(prefix + text, name)
	}
	
	def contains(String prefix, XExpression expression) {
		val text = expression.node.text
		map.containsKey(prefix + text)
	}
	
	def size() {
		map.size
	}
	
	def get(String prefix, XExpression expression) {
		val text = expression.node.text
		map.get(prefix + text)
	}
	
}

