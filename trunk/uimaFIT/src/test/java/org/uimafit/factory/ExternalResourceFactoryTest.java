/*
 Copyright 2009
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

package org.uimafit.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.bindResource;

import java.io.File;
import java.net.URI;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.Resource_ImplBase;
import org.apache.uima.resource.SharedResourceObject;
import org.apache.uima.resource.metadata.ResourceManagerConfiguration;
import org.junit.Test;
import org.uimafit.ComponentTestBase;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.descriptor.ExternalResource;

/**
 * Test case for {@link ExternalResource} annotations.
 * 
 * @author Richard Eckart de Castilho
 */
public class ExternalResourceFactoryTest extends ComponentTestBase {
	private static final String EX_URI = "http://dum.my";
	private static final String EX_FILE = "src/test/resources/data/html/1.html";

	@Test
	public void testScanBind() throws Exception {
		AnalysisEngineDescription desc = createPrimitiveDescription(DummyAE.class,
				typeSystemDescription);

		bindResource(desc, DummyResource.class);
		bindResource(desc, DummySharedResourceObject.class, EX_URI);
		// An undefined URL may be used if the specified file/remote URL does not exist or if
		// the network is down.
		bindResource(desc, DummyAE.RES_SOME_URI, new File(EX_FILE).toURI().toURL());
		bindResource(desc, DummyAE.RES_SOME_FILE, new File(EX_FILE));

		ResourceManagerConfiguration resCfg = desc.getResourceManagerConfiguration();
		assertEquals(4, resCfg.getExternalResourceBindings().length);

		AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(desc);
		assertNotNull(ae);

		ae.process(ae.newJCas());
	}

	public static final class DummyAE extends JCasAnnotator_ImplBase {
		@ExternalResource
		DummyResource r;

		@ExternalResource
		DummySharedResourceObject sharedObject;

		static final String RES_SOME_URI = "SomeUrl";
		@ExternalResource(key = RES_SOME_URI)
		DataResource someUrl;

		static final String RES_SOME_FILE = "SomeFile";
		@ExternalResource(key = RES_SOME_FILE)
		DataResource someFile;

		@Override
		public void process(JCas aJCas) throws AnalysisEngineProcessException {
			assertNotNull(r);
			assertNotNull(sharedObject);
			assertEquals(EX_URI, sharedObject.getUrl().toString());
			assertNotNull(someUrl);
			assertEquals(new File(EX_FILE).toURI().toString(), someUrl.getUri().toString());
			assertTrue(someFile.getUrl().toString().startsWith("file:"));
			assertTrue("URL [" + someFile.getUrl() + "] should end in [" + EX_FILE + "]", someFile
					.getUrl().toString().endsWith(EX_FILE));
		}
	}

	public static final class DummyResource extends Resource_ImplBase {
		// Nothing
	}

	public static final class DummySharedResourceObject implements SharedResourceObject {
		private URI uri;

		public void load(DataResource aData) throws ResourceInitializationException {
			assertEquals(EX_URI, aData.getUri().toString());
			uri = aData.getUri();
		}

		public URI getUrl() {
			return uri;
		}
	}
}
