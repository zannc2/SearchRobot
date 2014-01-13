package test.frontend.impl;

import javax.swing.JFrame;
import static org.junit.Assert.*;
import org.junit.Test;

import frontend.impl.SearchSettingsDialog;

public class SearchSettingsDialogTest {

	@Test
	public void testGetChosenSpeed() {
		JFrame jf = new JFrame();
		SearchSettingsDialog sd1 = new SearchSettingsDialog(jf, 200, true);
		assertTrue(sd1.isShowGrid());
		assertTrue(sd1.getChosenSpeed() == 200);
		assertFalse(sd1.getChosenSpeed() == 50);
		
		SearchSettingsDialog sd2 = new SearchSettingsDialog(jf, 50, false);
		assertFalse(sd2.isShowGrid());
		
	}

}
