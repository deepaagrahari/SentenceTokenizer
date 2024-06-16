package com.mastrodaro;

import java.io.IOException;

import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.parser.OutputFormat;
import com.mastrodaro.parser.SentenceParser;

public class Application {

	@Inject
	private ExporterProvider provider;

	@Inject
	private SentenceParser parser;

	public static void main(String[] args) throws IOException, XMLStreamException {
		if (args.length == 0) {
			System.err.println("Please provide the desired output format (xml|csv)");
			System.exit(0);
		}

		OutputFormat format = OutputFormat.XML;
		try {
			format = OutputFormat.valueOf(args[0].toUpperCase());
		} catch (IllegalArgumentException ex) {
			System.err.println("Please provide valid output format (xml|csv)");
			System.exit(0);
		}

		Injector injector = Guice.createInjector(new MyModule());
		Application main = injector.getInstance(Application.class);

		main.run(format);
	}

	void run(OutputFormat format) {

		provider.bootstrap();
		parser.parse(System.in, System.out, format);
		System.exit(-1);
	}
}
