package com.mastrodaro.exporters;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mastrodaro.parser.OutputFormat;

@Singleton
public class ExporterProvider {

	private static final Logger logger = LogManager.getLogger(ExporterProvider.class);

	private Map<OutputFormat, Exporter> exporters;

	@Inject
	public ExporterProvider() {
		// Default constructor logic, if needed
	}

	/**
	 * Bootstraps the provider with basic exporters: XML and CSV
	 */

	public void bootstrap() {
		exporters = new HashMap<>(2);
		exporters.put(OutputFormat.XML, new XMLExporter());
		exporters.put(OutputFormat.CSV, new CSVExporter());
		logger.debug("ExporterProvider bootstraped.");
	}

	/**
	 * Gets exporter.
	 * 
	 * @param format format for exporter
	 * @return Returns exporter for given format
	 */
	public Exporter getExporter(OutputFormat format) {
		return exporters.get(format);
	}

	/**
	 * Checks numbers of registered exporters
	 * 
	 * @return numbers of registered exporters
	 */
	public long registeredExportersSize() {
		return exporters.size();
	}

	/**
	 * Register an exporter
	 * 
	 * @param format format to register exported under
	 * @param writer exporter object
	 */
	public void registerExporter(OutputFormat format, Exporter writer) {
		if (exporters.containsKey(format) || exporters.containsValue(writer)) {
			throw new IllegalArgumentException("Exporter already registered!");
		}
		exporters.put(format, writer);
		logger.debug("New writer registered for format: {}", format);
	}

	/**
	 * Removes exporter from registered list
	 * 
	 * @param format format of exported to be unregistered
	 */
	public void unregisterExporter(OutputFormat format) {
		exporters.remove(format);
	}

}
