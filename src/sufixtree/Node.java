package sufixtree;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private String text;
	private char startsWith;
	private int index;
	private List<Node> paths;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		if (text != null && text.length() > 0) {
			this.startsWith = text.charAt(0);
		}
	}

	public List<Node> getPaths() {
		return paths;
	}

	public void setPaths(List<Node> paths) {
		this.paths = paths;
	}

	public char getStartsWith() {
		return startsWith;
	}

	public void setStartsWith(char startsWith) {
		this.startsWith = startsWith;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Node getPathForCharacter(char charAt) {
		Node _node = null;
		if (paths != null) {
			for (Node path : paths) {
				if (path.getStartsWith() == charAt) {
					_node = path;
					break;
				}
			}
		}
		return _node;
	}

	public void addPath(Node path) {
		if (paths == null) {
			paths = new ArrayList<Node>();
		}

		paths.add(path);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{");
		s.append("\"text\": ").append("\"").append(text).append("\",");
		s.append("\"index\": ").append(index).append("\"");
		if(hasPaths()){
			s.append(",");
			s.append("\"paths\": ").append(paths);
		}
		s.append("}");
		return s.toString();
	}
	
	public boolean hasPaths(){
		return paths != null && paths.size() > 0;
	}
}
