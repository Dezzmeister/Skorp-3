package com.dezzy.skorp3.skorp3D.render;

import java.awt.Container;

@FunctionalInterface
public interface SkorpPanelSupplier {
	public SkorpPanel createPanel(Container pane, int width, int height);
}
