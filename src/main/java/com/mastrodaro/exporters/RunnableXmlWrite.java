package com.mastrodaro.exporters;

import javax.xml.stream.XMLStreamException;

/**
 * Interface to deal with XMLStreamException inside lambdas
 */
@FunctionalInterface
public interface RunnableXmlWrite {

    void run() throws XMLStreamException;
}
