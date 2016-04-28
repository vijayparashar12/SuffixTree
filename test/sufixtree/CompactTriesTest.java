package sufixtree;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CompactTriesTest {

	@Test
	public void test() {
		CompactTries compactTries = new CompactTries();
		compactTries.insert("vijay");
		compactTries.insert("vishnu");
		compactTries.insert("vishal");
		compactTries.insert("vijesh");
		compactTries.insert("Vasu");
		Node treeForV = compactTries.getTreeForChar('v');
		System.out.println(treeForV);
		List<Node> paths = treeForV.getPaths();
		assertTrue(paths.size()==2);
		for(Node n :paths){
			assertTrue(n.getText().equals("i") || n.getText().equals("asu"));
		}
		
	}
	
	@Test
	public void verifySuffixTreeInsertion() throws Exception {
		Indexer indexer = new Indexer();
		indexer.index("vijay");
	}

}
