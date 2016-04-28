package sufixtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CompactTries {
	private Node[] lookup;
	private List<String> wordIndex;
	private int index;

	public CompactTries() {
		lookup = new Node[26];
		wordIndex = new ArrayList<String>();
		wordIndex.add(0, "__");
		index = 1;
	}

	public Node getTreeForChar(char c) {
		int _index = c - 97;
		Node node = lookup[_index];
		return node;
	}

	private void addNodeToLookup(Node node) {
		int _index = node.getStartsWith() - 97;
		lookup[_index] = node;
	}

	public void insert(String word) {
		if (word != null) {
			word = word.toLowerCase();
			addToTries(word);
			addWordToIndex(word);
		}
	}

	/**
	 * This Method will add all the suffix to the tries data structure.
	 * vijay\0
	 * ijay\0
	 * jay\0
	 * ay\0
	 * y\0
	 */
	public void addSufixToTries(String word) {
		if (word != null) {
			word = word.toLowerCase();
			for (int i = word.length() - 1; i >= 0; i--) {
				String sufix = word.substring(i, word.length());
				addToTries(sufix);
			}
			addWordToIndex(word);
		}
	}

	private void addToTries(String word) {
		char firstChar = word.charAt(0);
		Node node = getTreeForChar(firstChar);
		if (node == null) {
			node = new Node();
			node.setText(word);
			node.setIndex(index);
			addNodeToLookup(node);
		} else {
			insertToExistingNode(node, word);
		}
	}

	private void addWordToIndex(String word) {
		wordIndex.add(index, word);
		index++;
	}

	private void insertToExistingNode(Node node, String word) {
		boolean found = false;
		String nodeText = node.getText();
		for (int i = 1; i < nodeText.length(); i++) {
			if (nodeText.charAt(i) != word.charAt(i)) {
				String common = nodeText.substring(0, i);
				String diff = nodeText.substring(i);
				String wordDiff = word.substring(i);
				node.setText(common);

				List<Node> currentPaths = node.getPaths();
				int nodeIndex = node.getIndex();
				Node rest = new Node();
				rest.setText(diff);
				rest.setPaths(currentPaths);
				rest.setIndex(nodeIndex);

				node.setPaths(null);
				node.setIndex(0);

				node.addPath(rest);

				Node newPath = new Node();
				newPath.setText(wordDiff);
				newPath.setIndex(index);
				node.addPath(newPath);
				found = true;
				break;
			}
		}

		if (!found) {
			word = word.replaceAll(nodeText, "");
			Node pathForCharacter = node.getPathForCharacter(word.charAt(0));
			if (pathForCharacter == null) {
				pathForCharacter = new Node();
				pathForCharacter.setText(word);
				node.addPath(pathForCharacter);
			} else {
				insertToExistingNode(pathForCharacter, word);
			}
		}
	}

	public List<String> getWordIndex() {
		return wordIndex;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"tries\": ").append("[");
		boolean added = false;
		for (Node n : lookup) {
			if (n != null) {
				if (added) {
					sb.append(",");
				}
				sb.append(n);
				added = true;
			}
		}
		sb.append("]}");
		return sb.toString();
	}

	public Set<String> search(String query) {
		Set<String> response = new TreeSet<String>();
		if (query != null && query != "") {
			Node initialNode = getTreeForChar(query.charAt(0));
			initialNode.trace(query);
			addToResponse(initialNode, response);
		}
		return response;
	}

	private void addToResponse(Node node, Set<String> response) {
		if(node != null){
			if(node.getIndex() != 0){
				response.add(wordIndex.get(node.getIndex()));
			}
			if(node.getPaths() != null){
				for(Node path: node.getPaths()){
					addToResponse(path, response);
				}
			}
		}
	}
}
