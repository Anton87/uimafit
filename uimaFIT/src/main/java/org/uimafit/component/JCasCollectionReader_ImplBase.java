/*
 Copyright 2010
 Ubiquitous Knowledge Processing (UKP) Lab
 Technische Universitaet Darmstadt
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
package org.uimafit.component;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.initialize.ConfigurationParameterInitializer;
import org.uimafit.component.initialize.ExternalResourceInitializer;
import org.uimafit.descriptor.OperationalProperties;

/**
 * Base class for JCas collection readers which initializes itself based on annotations.
 *
 * @author Richard Eckart de Castilho
 */
@OperationalProperties(outputsNewCases=true)
public abstract class JCasCollectionReader_ImplBase extends CollectionReader_ImplBase {
	// This method should not be overwritten. Overwrite initialize(UimaContext) instead.
	@Override
	public final void initialize() throws ResourceInitializationException {
		ConfigurationParameterInitializer.initialize(this, getUimaContext());
		ExternalResourceInitializer.initialize(getUimaContext(), this);
		initialize(getUimaContext());
	}

	/**
	 * This method should be overwritten by subclasses.
	 *
	 * @param context
	 * @throws ResourceInitializationException
	 */
	public void initialize(UimaContext context) throws ResourceInitializationException {
		// Nothing by default
	}

	// This method should not be overwritten. Overwrite getNext(JCas) instead.
	public final void getNext(CAS cas) throws IOException, CollectionException {
		try {
			getNext(cas.getJCas());
		}
		catch (CASException e) {
			throw new CollectionException(e);
		}
	}

	/**
	 * Subclasses should implement this method rather than {@link #getNext(CAS)}
	 *
	 * @param jCas
	 * @throws IOException
	 * @throws CollectionException
	 */
	public abstract void getNext(JCas jCas) throws IOException, CollectionException;

	public void close() throws IOException {
		// Do nothing per default
	}
}
