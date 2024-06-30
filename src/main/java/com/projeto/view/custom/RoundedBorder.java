package com.projeto.view.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.Border;

public class RoundedBorder implements Border {
	private Color color;
	private int radius;

	public RoundedBorder(Color color, int radius) {
		this.color = color;
		this.radius = radius;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(radius, radius, radius, radius);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}
}