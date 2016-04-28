package sufixtree;

import java.util.ArrayList;
import java.util.List;

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
	
	public Node getTreeForChar(char c){
		int _index = c - 97;
		Node node = lookup[_index];
		return node;
	}
	
	private void addNodeToLookup(Node node){
		int _index = node.getStartsWith() - 97;
		lookup[_index] = node;
	}

	public void insert(String word) {
		if (word != null) {
			word = word.toLowerCase();
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
			addWordToIndex(word);
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
		
		if(!found){
			word = word.replaceAll(nodeText, "");
			Node pathForCharacter = node.getPathForCharacter(word.charAt(0));
			if(pathForCharacter == null){
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
}
