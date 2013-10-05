package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.NameGenerator;

public class NameTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		NameGenerator.randomName(4);
		fail("Not yet implemented");
	}

}
