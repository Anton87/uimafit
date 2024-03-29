/*
 Copyright 2009-2010	Regents of the University of Colorado.
 All rights reserved.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.uimafit.factory;

import static org.uimafit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.uimafit.factory.TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.impl.XCASDeserializer;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.xml.sax.SAXException;

/**
 * @author Steven Bethard, Philip Ogren
 * @author Richard Eckart de Castilho
 */
public final class JCasFactory {
	private JCasFactory() {
		// This class is not meant to be instantiated
	}

	/**
	 * Creates a new JCas for the automatically derived type system. See
	 * {@link TypeSystemDescriptionFactory#createTypeSystemDescription()}
	 */
	public static JCas createJCas() throws UIMAException {
		return CasCreationUtils.createCas(createTypeSystemDescription(), null, null).getJCas();

	}

	/**
	 * Creates a new JCas from type system descriptor files found by name
	 */
	public static JCas createJCas(String... typeSystemDescriptorNames) throws UIMAException {
		return CasCreationUtils.createCas(createTypeSystemDescription(typeSystemDescriptorNames),
				null, null).getJCas();
	}

	/**
	 * Creates a new JCas from type system descriptor files.
	 *
	 * @param typeSystemDescriptorPaths
	 *            paths to type system descriptor files
	 */
	public static JCas createJCasFromPath(String... typeSystemDescriptorPaths) throws UIMAException {
		return createJCas(createTypeSystemDescriptionFromPath(typeSystemDescriptorPaths));
	}

	/**
	 * Create a new JCas with a new type system defined by the classes provided
	 * 
	 * @param typeSystemClasses
	 *            should be classes generated by JCasGen
	 *            
	 * @deprecated This is not the method you want to use. If you declare only one type per XML type
	 *             descriptor, you'll end up with LOTS of these. Use
	 *             {@link TypeSystemDescriptionFactory#createTypeSystemDescription(String...)} or
	 *             automatic type system detection with
	 *             {@link TypeSystemDescriptionFactory#createTypeSystemDescription()}.
	 */
	@Deprecated
	public static JCas createJCas(Class<?>... typeSystemClasses) throws UIMAException {
		return createJCas(createTypeSystemDescription(typeSystemClasses));
	}

	/**
	 * Create a new JCas for the given type system description
	 */
	public static JCas createJCas(TypeSystemDescription typeSystemDescription) throws UIMAException {
		return CasCreationUtils.createCas(typeSystemDescription, null, null).getJCas();
	}

	/**
	 * This method creates a new JCas and loads the contents of an XMI file into it.
	 *
	 * @param xmiFileName
	 *            a file name for an XMI file.
	 */
	public static JCas createJCas(String xmiFileName, TypeSystemDescription typeSystemDescription)
			throws UIMAException, IOException {
		return createJCas(xmiFileName, typeSystemDescription, true);
	}

	/**
	 * This method creates a new JCas and loads the contents of an XMI or XCAS file into it.
	 *
	 * @param xmlFileName
	 *            a file name for an XML file containing XMI or XCAS content.
	 * @param isXmi
	 *            if true, than assume XMI format. Otherwise, assume XCAS.
	 */
	public static JCas createJCas(String xmlFileName, TypeSystemDescription typeSystemDescription,
			boolean isXmi) throws UIMAException, IOException {
		JCas jCas = createJCas(typeSystemDescription);
		loadJCas(jCas, xmlFileName, isXmi);
		return jCas;
	}

	/**
	 * This method passes through to {@link #loadJCas(JCas, String, boolean)}
	 *
	 * @param xmiFileName
	 *            a file name for an XMI file
	 */
	public static void loadJCas(JCas jCas, String xmiFileName) throws IOException {
		loadJCas(jCas, xmiFileName, true);
	}

	/**
	 * This method takes a JCas, resets it, and loads it with the contents of an XMI or XCAS file
	 *
	 * @param xmlFileName
	 *            a file name for an XML file containing XMI or XCAS content.
	 * @param isXmi
	 *            if true, than assume XMI format. Otherwise, assume XCAS.
	 */
	public static void loadJCas(JCas jCas, String xmlFileName, boolean isXmi) throws IOException {
		FileInputStream inputStream = new FileInputStream(xmlFileName);
		loadJCas(jCas, inputStream, isXmi);
	}

	/**
	 * This method passes through to {@link #loadJCas(JCas, InputStream, boolean)}
	 */
	public static void loadJCas(JCas jCas, InputStream xmiInputStream) throws IOException {
		loadJCas(jCas, xmiInputStream, true);
	}

	/**
	 * This method takes a JCas, resets it, and loads it with the contents of an XMI or XCAS input
	 * stream
	 *
	 * @param xmlInputStream
	 *            should contain the contents of a serialized CAS in the form of XMI or XCAS XML
	 * @param isXmi
	 *            if true, than assume XMI format. Otherwise, assume XCAS.
	 */
	public static void loadJCas(JCas jCas, InputStream xmlInputStream, boolean isXmi)
			throws IOException {
		jCas.reset();
		try {
			CAS cas = jCas.getCas();
			if (isXmi) {
				XmiCasDeserializer.deserialize(xmlInputStream, cas);
			}
			else {
				XCASDeserializer.deserialize(xmlInputStream, cas);
			}
		}
		catch (SAXException e) {
			IOException ioe = new IOException(e.getMessage());
			ioe.initCause(e);
			throw ioe; // NOPMD
			// If we were using Java 1.6 and add the wrapped exception to the IOException
			// constructor, we would not get a warning here
		}
		finally {
			IOUtils.closeQuietly(xmlInputStream);
		}
	}
}
