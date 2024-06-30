package com.projeto.agenda;

import javax.swing.SwingUtilities;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.projeto.view.MainInterface;

@SpringBootApplication
public class AgendaApplication {

	public static void main(String[] args) {
		//SpringApplication.run(AgendaApplication.class, args);
		ConfigurableApplicationContext context = new SpringApplicationBuilder(AgendaApplication.class)
				.headless(false)
				.run(args);
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainInterface gui;
				try {
					gui = new MainInterface(context);
					gui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
