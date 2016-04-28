package sufixtree;

import java.util.Set;

public class WordAutoComplete {
	private CompactTries compactTries;

	public WordAutoComplete() {
		compactTries = new CompactTries();
	}

	public void index(String text) {
		compactTries.addSufixToTries(text);
	}

	public void printJsonTree() {
		System.out.println(compactTries);
	}

	public Set<String> suggest(String query) {
		return compactTries.search(query);
	}

}
