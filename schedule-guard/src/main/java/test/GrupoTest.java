package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GrupoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void sumaDosMasTresIgualCinco() {
		assertEquals(5, suma(2,3));
	}
	
	@Test
	public void sumaDosMasCuatroIgualSeis() {
		assertEquals(6, suma(2,4));
	}
	
	@Test 
	public void sumaMUnoMasCuatroIgualSeis() {
		int a = 1;
		assertEquals(3, suma(a,4));
	}
	
	private Object suma(int i, int j) {
		
		return i+j;
	}

}
