package sufixtree;


public class Indexer {
	private CompactTries compactTries;
	public Indexer() {
		compactTries = new CompactTries();
	}
	/**
	 * vijay\0
	 * ijay\0
	 * jay\0
	 * ay\0
	 * y\0
	 */
	public void index(String text) {
		for (int i = text.length() - 1; i >= 0; i--) {
			String sufix = text.substring(i, text.length());
			compactTries.insert(sufix,text);
		}
	}
	
}
